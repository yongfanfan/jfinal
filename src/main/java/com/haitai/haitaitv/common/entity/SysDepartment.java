package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysDepartment extends org.beetl.sql.core.TailBean {
    private Integer id;
    //部门/11111
    private String name;
    //序号
    private Integer sort;
    //联系人
    private String linkman;
    //联系人电话
    private String linkmanNo;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;
    private String operatorId;

    public SysDepartment() {
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanNo() {
        return linkmanNo;
    }

    public void setLinkmanNo(String linkmanNo) {
        this.linkmanNo = linkmanNo;
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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
