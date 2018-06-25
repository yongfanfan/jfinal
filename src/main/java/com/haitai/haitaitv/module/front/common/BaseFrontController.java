package com.haitai.haitaitv.module.front.common;

import com.haitai.haitaitv.component.beetl.function.HaitaiUtil;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.base.BaseController;
import com.haitai.haitaitv.component.jfinal.interceptor.AccessTokenInterceptor;
import com.haitai.haitaitv.component.util.StrUtil;

/**
 * 封装所有前端控制器的通用属性与操作
 *
 * @author liuzhou
 *         create at 2017-03-26 20:30
 */
public abstract class BaseFrontController extends BaseController {

    protected final FrontCacheService frontCacheService = FrontCacheService.INSTANCE;

    /**
     * 获取bean的默认图片路径
     * 与HaitaiUtil的同名方法的区别是，若url不以http开头，将拼接basePath
     */
    public String getImage(Object bean) {
        return getImage(bean, "imageNetUrl", "imageUrl");
    }

    /**
     * 当七牛启用时，才优先使用网络图片（否则只会使用本地图片）
     * 与HaitaiUtil的同名方法的区别是，若url不以http开头，将拼接basePath
     *
     * @param netUrlAttr 网络图片字段
     * @param urlAttr    本地图片字段
     */
    public String getImage(Object bean, String netUrlAttr, String urlAttr) {
        String imageUrl = HaitaiUtil.getImage(bean, netUrlAttr, urlAttr);
        if (StrUtil.isNotEmpty(imageUrl) && !imageUrl.startsWith("http")) {
            imageUrl = getBasePath() + imageUrl;
        }
        return imageUrl;
    }
}
