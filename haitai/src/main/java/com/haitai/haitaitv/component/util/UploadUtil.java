package com.haitai.haitaitv.component.util;

import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UploadUtil {

    public static final int UPLOAD_MAX = 200 * 1024 * 1024;

    /**
     * 上传临时目录
     */
    public static final String UPLOAD_TMP_PATH = "tmp";

    public static final String UPLOAD_PATH = PathKit.getWebRootPath();

    /**
     * 根路径下的download目录路径
     */
    public static final String UPLOAD_DOWNLOAD_PATH = PathKit.getWebRootPath() + File.separator + "download";

    /**
     * 文章图片目录
     */
    public static final String ARICLE_PATH = "download" + File.separator + "image_url";
    /**
     * 文章图片全目录
     */
    public static final String UPLOAD_ARICLE_PATH = PathKit.getWebRootPath() + File.separator + ARICLE_PATH;

    /**
     * 栏目滚动图片目录
     */
    public static final String ROLL_IMAGE_PATH = "download" + File.separator + "roll_image";
    /**
     * 栏目滚动图片全目录
     */
    public static final String UPLOAD_ROLL_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + ROLL_IMAGE_PATH;

    /**
     * 文章文件目录
     */
    public static final String FILE_PATH = "download" + File.separator + "file_url";
    /**
     * 文章文件全目录
     */
    public static final String UPLOAD_FILE_PATH = PathKit.getWebRootPath() + File.separator + FILE_PATH;

    /**
     * Apk目录
     */
    public static final String APK_PATH = "download" + File.separator + "apk_url";
    /**
     * Apk全目录
     */
    public static final String UPLOAD_APK_PATH = PathKit.getWebRootPath() + File.separator + APK_PATH;

    /**
     * 图片目录
     */
    public static final String IMAGE_PATH = "download" + File.separator + "image";

    /**
     * 图片全目录
     */
    public static final String UPLOAD_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + IMAGE_PATH;

    /**
     * 专辑图片目录
     */
    public static final String ALBUM_IMAGE_PATH = "download" + File.separator + "album_image";

    /**
     * 专辑图片全目录
     */
    public static final String UPLOAD_ALBUM_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + ALBUM_IMAGE_PATH;

    /**
     * 视频目录
     */
    public static final String VIDEO_PATH = "download" + File.separator + "video";

    /**
     * 视频全目录
     */
    public static final String UPLOAD_VIDEO_PATH = PathKit.getWebRootPath() + File.separator + VIDEO_PATH;

    /**
     * 视频海报目录
     */
    public static final String VIDEO_IMAGE_PATH = "download" + File.separator + "video_image";

    /**
     * 视频海报全目录
     */
    public static final String UPLOAD_VIDEO_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + VIDEO_IMAGE_PATH;

    /**
     * 商品图目录
     */
    public static final String GOODS_IMAGE_PATH = "download" + File.separator + "goods_image";

    /**
     * 商品声波目录
     */
    public static final String GOODS_AUDIO_PATH = "download" + File.separator + "audio";

    /**
     * 商品图全目录
     */
    public static final String UPLOAD_GOODS_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + GOODS_IMAGE_PATH;

    /**
     * 商品超声波全目录
     */
    public static final String UPLOAD_GOODS_AUDIO_PATH = PathKit.getWebRootPath() + File.separator + GOODS_AUDIO_PATH;

    /**
     * 商品购买二维码目录
     */
    public static final String GOODS_QRCODE_PATH = "download" + File.separator + "qrcode_image";

    /**
     * 商品购买二维码图全目录
     */
    public static final String UPLOAD_GOODS_QRCODE_PATH = PathKit.getWebRootPath() + File.separator + GOODS_QRCODE_PATH;

    /**
     * 展品图片目录
     */
    public static final String SHOWGOODS_PATH = "download" + File.separator + "showgoods_image";
    /**
     * 展品图片全目录
     */
    public static final String UPLOAD_SHOWGOODS_PATH = PathKit.getWebRootPath() + File.separator + SHOWGOODS_PATH;

    /**
     * 启动页图目录
     */
    public static final String LOAD_IMAGE_PATH = "download" + File.separator + "load_image";

    /**
     * 商品图全目录
     */
    public static final String UPLOAD_LOAD_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + LOAD_IMAGE_PATH;

    /**
     * 彩票二维码目录
     */
    public static final String CAIPIAO_QRCODE_PATH = "download" + File.separator + "caipiao_image";

    /**
     * 彩票二维码图全目录
     */
    public static final String UPLOAD_CAIPIAO_QRCODE_PATH = PathKit.getWebRootPath() + File.separator + CAIPIAO_QRCODE_PATH;
    /**
     * 用户头像目录
     */
    public static final String USER_HEADER_PATH = "download" + File.separator + "head_img";

    /**
     * 用户头像全目录
     */
    public static final String UPLOAD_USER_HEADER_PATH = PathKit.getWebRootPath() + File.separator + USER_HEADER_PATH;
    /**
     * 导出商品的目录
     */
    public static final String EXPORT_GOODS_PATH = "download" + File.separator + "export";

    /**
     * 导出商品的全目录
     */
    public static final String UPLOAD_EXPORT_GOODS_PATH = PathKit.getWebRootPath() + File.separator + EXPORT_GOODS_PATH;

    /**
     * 首屏界面图片的目录
     */
    public static final String SCREEN_PATH = "download" + File.separator + "screen";

    /**
     * 首屏界面图片的全目录
     */
    public static final String UPLOAD_SCREEN_PATH = PathKit.getWebRootPath() + File.separator + SCREEN_PATH;

    /**
     * 发布图片的目录
     */
    public static final String PUBLISH_PATH = "download" + File.separator + "publish";

    /**
     * 发布图片的全目录
     */
    public static final String UPLOAD_PUBLISH_PATH = PathKit.getWebRootPath() + File.separator + PUBLISH_PATH;

    /**
     * dvb首页图标目录
     */
    public static final String DVB_IMAGE_PATH = "download" + File.separator + "dvb_image";

    /**
     * dvb首页图标全目录
     */
    public static final String UPLOAD_DVB_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + DVB_IMAGE_PATH;

    /**
     * dvb小窗遮罩目录
     */
    public static final String DVB_INFO_MASK_PATH = "download" + File.separator + "dvb_info_mask";

    /**
     * dvb小窗遮罩全目录
     */
    public static final String UPLOAD_DVB_INFO_MASK_PATH = PathKit.getWebRootPath() + File.separator + DVB_INFO_MASK_PATH;

    /**
     * dvb全屏遮罩目录
     */
    public static final String DVB_INFO_MASK_FULL_PATH = "download" + File.separator + "dvb_info_mask_full";

    /**
     * dvb全屏遮罩全目录
     */
    public static final String UPLOAD_DVB_INFO_MASK_FULL_PATH = PathKit.getWebRootPath() + File.separator + DVB_INFO_MASK_FULL_PATH;

    /**
     * 专题背景目录
     */
    public static final String PUBLISH_ALBUM_IMAGE_PATH = "download" + File.separator + "publish_album_image";

    /**
     * 专题背景全目录
     */
    public static final String UPLOAD_PUBLISH_ALBUM_IMAGE_PATH = PathKit.getWebRootPath() + File.separator + PUBLISH_ALBUM_IMAGE_PATH;

    /**
     * 分类图片目录
     */
    public static final String CLASSIFICATION_PATH = "download" + File.separator + "classification_image";

    /**
     * 分类图片全目录
     */
    public static final String UPLOAD_CLASSIFICATION_PATH = PathKit.getWebRootPath() + File.separator + CLASSIFICATION_PATH;

    /**
     * 千米图文商品详情图片目录
     */
    public static final String QIANMI_DETAIL_PATH = "download" + File.separator + "qianmi_detail";

    /**
     * 千米图文商品详情图片全目录
     */
    public static final String UPLOAD_QIANMI_DETAIL_PATH = PathKit.getWebRootPath() + File.separator + QIANMI_DETAIL_PATH;

    /**
     * 精选推荐图目录
     */
    public static final String HANDPICK_CONTENT_PATH = "download" + File.separator + "handpick_content";

    /**
     * 精选推荐图全目录
     */
    public static final String UPLOAD_HANDPICK_CONTENT_PATH = PathKit.getWebRootPath() + File.separator + HANDPICK_CONTENT_PATH;

    /**
     * 按钮图片目录
     */
    public static final String BUTTON_PATH = "download" + File.separator + "button";

    /**
     * 按钮图片全目录
     */
    public static final String UPLOAD_BUTTON_PATH = PathKit.getWebRootPath() + File.separator + BUTTON_PATH;

    /**
     * 手动数据同步目录
     */
    public static final String DATA_SYNCH_PATH = "download" + File.separator + "data_synch";

    /**
     * 手动数据同步全目录
     */
    public static final String UPLOAD_DATA_SYNCH_PATH = PathKit.getWebRootPath() + File.separator + DATA_SYNCH_PATH;

    private UploadUtil() {
    }

    /**
     * 重命名
     */
    public static String renameFile(String path, UploadFile uploadFile) {
        return renameFileHelper(path, uploadFile, null, null);
    }

    /**
     * 按参数文件名重命名
     */
    public static String renameFile(String path, UploadFile uploadFile, String myFileName) {
        return renameFileHelper(path, uploadFile, myFileName, null);
    }

    /**
     * 按参数文件名及参数文件后缀重命名
     */
    public static String renameFile(String path, UploadFile uploadFile, String myFileName, String suf) {
        return renameFileHelper(path, uploadFile, myFileName, suf);
    }

    private static String renameFileHelper(String path, UploadFile uploadFile, String myFileName, String suf) {
        File uploadPath = new File(path);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        if (suf == null) {
            suf = "";
            String uploadFileName = uploadFile.getFileName();
            int index = uploadFileName.lastIndexOf(".");
            if (index >= 0) {
                suf = uploadFileName.substring(index);
            }
        }
        if (myFileName == null) {
            myFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_" + new SecureRandom().nextInt(999999) + suf;
        } else {
            myFileName += suf;
        }
        File file = new File(path + File.separator + myFileName);
        file.delete();
        uploadFile.getFile().renameTo(file);
        return myFileName;
    }

    public static void saveFile(String path, String strUrl, String fileName) {
        byte[] btImg = getImageFromNetByUrl(strUrl);
        if (btImg == null) {
            return;
        }
        //存到公网
        String imgFullPath = path;// UploadUtil.UPLOAD_USER_HEADER_PATH;
        File file = new File(imgFullPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(imgFullPath + File.separator + fileName);
        FileOutputStream fops;
        try {
            fops = new FileOutputStream(file);
            fops.write(btImg);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            //System.out.println("length:"+btImg.length);
            return readInputStream(inStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
