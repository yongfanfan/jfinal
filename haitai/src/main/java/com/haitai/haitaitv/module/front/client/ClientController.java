package com.haitai.haitaitv.module.front.client;


import java.time.LocalDateTime;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.entity.TbLog;
import com.haitai.haitaitv.common.repository.TbLogDao;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.jfinal.interceptor.AccessTokenInterceptor;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.front.common.BaseFrontController;
import com.haitai.haitaitv.module.front.common.Md5SignInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

@ControllerBind(controllerKey = "/front/client")
@Clear(AccessTokenInterceptor.class)
public class ClientController  extends BaseFrontController{
	
    public void receiptMessage() {
        if (ConfigConsts.DEV_MODE) {
            this.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            this.getResponse().addHeader("Access-Control-Allow-Headers", "accessToken");
        }
        String videoId = getRequest().getParameter("videoId");
        if(videoId == null){
            renderJson(frontCacheService.getjson(new JSONObject()));
            return;
        }
        videoId = "123qwe";
        TbLog log = new TbLog();
        log.setId(null);
        log.setCreateTime(LocalDateTime.now());
        log.setRemark("用户开始看视频了");
        log.setVideoId(videoId);
        log.setUuid(UUID.randomUUID().toString());
        sm.getMapper(TbLogDao.class).insertTemplate(log, true);
        String path = (String) getRequest().getAttribute(SessionConsts.BASE_PATH);
        renderJson(frontCacheService.getVideoTimePoint(videoId, path, log));
    }
    
    public void out() {
        String uuid = getPara("logId", "");
        if(StrUtil.isEmpty(uuid)){
            return;
        }
        TbLog log = new TbLog();
        log.setUuid(uuid);
        log.setCreateTime(LocalDateTime.now());
        log.setRemark("用户退出了");
        sm.getMapper(TbLogDao.class).insertTemplate(log);
    }
    
    @Before(Md5SignInterceptor.class)
    public void pay() {
        this.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        this.getResponse().addHeader("Access-Control-Allow-Headers", "accessToken");
        String uuid = getPara("logId", "");
        String productId = getPara("productId", "");
        if(StrUtil.isEmpty(uuid) || StrUtil.isEmpty(productId)){
            return;
        }
        TbLog log = new TbLog();
        log.setUuid(uuid);
        TbLog old = sm.getMapper(TbLogDao.class).templateOne(log);
        if(old == null){
            return;
        }
        TbLog model = new TbLog();
        model.setId(null);
        model.setUuid(uuid);
        model.setCreateTime(LocalDateTime.now());
        model.setRemark("用户下单了");
        model.setVideoId(old.getVideoId());
        model.setProductId(productId);
        sm.getMapper(TbLogDao.class).insertTemplate(model);
    }
}