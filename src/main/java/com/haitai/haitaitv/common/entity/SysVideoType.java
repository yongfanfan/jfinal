package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class SysVideoType extends org.beetl.sql.core.TailBean{
    private Long id;
    private String name;
    private String desc;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int delFlag;
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
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public int getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
    
    
}
