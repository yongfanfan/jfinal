package com.haitai.haitaitv.module.back.admin.sysGoods;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import com.haitai.haitaitv.common.entity.SysGoods;
import com.haitai.haitaitv.common.entity.SysGoodsType;
import com.haitai.haitaitv.common.entity.SysSupplier;
import com.haitai.haitaitv.common.repository.SysGoodsDao;
import com.haitai.haitaitv.common.repository.SysGoodsTypeDao;
import com.haitai.haitaitv.common.repository.SysSupplierDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.util.UploadUtil;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey = "/admin/sysGoods")
@RequiresPermissions("admin/sysGoods/list:*")
public class SysGoodsController extends BaseBackController{
    
    private SysGoodsDao dao = sm.getMapper(SysGoodsDao.class);
    
    @RequiresPermissions("admin/sysGoods/list:view")
    public void list() {
        SysGoods attr = getBean(SysGoods.class, "attr");
        PageQuery<SysGoods> pageQuery = getPageQuery(attr);
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        setAttr("types", sm.getMapper(SysGoodsTypeDao.class).all());
        setAttr("suppliers", sm.getMapper(SysSupplierDao.class).all());
        render("main.html");
    }
    
    public void edit() {
        SysGoods model = dao.single(getParaToInt());
        setAttr("model", model);
        setAttr("types", sm.getMapper(SysGoodsTypeDao.class).all());
        setAttr("suppliers", sm.getMapper(SysSupplierDao.class).all());
        render("edit.html");
    }

    public void saveEdit() {
        UploadFile uploadImage = getFile("image", UploadUtil.UPLOAD_TMP_PATH, UploadUtil.UPLOAD_MAX);
        SysGoods model = getBean(SysGoods.class, "model");
        if (model.getId() == null) {
            // 不可能进入这里
            dwzSuccess("没有id，保存失败，请重新编辑");
            return;
        }
        new SysGoodsService().saveEdit(model, uploadImage);
        dwzSuccess("保存成功", "sysGoods");
    }

    public void delete() {
        String[] ids = getParaValues("ids");
        if (ids == null || ids.length == 0) {
            dwzFail("没有勾选商品，无法删除");
            return;
        }
        int count = new SysGoodsService().delete(ids);
        if (count == 0) {
            dwzFail("选中的商品均处于发布上架状态或正在被展品使用，无法删除");
            return;
        }
        dwzSuccessNoClose("成功删除了" + count + "个商品");
    }
    
    public void excel() {
        render("excel.html");
    }
    
    public void saveExcel() {
        UploadFile uploadImage = getFile("excel", UploadUtil.UPLOAD_TMP_PATH, UploadUtil.UPLOAD_MAX);
        new SysGoodsService().saveExcel(uploadImage);
        dwzSuccess("导入成功", "sysGoods");
    }
}
