package com.haitai.haitaitv.common.repository;

import java.util.List;

import com.haitai.haitaitv.common.entity.SysGoods;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface SysGoodsDao extends BaseMapper<SysGoods> {

    SysGoods getGoodsByProductId(String productId);

    void page(PageQuery<SysGoods> pageQuery);

    List<SysGoods> getProductBySize(int size);


}
