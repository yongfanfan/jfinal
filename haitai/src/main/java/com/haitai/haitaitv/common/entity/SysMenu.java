package com.haitai.haitaitv.common.entity;

import org.beetl.sql.core.annotatoin.TableTemplate;

import java.time.LocalDateTime;

/*
* 此表现在实为permission权限表。type为1时同时兼具菜单的作用，type为2时作为渠道的权限
* gen by beetlsql 2017-03-23
*/
@TableTemplate("ORDER BY sort ASC")
public class SysMenu extends org.beetl.sql.core.TailBean {
    //id
    private Integer id;
    //父id
    private Integer parentId;
    //名称/11111
    private String name;
    //菜单key
    private String urlKey;
    //链接地址
    private String url;
    //状态//radio/2,隐藏,1,显示
    private Integer status;
    //类型 1，菜单；2，渠道
    private Integer type;
    //排序
    private Integer sort;
    //级别
    private Integer level;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;

    public SysMenu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
