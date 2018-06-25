package com.haitai.haitaitv.common.repository;

import java.util.List;

import com.haitai.haitaitv.common.entity.TbLog;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface TbLogDao extends BaseMapper<TbLog> {

    void page(PageQuery<TbLog> pageQuery);

    List<TbLog> getCreateByTime(String startTime, String endTime);

    List<TbLog> getPayByTime(String startTime, String endTime);

    List<TbLog> getCreateCountByDate(String time);

    List<TbLog> getProductTop10ByTime(String startTime, String endTime);

    List<TbLog> getVideoTop10ByTime(String startTime, String endTime);


}
