package com.haitai.haitaitv.module.back.system.menu;

import com.haitai.haitaitv.common.cache.ShiroCache;
import com.haitai.haitaitv.common.entity.StbConfig;
import com.haitai.haitaitv.common.entity.SysMenu;
import com.haitai.haitaitv.common.repository.StbConfigDao;
import com.haitai.haitaitv.common.repository.SysMenuDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.common.BaseBackController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author liuzhou
 *         create at 2017-04-11 17:24
 */
@ControllerBind(controllerKey = "/system/menu")
@RequiresPermissions("system/menu/list:*")
public class MenuController extends BaseBackController {

    private SysMenuDao dao = sm.getMapper(SysMenuDao.class);

    @RequiresPermissions("system/menu/list:view")
    public void list() {
        SysMenu attr = getBean(SysMenu.class, "attr");
        PageQuery<SysMenu> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("sort, id desc");
        }
        dao.page(pageQuery);
        // 父菜单名map
        SysMenu template = new SysMenu();
        template.setParentId(0);
        template.setType(1);
        List<SysMenu> roots = dao.template(template);
        Map<Integer, String> meName = roots.stream()
                .collect(HashMap::new, (map, menu) -> map.put(menu.getId(), menu.getName()), HashMap::putAll);
        meName.put(0, "根目录");

        setAttr("page", pageQuery);
        setAttr("attr", attr);
        setAttr("meName", meName);
        render("main.html");
    }

    @RequiresPermissions("system/menu/list:view")
    public void view() {
        SysMenu model = dao.unique(getParaToInt());
        if (model.getParentId() == 0) {
            model.set("parentName", "根目录");
        } else {
            SysMenu parent = dao.unique(model.getParentId());
            model.set("parentName", parent.getName());
        }
        setAttr("model", model);
        render("view.html");
    }

    public void edit() {
        Integer id = getParaToInt();
        SysMenu model;
        if (id != null) {
            model = dao.unique(id);
            if (model.getType() == 2) {
                // 渠道权限情形，走单独的页面
                setAttr("model", model);
                render("editOperator.html");
                return;
            }
        } else {
            model = new SysMenu();
        }
        SysMenu template = new SysMenu();
        template.setStatus(1);
        template.setParentId(0);
        template.setType(1);
        List<SysMenu> roots = dao.template(template);
        setAttr("roots", roots);
        setAttr("model", model);
        render("edit.html");
    }

    public void addOperator() {
        SysMenu model = new SysMenu();
        setAttr("model", model);
        render("editOperator.html");
    }
    
    public void search() {
        StbConfig attr = getBean(StbConfig.class, "attr");
        PageQuery<StbConfig> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("update_time desc");
        }
        sm.getMapper(StbConfigDao.class).page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("search.html");
    }

    public void save() {
        SysMenu model = getBean(SysMenu.class, "model");
        // 根目录级别为1
        if (model.getParentId() == 0) {
            model.setLevel(1);
        } else {
            model.setLevel(2);
        }
        if (model.getId() != null) {
            dao.updateTemplateById(model);
        } else {
            model.setCreateId(getSessionUser().getId());
            model.setCreateTime(LocalDateTime.now());
            dao.insertTemplate(model);
        }
        // 清shiro缓存
        ShiroCache.clearCache();
        dwzSuccess("保存成功");
    }

    public void delete() {
        Integer id = getParaToInt();
        SysMenu template = new SysMenu();
        template.setParentId(id);
        long count = dao.templateCount(template);
        if (count > 0) {
            dwzFail("此菜单下有子菜单，不允许删除");
            return;
        }
        dao.deleteById(id);
        // 清shiro缓存
        ShiroCache.clearCache();
        dwzSuccessNoClose("删除成功");
    }

}
