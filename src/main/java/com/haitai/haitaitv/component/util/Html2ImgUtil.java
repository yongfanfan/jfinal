package com.haitai.haitaitv.component.util;

import com.jfinal.kit.PathKit;

import java.util.ArrayList;
import java.util.List;

/**
 * html截图工具
 * 需要先把phantomjs、casperjs所在路径加入环境变量
 *
 * @author liuzhou
 *         create at 2017-06-27 10:44
 */
public class Html2ImgUtil {

    /**
     * 截图文件格式 ${prefix}i${postfix}
     *
     * @param url     欲截图的html地址
     * @param prefix  截图文件名前缀（含所在文件夹的全路径）
     * @param postfix 截图文件名后缀
     * @return 截图数量
     */
    public static String excute(String url, String prefix, String postfix) {
        List<String> command = new ArrayList<>();
        command.add("casperjs");
        command.add(PathKit.getRootClassPath() + "/js/capture.js");
        command.add(url);
        command.add(prefix);
        command.add(postfix);
        // System.out.println(command);
        return ShellUtil.excuteWithResult(command);
    }

}
