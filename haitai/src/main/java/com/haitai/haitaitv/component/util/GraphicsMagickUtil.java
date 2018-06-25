package com.haitai.haitaitv.component.util;

import com.haitai.haitaitv.component.constant.OtherConsts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 图片处理工具
 * 需要先把GraphicsMagick和pngout所在路径加入环境变量
 *
 * @author liuzhou
 *         create at 2016-12-20 15:35
 */
public class GraphicsMagickUtil {

    // private static final Log log = Log.getLog(GraphicsMagickUtil.class);

    public static void processUpload(File file) {
        if (false) {
            return;
        }
        String path = file.getAbsolutePath();
        long bytes = file.length();
        processImg(path, bytes);
    }

    /**
     * 此方法仅用于千米商品详情图批量改变宽度
     */
    public static void batchResize(String prefix, String postfix, int count) {
        // 采用并行流并行处理
        IntStream.range(0, count).parallel().forEach(i -> {
            List<String> command = new ArrayList<>();
            // 命令：gm convert -resize 690x 输入 输出
            command.add("gm");
            command.add("convert");
            command.add("-resize");
            command.add("690x");
            command.add(prefix + i + postfix);
            command.add(prefix + "dvb_" + i + postfix);
            ShellUtil.excuteWithoutResult(command);
        });
    }

    private static void processImg(String path, long bytes) {
        /*List<String> command = new ArrayList<>();
        // 命令：gm identify -format %w,%h 输入
        command.add("gm");
        command.add("identify");
        command.add("-format");
        command.add("%w,%h");
        command.add(path);
        // 范例：list.png PNG 500x500+0+0 DirectClass 8-bit 33.9Ki 0.000u 0m:0.000000s
        String result = ShellUtil.excuteWithResult(command);
        if (result == null) {
            return;
        }
        String[] sizes = result.split(",");
        if (sizes.length != 2) {
            return;
        }
        int width = Integer.valueOf(sizes[0]);
        int height = Integer.valueOf(sizes[1]);
        if (width <= 500 || height <= 500 || bytes <= 100 * 1024) {
            return;
        }*/
        // 只在图片大于100K时缩放
        if (bytes <= 100 * 1024) {
            return;
        }
        List<String> command = new ArrayList<>();
        // 经测试对比，压缩出来的图片的空间大小排序sample<thumbnail<scale
        // 命令：gm convert -size 500x500 输入 -quality 80 -sample 500x500 +profile "*" 输出
        command.add("gm");
        command.add("convert");
        command.add("-size");
        command.add("500x500");
        command.add(path);
        command.add("-quality");
        command.add("80");
        command.add("-sample");
        command.add("500x500");
        command.add("+profile");
        command.add("\"*\"");
        command.add(path);
        ShellUtil.excuteWithoutResult(command);
        if (!path.toLowerCase().endsWith(".png")) {
            // 非png格式则不再进行进一步压缩
            return;
        }
        command = new ArrayList<>();
        /*// 命令：pngout 输入 输出 -y
        command.add("pngout");
        command.add(path);
        command.add(path);
        command.add("-y");*/
        // 命令：pngquant --force --strip 输入 --output 输出
        command.add("pngquant");
        command.add("--force");
        //command.add("--strip");
        command.add(path);
        command.add("--output");
        command.add(path);
        ShellUtil.excuteWithoutResult(command);
    }

    public static void main(String[] args) {
        File file = new File("E:\\test.jpg");
        processUpload(file);
        //gm, convert, -size, 500x500, E:\test.jpg, -quality, 80, -sample, 500x500, +profile, "*", E:\test.jpg
        
    }
}
