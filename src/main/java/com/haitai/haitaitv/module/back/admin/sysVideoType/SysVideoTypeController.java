package com.haitai.haitaitv.module.back.admin.sysVideoType;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import com.haitai.haitaitv.common.entity.SysVideoType;
import com.haitai.haitaitv.common.repository.SysVideoTypeDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.common.BaseBackController;

@ControllerBind(controllerKey = "/admin/sysVideoType")
@RequiresPermissions("admin/sysVideoType/list:*")
public class SysVideoTypeController extends BaseBackController{
    
    private SysVideoTypeDao dao = sm.getMapper(SysVideoTypeDao.class);
    
    @RequiresPermissions("admin/sysVideoType/list:view")
    public void list() {
        SysVideoType attr = getBean(SysVideoType.class, "attr");
        PageQuery<SysVideoType> pageQuery = getPageQuery(attr);
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }
  
    public void add() {
        SysVideoType model = new SysVideoType();
        setAttr("model", model);
        render("edit.html");
    }
    
    public void edit() {
        SysVideoType model = dao.single(getPara());
        setAttr("model", model);
        render("edit.html");
    }
    
    public void save() {
        SysVideoType model = getBean(SysVideoType.class, "model");
        if(model == null){
            dwzFail("不能为空");
        }
        new SysVideoTypeService().save(model);
        dwzSuccess("保存成功", "sysVideoType");
    }
    
    public void delete() {
        String[] ids = getParaValues("ids");
        if (ids == null || ids.length == 0) {
            dwzFail("没有勾选商品，无法删除");
            return;
        }
        int count = new SysVideoTypeService().delete(ids);
        if (count == 0) {
            dwzFail("选中的商品均处于发布上架状态或正在被展品使用，无法删除");
            return;
        }
        dwzSuccessNoClose("成功删除了" + count + "个商品");
    }
}
