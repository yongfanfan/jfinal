package com.haitai.haitaitv.component.util;

import com.haitai.haitaitv.component.constant.QiniuConsts;
import com.jfinal.upload.UploadFile;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.File;

/**
 * 七牛上传工具类
 */
public class QiniuUtil {

    // 是否在本地上传图片的时候也向七牛上传
    private static final boolean QINIU_UPLOAD_ENALBE = StrUtil.isNotEmpty(QiniuConsts.QINIU_HOST);
    //要上传的空间
    private static final String BUCKET = "tvcms";
    //密钥配置
    private static Auth auth;
    //创建上传对象
    private static UploadManager uploadManager;

    static {
        if (QINIU_UPLOAD_ENALBE) {
            auth = Auth.create(QiniuConsts.QINIU_ACCESS_KEY, QiniuConsts.QINIU_ACCESS_SECRET);
            // 采用默认配置，机房自动判断
            uploadManager = new UploadManager(new Configuration());
        }
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private static String getUpToken() {
        return auth.uploadToken(BUCKET);
    }

    public static String upload(File file) throws QiniuException {
        if (!QINIU_UPLOAD_ENALBE) {
            // 无须上传到七牛
            return null;
        }
        // 调用put方法上传
        String token = getUpToken();
        Response res = uploadManager.put(file, null, token);
        // 返回的信息
        StringMap stringMap = res.jsonToMap();
        return (String) stringMap.get("key");
    }


    public static String upload(UploadFile uploadFile) throws QiniuException {
        return upload(uploadFile.getFile());
    }
}