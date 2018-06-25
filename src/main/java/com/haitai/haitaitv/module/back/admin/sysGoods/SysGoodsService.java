package com.haitai.haitaitv.module.back.admin.sysGoods;

import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.component.constant.QiniuConsts;
import com.haitai.haitaitv.component.util.GraphicsMagickUtil;
import com.haitai.haitaitv.component.util.ImgCompress;
import com.haitai.haitaitv.component.util.Poi;
import com.haitai.haitaitv.component.util.QiniuUtil;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.component.util.UploadUtil;
import com.haitai.haitaitv.module.back.common.BaseBackService;
import com.jfinal.upload.UploadFile;
import com.qiniu.common.QiniuException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

public class SysGoodsService extends BaseBackService {

    private SysGoodsDao dao = sm.getMapper(SysGoodsDao.class);

    public void saveEdit(SysGoods model, UploadFile uploadImage) {
        //图片附件
        String imgFullPath = UploadUtil.UPLOAD_GOODS_IMAGE_PATH;
        String imgPath = UploadUtil.GOODS_IMAGE_PATH;
        if (uploadImage != null) {
            // 调整图片大小
            //GraphicsMagickUtil.processUpload(uploadImage.getFile());
            // 存七牛图片
            String key = null;
            try {
                key = QiniuUtil.upload(uploadImage.getFile());
            } catch (QiniuException e) {
                LOG.error("七牛上传图片失败", e);
            }
            if (StrUtil.isNotEmpty(key)) {
                model.setImageNetUrl(QiniuConsts.QINIU_HOST + File.separator + key);
            }
            Random random = new Random();
            String fileName = model.getProductId() + "_" + (random.nextInt(899) + 100);
            fileName = UploadUtil.renameFile(imgFullPath, uploadImage, fileName);
            model.setImageUrl(imgPath + File.separator + fileName);
            
            //压缩图片
//            try {
//                new ImgCompress(imgFullPath + File.separator + fileName, imgFullPath + File.separator + fileName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        // 更新
        model.setUpdateTime(LocalDateTime.now());
        dao.updateTemplateById(model);
    }


    public int delete(String[] ids) {
        List<Integer> deleteIds = new ArrayList<>(ids.length);
        for (String id : ids) {
            if (StrUtil.isNotEmpty(id)) {
                deleteIds.add(Integer.parseInt(id));
            }
        }
        List<SysGoods> list = new ArrayList<>(deleteIds.size());
        LocalDateTime now = LocalDateTime.now();
        for (Integer id : deleteIds) {
            SysGoods goods = dao.single(id);
            goods.setUpdateTime(now);
            goods.setDelFlag(1);
            dao.updateTemplateById(goods);
            list.add(goods);
        }
        return list.size();
    }


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
        List<SysGoods> goodsList = new ArrayList<SysGoods>();
        for (int i = 1; i < result.length; i++) {
            SysGoods goods = new SysGoods();
            goods.setId(null);
            goods.setCreateTime(now);
            goods.setUpdateTime(now);
            for (int j = 2; j < result[i].size(); j++) {
                Object cell = result[i].get(j);
                if(cell == ""){
                    continue;
                }
                if(j == 2){
                    goods.setProductId((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 3){
                    goods.setName((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 4){
                    goods.setSupplier((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 6){
                    goods.setPrice((BigDecimal)ConvertUtils.convert(cell, BigDecimal.class));
                }else if(j == 10){
                    goods.setUrl((String)ConvertUtils.convert(cell, String.class));
                }else if(j == 11){
                    goods.setGoodsId((Long)ConvertUtils.convert(cell, Long.class));
                }
            }
            if(goods.getPrice() != null){
                goodsList.add(goods);
            }
        }
        
        for(SysGoods goods : goodsList){
            goods.setId(null);
            goods.setDelFlag(2);
            if(goods.getSupplier().contains("京东")){
                goods.setSupplierId(6);
            }else if(goods.getSupplier().contains("海苔")){
                goods.setSupplierId(2);
            }else if(goods.getSupplier().contains("三佳")){
                goods.setSupplierId(3);
            }else if(goods.getSupplier().contains("中视")){
                goods.setSupplierId(4);
            }else if(goods.getSupplier().contains("海莱")){
                goods.setSupplierId(5);
            }else{
                goods.setSupplierId(1);
            }
            if(goods.getGoodsType() == null){
                goods.setGoodsType(1L);
            }
            SysGoods exist = new SysGoods();
            exist.setDelFlag(2);
            exist.setProductId(goods.getProductId());
            if(dao.templateCount(exist) <= 0){
                dao.insertTemplate(goods);
            }
        }
        
    }

}
