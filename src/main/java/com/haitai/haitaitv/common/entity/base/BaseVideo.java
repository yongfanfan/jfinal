package com.haitai.haitaitv.common.entity.base;

import org.beetl.sql.core.TailBean;

import java.time.LocalDateTime;

/**
 * @author liuzhou
 *         create at 2017-04-18 13:35
 */
public abstract class BaseVideo extends TailBean implements IdIntegerAdapter {
    //主键
    private Integer id;
    //专辑ID
    private Integer albumId;
    //视频名称
    private String name;
    //点播视频路径
    private String videoUrl;
    //网络视频路径
    private String videoNetUrl;
    //视频海报
    private String imageUrl;
    //缩略图
    private String imageNetUrl;
    //扩展名
    private String extName;
    //描述
    private String description;
    //视频时长
    private Integer duration;
    //视频分辨率
    private String resolution;
    //视频码率
    private Integer bitrate;
    //排序
    private Integer sort;
    //更新时间
    private LocalDateTime updateTime;
    //更新者ID
    private Integer updateId;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;
    //删除状态：1已删除 2存在
    private Integer deleteFlag;
    //唯一标识
    private String uuid;

    public BaseVideo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoNetUrl() {
        return videoNetUrl;
    }

    public void setVideoNetUrl(String videoNetUrl) {
        this.videoNetUrl = videoNetUrl;
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

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
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
     * 复制必要的字段
     *
     * @param other 被复制的对象
     */
    public void copy(BaseVideo other) {
        setUuid(other.getUuid());
        setAlbumId(other.getAlbumId());
        setName(other.getName());
        setVideoUrl(other.getVideoUrl());
        setVideoNetUrl(other.getVideoNetUrl());
        // 不再复制，发布vod和展品vod在添加后优先使用公网的图片，若视频vod图片非空才使用视频vod的图片
        /*setImageUrl(other.getImageUrl());
        setImageNetUrl(other.getImageNetUrl());*/
        setExtName(other.getExtName());
        setDescription(other.getDescription());
        setDuration(other.getDuration());
        setBitrate(other.getBitrate());
        setSort(other.getSort());
        setDeleteFlag(other.getDeleteFlag());
    }
}
