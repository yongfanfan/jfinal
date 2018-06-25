package com.haitai.haitaitv.module.back.admin.videoTimePoint;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.sql.core.engine.PageQuery;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.entity.SysGoods;
import com.haitai.haitaitv.common.entity.SysVideo;
import com.haitai.haitaitv.common.entity.TbVideoTimePoint;
import com.haitai.haitaitv.common.repository.SysGoodsDao;
import com.haitai.haitaitv.common.repository.SysVideoDao;
import com.haitai.haitaitv.common.repository.TbVideoTimePointDao;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.util.UploadUtil;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.haitai.haitaitv.module.front.common.FrontCacheService;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey = "/admin/videoTimePoint")
@RequiresPermissions("admin/videoTimePoint/list:*")
public class VideoTimePointController extends BaseBackController{
    
    private TbVideoTimePointDao dao = sm.getMapper(TbVideoTimePointDao.class);
    
    @RequiresPermissions("admin/videoTimePoint/list:view")
    public void list() {
        TbVideoTimePoint attr = getBean(TbVideoTimePoint.class, "attr");
        PageQuery<TbVideoTimePoint> pageQuery = getPageQuery(attr);
        dao.page(pageQuery);
        setAttr("page", pageQuery);
        setAttr("attr", attr);
        render("main.html");
    }
    
    public void edit() {
        TbVideoTimePoint model = dao.single(getParaToInt());
        setAttr("model", model);
        SysVideo video = new SysVideo();
        video.setDelFlag(2);
        video.setVideoId(model.getVideoId());
        SysVideo oldVideo = sm.getMapper(SysVideoDao.class).templateOne(video);
        if(oldVideo != null){
            setAttr("video", oldVideo);
        }
        SysGoods goods = new SysGoods();
        goods.setDelFlag(2);
        goods.setProductId(model.getProductId());
        SysGoods oldGoods = sm.getMapper(SysGoodsDao.class).templateOne(goods);
        if(oldGoods != null){
            setAttr("goods", oldGoods);
        }
        render("edit.html");
    }
    
    public void search() {
        int type = getParaToInt();
        if(type == 1){//视频
            SysVideo attr = getBean(SysVideo.class, "attr");
            PageQuery<SysVideo> pageQuery = getPageQuery(attr);
            if (pageQuery.getOrderBy() == null) {
                pageQuery.setOrderBy("create_time desc");
            }
            sm.getMapper(SysVideoDao.class).page(pageQuery);
            setAttr("page", pageQuery);
            setAttr("attr", attr);
            render("searchVideo.html");
        }else if(type == 2){//商品
            SysGoods attr = getBean(SysGoods.class, "attr");
            PageQuery<SysGoods> pageQuery = getPageQuery(attr);
            if (pageQuery.getOrderBy() == null) {
                pageQuery.setOrderBy("update_time desc");
            }
            sm.getMapper(SysGoodsDao.class).page(pageQuery);
            setAttr("page", pageQuery);
            setAttr("attr", attr);
            render("search.html");
        }
    }
    
    public void saveEdit() {
        TbVideoTimePoint model = getBean(TbVideoTimePoint.class, "model");
        dwz(new VideoTimePointService().saveEdit(model, getPara("goods.id", "")));
        // 清空前端缓存
    }
    
    public void delete() {
        String[] ids = getParaValues("ids");
        if (ids == null || ids.length == 0) {
            dwzFail("没有勾选，无法删除");
            return;
        }
        int count = new VideoTimePointService().delete(ids);
        if(count <= 0){
            dwzFail("删除失败");
            return;
        }
        dwzSuccessNoClose("成功删除了" + count + "个");
    }
    
    public void add() {
        render("add.html");
    }
    
    public void saveAdd() {
        renderJson(new VideoTimePointService().saveAdd(getParaValues("goodsIds"), getParaValues("timePoints"), getParaValues("durations"), 
                getParaValues("lefts"), getParaValues("tops"), getPara("videoId")));
    }
}
