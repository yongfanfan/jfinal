package com.haitai.haitaitv.common.repository;


import com.haitai.haitaitv.common.entity.SysSupplier;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysSupplierDao extends BaseMapper<SysSupplier> {

    void page(PageQuery<SysSupplier> pageQuery);



}
