package com.haitai.haitaitv.module.front.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.component.cache.MyCache;
import com.haitai.haitaitv.component.cache.MyCacheKit;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.base.BaseService;
import com.haitai.haitaitv.component.util.QRCodeUtil;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.component.util.UploadUtil;
import com.jfinal.config.Constants;

import org.beetl.sql.core.engine.PageQuery;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class FrontCacheService extends BaseService {

    public static final FrontCacheService INSTANCE = new FrontCacheService();
    private final MyCache cache = MyCacheKit.get("FrontCacheService");

    private FrontCacheService() {
    }

    public JSONObject getVideoTimePoint(String videoId, String path, TbLog log) {
        return cache.orElseGet("videoTimePoint" + videoId, () -> {
            JSONObject json = new JSONObject();
            json.put("status", "2000");
            TbVideoTimePoint point = new TbVideoTimePoint();
            point.setVideoId(videoId);
            point.setAllFlag(0);
            point.setDelFlag(2);
            List<TbVideoTimePoint> videoTimePointList = sm.getMapper(TbVideoTimePointDao.class).template(point);
            if(videoTimePointList == null || videoTimePointList.size() <= 0){
                return getjson(json);
            }
            JSONArray arr = new JSONArray();
            for(TbVideoTimePoint model : videoTimePointList){
                SysGoods good = new SysGoods();
                good.setProductId(model.getProductId());
                good.setDelFlag(2);
                SysGoods goods = sm.getMapper(SysGoodsDao.class).templateOne(good);
                if(goods == null){
                    return getjson(json);
                }
                SysVideo old = new SysVideo();
                old.setVideoId(videoId);
                old.setDelFlag(2);
                SysVideo video = sm.getMapper(SysVideoDao.class).templateOne(old);
                if(video == null){
                    return getjson(json);
                }
                //
                String content = goods.getUrl();
                String destPath = UploadUtil.UPLOAD_GOODS_QRCODE_PATH;
                destPath += File.separator + "qianmi" + File.separator + model.getProductId();
                String fileName = ConfigConsts.COMMERCE_OPERATOR_ID + "_" + model.getProductId() + ".jpg";
                content += "&operaId=" + ConfigConsts.COMMERCE_OPERATOR_ID + "&count=1" + "&v=1" + "&logId=" + log.getUuid();
                
                try {
                    // 二维码内容形如http://haitai.dev02.huo.so/hitgou-weisite/shop/scan?productId=6286&operaId=888888&count=1&v=2
                    fileName = QRCodeUtil.encode(content, destPath, fileName);
                } catch (Exception e) {
                    LOG.error("生成二维码图片失败", e);
                }
                // 开头无/，使用的时候会在前面拼basePath，basePath末尾自带/
                String qrcodePath = UploadUtil.GOODS_QRCODE_PATH + File.separator + "qianmi" + File.separator + model.getProductId();
                qrcodePath += File.separator + fileName;
                qrcodePath = qrcodePath.replaceAll("\\\\", "\\/");
                //
                JSONObject template = new JSONObject();
                template.put("name", goods.getName());
                template.put("image", path + goods.getImageUrl());
                template.put("price", goods.getPrice());
                template.put("qrcode", path + qrcodePath);
                template.put("timePoint", model.getTimePoint());
                template.put("productId", model.getProductId());
                template.put("duration", model.getDuration());
                template.put("totalTime", video.getTotalTime());
                template.put("left", model.getLeft());
                template.put("top", model.getTop());
                template.put("logId", log.getUuid());
                template.put("supplierId", goods.getSupplierId() == null ? 0 : goods.getSupplierId());
                arr.add(template);
            }
            json.put("data", arr);
            return json;
        });
    }
    
    public JSONObject getjson(JSONObject json){
        json.put("data", "");
        json.put("status", "404");
        return json;
    }
}
