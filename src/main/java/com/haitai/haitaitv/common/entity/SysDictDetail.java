package com.haitai.haitaitv.common.entity;

import org.beetl.sql.core.annotatoin.TableTemplate;

import java.time.LocalDateTime;

/*
* 
* gen by beetlsql 2017-03-23
*/
@TableTemplate("ORDER BY detail_sort, id")
public class SysDictDetail extends org.beetl.sql.core.TailBean {
    //主键
    private Integer id;
    //类型
    private String detailType;
    //名称
    private String detailName;
    //代码
    private String detailCode;
    //排序号
    private Integer detailSort;
    //数据字典类型
    private String dictType;
    //状态
    private String detailState;
    //内容
    private String detailContent;
    //备注
    private String detailRemark;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;

    public SysDictDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public Integer getDetailSort() {
        return detailSort;
    }

    public void setDetailSort(Integer detailSort) {
        this.detailSort = detailSort;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDetailState() {
        return detailState;
    }

    public void setDetailState(String detailState) {
        this.detailState = detailState;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
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
