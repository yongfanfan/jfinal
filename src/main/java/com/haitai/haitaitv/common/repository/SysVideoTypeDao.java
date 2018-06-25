package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysVideo;
import com.haitai.haitaitv.common.entity.SysVideoType;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysVideoTypeDao extends BaseMapper<SysVideoType> {

    void page(PageQuery<SysVideoType> pageQuery);


}
