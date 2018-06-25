package com.haitai.haitaitv.module.back.admin.sysGoodsType;

import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.back.common.BaseBackService;
import java.time.LocalDateTime;
import java.util.*;

public class SysGoodsTypeService extends BaseBackService {

    private SysGoodsTypeDao dao = sm.getMapper(SysGoodsTypeDao.class);

    public void save(SysGoodsType model) {
        LocalDateTime now = LocalDateTime.now();
        if(model.getId() != null){
            model.setDelFlag(2);
            dao.updateTemplateById(model);
        }else{//新增
            model.setId(null);
            model.setDelFlag(2);
            dao.insertTemplate(model);
        }
    }


    public int delete(String[] ids) {
        List<Integer> deleteIds = new ArrayList<>(ids.length);
        for (String id : ids) {
            if (StrUtil.isNotEmpty(id)) {
                deleteIds.add(Integer.parseInt(id));
            }
        }
        List<SysGoodsType> list = new ArrayList<>(deleteIds.size());
        LocalDateTime now = LocalDateTime.now();
        for (Integer id : deleteIds) {
            SysGoodsType video = dao.single(id);
            video.setDelFlag(1);
            dao.updateTemplateById(video);
            list.add(video);
        }
        return list.size();
    }

}
