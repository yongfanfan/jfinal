package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysGoodsType;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysGoodsTypeDao extends BaseMapper<SysGoodsType> {

    void page(PageQuery<SysGoodsType> pageQuery);



}
