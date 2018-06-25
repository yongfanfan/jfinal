package com.haitai.haitaitv.module.back.system.log;

import com.haitai.haitaitv.common.entity.SysLog;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysLogDao;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.module.back.common.BackCacheService;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志管理
 *
 * @author liuzhou
 *         create at 2017-04-12 16:12
 */
@ControllerBind(controllerKey = "/system/log")
@RequiresPermissions("system/log/list:*")
public class LogController extends BaseBackController {

    private SysLogDao dao = sm.getMapper(SysLogDao.class);

    @RequiresPermissions("system/log/list:view")
    public void list() {
        SysLog attr = getBean(SysLog.class, "attr");
        PageQuery<SysLog> pageQuery = getPageQuery(attr);
        if (pageQuery.getOrderBy() == null) {
            pageQuery.setOrderBy("id desc");
        }
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }

    @RequiresPermissions("system/log/list:view")
    public void view() {
        SysLog model = dao.unique(getParaToInt());
        setAttr("model", model);
        render("view.html");
    }
}
