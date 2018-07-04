package com.haitai.haitaitv.test.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class test {
    public static void main(String[] args) throws Exception {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=036fec4fd6cc83277cd768525a8e31a00e7885f57c8f0baa978ae96148cc31a6";
//        String token = "https://oapi.dingtalk.com/robot/send?access_token=e38d722c7eb54a1b87e78fead88ce4bb63e6ca4da35d7ff53515d88e598a6a1c";
//        String content = "";
//        content = "{\"msgtype\": \"text\",\"text\": {\"content\": \"钉钉测试发送代码消息\"}}";
//        String r = httpsRequest(token, "POST", content);
//        System.out.println("OK"+r);
        
        HttpClient httpclient = HttpClients.createDefault();
        
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
 
        String textMsg = "{\"msgtype\": \"text\", \"text\": {\"content\": \"刚才在等地铁，旁边一对情侣不知什么原因吵起来了，我就在旁边看着，那女的忽然指着我说，你要是有他一半帅，我们就不会吵架了！男的看了看我大声吼出：我要是有他一半帅，我还能看上你？我.....尼玛…关我叼事啊这大晚上的！\"},\"at\":{\"isAtAll\": true}}";
        System.out.println("textMsg:"+textMsg);
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
 
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }

    /**
     * 发送https请求
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/json");
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("utf-8"));
                outputStream.close();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
