package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysMenu;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysMenuDao extends BaseMapper<SysMenu> {

    List<SysMenu> listByTypeAndRoleIds(@Param("type") Integer type, @Param("roleIds") List<Integer> roleIds);

    @SqlStatement(returnType = String.class)
    List<String> listPermissionsWhenSuper();

    /**
     * @param canUpdate 0：阅读权限，1：修改权限
     */
    @SqlStatement(params = "canUpdate,roleIds", returnType = String.class)
    List<String> listPermissionByRoleIds(Integer canUpdate, List<Integer> roleIds);

    List<SysMenu> listValid();

    void page(PageQuery<SysMenu> pageQuery);
}
