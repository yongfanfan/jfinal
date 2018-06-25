package com.haitai.haitaitv.module.front.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * front接口通用返回DTO
 *
 * @author liuzhou
 *         create at 2017-05-03 16:16
 */
public abstract class BaseDTO implements Responsive {

    @ApiModelProperty(value = "状态。" +
            "(2000：返回成功；" +
            "1002：无效的accessToken；" +
            "1003：operatorId不匹配；" +
            "1004：参数错误；" +
            "1005：服务器内部错误；" +
            "1100：数据不存在(例外，微信相关接口里表示用户没有用微信扫码登录或已注销)；" +
            "1500：调用第三方接口失败；" +
            "5001：商品不存在；" +
            "5051：视频不存在；" +
            "5052：已收藏)", example = "2000")
    private int status = 2000; // 默认2000

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
