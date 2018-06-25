package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.SysDictDetail;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysDictDetailDao extends BaseMapper<SysDictDetail> {

    void page(PageQuery<SysDictDetail> pageQuery);
}
