package com.haitai.haitaitv.component.util;

//import org.im4java.core.ConvertCmd;
//import org.im4java.core.IMOperation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 图片处理Utils
 */
public class ImageUtils {

    /**
     * GraphicsMagick的路径
     */
    public static String graphicsMagickPath = null;

    static { /**获取ImageMagick的路径 */
        //Properties prop = new PropertiesFile().getPropertiesFile();
        //linux下不要设置此值，不然会报错
        //imageMagickPath = prop.getProperty("imageMagickPath");
    }

    /** * 根据坐标裁剪图片
     * @param srcPath 要裁剪图片的路径
     * @param newPath 裁剪图片后的路径
     * @param x 起始横坐标
     * @param y 起始挫坐标
     * @param x1 结束横坐标
     * @param y1 结束挫坐标
     */
    /*public static void cutImage(String srcPath, String newPath, int x, int y, int x1, int y1)
            throws Exception {
		int width = x1 - x; int height = y1 - y;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		 * width：裁剪的宽度
		 * height：裁剪的高度
		 * x：裁剪的横坐标
		 * y：裁剪的挫坐标

		op.crop(width, height, x, y);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		//linux下不要设置此值，不然会报错
		//convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}*/

    /**
     * 根据尺寸缩放图片
     * @param width 缩放后的图片宽度
     * @param height 缩放后的图片高度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     * @param type 1为比例处理，2为大小处理，如（比例：1024x1024,大小：50%x50%）
     */
	/*public static String cutImage(int width, int height, String srcPath, String newPath,int type,String quality) throws Exception {
		IMOperation op = new IMOperation();
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = "";
		if(type == 1){
			//按像素
			raw = width+"x"+height+"^";
		}else{
			//按像素百分比
			raw = width+"%x"+height+"%";
		}
		op.addRawArgs("-sample" ,  raw );
		if((quality !=null && quality.equals(""))){
			op.addRawArgs("-quality" ,  quality );
		}
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.indexOf("win") != -1) {
			//linux下不要设置此值，不然会报错
			cmd.setSearchPath("C://Program Files//GraphicsMagick-1.3.14-Q16");
		}

		try{
			cmd.run(op, srcPath, newPath);
		}catch(Exception e){
			e.printStackTrace();
		}
		return newPath;
	}*/

    /**
     * 给图片加水印
     * @param srcPath 源图片路径
     */
	/*public static void addImgText(String srcPath, String dstPath) throws Exception {
		IMOperation op = new IMOperation();
		op.font("Roboto").gravity("southeast").pointsize(22).fill("#BCBFC8").draw("text 100,100 yoxon");
		op.addImage();
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		ConvertCmd cmd = new ConvertCmd(true);
		if(osName.indexOf("win") != -1) {
			//linux下不要设置此值，不然会报错
			cmd.setSearchPath("C://Program Files//GraphicsMagick-1.3.23-Q8");
		}

		try{
			cmd.run(op, srcPath, dstPath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		//cutImage("D:\\apple870.jpg", "D:\\apple870eee.jpg",98, 48, 370, 320);
		Long start = System.currentTimeMillis();
		//cutImage(100,100, "e:\\37AF7D10F2D8448A9A5.jpg","e:\\37AF7D10F2D8448A9A5_bak2.jpg",2,"100");
		//addImgText("e:\\moer_gengduo_tu.png","e:\\moer_gengduo_tu_watermark.png");
        String[] strArray={"e:\\moer_gengduo_tu.png","e:\\focuslt.png","e:\\focusrt.png"};
        montage(strArray,218,141,"e:\\moer_gengduo_tu-f.png",400,"2");
		Long end = System.currentTimeMillis();
		System.out.println("time:"+(end-start)/3600);
	}*/

	/*public static void main(String[] args) throws FileNotFoundException, IOException {
		long start = System.currentTimeMillis();
		// ImageModel model = getIamge("D:\Downloads\SogouWP\Net\WallPaper\387162.jpg");
		ImageModel model = getIamge("http://i11.tietuku.com/d354a14b308a1473.png");
		
		System.out.println(model.getName());
		System.out.println(model.getExt());
		System.out.println(model.getSize());
		System.out.println(model.getWidth());
		System.out.println(model.getHeight());
		System.out.println("time:" + (System.currentTimeMillis() - start));
	}*/

    /**
     * 获取图片信息
     *
     * @param path
     * @return
     */
    public static ImageModel getIamge(String path) {
        ImageModel model = null;
        BufferedImage sourceImg = null;
        try {
            model = new ImageModel();
            if (path.startsWith("http")) {
                URL url = new URL(path);
                URLConnection uc = url.openConnection();
                InputStream is = null;
                try {
                    is = uc.getInputStream();
                    sourceImg = ImageIO.read(is);
                } finally {
                    if (is != null)
                        is.close();
                }
                String file = url.getFile();
                model.setName(file.replace("/", ""));
                if (file.lastIndexOf(".") >= 0) {
                    model.setExt(file.substring(file.lastIndexOf(".") + 1));
                }
                // 未设置大小
            } else {
                File picture = new File(path);
                // sourceImg = ImageIO.read(new FileInputStream(picture));
                sourceImg = ImageIO.read(picture);

                model.setName(picture.getName());
                if (path.lastIndexOf(".") >= 0) {
                    model.setExt(path.substring(path.lastIndexOf(".") + 1));
                }
                model.setSize((picture.length() / 1024.0));
            }

            model.setWidth(sourceImg.getWidth());
            model.setHeight(sourceImg.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;

    }

    /**
     * 焦点图片合成
     * @param args
     * @param maxWidth
     * @param maxHeight
     * @param newpath
     * @param mrg
     * @param type 1:横,2:竖
     */
    /*public static void montage(String[] args,Integer maxWidth,Integer maxHeight,String newpath,Integer mrg,String type) {
        IMOperation op = new IMOperation();
        String osName = System.getProperty("os.name").toLowerCase();
        ConvertCmd cmd = new ConvertCmd(true);
        if(osName.indexOf("win") != -1) {
            //linux下不要设置此值，不然会报错
            cmd.setSearchPath("C://Program Files//GraphicsMagick-1.3.23-Q8");
        }
        String thumb_size = maxWidth+"x"+maxHeight+"^";
        String extent = maxWidth+"x"+maxHeight;
        if("1".equals(type)){
            op.addRawArgs("+append");
        }else if("2".equals(type)){
            op.addRawArgs("-append");
        }

        op.addRawArgs("-thumbnail",thumb_size);
        op.addRawArgs("-gravity","center");
        op.addRawArgs("-extent",extent);

        op.addRawArgs("-border",1+"x"+1);
        op.addRawArgs("-bordercolor","#fff");

        for(String img : args){
            op.addImage(img);
        }
        if("1".equals(type)){
            Integer whole_width = ((mrg / 2) +1 + 1 + maxWidth + 1 + (mrg / 2) +1)*args.length - mrg;
            Integer whole_height = maxHeight + 1 + 1;
            op.addRawArgs("-extent",whole_width + "x" +whole_height);
        }else if("2".equals(type)){
            Integer whole_width = maxWidth + 1 + 1;
            Integer whole_height = ((mrg / 2) +1 + 1 + maxHeight + 1 + (mrg / 2) +1)*args.length - mrg;
            op.addRawArgs("-extent",whole_width + "x" +whole_height);
        }
        try {
        op.addImage(newpath);
            cmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
