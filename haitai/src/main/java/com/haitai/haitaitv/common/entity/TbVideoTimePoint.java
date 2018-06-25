package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;

public class TbVideoTimePoint extends org.beetl.sql.core.TailBean{
	private Long id;
	private String videoId;
	private String timePoint;
	private String productId;
	private LocalDateTime createTime;
	private Integer allFlag;
	private Integer delFlag;
	private String duration;
	private String left;
	private String top;
	private LocalDateTime updateTime;
	
	
	public String getLeft() {
        return left;
    }
    public void setLeft(String left) {
        this.left = left;
    }
    public String getTop() {
        return top;
    }
    public void setTop(String top) {
        this.top = top;
    }
    public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public Integer getAllFlag() {
		return allFlag;
	}
	public void setAllFlag(Integer allFlag) {
		this.allFlag = allFlag;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
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
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

}
