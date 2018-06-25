package com.haitai.haitaitv.component.util;

import com.haitai.haitaitv.component.util.encryption.DES3Utils;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * 无视Https证书是否正确的Java Http Client
 *
 * @author huangxuebin
 * @version 1.0
 * @create 2012.8.17
 */
public class HttpsUtil {

    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            System.out.println("WARNING: Hostname is not matched for cert.");
            return true;
        }
    };

    /**
     * Ignore Certification
     */
    private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

        private X509Certificate[] certificates;

        @Override
        public void checkClientTrusted(X509Certificate certificates[],
                                       String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                System.out.println("init at checkClientTrusted");
            }

        }

        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate,
                                       String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
                System.out.println("init at checkServerTrusted");
            }

//            for (int c = 0; c < certificates.length; c++) {
//                X509Certificate cert = certificates[c];
//                System.out.println(" Server certificate " + (c + 1) + ":");
//                System.out.println("  Subject DN: " + cert.getSubjectDN());
//                System.out.println("  Signature Algorithm: "
//                        + cert.getSigAlgName());
//                System.out.println("  Valid from: " + cert.getNotBefore());
//                System.out.println("  Valid until: " + cert.getNotAfter());
//                System.out.println("  Issuer: " + cert.getIssuerDN());
//            }

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };


    public static String get(String url) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {
         /*
          * use ignore host name verifier
          */
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();

            // Prepare SSL Context
            TrustManager[] tm = {ignoreCertificationTrustManger};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);

            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[512];
            int length = reader.read(bytes);

            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);

            // result.setResponseData(bytes);
            System.out.println(buffer.toString());
            reader.close();

            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        return new String(buffer.toByteArray());
    }

    public static String post(String url, String content) {


        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {
         /*
          * use ignore host name verifier
          */
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();

            // Prepare SSL Context
            TrustManager[] tm = {ignoreCertificationTrustManger};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.getOutputStream().write(DES3Utils.INSTANCE.encryptString(content).getBytes("UTF-8"));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[512];
            int length = reader.read(bytes);

            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);

            // result.setResponseData(bytes);
            System.out.println("回执：" + buffer.toString());
            reader.close();

            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        return new String(buffer.toByteArray());
    }
}