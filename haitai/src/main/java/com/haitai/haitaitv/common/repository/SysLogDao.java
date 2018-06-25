package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysLog;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysLogDao extends BaseMapper<SysLog> {

    void page(PageQuery<SysLog> pageQuery);
}
