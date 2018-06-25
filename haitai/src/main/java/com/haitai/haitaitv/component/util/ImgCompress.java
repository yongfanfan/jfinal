package com.haitai.haitaitv.component.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;

import com.haitai.haitaitv.component.constant.ConfigConsts;

public class ImgCompress {
    static String path;
    private Image img;  
    private static int width;  
    private static int height;  
    @SuppressWarnings("deprecation")  
    public static void main(String[] args) throws Exception {  
        System.out.println("开始：" + new Date().toLocaleString());  
        new ImgCompress("E:\\test (2).png", "E:\\test (2).jpg");  
        System.out.println("结束：" + new Date().toLocaleString());  
    }  
    /** 
     * 构造函数 
     * filepath:开始路径
     * nfilepath:要保存新的路径
     * 
     */  
    public ImgCompress(String filepath, String nfilepath) throws IOException {  
        if (path.startsWith("http")) {
            byte[] len = UploadUtil.getImageFromNetByUrl(path);
            if(len.length < ConfigConsts.MAX_LENGTH){
                return;
            }
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            img = ImageIO.read(conn.getInputStream()); 
        }else{
            File file = new File(filepath);// 读入文件  
            if(file.length() < ConfigConsts.MAX_LENGTH){
                return;
            }
            img = ImageIO.read(file); 
        }
        
        width = img.getWidth(null);    // 得到源图宽  
        height = img.getHeight(null);  // 得到源图长  
        path = nfilepath;
        resizeFix(width, height);  
    }  
    /** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     */  
    public void resizeFix(int w, int h) throws IOException {  
        if (width / height > w / h) {  
            resizeByWidth(w);  
        } else {  
            resizeByHeight(h);  
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     */  
    public void resizeByWidth(int w) throws IOException {  
        int h = (int) (height * w / width);  
        resize(w, h);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     */  
    public void resizeByHeight(int h) throws IOException {  
        int w = (int) (width * h / height);  
        resize(w, h);  
    }  
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param w int 新宽度 
     * @param h int 新高度 
     */  
    public void resize(int w, int h) throws IOException {  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
//        File destFile = new File("C:\\temp\\456.jpg");  
//        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
//        // 可以正常实现bmp、png、gif转jpg  
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
//        encoder.encode(image); // JPEG编码  
//        out.close();  
        
        image.getGraphics().drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
       
             ImageIO.write(image, "jpg",new File(path));
    }  
}
