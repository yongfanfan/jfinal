package com.haitai.haitaitv.common.repository;

import java.util.List;

import com.haitai.haitaitv.common.entity.TbVideoTimePoint;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface TbVideoTimePointDao extends BaseMapper<TbVideoTimePoint> {

    List<TbVideoTimePoint> getVideoTimePonintListByVid(String videoId);

    void page(PageQuery<TbVideoTimePoint> pageQuery);


}
