package com.haitai.haitaitv.component.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用ffmpeg进行视频处理
 * 注意需要先将ffmpeg所在路径加入环境变量
 *
 * @author liuzhou
 *         create at 2016-12-19 15:18
 */
public class FfmpegUtil {

    /**
     * 命令：ffmpeg -ss 5 -t 0.001 -i 45014_2300k_1920x1080.mp4 -f image2 -y image.jpg
     *
     * @param inputPath      作为输入的视频所在路径
     * @param outputPath     作为输出的缩略图路径
     * @param screenshotFlag 是否截缩略图
     * @return -1表示调用失败
     */
    public static Map<String, Object> processVideo(String inputPath, String outputPath, boolean screenshotFlag) {
        String result = excuteShell(inputPath);
        if (result == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("Duration: (.*?), start:.*?, bitrate: (.*?) .*?Video:.*?,.*?, (.*?)[ ,]");
        Matcher matcher = pattern.matcher(result);
        Map<String, Object> map = new HashMap<>();
        if (matcher.find()) {
            Integer duration = getTime(matcher.group(1));
            map.put("duration", duration);
            try {
                map.put("bitrate", Integer.valueOf(matcher.group(2)));
            } catch (Exception e) {
                map.put("bitrate", 0);
            }
            map.put("resolution", matcher.group(3));
            // 截缩略图
            if (duration > 13 && screenshotFlag) {
                excuteShell(inputPath, outputPath, duration - 13);
            }
            return map;
        } else {
            return null;
        }
    }

    /**
     * 获取视频信息，命令：ffmpeg -i 输入
     */
    private static String excuteShell(String inputPath) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(inputPath);
        return ShellUtil.excuteWithResult(command);
    }

    /*
     * 执行shell命令：ffmpeg -ss 5 -t 0.001 -i 输入 -f image2 -s 640x360 -y 输出
     */
    private static void excuteShell(String inputPath, String outputPath, int start) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add(start + "");
        command.add("-t");
        command.add("0.001");
        command.add("-i");
        command.add(inputPath);
        command.add("-f");
        command.add("image2");
        command.add("-s");
        command.add("640x360");
        command.add("-y");
        command.add(outputPath);
        //String result = ShellUtil.excuteWithResult(command);
        //System.out.println(result);
        ShellUtil.excuteWithoutResult(command);
    }

    // 传入参数："00:02:15.60"
    private static Integer getTime(String time) {
        int min = 0;
        String strs[] = time.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    // ffmpeg -i 输入 -strict -2 -b:a 256k 输出 -y
    public static void wavToM4a(String input, String output) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(input);
        command.add("-strict");
        command.add("-2");
        command.add("-b:a");
        command.add("256k");
        command.add(output);
        command.add("-y");
        ShellUtil.excuteWithoutResult(command);
    }

    /*public static void main(String[] args) {
        Map<String, Object> map = processVideo("http://vod.a008.ottcn.com/otv/yfy/D/37/98/00000049814/49814_2300k_1920x1080.mp4");//, "d:/image.jpg");
        System.out.println(map);
    }*/

}
