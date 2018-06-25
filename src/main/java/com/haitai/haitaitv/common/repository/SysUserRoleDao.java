package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysUserRole;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

    @SqlStatement(returnType = Integer.class, params = "userId")
    List<Integer> listRoleId(Integer userId);

    int deleteByUserId(@Param("userId") Integer userId);

    int deleteByRoleId(@Param("roleId") Integer roleId);

}
