package com.haitai.haitaitv.module.back.system.role;

import com.haitai.haitaitv.common.cache.ShiroCache;
import com.haitai.haitaitv.common.entity.SysMenu;
import com.haitai.haitaitv.common.entity.SysRole;
import com.haitai.haitaitv.common.entity.SysRoleMenu;
import com.haitai.haitaitv.common.repository.SysMenuDao;
import com.haitai.haitaitv.common.repository.SysRoleDao;
import com.haitai.haitaitv.common.repository.SysRoleMenuDao;
import com.haitai.haitaitv.common.repository.SysUserRoleDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.haitai.haitaitv.module.back.system.user.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author liuzhou
 *         create at 2017-04-11 11:44
 */
@ControllerBind(controllerKey = "/system/role")
@RequiresPermissions("system/role/list:*")
public class RoleController extends BaseBackController {

    private SysRoleDao dao = sm.getMapper(SysRoleDao.class);

    @RequiresPermissions("system/role/list:view")
    public void list() {
        SysRole attr = getBean(SysRole.class, "attr");
        PageQuery<SysRole> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("sort, id desc");
        }
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }

    @RequiresPermissions("system/role/list:view")
    public void view() {
        SysRole model = dao.unique(getParaToInt());
        setAttr("model", model);
        render("view.html");
    }

    public void edit() {
        Integer id = getParaToInt();
        SysRole model;
        if (id != null) {
            model = dao.unique(getParaToInt());
        } else {
            model = new SysRole();
        }
        setAttr("model", model);
        render("edit.html");
    }

    public void save() {
        SysRole model = getBean(SysRole.class, "model");
        if (model.getId() != null) {
            dao.updateTemplateById(model);
        } else {
            model.setCreateId(getSessionUser().getId());
            model.setCreateTime(LocalDateTime.now());
            dao.insertTemplate(model);
        }
        dwzSuccess("保存成功");
    }

    public void delete() {
        Integer id = getParaToInt();
        // 删除授权
        sm.getMapper(SysUserRoleDao.class).deleteByRoleId(id);
        sm.getMapper(SysRoleMenuDao.class).deleteByRoleId(id);
        dao.deleteById(id);
        // 清shiro缓存
        ShiroCache.clearCache();
        dwzSuccessNoClose("删除成功");
    }

    public void editAuth() {
        Integer roleId = getParaToInt();
        List<SysMenu> allMenu = sm.getMapper(SysMenuDao.class).listValid();
        Map<SysMenu, List<SysMenu>> menuMap = UserService.INSTANCE.getSysMenuListMap(allMenu);
        SysRoleMenu template = new SysRoleMenu();
        template.setRoleId(roleId);
        List<SysRoleMenu> roleMenus = sm.getMapper(SysRoleMenuDao.class).template(template);
        // 分别提取查看权限和修改权限的菜单id
        Function<SysRoleMenu, String> mapper = srm -> srm.getMenuId().toString();
        Collector<CharSequence, ?, String> collector = Collectors.joining(",");
        String views = roleMenus.stream().filter(srm -> srm.getCanUpdate() == 0).map(mapper).collect(collector);
        String updates = roleMenus.stream().filter(srm -> srm.getCanUpdate() == 1).map(mapper).collect(collector);
        setAttr("roleId", roleId);
        setAttr("menuMap", menuMap);
        setAttr("views", views);
        setAttr("updates", updates);
        render("editAuth.html");
    }

    public void saveAuth() {
        Integer roleId = getParaToInt("roleId");
        String[] views = getPara("views").split(",");
        String[] updates = getPara("updates").split(",");
        SysRoleMenuDao roleMenuDao = sm.getMapper(SysRoleMenuDao.class);
        roleMenuDao.deleteByRoleId(roleId);
        List<SysRoleMenu> list = new ArrayList<>();
        for (String view : views) {
            if ("".equals(view)) {
                continue;
            }
            SysRoleMenu roleMenu = new SysRoleMenu(null, roleId, Integer.valueOf(view), 0);
            list.add(roleMenu);
        }
        for (String update : updates) {
            if ("".equals(update)) {
                continue;
            }
            SysRoleMenu roleMenu = new SysRoleMenu(null, roleId, Integer.valueOf(update), 1);
            list.add(roleMenu);
        }
        roleMenuDao.insertBatch(list);
        // 清shiro缓存
        ShiroCache.clearCache();
        dwzSuccess("保存成功");
    }
}
