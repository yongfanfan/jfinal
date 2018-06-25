package com.haitai.haitaitv.module.back.admin.supplier;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import com.haitai.haitaitv.common.entity.SysSupplier;
import com.haitai.haitaitv.common.repository.SysSupplierDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.common.BaseBackController;

@ControllerBind(controllerKey = "/admin/sysSupplier")
@RequiresPermissions("admin/sysSupplier/list:*")
public class SysSupplierController extends BaseBackController{
    
    private SysSupplierDao dao = sm.getMapper(SysSupplierDao.class);
    
    @RequiresPermissions("admin/sysSupplier/list:view")
    public void list() {
        SysSupplier attr = getBean(SysSupplier.class, "attr");
        PageQuery<SysSupplier> pageQuery = getPageQuery(attr);
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }
    public void add() {
        SysSupplier model = new SysSupplier();
        setAttr("model", model);
        render("edit.html");
    }
    
    public void edit() {
        SysSupplier model = dao.single(getPara());
        setAttr("model", model);
        render("edit.html");
    }
    
    public void save() {
        SysSupplier model = getBean(SysSupplier.class, "model");
        if(model == null){
            dwzFail("不能为空");
        }
        new SysSupplierService().save(model);
        dwzSuccess("保存成功", "sysSupplier");
    }
    
    public void delete() {
        String[] ids = getParaValues("ids");
        if (ids == null || ids.length == 0) {
            dwzFail("没有勾选，无法删除");
            return;
        }
        int count = new SysSupplierService().delete(ids);
        if (count == 0) {
            dwzFail("无法删除");
            return;
        }
        dwzSuccessNoClose("成功删除了" + count + "个商品");
    }
    
}
