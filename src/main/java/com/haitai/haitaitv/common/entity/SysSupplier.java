package com.haitai.haitaitv.common.entity;

import com.haitai.haitaitv.common.repository.SysDictDetailDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysSupplier extends org.beetl.sql.core.TailBean {

    private Long id;
    private String name;
    private Integer delFlag;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
