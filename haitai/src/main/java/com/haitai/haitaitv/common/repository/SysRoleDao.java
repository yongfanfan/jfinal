package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysRole;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysRoleDao extends BaseMapper<SysRole> {

    List<SysRole> listValid();

    void page(PageQuery<SysRole> pageQuery);

}
