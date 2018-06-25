package com.haitai.haitaitv.module.back.admin.sysVideoType;

import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.back.common.BaseBackService;
import java.time.LocalDateTime;
import java.util.*;

public class SysVideoTypeService extends BaseBackService {

    private SysVideoTypeDao dao = sm.getMapper(SysVideoTypeDao.class);

    public void save(SysVideoType model) {
        LocalDateTime now = LocalDateTime.now();
        if(model.getId() != null){
            model.setUpdateTime(now);
            model.setDelFlag(2);
            dao.updateTemplateById(model);
        }else{//新增
            model.setId(null);
            model.setUpdateTime(now);
            model.setCreateTime(now);
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
        List<SysVideoType> list = new ArrayList<>(deleteIds.size());
        LocalDateTime now = LocalDateTime.now();
        for (Integer id : deleteIds) {
            SysVideoType video = dao.single(id);
            video.setUpdateTime(now);
            video.setDelFlag(1);
            dao.updateTemplateById(video);
            list.add(video);
        }
        return list.size();
    }

}
