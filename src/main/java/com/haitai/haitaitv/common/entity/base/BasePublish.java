package com.haitai.haitaitv.common.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import org.beetl.sql.core.TailBean;

import java.time.LocalDateTime;

/**
 * @author liuzhou
 *         create at 2017-04-18 10:59
 */
public abstract class BasePublish extends TailBean implements OperatorIdAdapter, IdIntegerAdapter {

    private Integer id;
    //showgoods/video的uuid，goods的product_id
    private String content;
    //1：tb_showgoods(_vod)，2：tb_goods(_vod)，3：,tb_video(_vod)
    private Integer type;
    private String operatorId;
    //1，逻辑删除，2，上架，3，下架
    private Integer status;
    //唯一标识
    private String uuid;
    //名称，直接在素材里取，冗余，需要同步
    private String name;
    //图片路径，直接在素材里取，冗余，不必同步
    @JSONField(serialize = false)
    private String imageUrl;
    @JSONField(serialize = false)
    //网络图片路径，直接在素材里取，冗余，不必同步
    private String imageNetUrl;
    //完整编号，前两位为渠道的nickId，后四位为num（不足四位在前面补0），逻辑上唯一
    private String numberList;
    //超声波uuid，对应tb_goods_ultrasonic表的uuid字段
    private String ultrasonicUuid;
    //排序，默认1（新发布的在前）
    private Integer sort;
    //冗余，以便查商品分类
    private String productId;
    //更新时间
    private LocalDateTime updateTime;
    //更新者
    private Integer updateId;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;

    public BasePublish() {
    }

    public BasePublish(String content, Integer type, String operatorId, Integer status) {
        this.content = content;
        this.type = type;
        this.operatorId = operatorId;
        this.status = status;
    }

    public BasePublish(Integer id, String content, Integer type, String operatorId, Integer status, String uuid,
                       String name, String imageUrl, String imageNetUrl, Integer sort) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.operatorId = operatorId;
        this.status = status;
        this.uuid = uuid;
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageNetUrl = imageNetUrl;
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNumberList() {
        return numberList;
    }

    public void setNumberList(String numberList) {
        this.numberList = numberList;
    }

    public String getUltrasonicUuid() {
        return ultrasonicUuid;
    }

    public void setUltrasonicUuid(String ultrasonicUuid) {
        this.ultrasonicUuid = ultrasonicUuid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    /**
     * 复制非id非json序列化的字段
     */
    public void copy(BasePublish other) {
        setContent(other.getContent());
        setType(other.getType());
        setOperatorId(other.getOperatorId());
        setStatus(other.getStatus());
        setUuid(other.getUuid());
        setName(other.getName());
        setNumberList(other.getNumberList());
        setUltrasonicUuid(other.getUltrasonicUuid());
        setSort(other.getSort());
        setImageUrl(other.getImageUrl());
        setImageNetUrl(other.getImageNetUrl());
        setProductId(other.getProductId());
    }
}
