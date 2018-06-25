package com.haitai.haitaitv.common.repository;

import java.util.List;

import com.haitai.haitaitv.common.entity.SysVideo;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysVideoDao extends BaseMapper<SysVideo> {

    SysVideo getVideoByVideoId(String videoId);

    void page(PageQuery<SysVideo> pageQuery);

    List<SysVideo> getVideoBySize(int size);


}
