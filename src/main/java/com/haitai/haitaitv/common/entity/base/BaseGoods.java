package com.haitai.haitaitv.common.entity.base;

import org.beetl.sql.core.TailBean;

import java.time.LocalDateTime;

/**
 * @author liuzhou
 *         create at 2017-04-18 15:14
 */
public abstract class BaseGoods extends TailBean implements IdIntegerAdapter {

    //主键
    private Integer id;
    //product_id ,duobao_id,qianmi_id一样的
    private String productId;
    //分类ID
    private Integer albumId;
    //商品名称
    private String name;
    //商品别名
    private String aliasName;
    //描述
    private String description;
    //商品大图
    private String imageUrl;
    //缩略图
    private String imageNetUrl;
    //订单图
    private String bigImageUrl;
    //订单图
    private String bigImageNetUrl;
    //排序
    private Integer sort;
    //更新时间
    private LocalDateTime updateTime;
    //更新者id
    private Integer updateId;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;
    //状态，1已删除，2可用
    private Integer status;
    //供货商id，默认为海苔自营
    private Integer providerId;
    //备注，商品详细内容，方便400人员查看
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageNetUrl() {
        return imageNetUrl;
    }

    public void setImageNetUrl(String imageNetUrl) {
        this.imageNetUrl = imageNetUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getBigImageNetUrl() {
        return bigImageNetUrl;
    }

    public void setBigImageNetUrl(String bigImageNetUrl) {
        this.bigImageNetUrl = bigImageNetUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
