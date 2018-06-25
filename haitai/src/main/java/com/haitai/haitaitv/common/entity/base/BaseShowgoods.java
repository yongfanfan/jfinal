package com.haitai.haitaitv.common.entity.base;

import org.beetl.sql.core.TailBean;

import java.time.LocalDateTime;

/**
 * @author liuzhou
 *         create at 2017-04-18 14:13
 */
public abstract class BaseShowgoods extends TailBean implements IdIntegerAdapter {

    //id
    private Integer id;
    //专题ID
    private Integer folderId;
    //展品名称
    private String title;
    //视频uuid
    private String videoUuid;
    //产品ID
    private String productId;
    //图片路径
    private String imageUrl;
    //网络图片路径
    private String imageNetUrl;
    //更新时间
    private LocalDateTime updateTime;
    //更新者
    private Integer updateId;
    //是否已删除//radio/2,否,1,是
    private Integer deleteFlag;
    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUuid() {
        return videoUuid;
    }

    public void setVideoUuid(String videoUuid) {
        this.videoUuid = videoUuid;
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 复制uuid, publicId, videoUuid, deleteFlag四个字段，新增复制title，imageUrl，imageNetUrl字段
     */
    public void copy(BaseShowgoods other) {
        setUuid(other.getUuid());
        setProductId(other.getProductId());
        setVideoUuid(other.getVideoUuid());
        setDeleteFlag(other.getDeleteFlag());
        setTitle(other.getTitle());
        setImageUrl(other.getImageUrl());
        setImageNetUrl(other.getImageNetUrl());
    }
}
