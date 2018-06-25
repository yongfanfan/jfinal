package com.haitai.haitaitv.module.back.admin.home;

import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.jfinal.interceptor.WholePageInterceptor;
import com.haitai.haitaitv.module.back.admin.index.IndexController;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.haitai.haitaitv.module.back.common.CommonDTO;
import com.jfinal.aop.Before;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author liuzhou
 *         create at 2017-03-30 16:20
 */
@ControllerBind(controllerKey = "admin/home")
public class HomeController extends BaseBackController {

    public void welcome() {
        SysUser user = getSessionUser();
        if (user == null) {
            setAttr("version", ConfigConsts.PROJECT_VERSION);
            redirect(IndexController.LOGIN_PAGE);
            return;
        }
        setAttr("user", user);
        render("welcome.html");
    }

   

}
