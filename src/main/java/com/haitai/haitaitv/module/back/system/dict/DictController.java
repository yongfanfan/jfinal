package com.haitai.haitaitv.module.back.system.dict;

import com.haitai.haitaitv.common.cache.DictCache;
import com.haitai.haitaitv.common.entity.SysDict;
import com.haitai.haitaitv.common.entity.SysDictDetail;
import com.haitai.haitaitv.common.repository.SysDictDao;
import com.haitai.haitaitv.common.repository.SysDictDetailDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author liuzhou
 *         create at 2017-04-12 10:21
 */
@ControllerBind(controllerKey = "/system/dict")
@RequiresPermissions("system/dict/list:*")
public class DictController extends BaseBackController {

    private SysDictDetailDao dao = sm.getMapper(SysDictDetailDao.class);

    @RequiresPermissions("system/dict/list:view")
    public void list() {
        SysDictDetail attr = getBean(SysDictDetail.class, "attr");
        PageQuery<SysDictDetail> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("dict_type, id desc");
        }
        dao.page(pageQuery);
        // 数据字典类型map
        List<SysDict> dicts = sm.getMapper(SysDictDao.class).all();
        Map<String, String> diName = dicts.stream()
                .collect(HashMap::new, (map, dict) -> map.put(dict.getDictType(), dict.getDictName()), HashMap::putAll);

        setAttr("page", pageQuery);
        setAttr("attr", attr);
        setAttr("diName", diName);
        render("main.html");
    }

    @RequiresPermissions("system/dict/list:view")
    public void view() {
        SysDictDetail model = dao.unique(getParaToInt());
        SysDict template = new SysDict();
        template.setDictType(model.getDictType());
        SysDict dict = sm.getMapper(SysDictDao.class).templateOne(template);
        setAttr("dictType", dict.getDictName());
        setAttr("model", model);
        render("view.html");
    }

    public void edit() {
        Integer id = getParaToInt();
        SysDictDetail model;
        if (id != null) {
            model = dao.unique(id);
        } else {
            model = new SysDictDetail();
        }
        List<SysDict> dicts = sm.getMapper(SysDictDao.class).all();
        setAttr("model", model);
        setAttr("dicts", dicts);
        render("edit.html");
    }

    public void save() {
        SysDictDetail model = getBean(SysDictDetail.class, "model");
        if (model.getId() != null) {
            dao.updateTemplateById(model);
        } else {
            model.setCreateId(getSessionUser().getId());
            model.setCreateTime(LocalDateTime.now());
            dao.insertTemplate(model);
        }
        // 初始化数据字典缓存
        DictCache.init();
        dwzSuccess("保存成功");
    }

    public void delete() {
        dao.deleteById(getParaToInt());
        // 初始化数据字典缓存
        DictCache.init();
        dwzSuccessNoClose("删除成功");
    }

    public void editDict() {
        String type = getPara("dictType");
        SysDict model;
        if (StrUtil.isNotEmpty(type)) {
            SysDict template = new SysDict();
            template.setDictType(type);
            model = sm.getMapper(SysDictDao.class).templateOne(template);
        } else {
            model = new SysDict();
        }
        setAttr("model", model);
        render("editDict.html");
    }

    public void saveDict() {
        SysDict model = getBean(SysDict.class, "model");
        SysDictDao dictDao = sm.getMapper(SysDictDao.class);
        if (model.getId() != null) {
            dictDao.updateTemplateById(model);
        } else {
            dictDao.insertTemplate(model);
        }
        dwzSuccess("保存成功");
    }

    public void deleteDict() {
        sm.getMapper(SysDictDao.class).deleteById(getParaToInt());
        dwzSuccess("删除成功");
    }

}
