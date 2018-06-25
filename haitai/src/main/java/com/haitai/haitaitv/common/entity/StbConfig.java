package com.haitai.haitaitv.common.entity;

import java.time.LocalDateTime;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class StbConfig extends org.beetl.sql.core.TailBean {
    //主键
    private Integer id;
    //运营商名称
    private String operatorName;
    //运营商ID
    private String operatorId;
    //400电话
    private String callNumber;
    //启动页素材ID
    private Integer loadId;
    //升级包url
    private String apkUrl;
    //升级包网络url
    private String apkNetUrl;
    //应用版本号
    private String version;
    //强制升级使/0,不强制，1，强制
    private Integer updateForce;
    //升级说明
    private String description;
    //更新时间
    private LocalDateTime updateTime;
    //更新者id
    private Integer updateId;
    //该渠道是否与公网网络同步：2为否，1为是
    private Integer syncFlag;
    private String nickId;
    //与父渠道的商业合作标志：1为是，2为否且对父级不展示，3为否且对父级展示。当parentOperatorId非空时，1的情形是子完全从属于父，而2的情形处理如下：将子作为父的并列层级，展示于一级菜单，父的合计里不包含子的数据。
    private Integer businessType;
    //父渠道id
    private String parentOperatorId;
    //表示分成比例，同时在此渠道为A渠道的合作子渠道时，此字段也代表A渠道的成本比例
    private Integer shareCost;
    //转场图素材，对应一个相册
    private Integer albumId;

    public StbConfig() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Integer getLoadId() {
        return loadId;
    }

    public void setLoadId(Integer loadId) {
        this.loadId = loadId;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getApkNetUrl() {
        return apkNetUrl;
    }

    public void setApkNetUrl(String apkNetUrl) {
        this.apkNetUrl = apkNetUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getUpdateForce() {
        return updateForce;
    }

    public void setUpdateForce(Integer updateForce) {
        this.updateForce = updateForce;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getNickId() {
        return nickId;
    }

    public void setNickId(String nickId) {
        this.nickId = nickId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getParentOperatorId() {
        return parentOperatorId;
    }

    public void setParentOperatorId(String parentOperatorId) {
        this.parentOperatorId = parentOperatorId;
    }

    public Integer getShareCost() {
        return shareCost;
    }

    public void setShareCost(Integer shareCost) {
        this.shareCost = shareCost;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }
}
