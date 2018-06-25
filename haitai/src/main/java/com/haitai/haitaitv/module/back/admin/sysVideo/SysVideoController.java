package com.haitai.haitaitv.module.back.admin.sysVideo;


import java.math.BigDecimal;
import java.util.List;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;



import com.haitai.haitaitv.common.entity.SysVideo;
import com.haitai.haitaitv.common.entity.SysVideoType;
import com.haitai.haitaitv.common.repository.SysVideoDao;
import com.haitai.haitaitv.common.repository.SysVideoTypeDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.util.UploadUtil;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey = "/admin/sysVideo")
@RequiresPermissions("admin/sysVideo/list:*")
public class SysVideoController extends BaseBackController{
    
    private SysVideoDao dao = sm.getMapper(SysVideoDao.class);
    
    @RequiresPermissions("admin/sysVideo/list:view")
    public void list() {
        SysVideo attr = getBean(SysVideo.class, "attr");
        PageQuery<SysVideo> pageQuery = getPageQuery(attr);
        dao.page(pageQuery);
        pageQuery.getList().forEach(v -> {
           Long i = v.getTotalTime();
           Long j = 60L;
           Long t = i%j;
           BigDecimal end = new BigDecimal(i).divide(new BigDecimal(j), 0, BigDecimal.ROUND_DOWN);
           v.setDesc(end + "分钟" + t +"秒");
        });
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        List<SysVideoType> typeList = sm.getMapper(SysVideoTypeDao.class).all();
        setAttr("typeList", typeList);
        render("main.html");
    }
    
    public void edit() {
        SysVideo model = dao.single(getPara());
        SysVideoType type = new SysVideoType();
        type.setDelFlag(2);
        List<SysVideoType> typeList = sm.getMapper(SysVideoTypeDao.class).template(type);
        setAttr("model", model);
        setAttr("typeList", typeList);
        render("edit.html");
    }
    
    public void saveEdit() {
        SysVideo model = getBean(SysVideo.class, "model");
        if(model == null){
            dwzFail("不能为空");
        }
        model.setId(getParaToLong());
        dao.updateTemplateById(model);
        dwzSuccess("保存成功", "sysVideo");
    }
    
    public void excel() {
        render("excel.html");
    }
    
    public void saveExcel() {
        UploadFile uploadImage = getFile("excel", UploadUtil.UPLOAD_TMP_PATH, UploadUtil.UPLOAD_MAX);
        new SysVideoService().saveExcel(uploadImage);
        dwzSuccess("导入成功", "sysVideo");
    }
  
}
