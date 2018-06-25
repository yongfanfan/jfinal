package com.haitai.haitaitv.module.back.system.user;

import com.haitai.haitaitv.common.cache.ShiroCache;
import com.haitai.haitaitv.common.entity.SysRole;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.entity.SysUserRole;
import com.haitai.haitaitv.common.repository.SysRoleDao;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.common.repository.SysUserRoleDao;
import com.haitai.haitaitv.component.constant.OtherConsts;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.shiro.credentials.PasswordUtil;
import com.haitai.haitaitv.component.util.encryption.DESUtils;
import com.haitai.haitaitv.module.back.common.BackCacheService;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author liuzhou
 *         create at 2017-04-10 16:18
 */
@ControllerBind(controllerKey = "/system/user")
@RequiresPermissions("system/user/list:*")
public class UserController extends BaseBackController {

    private SysUserDao dao = sm.getMapper(SysUserDao.class);

    @RequiresPermissions("system/user/list:view")
    public void list() {
        SysUser attr = getBean(SysUser.class, "attr");
        PageQuery<SysUser> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("id desc");
        }
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }

    @RequiresPermissions("system/user/list:view")
    public void view() {
        SysUser model = dao.unique(getParaToInt());
        setAttr("model", model);
        render("view.html");
    }

    public void edit() {
        Integer id = getParaToInt();
        SysUser model;
        if (id != null) {
            model = dao.unique(id);
        } else {
            model = new SysUser();
        }
        setAttr("model", model);
        render("edit.html");
    }

    public void save() {
        SysUser model = getBean(SysUser.class, "model");
        if (model.getId() != null) {
            dao.updateTemplateById(model);
        } else {
            model.setCreateId(getSessionUser().getId());
            model.setCreateTime(LocalDateTime.now());
            model.setSalt(PasswordUtil.generateSalt());
            model.setPassword(PasswordUtil.generatePassword(model.getUsername(), OtherConsts.DEFAULT_PASSWORD, model.getSalt()));
            dao.insertTemplate(model);
        }
        // 有可能更改用户类型，所以需要清shiro缓存
        ShiroCache.clearCache();
        // 如果修改的是当前登录用户，则需要清除sessionUser，由MyUserFilter重设
        if (getSessionUser().getId().equals(model.getId())) {
            getSession().removeAttribute(SessionConsts.USER);
        }
        dwzSuccess("保存成功");
    }

    public void delete() {
        Integer id = getParaToInt();
        // 不允许删除的是当前登录用户
        if (getSessionUser().getId().equals(id)) {
            dwzFail("不允许删除当前登录用户");
            return;
        }
        // 删除授权
        sm.getMapper(SysUserRoleDao.class).deleteByUserId(id);
        dao.deleteById(id);
        // 清shiro缓存
        ShiroCache.clearCache();
        dwzSuccessNoClose("删除成功");
    }

    public void editAuth() {
        Integer userId = getParaToInt();
        List<SysRole> roles = sm.getMapper(SysRoleDao.class).listValid();
        SysUserRole template = new SysUserRole();
        template.setUserId(userId);
        List<SysUserRole> userRoles = sm.getMapper(SysUserRoleDao.class).template(template);
        String roleIds = userRoles.stream().map(sur -> sur.getRoleId().toString())
                .collect(Collectors.joining(","));
        setAttr("userId", userId);
        setAttr("roles", roles);
        setAttr("roleIds", roleIds);
        render("editAuth.html");
    }

    public void saveAuth() {
        Integer userId = getParaToInt("userId");
        String[] roleIds = getParaValues("roleId");
        SysUserRoleDao userRoleDao = sm.getMapper(SysUserRoleDao.class);
        userRoleDao.deleteByUserId(userId);
        if (roleIds != null) {
            List<SysUserRole> list = new ArrayList<>();
            for (String roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Integer.valueOf(roleId));
                list.add(userRole);
            }
            userRoleDao.insertBatch(list);
        }
        // 清shiro缓存
        ShiroCache.clearCache();
        dwzSuccess("保存成功");
    }

    public void resetPassword() {
        Integer id = getParaToInt();
        SysUser model = dao.unique(id);
        model.setSalt(PasswordUtil.generateSalt());
        model.setPassword(PasswordUtil.generatePassword(model.getUsername(), OtherConsts.DEFAULT_PASSWORD, model.getSalt()));
        dao.updateById(model);
        ShiroCache.clearCache();
        // 如果修改的是当前登录用户，则需要清除sessionUser，由MyUserFilter重设
        if (getSessionUser().getId().equals(id)) {
            getSession().removeAttribute(SessionConsts.USER);
        }
        dwzSuccessNoClose("保存成功");
    }
}
