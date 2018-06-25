package com.haitai.haitaitv.module.back.admin.supplier;

import com.haitai.haitaitv.common.entity.*;
import com.haitai.haitaitv.common.repository.*;
import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.module.back.common.BaseBackService;
import java.util.*;

public class SysSupplierService extends BaseBackService {

    private SysSupplierDao dao = sm.getMapper(SysSupplierDao.class);

    public void save(SysSupplier model) {
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
        List<SysSupplier> list = new ArrayList<>(deleteIds.size());
        for (Integer id : deleteIds) {
            SysSupplier video = dao.single(id);
            video.setDelFlag(1);
            dao.updateTemplateById(video);
            list.add(video);
        }
        return list.size();
    }

}
