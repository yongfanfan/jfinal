package com.haitai.haitaitv.component.util;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.component.exception.MyException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Random;

/**
 * 超声波工具类
 *
 * @author liuzhou
 *         create at 2017-01-04 9:26
 */
public class SoundnetUtil {

    private static final Logger log = LogManager.getLogger(SoundnetUtil.class);
    private static String groupID = "D6";
    private static String merID = "M1600000058";
    private static String apiAuthCode = "0b81ba0559d42d961d240727fe389db7";

    public static String setData(String ID, String title, String value1, String value2, String notes, int isValid) {
        JSONObject queryData = new JSONObject(true);
        queryData.put("groupID", groupID);
        queryData.put("ID", ID);
        queryData.put("title", title);
        queryData.put("action", "url");
        queryData.put("value1", value1);
        queryData.put("value2", value2);
        queryData.put("notes", notes);
        // String validDate = dateFormat.format(new Date());
        queryData.put("validDate", "2029/12/31 23:59:59");
        queryData.put("isValid", isValid);
        String sessID = createRandomString(16);
        queryData.put("sessID", sessID);
        queryData.put("merID", merID);
        String validateCode = sha1(merID, apiAuthCode, sessID);
        queryData.put("validateCode", validateCode);
        queryData.put("lang", "zh-Hans");
        // System.out.println(queryData.toString());
        String responseStr = UrlGetUtils.sendPost("http://www.swipy.com.cn/swipygo/setData.php", queryData.toString(), "utf-8");
        // System.out.println(responseStr);
        JSONObject responseData = JSONObject.parseObject(responseStr);
        if (StrUtil.isNotEmpty(responseData.getString("errorMessage"))) {
            log.warn(responseStr);
            throw new MyException(responseData.getString("errorMessage"));
        }
        String checkValue = sha1(merID, apiAuthCode, sessID, responseData.getString("soundGroup"), responseData.getString("soundID"));
        if (!checkValue.equals(responseData.getString("checkValue"))) {
            log.warn("检查码校验失败！返回数据为：" + responseStr);
            throw new MyException("超声波提供方返回的检查码校验失败！");
        }
        ID = responseData.getString("soundID");
        if (StrUtil.isEmpty(ID)) {
            throw new MyException("超声波ID获取失败，无法获取超声波文件");
        }
        return ID;
    }

    public static String downloadSound(String ID, File file) {
        JSONObject queryData = new JSONObject(true);
        queryData.put("groupID", groupID);
        queryData.put("ID", ID);
        queryData.put("type", "1");
        String sessID = createRandomString(16);
        queryData.put("sessID", sessID);
        queryData.put("merID", merID);
        String validateCode = sha1(merID, apiAuthCode, sessID);
        queryData.put("validateCode", validateCode);
        queryData.put("lang", "zh-Hans");
        // 下载文件
        return UrlGetUtils.downloadFile("http://www.swipy.com.cn/swipygo/downloadSound.php", queryData.toString(), "utf-8", file);
    }

    private static String createRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String randomCandidate = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(randomCandidate.length());
            sb.append(randomCandidate.charAt(number));
        }
        return sb.toString();
    }

    private static String sha1(String s, String... strs) {
        StringBuilder sb = new StringBuilder(s);
        for (String str : strs) {
            sb.append(str);
        }
        return DigestUtils.sha1Hex(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(sha1(merID, apiAuthCode, "tzk7zt3etiq2i69p", groupID, "1123"));
    }

}
