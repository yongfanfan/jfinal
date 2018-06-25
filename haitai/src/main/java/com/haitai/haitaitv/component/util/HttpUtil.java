package com.haitai.haitaitv.component.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by harrid on 2016/6/20.
 * 通过url地址请求外部数据同步
 */
public class HttpUtil {

    private static final Logger LOG = LogManager.getLogger(HttpUtil.class);

    private HttpUtil() {
    }

    /**
     * 根据网址，返回JSONObject对象
     * 注：只适合请求响应为json格式网址
     *
     * @param spec        来源网址
     * @param charsetName 编码方式
     */
    public static JSONObject getJsonObj(String spec, String charsetName) {
        String response = httpRequest(spec, charsetName, null);
        if (response == null) {
            return null;
        }
        if (response.charAt(0) == '[') {
            response = response.substring(1, response.length() - 1);
        }
        return JSONObject.parseObject(response);
    }

    /**
     * 根据网址，返回JSONObject对象
     * 注：只适合请求响应为json格式网址
     *
     * @param spec        来源网址
     * @param charsetName 编码方式
     * @param data        POST数据
     */
    public static JSONObject getJsonObjByPost(String spec, String charsetName, String data) {
        String response = httpRequest(spec, charsetName, data);
        if (response == null) {
            return null;
        }
        if (response.charAt(0) == '[') {
            response = response.substring(1, response.length() - 1);
        }
        return JSONObject.parseObject(response);
    }

    /**
     * 发送http请求
     *
     * @param spec        连接网址，get请求需自行拼接请求参数
     * @param charsetName 字符集
     * @param data        为空则发送get请求，否则发送post请求
     * @return 响应字符串
     */
    public static String httpRequest(String spec, String charsetName, String data) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(spec);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (data != null) {
                connection.setConnectTimeout(1000 * 5);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data.getBytes(charsetName));
                outputStream.flush();
                outputStream.close();
            } else {
                connection.setConnectTimeout(1000);
            }
            connection.connect();
            int status = connection.getResponseCode();
            if (status != 200) {
                LOG.warn("http请求失败，状态码为：" + status);
            }
            inputStream = connection.getInputStream();
            String contentEncode = connection.getContentEncoding();
            if (contentEncode != null && contentEncode.compareTo("gzip") == 0) {
                inputStream = new GZIPInputStream(inputStream);
            }
            inputStreamReader = new InputStreamReader(inputStream, charsetName);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder content = new StringBuilder();
            String line; // 存放每行内容
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            connection.disconnect();
            return content.toString();
        } catch (SocketTimeoutException e) {
            LOG.error("连接超时!!!");
        } catch (Exception e) {
            LOG.error("连接网址不对或读取流出现异常!!!");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!");
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!");
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!");
                }
            }
        }
        return null;
    }

}
