package com.haitai.haitaitv.component.constant;

import com.jfinal.kit.PropKit;

/**
 * mq相关的常量
 *
 * @author liuzhou
 *         create at 2017-03-23 23:01
 */
public class MqConsts {

    public static final String ONEGOODS_MQ_KEY = PropKit.get("ONEGOODS.MQ.KEY");
    public static final String ONEGOODS_MQ_URL = PropKit.get("ONEGOODS.MQ.URL");
    public static final String ONEGOODS_MQ_USER = PropKit.get("ONEGOODS.MQ.USER");
    public static final String ONEGOODS_MQ_PASSWORD = PropKit.get("ONEGOODS.MQ.PASSWORD");
    public static final String NEIWANG_SERVER_STATIC = PropKit.get("NEIWANG.SERVER_STATIC");
    public static final String NEIWANG_MQ_KEY = PropKit.get("NEIWANG.MQ.KEY");
    public static final String NEIWANG_MQ_URL = PropKit.get("NEIWANG.MQ.URL");
    public static final String NEIWANG_MQ_USER = PropKit.get("NEIWANG.MQ.USER");
    public static final String NEIWANG_MQ_PASSWORD = PropKit.get("NEIWANG.MQ.PASSWORD");

    /**
     * 彩票平台类型
     */
    public static final String PLAT_FORM_TYPE_CAIPIAO = "caipiao";
    /**
     * 夺宝平台类型
     */
    public static final String PLAT_FORM_TYPE_DUOBAO = "duobao";
    /**
     * 千米平台类型
     */
    public static final String PLAT_FORM_TYPE_QIANMI = "qianmi";
    /**
     * 全局消息类型-receipt领取回执
     */
    public static final String GLOBAL_INFO_TYPE_RECEIPT = "receipt";
    /**
     * 全局消息类型-draw领取消息
     */
    public static final String GLOBAL_INFO_TYPE_DRAW = "draw";
    /**
     * 全局消息类型-bonus中奖消息
     */
    public static final String GLOBAL_INFO_TYPE_BONUS = "bonus";
    /**
     * 全局消息类型-balance余额消息
     */
    public static final String GLOBAL_INFO_TYPE_BALANCE = "balance";
    /**
     * 全局消息类型-夺宝退还
     */
    public static final String GLOBAL_INFO_TYPE_REFUND = "REFUND";
    /**
     * 全局消息类型-微信充值
     */
    public static final String GLOBAL_INFO_TYPE_WXPAY = "WXPAY";
    /**
     * 全局消息类型-SUBJECT参与活动获得
     */
    public static final String GLOBAL_INFO_TYPE_SUBJECT = "SUBJECT";
    /**
     * 全局消息类型-SNATCH参与一元夺宝消费
     */
    public static final String GLOBAL_INFO_TYPE_SNATCH = "SNATCH";
    /**
     * 全局消息类型-TVREGISTER注册获得
     */
    public static final String GLOBAL_INFO_TYPE_TVREGISTER = "TVREGISTER";
    /**
     * /**
     * 消息队列名-云端coin.notice余额变动
     */
    public static final String MQ_CLOUD_COIN_NOTICE = "com.ljsy.onegoods.coin.notice";
    /**
     * 消息队列名-云端order.notice订单通知
     */
    public static final String MQ_CLOUD_ORDER_NOTICE = "com.ljsy.onegoods.order.notice";
    /**
     * 消息队列名-云端open.notice开奖通知
     */
    public static final String MQ_CLOUD_OPEN_NOTICE = "com.ljsy.onegoods.open.notice";
    /**
     * 消息队列名-云端user.register注册
     */
    public static final String MQ_CLOUD_USER_REGISTER = "com.ljsy.onegoods.user.register";
    /**
     * 夺宝规则--首次赠送
     */
    public static final String DUOBAO_RULE_SCZS = "SCZS";
    /**
     * 彩票规则--夺宝成功赠送夺宝币
     */
    public static final String DUOBAO_RULE_DBDB = "DBDB";
    /**
     * 彩票规则--夺宝成功赠送彩票
     */
    public static final String CAIPIAO_RULE_DBCG = "DBCG";
    /**
     * 彩票规则--每天第一次登录
     */
    public static final String CAIPIAO_RULE_MTCP = "MTCP";
    /**
     * 全局消息--tv首次注册通知领取夺宝币
     */
    public static final String GLOBAL_INFO_TYPE_NOTICEREGISTER = "NOTICEREGISTER";
    /**
     * 全局消息--千米订单成功通知
     */
    public static final String GLOBAL_INFO_TYPE_QIANMI = "qianmi";
    /**
     * 全局消息--内外网通知
     */
    public static final String GLOBAL_INFO_TYPE_TVCMS = "tvcms";
    /**
     * 内外网同步--goods表
     */
    public static final String MQ_NEIWANG_GOODSs = "com.ljsy.neiwang.goodss";
    public static final String MQ_NEIWANG_GOODS = "com.ljsy.neiwang.goods";
    /**
     * 内外网同步--goods_duobao表
     */
    public static final String MQ_NEIWANG_GOODS_DUOBAO = "com.ljsy.neiwang.goods.duobao";
    /**
     * 内外网同步--goods_duobao_info表
     */
    public static final String MQ_NEIWANG_GOODS_DUOBAO_INFO = "com.ljsy.neiwang.goods.duobao.info";
    /**
     * 内外网同步--goods_qianmi表
     */
    public static final String MQ_NEIWANG_GOODS_QIANMI = "com.ljsy.neiwang.goods.qianmi";
    /**
     * 内网全局消息队列前缀
     */
    public static final String GLOBAL_INFO_PREFIX = "com.ljsy.onegoods.global.info";

