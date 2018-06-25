package com.haitai.haitaitv.common.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class SysGoods extends org.beetl.sql.core.TailBean{
    private Long id;
    private String name;
    private String productId;
    private String imageUrl;
    private String imageNetUrl;
    private LocalDateTime createTime;
    private Integer delFlag;
    private String phone;
    private BigDecimal price;
    private String url;
    private String qrcode;
    private LocalDateTime updateTime;
    private Long goodsId;
    private String supplier;
    private Integer supplierId;
    private Long goodsType;

    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
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
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
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
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getQrcode() {
        return qrcode;
    }
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Long getGoodsType() {
        return goodsType;
    }
    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }
    
    
}
