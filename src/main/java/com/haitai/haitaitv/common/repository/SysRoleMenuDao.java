package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysRoleMenu;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    int deleteByRoleId(@Param("roleId") Integer roleId);

}
