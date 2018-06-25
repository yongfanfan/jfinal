package com.haitai.haitaitv.component.es;

import java.io.IOException;
import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseToEnglish {

    // 将汉字转换为全拼
    public static String getPingYin(String src) {
        if (src == null || src.equals("")) {
            return "";
        }

        char[] t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符  
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4.append(t2[0]);
                } else
                    t4.append(Character.toString(t1[i]));
            }
            // System.out.println(t4);  
            return t4.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4.toString();
    }

    // 返回中文的首字母  
    public static String getPinYinHeadChar(String src) {
        if (src == null || src.equals("")) {
            return "";
        }

        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < src.length(); j++) {
            char word = src.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    // 将字符串转移为ASCII码  
    private static String getCnASCII(String src) {
        StringBuilder strBuf = new StringBuilder();
        byte[] bGBK = src.getBytes();
        for (byte aBGBK : bGBK) {
            strBuf.append(Integer.toHexString(aBGBK & 0xff));
        }
        return strBuf.toString();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        java.util.List<String> com = new java.util.ArrayList<String>(Arrays.asList(new String[]{"E://MyDownloads//Download//casperjs//casperjs-casperjs-cd1fab5//batchbin//casperjs.bat","E://test.js"
                ,"file:///C://Users//Administrator//Desktop//demo.html","E://test.jpg"}));
        Process p = new ProcessBuilder(com).start();
//        Runtime.getRuntime().exec(new String[]{"E://MyDownloads//Download//casperjs//casperjs-casperjs-cd1fab5//batchbin//casperjs.bat","E://test.js"
//                ,"file:///C://Users//Administrator//Desktop//demo.html","E://test.jpg"});
    }

//    public static void main(String[] args) {
//        System.out.println(getPingYin("均居qq县"));
//        System.out.println(getPinYinHeadChar("均居s江县"));
//        System.out.println(getCnASCII("均居江县"));
//    }
}
