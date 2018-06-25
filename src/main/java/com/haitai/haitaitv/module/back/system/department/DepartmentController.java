package com.haitai.haitaitv.module.back.system.department;

import com.haitai.haitaitv.common.entity.SysDepartment;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysDepartmentDao;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.common.BackCacheService;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import java.time.LocalDateTime;

/**
 * 组织机构
 *
 * @author liuzhou
 *         create at 2017-04-10 14:52
 */
@ControllerBind(controllerKey = "/system/department")
@RequiresPermissions("system/department/list:*")
public class DepartmentController extends BaseBackController {

    private SysDepartmentDao dao = sm.getMapper(SysDepartmentDao.class);

    @RequiresPermissions("system/department/list:view")
    public void list() {
        SysDepartment attr = getBean(SysDepartment.class, "attr");
        PageQuery<SysDepartment> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("id desc");
        }
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("opName", BackCacheService.INSTANCE.getOperatorNameMap());
        setAttr("attr", attr);
        render("main.html");
    }

    @RequiresPermissions("system/department/list:view")
    public void view() {
        SysDepartment model = dao.unique(getParaToInt());
        setAttr("model", model);
        render("view.html");
    }

    public void edit() {
        Integer id = getParaToInt();
        SysDepartment model;
        if (id != null) {
            model = dao.unique(id);
        } else {
            model = new SysDepartment();
        }
        setAttr("model", model);
        setAttr("operators", getOperatorList(false));
        render("edit.html");
    }

    public void save() {
        SysDepartment model = getBean(SysDepartment.class, "model");
        if (model.getId() != null) {
            dao.updateTemplateById(model);
        } else {
            model.setCreateId(getSessionUser().getId());
            model.setCreateTime(LocalDateTime.now());
            dao.insertTemplate(model);
        }
        // 清除部门名称缓存
     // 清除部门名称缓存
        BackCacheService.INSTANCE.clearCacheOfDepartmentNameMap();
        dwzSuccess("保存成功");
    }

    public void delete() {
        Integer id = getParaToInt();
        SysUser template = new SysUser();
        template.setDepartmentId(id);
        long count = sm.getMapper(SysUserDao.class).templateCount(template);
        if (count > 0) {
            dwzFail("此部门下有用户，不允许删除");
            return;
        }
        dao.deleteById(id);
        // 清除部门名称缓存
     // 清除部门名称缓存
        BackCacheService.INSTANCE.clearCacheOfDepartmentNameMap();
        dwzSuccessNoClose("删除成功");
    }

}
