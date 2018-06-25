package com.haitai.haitaitv.module.front.common;

import com.haitai.haitaitv.component.constant.QiniuConsts;
import com.haitai.haitaitv.component.constant.operator.IcntvConsts;
import com.haitai.haitaitv.component.jfinal.base.BaseService;
import com.haitai.haitaitv.component.util.BeanUtil;
import com.haitai.haitaitv.component.util.StrUtil;
import org.beetl.sql.core.engine.PageQuery;

/**
 * @author liuzhou
 *         create at 2017-05-03 17:27
 */
public class BaseFrontService extends BaseService {

    /**
     * 前端接口获取bean的默认图片路径
     */
    public static String getImage(String basePath, Object bean) {
        return getImage(basePath, bean, "imageNetUrl", "imageUrl");
    }

    /**
     * 当七牛启用时，才优先使用网络图片（否则只会使用本地图片）
     *
     * @param netUrlAttr 网络图片字段
     * @param urlAttr    本地图片字段
     */
    public static String getImage(String basePath, Object bean, String netUrlAttr, String urlAttr) {
        String imageUrl = null;
        if (QiniuConsts.QINIU_ENABLED) {
            imageUrl = BeanUtil.get(bean, netUrlAttr);
        }
        if (StrUtil.isEmpty(imageUrl)) {
            imageUrl = BeanUtil.get(bean, urlAttr);
            if (StrUtil.isNotEmpty(imageUrl)) {
                imageUrl = basePath + imageUrl;
                imageUrl = imageUrl.replaceAll("\\\\", "\\/");
            }
        } else if (IcntvConsts.FLAG) {
            // 启用七牛时，未来服务器需替换域名
            imageUrl = imageUrl.replaceAll("resource.tvcms.haitai.tv", "resource.a008.ottcn.com");
        }
        return imageUrl;
    }

    /**
     * 获取分页查询对象
     */
    protected <T> PageQuery<T> getPageQuery(Object paras, int pageNumber, int pageSize, String orderBy) {
        PageQuery<T> pageQuery = new PageQuery<>(pageNumber, paras);
        pageQuery.setPageSize(pageSize);
        pageQuery.setOrderBy(orderBy);
        return pageQuery;
    }

   
}
