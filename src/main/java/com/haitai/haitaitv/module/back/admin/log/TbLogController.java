package com.haitai.haitaitv.module.back.admin.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.haitai.haitaitv.common.entity.TbLog;
import com.haitai.haitaitv.common.repository.TbLogDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.back.common.BaseBackController;

@ControllerBind(controllerKey = "/admin/tbLog")
@RequiresPermissions("admin/tbLog/list:*")
public class TbLogController extends BaseBackController {
    private static final String pattern = "yyyy-MM-dd";
    private TbLogDao dao = sm.getMapper(TbLogDao.class);
    private TbLogService logService = new TbLogService();
    
    @RequiresPermissions("admin/tbLog/list:view")
    public void list() {
        String startTimenew = getPara("startTime", "");
        String endTimenew = getPara("endTime", "");
        LocalDateTime now = LocalDateTime.now();
        if(StrUtil.isEmpty(endTimenew)){
            endTimenew = now.format(DateTimeFormatter.ofPattern(pattern));
        }
        if(StrUtil.isEmpty(startTimenew)){
            startTimenew = TbLogService.addDay(endTimenew, -7);
        }
        List<String> times = new ArrayList<String>();
        String startTime = startTimenew;
        String endTime = endTimenew;
        while(TbLogService.compare_date(startTime, endTime)){
            times.add(startTime);
            startTime = TbLogService.addDay(startTime, 1);
        }
        List<Long> ccount = logService.getParam(dao.getCreateByTime(startTimenew, endTimenew), times);
        List<Long> pcount = logService.getParam(dao.getPayByTime(startTimenew, endTimenew), times);
        
        List<String> hours = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
        List<Long> ocount = logService.getHours(dao.getCreateCountByDate(startTimenew), hours);
        
        //查询商品热度前十名
        List<TbLog> getProductTop10 = logService.getProductTop10(dao.getProductTop10ByTime(startTimenew, endTimenew), startTimenew, endTimenew);
//        List<Long> productX = new ArrayList<>();
//        List<String> productY = new ArrayList<>();
//        for(TbLog log : getProductTop10){
//            productX.add(log.getId());
//            productY.add(log.getRemark());
//        }
        
        //查询视频热度前十名
        List<TbLog> getVideoTop10 = logService.getVideoTop10(dao.getVideoTop10ByTime(startTimenew, endTimenew), startTimenew, endTimenew);
//        List<Long> videoX = new ArrayList<>();
//        List<String> videoY = new ArrayList<>();
//        for(TbLog log : getVideoTop10){
//            videoX.add(log.getId());
//            videoY.add(log.getRemark());
//        }
        
        setAttr("startTime", startTimenew);
        setAttr("endTime", endTimenew);
        setAttr("ccount", JSONArray.toJSON(ccount));
        setAttr("ocount", JSONArray.toJSON(ocount));
        setAttr("pcount", JSONArray.toJSON(pcount));
        setAttr("time", JSONArray.toJSON(times));
        setAttr("hours", JSONArray.toJSON(hours));
//        setAttr("productX", JSONArray.toJSON(productX));
//        setAttr("productY", JSONArray.toJSON(productY));
//        setAttr("videoX", JSONArray.toJSON(videoX));
//        setAttr("videoY", JSONArray.toJSON(videoY));
        setAttr("getProductTop10", getProductTop10);
        setAttr("getVideoTop10", getVideoTop10);
        render("main.html");
    }
    
   
}
