package com.haitai.haitaitv.common.entity.base;

import org.beetl.sql.core.TailBean;

/**
 * 存放TbScreen和TbScreenTemp共有的属性
 *
 * @author liuzhou
 *         create at 2017-04-15 16:42
 */
public abstract class BaseScreen extends TailBean {

    private Integer id;
    private String images;
    //1.folder文件目录，2.publish/publishvod，3.广告栏
    private Integer contentType;
    //folder的id，或者publish/publishvod的uuid，或者广告链接
    private String content;
    //顺序
    private Integer sort;
    //模板id
    private Integer templateId;
    //1,2,3,4,5,6模块名,图形形状
    private Integer moduleType;
    //1，已删除。2，未删除。
    private Integer status;
    //存七牛图片
    private String netImages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNetImages() {
        return netImages;
    }

    public void setNetImages(String netImages) {
        this.netImages = netImages;
    }

    /**
     * 复制id以外的八个字段
     *
     * @param other 被复制的对象
     */
    public void copy(BaseScreen other) {
        setImages(other.getImages());
        setContentType(other.getContentType());
        setContent(other.getContent());
        setSort(other.getSort());
        setTemplateId(other.getTemplateId());
        setModuleType(other.getModuleType());
        setStatus(other.getStatus());
        setNetImages(other.getNetImages());
    }
}
