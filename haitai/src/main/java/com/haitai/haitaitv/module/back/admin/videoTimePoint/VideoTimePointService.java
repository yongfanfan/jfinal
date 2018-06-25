package com.haitai.haitaitv.module.back.admin.videoTimePoint;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.module.back.common.BaseBackService;
import com.haitai.haitaitv.module.back.common.DwzDTO;

public class VideoTimePointService extends BaseBackService {

    private TbVideoTimePointDao dao = sm.getMapper(TbVideoTimePointDao.class);

    public DwzDTO saveEdit(TbVideoTimePoint model, String productId) {
        if(StringUtils.isBlank(productId)){
            return dwzFail("请选择商品，修改失败");
        }
        if(model == null || model.getId() == null){
            return dwzFail("改视频不存在");
        }
        model.setUpdateTime(LocalDateTime.now());
        model.setProductId(productId);
        dao.updateTemplateById(model);
        return dwzSuccess("修改成功");
    }

    public int delete(String[] ids) {
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        for(String id : ids){
            TbVideoTimePoint model = new TbVideoTimePoint();
            model.setId(Long.parseLong(id));
            model.setDelFlag(2);
            TbVideoTimePoint exist = dao.templateOne(model);
            if(exist != null){
                model.setDelFlag(1);
                model.setUpdateTime(now);
                dao.updateTemplateById(model);
                count = count + 1;
            }
        }
        return count;
    }

    public DwzDTO saveAdd(TbVideoTimePoint model) {
        LocalDateTime now = LocalDateTime.now();
        if(model == null || model.getVideoId() == null){
            return dwzFail("添加失败，请重新选择");
        }
        TbVideoTimePoint old = new TbVideoTimePoint();
        old.setVideoId(model.getVideoId());
        old.setTimePoint(model.getTimePoint());
        TbVideoTimePoint exist = dao.templateOne(old);
        model.setDelFlag(2);
        model.setAllFlag(0);
        model.setUpdateTime(now);
        if(exist == null){
            model.setCreateTime(now);
            dao.insertTemplate(model);
        }else{
            model.setId(exist.getId());
            dao.updateTemplateById(model);
        }
        return dwzSuccess("添加成功");
    }

    public JSONObject saveAdd(String[] goodsIds, String[] timePoints, String[] durations, String[] lefts, String[] tops,
            String videoId) {
        LocalDateTime now = LocalDateTime.now();
        JSONObject json = new JSONObject();
        if(StringUtils.isBlank(videoId) || goodsIds == null || timePoints == null || durations == null || lefts == null || tops == null){
            json.put("msg", "选择错误");
            json.put("status", 0);
            return json;
        }
        int count = 0;
        int update = 0;
        for(int i = 0; i < goodsIds.length; i++){
            String productId = goodsIds[i];
            String timePoint = timePoints[i];
            String duration = durations[i];
            String left = lefts[i];
            String top = tops[i];
            TbVideoTimePoint model = new TbVideoTimePoint();
            model.setVideoId(videoId);
            model.setAllFlag(0);
            model.setTimePoint(timePoint);
            TbVideoTimePoint exist = dao.templateOne(model);
            model.setCreateTime(now);
            model.setUpdateTime(now);
            model.setDelFlag(2);
            model.setProductId(productId);
            model.setDuration(duration);
            model.setLeft(left);
            model.setTop(top);
            if(exist == null){
                dao.insertTemplate(model);
                count = count + 1;
            }else{
                model.setId(exist.getId());
                dao.updateTemplateById(model);
                update = update + 1;
            }
        }
        json.put("msg", "成功添加了" + count +"条，成功更新了" + update +"条。");
        json.put("status", 1);
        return json;
    }

   
}
