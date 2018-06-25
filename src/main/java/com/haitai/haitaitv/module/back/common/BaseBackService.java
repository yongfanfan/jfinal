package com.haitai.haitaitv.module.back.common;

import com.haitai.haitaitv.common.entity.StbConfig;
import com.haitai.haitaitv.common.repository.StbConfigDao;
import com.haitai.haitaitv.component.jfinal.base.BaseService;
import com.haitai.haitaitv.component.util.StrUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author liuzhou
 *         create at 2017-04-05 22:41
 */
public class BaseBackService extends BaseService {

    /**
     * 获取需要同步的渠道号集合（publish的同步不用此方法，因为publish需要手动指定渠道）
     */
    protected Set<String> getSyncOperatorIds() {
        StbConfig template = new StbConfig();
        template.setSyncFlag(1);
        List<StbConfig> configs = sm.getMapper(StbConfigDao.class).template(template);

        Set<String> operatorIds = new HashSet<>();
        for (StbConfig appConfig : configs) {
            if (StrUtil.isNotEmpty(appConfig.getParentOperatorId())) {
                operatorIds.add(appConfig.getParentOperatorId());
            } else {
                operatorIds.add(appConfig.getOperatorId());
            }
        }
        return operatorIds;
    }

    /**
     * 以下是通用解释，并不完全适用所有情形
     * （如回调dialogAjaxDone在处理callbackType时，只处理closeCurrent的情形，另外也可自定义回调）
     * 具体需参考dwz.ajax.js的源码
     *
     * @param statusCode   状态码，200成功，300失败，301超时，601部分成功（弹warn框，需使用myNavTabAjaxDone回调）
     * @param message      提示语
     * @param navTabId     刷新指定id的navTab，若为空，则刷新当前激活的navTab，可以传入不存在的navTabId来达到不刷新任何navTab的效果
     * @param rel          暂未使用过，按dwz.ajax.js的解释为“可选 用于局部刷新div id号”
     * @param callbackType 在dialogAjaxDone和navTabAjaxDone中，closeCurrent将关闭当前dialog或navTab，
     *                     在navTabAjaxDone中，forward会将当前navTab跳转到forwardUrl，
     *                     在navTabAjaxDone中，forwardConfirm会弹出确认框，要么跳转，要么关闭当前或指定的navTab
     *                     在navTabAjaxDone中，非以上三种时会将当前navTab表单重置
     * @param forwardUrl   跳转链接，参考callbackType的解释
     */
    private DwzDTO dwzRender(int statusCode, String message, String navTabId, String rel, String callbackType, String forwardUrl) {
        return new DwzDTO(statusCode, message, navTabId, rel, callbackType, forwardUrl);
    }

    protected DwzDTO dwzFail(String message) {
        return dwzRender(300, message, null, null, null, null);
    }

    protected DwzDTO dwz601(String message) {
        return dwzRender(601, message, null, null, "closeCurrent", null);
    }

    protected DwzDTO dwzSuccess(String message) {
        return dwzRender(200, message, null, null, "closeCurrent", null);
    }

    protected DwzDTO dwzSuccess(String message, String navTabId) {
        return dwzRender(200, message, navTabId, null, "closeCurrent", null);
    }

    protected DwzDTO dwzSuccess(String message, String navTabId, String forwardUrl) {
        return dwzRender(200, message, navTabId, null, "closeCurrent", forwardUrl);
    }

    protected DwzDTO dwzSuccessNoClose(String message) {
        return dwzRender(200, message, null, null, null, null);
    }

    protected DwzDTO dwzSuccessNoClose(String message, String navTabId) {
        return dwzRender(200, message, navTabId, null, null, null);
    }

    protected DwzDTO dwzSuccessForwardUrl(String message, String navTabId, String forwardUrl) {
        return dwzRender(200, message, navTabId, null, "forward", forwardUrl);
    }

    protected DwzDTO dwzTimeout() {
        return dwzRender(301, "登录超时，请重新登录", null, null, null, null);
    }
}
