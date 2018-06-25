package com.haitai.haitaitv.component.util.encryption;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * java8 base64 api 示例
 *
 * @author liuzhou
 *         create at 2017-03-06 17:30
 */
public class Base64Demo {

    /**
     * Basic编码是标准的BASE64编码，用于处理常规的需求：输出的内容不添加换行符，而且输出的内容由字母加数字组成
     */
    public static void basic() throws UnsupportedEncodingException {
        // 编码
        String asB64 = Base64.getEncoder().encodeToString("some string".getBytes("utf-8"));
        System.out.println(asB64); // 输出为: c29tZSBzdHJpbmc=

        // 解码
        byte[] asBytes = Base64.getDecoder().decode("c29tZSBzdHJpbmc=");
        System.out.println(new String(asBytes, "utf-8")); // 输出为: some string
    }

    /**
     * URL编码也是我们经常会面对的需求，但由于URL对反斜线“/”有特殊的意义，因此URL编码需要替换掉它，使用下划线替换
     * 如果是使用基本的编码器，那么输出可能会包含反斜线“/”字符，但是如果使用URL编码器，那么输出的内容对URL来说是安全的
     */
    public static void url() throws UnsupportedEncodingException {
        String basicEncoded = Base64.getEncoder().encodeToString("subjects?abcd".getBytes("utf-8"));
        System.out.println("Using Basic Alphabet: " + basicEncoded);

        String urlEncoded = Base64.getUrlEncoder().encodeToString("subjects?abcd".getBytes("utf-8"));
        System.out.println("Using URL Alphabet: " + urlEncoded);
        // 输出为:
        // Using Basic Alphabet: c3ViamVjdHM/YWJjZA==
        // Using URL Alphabet: c3ViamVjdHM_YWJjZA==
    }

    /**
     * MIME编码器会使用基本的字母数字产生BASE64输出，而且对MIME格式友好：每一行输出不超过76个字符，而且每行以“\r\n”符结束
     */
    public static void mime() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < 10; ++t) {
            sb.append(UUID.randomUUID().toString());
        }

        byte[] toEncode = sb.toString().getBytes("utf-8");
        String mimeEncoded = Base64.getMimeEncoder().encodeToString(toEncode);
        System.out.println(mimeEncoded);
        // 输出示例:
        // NDU5ZTFkNDEtMDVlNy00MDFiLTk3YjgtMWRlMmRkMWEzMzc5YTJkZmEzY2YtM2Y2My00Y2Q4LTk5
        // ZmYtMTU1NzY0MWM5Zjk4ODA5ZjVjOGUtOGMxNi00ZmVjLTgyZjctNmVjYTU5MTAxZWUyNjQ1MjJj
        // NDMtYzA0MC00MjExLTk0NWMtYmFiZGRlNDk5OTZhMDMxZGE5ZTYtZWVhYS00OGFmLTlhMjgtMDM1
        // ZjAyY2QxNDUyOWZiMjI3NDctNmI3OC00YjgyLThiZGQtM2MyY2E3ZGNjYmIxOTQ1MDVkOGQtMzIz
        // Yi00MDg0LWE0ZmItYzkwMGEzNDUxZTIwOTllZTJiYjctMWI3MS00YmQzLTgyYjUtZGRmYmYxNDA4
        // Mjg3YTMxZjMxZmMtYTdmYy00YzMyLTkyNzktZTc2ZDc5ZWU4N2M5ZDU1NmQ4NWYtMDkwOC00YjIy
        // LWIwYWItMzJiYmZmM2M0OTBm
    }

    /**
     * java.util.Base64类封装了所有的BASE64编码器和解码器，还支持流的封装
     * 这是一个非常优雅的构造——包括编码和效率都很高（无需缓冲Buffer）——即编码器和解码器的输入和输出无需缓冲Buffer
     */
    public static void wrapping() throws IOException {
        String src = "This is the content of any resource read from somewhere" +
                " into a stream. This can be text, image, video or any other stream.";

        // 编码器封装OutputStream, 文件/tmp/buff-base64.txt的内容是BASE64编码的形式
        try (OutputStream os = Base64.getEncoder().wrap(new FileOutputStream("D:/buff-base64.txt"))) {
            os.write(src.getBytes("utf-8"));
        }

        // 解码器封装InputStream, 以及以流的方式解码, 无需缓冲
        try (InputStream is = Base64.getDecoder().wrap(new FileInputStream("D:/buff-base64.txt"))) {
            int len;
            byte[] bytes = new byte[100];
            while ((len = is.read(bytes)) != -1) {
                System.out.print(new String(bytes, 0, len, "utf-8"));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        wrapping();
    }

}
