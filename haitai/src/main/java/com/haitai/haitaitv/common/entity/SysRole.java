package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysRole extends org.beetl.sql.core.TailBean {
    //id
    private Integer id;
    //名称/11111/
    private String name;
    //状态//radio/2,隐藏,1,显示
    private Integer status;
    //排序
    private Integer sort;
    //说明//textarea
    private String remark;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;

    public SysRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }
}
