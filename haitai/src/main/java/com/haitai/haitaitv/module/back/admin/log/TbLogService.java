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

import com.haitai.haitaitv.common.entity.SysGoods;
import com.haitai.haitaitv.common.entity.SysVideo;
import com.haitai.haitaitv.common.entity.TbLog;
import com.haitai.haitaitv.common.repository.SysGoodsDao;
import com.haitai.haitaitv.common.repository.SysVideoDao;
import com.haitai.haitaitv.common.repository.TbLogDao;
import com.haitai.haitaitv.module.back.common.BaseBackService;


public class TbLogService extends BaseBackService {

    private TbLogDao dao = sm.getMapper(TbLogDao.class);
    private static final String pattern = "yyyy-MM-dd";

    public List<Long> getParam(List<TbLog> cList, List<String> ctime) {
        List<Long> ccount = new ArrayList<Long>();
        for(int i = 0; i < ctime.size(); i++){
            boolean flg = true;
            String time = ctime.get(i);
            for(int j = 0; j < cList.size(); j++){
                TbLog model = cList.get(j);
                LocalDateTime times = null;
                if(model.getCreateTime() != null){
                    times = model.getCreateTime();
                }
                
                if(time != null){
                    if(time.equals(times.format(DateTimeFormatter.ofPattern(pattern)))){
                        ccount.add(model.getId());
                        flg = false;
                        break;
                    }
                }
                
            }
            if (flg) {
                ccount.add(0L);
            }
        }
        return ccount;
    }

    public List<Long> getHours(List<TbLog> cList, List<String> ctime) {
        List<Long> ccount = new ArrayList<Long>();
        for(int i = 0; i < ctime.size(); i++){
            boolean flg = true;
            String time = ctime.get(i);
            for(int j = 0; j < cList.size(); j++){
                TbLog model = cList.get(j);
                if(time != null){
                    if(time.equals(model.getRemark())){
                        ccount.add(model.getId());
                        flg = false;
                        break;
                    }
                }
                
            }
            if (flg) {
                ccount.add(0L);
            }
        }
        return ccount;
    }

    /**
     * 比较两个日期 大小
     * 
     * @param nowDate
     *            日期1
     * @param endDate
     *            日期2
     * @return nowDate<endDate 返回true,否则返回false
     */
    public static boolean compare_date(String nowDate, String endDate) {

        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(nowDate);
            Date dt2 = df.parse(endDate);
            if (dt1.getTime() <= dt2.getTime()) {
                return true;
            } else if (dt1.getTime() > dt2.getTime()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 日期加一天
     * 
     * @param s
     *            要增加的日期
     * @param n
     *            要增加的天数
     * @return String 增加n填后的日期
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);// 增加一天
            // cd.add(Calendar.MONTH, n);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }
    }

    public List<TbLog> getProductTop10(List<TbLog> productTop10, String startTime, String endTime) {
        List<String> productids = new ArrayList<>();
        for(TbLog log : productTop10){
            SysGoods goods = new SysGoods();
            goods.setProductId(log.getProductId());
            SysGoods model = sm.getMapper(SysGoodsDao.class).templateOne(goods);
            log.setRemark(model.getName());
            productids.add(log.getProductId());
        }
        int size = 10 - productTop10.size();
        if(size > 0){
            List<SysGoods> goodsList = sm.getMapper(SysGoodsDao.class).getProductBySize(size);
            for(int i = 0; i < size; i++){
                if(goodsList.size() > i && !productids.contains(goodsList.get(i).getProductId())){
                    SysGoods goods = goodsList.get(i);
                    TbLog log = new TbLog();
                    log.setId(0L);
                    log.setRemark(goods.getName());
                    productTop10.add(log);
                }
            }
        }
        return productTop10;
    }

    public List<TbLog> getVideoTop10(List<TbLog> videoTop10ByTime, String startTime, String endTime) {
        List<String> videoids = new ArrayList<>();
        for(TbLog log : videoTop10ByTime){
            SysVideo video = new SysVideo();
            video.setVideoId(log.getVideoId());
            SysVideo model = sm.getMapper(SysVideoDao.class).templateOne(video);
            log.setRemark(model.getContent());
            videoids.add(log.getVideoId());
        }
        int size = 10 - videoTop10ByTime.size();
        if(size > 0){
            List<SysVideo> videoList = sm.getMapper(SysVideoDao.class).getVideoBySize(size);
            for(int i = 0; i < size; i++){
                if(videoList.size() > i && !videoids.contains(videoList.get(i).getVideoId())){
                    SysVideo video = videoList.get(i);
                    TbLog log = new TbLog();
                    log.setId(0L);
                    log.setRemark(video.getContent());
                    videoTop10ByTime.add(log);
                }
            }
        }
        return videoTop10ByTime;
    }
    
}
