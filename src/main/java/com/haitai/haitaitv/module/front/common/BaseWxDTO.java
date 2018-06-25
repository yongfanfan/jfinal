package com.haitai.haitaitv.module.front.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 微信相关的front接口通用返回DTO
 *
 * @author liuzhou
 *         create at 2017-05-05 17:13
 */
public abstract class BaseWxDTO extends BaseDTO {

    @ApiModelProperty("手机扫码登录地址，status为1100时使用")
    private String url;
    @ApiModelProperty("二维码图片地址，status为1100时使用")
    private String qrcode;

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
}
