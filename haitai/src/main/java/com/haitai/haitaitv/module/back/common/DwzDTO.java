package com.haitai.haitaitv.module.back.common;

/**
 * 用于dwz_jui的ajax请求的响应渲染
 *
 * @author liuzhou
 *         create at 2017-04-05 22:33
 */
public class DwzDTO {

    private int statusCode;
    private String message;
    private String navTabId;
    private String rel;
    private String callbackType;
    private String forwardUrl;

    public DwzDTO() {
    }

    /**
     * 以下是通用解释，并不完全适用所有情形
     * （如回调dialogAjaxDone在处理callbackType时，只处理closeCurrent的情形，另外也可自定义回调）
     * 具体需参考dwz.ajax.js的源码
     *
     * @param statusCode   状态码，200成功，300失败，301超时
     * @param message      提示语
     * @param navTabId     刷新指定id的navTab，若为空，则刷新当前激活的navTab，可以传入不存在的navTabId来达到不刷新任何navTab的效果
     * @param rel          暂未使用过，按dwz.ajax.js的解释为“可选 用于局部刷新div id号”
     * @param callbackType 在dialogAjaxDone和navTabAjaxDone中，closeCurrent将关闭当前dialog或navTab，
     *                     在navTabAjaxDone中，forward会将当前navTab跳转到forwardUrl，
     *                     在navTabAjaxDone中，forwardConfirm会弹出确认框，要么跳转，要么关闭当前或指定的navTab
     *                     在navTabAjaxDone中，非以上三种时会将当前navTab表单重置
     * @param forwardUrl   跳转链接，参考callbackType的解释
     */
    public DwzDTO(int statusCode, String message, String navTabId, String rel, String callbackType, String forwardUrl) {
        this.statusCode = statusCode;
        this.message = message;
        this.navTabId = navTabId;
        this.rel = rel;
        this.callbackType = callbackType;
        this.forwardUrl = forwardUrl;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }
}
