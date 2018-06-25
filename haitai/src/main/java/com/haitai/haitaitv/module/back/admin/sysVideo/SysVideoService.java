package com.haitai.haitaitv.module.back.admin.sysVideo;

import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.component.constant.QiniuConsts;
import com.haitai.haitaitv.component.util.GraphicsMagickUtil;
import com.haitai.haitaitv.component.util.Poi;
import com.haitai.haitaitv.component.util.QiniuUtil;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.component.util.UploadUtil;
import com.haitai.haitaitv.module.back.common.BaseBackService;
import com.jfinal.upload.UploadFile;
import com.qiniu.common.QiniuException;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

public class SysVideoService extends BaseBackService {

    private SysVideoDao dao = sm.getMapper(SysVideoDao.class);

    public void saveExcel(UploadFile uploadImage) {
        LocalDateTime now = LocalDateTime.now();
        Poi poi = new Poi();  
        System.out.println("path:"+uploadImage.getFile().getPath());
        poi.loadExcel(uploadImage.getFile().getPath());  
        poi.init();  
        LinkedList[] result = poi.show();
        if(result == null){
            return;
        }
        List<SysVideo> modelList = new ArrayList<SysVideo>();
        for (int i = 1; i < result.length; i++) {
            SysVideo model = new SysVideo();
            model.setId(null);
            model.setCreateTime(now);
            for (int j = 0; j < result[i].size(); j++) {
                Object cell = result[i].get(j);
                if(cell == ""){
                    continue;
                }
                if(j == 0){
                    model.setVideoId((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 1){
                    model.setMain((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 2){
                    model.setAuthor((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 3){
                    model.setTotalTime((Long)ConvertUtils.convert(cell, Long.class));
                }else if(j == 4){
                    model.setContent((String)ConvertUtils.convert(cell, String.class));
                }
            }
            if(model.getTotalTime() != null){
                modelList.add(model);
            }
        }
        
        for(SysVideo model : modelList){
            model.setId(null);
            model.setDelFlag(2);
            dao.insertTemplate(model);
        }
        
    }

}
