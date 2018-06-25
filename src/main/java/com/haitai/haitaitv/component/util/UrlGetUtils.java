package com.haitai.haitaitv.component.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * 通过url地址请求外部数据同步
 */
public class UrlGetUtils {

    private final static Logger LOG = LogManager.getLogger(UrlGetUtils.class);

    /**
     * 根据网址，返回JSONObject对象
     * 注：只适合请求响应为json格式网址
     *
     * @param src  来源网址
     * @param code 编码方式
     */
    public static JSONObject getJsonObj(String src, String code) {
        //System.out.println(src);
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            URL url = new URL(src);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(1000);
            InputStream urlStream = connection.getInputStream();
            String contentEncode = connection.getContentEncoding();
            if (contentEncode != null && contentEncode.compareTo("gzip") == 0)
                urlStream = new GZIPInputStream(urlStream);
            reader = new InputStreamReader(urlStream, code);
            in = new BufferedReader(reader);
            String line = null;        //每行内容
            int lineFlag = 0;        //标记: 判断有没有数据
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                lineFlag++;
            }
            String jsonStr = content.toString();
            if (jsonStr.charAt(0) == '[')
                jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
            //System.out.println(jsonStr);
            return lineFlag == 0 ? null : JSONObject.parseObject(jsonStr);
        } catch (SocketTimeoutException e) {
            LOG.error("连接超时!!!", e);
            return null;
        } catch (JSONException e) {
            LOG.error("网站响应不是json格式，无法转化成JSONObject!!!", e);
            return null;
        } catch (Exception e) {
            LOG.error("连接网址不对或读取流出现异常!!!", e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
        }
    }

    /**
     * 根据网址，返回JSONObject对象
     * 注：只适合请求响应为json格式网址
     *
     * @param src         来源网址
     * @param data        POST数据
     * @param charsetName 编码方式
     */
    public static JSONObject getJsonObjByPost(String src, String data, String charsetName) {
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 5);
            connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            // System.out.println(data);
            outputStream.write(data.getBytes(charsetName));
            outputStream.flush();
            outputStream.close();
            InputStream urlStream = connection.getInputStream();
            String contentEncode = connection.getContentEncoding();
            if (contentEncode != null && contentEncode.compareTo("gzip") == 0)
                urlStream = new GZIPInputStream(urlStream);
            reader = new InputStreamReader(urlStream, charsetName);
            in = new BufferedReader(reader);
            String line = null;        //每行内容
            int lineFlag = 0;        //标记: 判断有没有数据
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                lineFlag++;
            }
            connection.disconnect();
            String jsonStr = content.toString();
            if (jsonStr.charAt(0) == '[')
                jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
            //System.out.println(jsonStr);
            return lineFlag == 0 ? null : JSONObject.parseObject(jsonStr);
        } catch (SocketTimeoutException e) {
            LOG.error("连接超时!!!", e);
            return null;
        } catch (JSONException e) {
            LOG.error("网站响应不是json格式，无法转化成JSONObject!!!", e);
            return null;
        } catch (Exception e) {
            LOG.error("连接网址不对或读取流出现异常!!!", e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
        }
    }

    /**
     * 发起post请求
     *
     * @param src  来源网址
     * @param data POST数据
     * @param code 编码方式
     */
    public static String sendPost(String src, String data, String code) {
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 5);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", code);
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
//            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes(code));
            outputStream.flush();
            outputStream.close();
            /*int status = connection.getResponseCode();
            if (status != 200) {
                System.out.println("http请求失败，状态码为：" + status);
            }*/
            InputStream urlStream = connection.getInputStream();
            String contentEncode = connection.getContentEncoding();
            if (contentEncode != null && contentEncode.compareTo("gzip") == 0)
                urlStream = new GZIPInputStream(urlStream);
            reader = new InputStreamReader(urlStream, code);
            in = new BufferedReader(reader);
            String line;        //每行内容
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            connection.disconnect();
            return content.toString();
        } catch (SocketTimeoutException e) {
            LOG.error("连接超时!!!", e);
            return null;
        } catch (Exception e) {
            LOG.error("连接网址不对或读取流出现异常!!!", e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
        }
    }

    /**
     * 供超声波工具类调用，若请求返回的数据超过1024字节则认为是文件，保存至指定位置，并返回"SUCCESS"，否则当作文字信息返回
     */
    public static String downloadFile(String src, String data, String code, File file) {
        InputStream in = null;
        OutputStream out = null;
        BufferedReader inReader = null;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 5);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", code);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes(code));
            outputStream.flush();
            outputStream.close();
            InputStream urlStream = connection.getInputStream();
            String contentEncode = connection.getContentEncoding();
            if (contentEncode != null && contentEncode.compareTo("gzip") == 0)
                urlStream = new GZIPInputStream(urlStream);
            int contentLength = connection.getContentLength();
            if (contentLength > 1024) {
                // 文件情形
                in = new BufferedInputStream(urlStream);
                out = new FileOutputStream(file);
                int size = 0;
                int len = 0;
                byte[] buf = new byte[1024];
                while ((size = in.read(buf)) != -1) {
                    len += size;
                    out.write(buf, 0, size);
                }
                connection.disconnect();
                return "SUCCESS";
            } else {
                inReader = new BufferedReader(new InputStreamReader(urlStream, code));
                String line;        //每行内容
                StringBuilder content = new StringBuilder();
                while ((line = inReader.readLine()) != null) {
                    content.append(line);
                }
                connection.disconnect();
                return content.toString();
            }
        } catch (SocketTimeoutException e) {
            LOG.error("连接超时!!!", e);
            return null;
        } catch (Exception e) {
            LOG.error("连接网址不对或读取流出现异常!!!", e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
            if (inReader != null) {
                try {
                    inReader.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOG.error("关闭流出现异常!!!", e);
                }
            }
        }
    }

}
