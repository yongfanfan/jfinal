package com.haitai.haitaitv.common.entity;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysRoleMenu extends org.beetl.sql.core.TailBean {
    //id
    private Integer id;
    //角色id
    private Integer roleId;
    //菜单id
    private Integer menuId;
    //false：阅读权限。true：修改权限
    private Integer canUpdate;

    public SysRoleMenu() {
    }

    public SysRoleMenu(Integer id, Integer roleId, Integer menuId, Integer canUpdate) {
        this.id = id;
        this.roleId = roleId;
        this.menuId = menuId;
        this.canUpdate = canUpdate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Integer canUpdate) {
        this.canUpdate = canUpdate;
    }
}
