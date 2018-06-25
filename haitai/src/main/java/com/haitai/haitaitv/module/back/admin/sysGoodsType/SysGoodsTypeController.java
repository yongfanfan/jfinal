package com.haitai.haitaitv.module.back.admin.sysGoodsType;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import com.haitai.haitaitv.common.entity.SysGoodsType;
import com.haitai.haitaitv.common.entity.SysVideoType;
import com.haitai.haitaitv.common.repository.SysGoodsTypeDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.admin.sysVideoType.SysVideoTypeService;
import com.haitai.haitaitv.module.back.common.BaseBackController;

@ControllerBind(controllerKey = "/admin/sysGoodsType")
@RequiresPermissions("admin/sysGoodsType/list:*")
public class SysGoodsTypeController extends BaseBackController{
    
    private SysGoodsTypeDao dao = sm.getMapper(SysGoodsTypeDao.class);
    
    @RequiresPermissions("admin/sysGoodsType/list:view")
    public void list() {
        SysGoodsType attr = getBean(SysGoodsType.class, "attr");
        PageQuery<SysGoodsType> pageQuery = getPageQuery(attr);
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }
    public void add() {
        SysGoodsType model = new SysGoodsType();
        setAttr("model", model);
        render("edit.html");
    }
    
    public void edit() {
        SysGoodsType model = dao.single(getPara());
        setAttr("model", model);
        render("edit.html");
    }
    
    public void save() {
        SysGoodsType model = getBean(SysGoodsType.class, "model");
        if(model == null){
            dwzFail("不能为空");
        }
        new SysGoodsTypeService().save(model);
        dwzSuccess("保存成功", "sysGoodsType");
    }
    
    public void delete() {
        String[] ids = getParaValues("ids");
        if (ids == null || ids.length == 0) {
            dwzFail("没有勾选，无法删除");
            return;
        }
        int count = new SysGoodsTypeService().delete(ids);
        if (count == 0) {
            dwzFail("无法删除");
            return;
        }
        dwzSuccessNoClose("成功删除了" + count + "个商品");
    }
    
}
