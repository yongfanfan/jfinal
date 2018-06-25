package com.haitai.haitaitv.common.entity;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysDict extends org.beetl.sql.core.TailBean {
    //主键
    private Integer id;
    //名称
    private String dictName;
    //备注
    private String dictRemark;
    //类型
    private String dictType;

    public SysDict() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictRemark() {
        return dictRemark;
    }

    public void setDictRemark(String dictRemark) {
        this.dictRemark = dictRemark;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }


}
