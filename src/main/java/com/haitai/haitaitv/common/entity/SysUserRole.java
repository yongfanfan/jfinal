package com.haitai.haitaitv.common.entity;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysUserRole extends org.beetl.sql.core.TailBean {
    //id
    private Integer id;
    //用户id
    private Integer userId;
    //角色id
    private Integer roleId;

    public SysUserRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}