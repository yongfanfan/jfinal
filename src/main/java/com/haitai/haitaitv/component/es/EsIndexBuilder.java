package com.haitai.haitaitv.component.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liuzhou
 *         create at 2017-06-21 13:48
 */
public class EsIndexBuilder {

    private String index;
    private String type;
    private String id;
    private String source;

    public String getIndex() {
        return index;
    }

    public EsIndexBuilder setIndex(String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public EsIndexBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public EsIndexBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public String getSource() {
        return source;
    }

    public EsIndexBuilder setSource(JSONObject jsonSource) {
        this.source = jsonSource.toJSONString();
        return this;
    }

    public EsIndexBuilder setSourceWithDateFormat(JSONObject jsonSource) {
        return setSourceWithDateFormat(jsonSource, "yyyy-MM-dd HH:mm:ss");
    }

    public EsIndexBuilder setSourceWithDateFormat(JSONObject jsonSource, String dateFormat) {
        this.source = JSON.toJSONStringWithDateFormat(jsonSource, dateFormat);
        return this;
    }
}
