package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;

import org.beetl.sql.core.annotatoin.ColumnIgnore;


public class SysVideo extends org.beetl.sql.core.TailBean{
    private Long id;
    private String videoId;
    private String author;
    private String content;
    private Long totalTime;
    private LocalDateTime createTime;
    private Integer delFlag;
    private Long type;
    private String videoUrl;
    private String main;
    
    private String desc;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVideoId() {
        return videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
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
    public Long getType() {
        return type;
    }
    public void setType(Long type) {
        this.type = type;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDesc() {
        return desc;
    }
    @ColumnIgnore(insert=false)
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