    /**
     * 内外网同步--show_goods表
     */
    public static final String MQ_NEIWANG_SHOW_GOODS = "com.ljsy.neiwang.show.goods";

    /**
     * 消息队列名-云端用户行为统计监听
     */
    public static final String MQ_CLOUD_DATA_STATIC = "com.ljsy.onegoods.data.static";

    /**
     * 消息队列名-云端内网vod表同步
     */
    public static final String MQ_CLOUD_VOD = "com.ljsy.onegoods.vod";
    /**
     * 消息队列名-云端内网flod表同步
     */
    public static final String MQ_CLOUD_FLOD = "com.ljsy.onegoods.flod";
    /**
     * 消息队列名-彩票开奖通知
     */
    public static final String MQ_TICKET_OPEN_NOTICE = "com.ljsy.ticket.open.notice";
    /**
     * 消息队列名-彩票下单通知
     */
    public static final String MQ_TICKET_ORDER_NOTICE = "com.ljsy.ticket.order.notice";
    /**
     * 消息队列名-彩票未中奖通知
     */
    public static final String MQ_TICKET_LOSE_NOTICE = "com.ljsy.ticket.lose.notice";
    /**
     * 消息队列名-夺宝新一期通知
     */
    public static final String MQ_ONEGOODS_ISSUE_NOTICE = "com.ljsy.onegoods.issue.notice";
    /**
     * 内外网通知-夺宝新一期通知
     */
    public static final String MQ_NEIWANG_ONEGOODS_ISSUE_NOTICE = "com.ljsy.neiwang.onegoods.issue.notice";
    /**
     * 内外网通知-彩票下单通知
     */
    public static final String MQ_NEIWANG_TICKET_ORDER_NOTICE = "com.ljsy.neiwang.ticket.order.notice";
    /**
     * 内外网通知-彩票未中奖通知
     */
    public static final String MQ_NEIWANG_TICKET_LOSE_NOTICE = "com.ljsy.neiwang.ticket.lose.notice";
    /**
     * 内外网通知-彩票开奖通知
     */
    public static final String MQ_NEIWANG_TICKET_OPEN_NOTICE = "com.ljsy.neiwang.ticket.open.notice";
    /**
     * 内外网通知-文件修改通知
     */
    public static final String MQ_NEIWANG_IMAGE_UPDATE_NOTICES = "com.ljsy.neiwang.image.update.noticeSS";
    public static final String MQ_NEIWANG_IMAGE_UPDATE_NOTICE = "com.ljsy.neiwang.image.update.notice";
    /**
     * insert通知
     */
    public static final String MQ_UPDATE_NOTICE = "com.update.notice";
    /**
     * insert通知
     */
    public static final String MQ_INSERT_NOTICE = "com.insert.notice";
    /**
     * insert通知
     */
    public static final String MQ_DELETE_NOTICE = "com.delete.notice";
    /**
     * 千米到电视端，的商品同步
     */
    public static final String MQ_QIANMI_GOODS = "com.qianmi.goods";
    /**
     * 千米到电视端，的订单同步
     */
    public static final String MQ_QIANMI_ORDER = "com.qianmi.order";
    /**
     * 内外网，的商品同步
     */
    public static final String MQ_NEIWANG_QIANMI_GOODS = "com.neiwang.qianmi.goods";
    /**
     * 内外网，的订单同步
     */
    public static final String MQ_NEIWANG_QIANMI_ORDER = "com.neiwang.qianmi.order";
    /**
     * 内外网，的用户同步
     */
    public static final String MQ_NEIWANG_USER = "com.neiwang.user";
    /**
     * 内外网，的物流同步
     */
    public static final String MQ_NEIWANG_EXPRESS = "com.neiwang.express";
    /**
     * 内外网，的时间同步
     */
    public static final String MQ_NEIWANG_TIME = "com.neiwang.time";
    /**
     * 千米到电视端，的用户领取代金券
     */
    public static final String MQ_QIANMI_COUPON = "com.qianmi.coupon";
    /**
     * 内外网，的供货商同步
     */
    public static final String MQ_NEIWANG_PROVIDER = "com.neiwang.provider";
    /**
     * 内外网，的首屏数据同步
     */
    public static final String MQ_NEIWANG_ICNTV = "com.neiwang.icntv";
    /**
     * 内外网，的首屏数据同步
     */
    public static final String MQ_NEIWANG_FOLDER = "com.neiwang.folder";
    /**
     * 千米到电视端，的促销活动
     */
    public static final String MQ_QIANMI_MARKETING = "com.qianmi.marketing";
    /**
     * 内网同步到公网，并调用领取电商优惠券的接口
     */
    public static final String MQ_NEIWANG_SENDCOUPON = "com.neiwang.sendCoupon";

    public static final String MQ_NEIWANG_VIDEO = "com.neiwang.video";
    /**
     * 公网到内网，发布渠道
     */
    public static final String MQ_NEIWANG_PUBLISH = "com.neiwang.publish";

    /**
     * 公网到内网，同步超声波
     */
    public static final String MQ_NEIWANG_ULTRASONIC = "com.neiwang.ultrasonic";

    /**
     * 公网到内网，同步发布主题
     */
    public static final String MQ_NEIWANG_PUBLISH_ALBUM = "com.neiwang.publish.album";

    /**
     * 公网到内网，同步发布与主题的关联关系
     */
    public static final String MQ_NEIWANG_RL_PUBLISH_ALBUM = "com.neiwang.rl.publish.album";

    /**
     * 公网到内网，同步分类
     */
    public static final String MQ_NEIWANG_CLASSIFICATION = "com.neiwang.classification";

    /**
     * 内网到公网，手动使用mq做数据同步
     */
    public static final String MQ_NEIWANG_DATASYNCH = "com.neiwang.dataSynch";

    private MqConsts() {
    }
}
