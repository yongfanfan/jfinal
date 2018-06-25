package com.haitai.haitaitv.common.repository;

import com.haitai.haitaitv.common.entity.StbConfig;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/*
* 
* gen by beetlsql mapper 2017-03-23
*/
public interface StbConfigDao extends BaseMapper<StbConfig> {

    @SqlStatement(returnType = String.class)
    List<String> listChildrenOperatorId(@Param("operatorId") String operatorId);

    @SqlStatement(params = "operatorIds,operatorId")
    List<StbConfig> listByOperatorId(List<String> operatorIds, String operatorId);

    List<StbConfig> listParentAndBusinessChildren(@Param("operatorId") String operatorId);

    List<StbConfig> listIndependent();

    void page(PageQuery<StbConfig> pageQuery);

    List<StbConfig> listParent(@Param("operatorId") String operatorId);

    StbConfig getByOperatorId(@Param("operatorId") String operatorId);
}
