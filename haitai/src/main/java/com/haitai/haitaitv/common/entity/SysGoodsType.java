package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class SysGoodsType extends org.beetl.sql.core.TailBean{
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
