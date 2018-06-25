package com.haitai.haitaitv.module.back.admin.person;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.cache.ShiroCache;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.component.constant.OtherConsts;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.shiro.credentials.PasswordUtil;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.back.common.BaseBackController;

import java.util.Objects;

/**
 * 个人信息
 *
 * @author liuzhou
 *         create at 2017-04-17 16:54
 */
@ControllerBind(controllerKey = "/admin/person")
public class PersonController extends BaseBackController {

    public void index() {
        SysUser model = getSessionUser();
        setAttr("model", model);
        render("main.html");
    }

    public void save() {
        SysUser user = getSessionUser();
        SysUser model = getBean(SysUser.class, "model");
        if (!Objects.equals(user.getId(), model.getId())) {
            dwzFail("提交数据错误！");
            return;
        }
        // 第三方用户不需要密码
        if (user.getType() != 4) {
            String oldPassword = getPara("oldPassword");
            String newPassword = getPara("newPassword");
            if (!PasswordUtil.isMatch(user, oldPassword)) {
                dwzFail("密码错误");
                return;
            }
            if (OtherConsts.DEFAULT_PASSWORD.equals(newPassword)) {
                dwzFail("新密码不得为初始密码");
                return;
            }
            if (StrUtil.isNotEmpty(newPassword)) {
                model.setSalt(PasswordUtil.generateSalt());
                model.setPassword(PasswordUtil.generatePassword(user.getUsername(), newPassword, model.getSalt()));
            }
        }
        if (StrUtil.isNotEmpty(model.getEmail()) && model.getEmail().contains("@")) {
            dwzFail("email格式错误！");
            return;
        }
        sm.getMapper(SysUserDao.class).updateTemplateById(model);
        // 需要清除sessionUser，由MyUserFilter重设
        getSession().removeAttribute(SessionConsts.USER);
        ShiroCache.clearCache();
        dwzSuccess("保存成功！");
    }

    public void validateDefaultPassword() {
        SysUser user = getSessionUser();
        boolean isDefault = false;
        // 第三方用户不需要密码
        if (user.getType() != 4) {
            isDefault = PasswordUtil.isDefault(user);
        }
        JSONObject json = new JSONObject();
        json.put("isDefault", isDefault);
        renderJson(json);
    }

    public void editDefaultPassowrd() {
        SysUser model = getSessionUser();
        setAttr("model", model);
        render("defaultPassword.html");
    }

    public void savePassword() {
        SysUser user = getSessionUser();
        SysUser model = getBean(SysUser.class, "model");
        if (!Objects.equals(user.getId(), model.getId())) {
            dwzFail("提交数据错误！");
            return;
        }
        // 第三方用户不需要密码
        if (user.getType() == 4) {
            dwzFail("第三方用户不需要密码");
            return;
        }
        String oldPassword = getPara("oldPassword");
        String newPassword = getPara("newPassword");
        if (!PasswordUtil.isMatch(user, oldPassword)) {
            dwzFail("密码错误");
            return;
        }
        if (OtherConsts.DEFAULT_PASSWORD.equals(newPassword)) {
            dwzFail("新密码不得为初始密码");
            return;
        }
        model.setSalt(PasswordUtil.generateSalt());
        model.setPassword(PasswordUtil.generatePassword(user.getUsername(), newPassword, model.getSalt()));
        sm.getMapper(SysUserDao.class).updateTemplateById(model);
        // 需要清除sessionUser，由MyUserFilter重设
        getSession().removeAttribute(SessionConsts.USER);
        ShiroCache.clearCache();
        dwzSuccess("保存成功！");
    }
}
