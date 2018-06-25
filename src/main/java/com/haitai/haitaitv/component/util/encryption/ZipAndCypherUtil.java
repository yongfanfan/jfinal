package com.haitai.haitaitv.component.util.encryption;

import com.haitai.haitaitv.component.exception.MyException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author liuzhou
 *         create at 2017-03-13 16:55
 */
public class ZipAndCypherUtil {

    public static final String ALGORITHM = "DESede/CBC/PKCS5Padding";
    private String key;

    public ZipAndCypherUtil(String key) {
        if (key == null) {
            key = "";
        }
        int keyLength = key.length();
        if (keyLength < 24) {
            for (int i = 0; i < 24 - keyLength; i++) {
                key += (char) ('a' + (char) i);
            }
        }
        System.out.println(key);
        this.key = key;
    }

    private Cipher getCipher() {
        Cipher cipher;
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            cipher = Cipher.getInstance(ALGORITHM);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec("12345678".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new MyException("加/解密失败");
        }
        return cipher;
    }

    /**
     * @param os   要包装的输出流
     * @param file 要压缩的文件(夹)
     */
    public void out(OutputStream os, File file) throws IOException {
        CipherOutputStream cos = new CipherOutputStream(os, getCipher());
        ZipOutputStream zos = new ZipOutputStream(cos);
        BufferedOutputStream bos = new BufferedOutputStream(zos);
        outHelper(zos, bos, file, file.getName());
    }

    private void outHelper(ZipOutputStream zos, BufferedOutputStream bos, File file, String base) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                // 创建zip压缩进入点base
                zos.putNextEntry(new ZipEntry(base + "/"));
                return;
            }
            for (File f : files) {
                outHelper(zos, bos, f, base + "/" + f.getName()); // 递归遍历子文件夹
            }
        } else {
            // 创建zip压缩进入点base
            zos.putNextEntry(new ZipEntry(base));
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            int b;
            while ((b = bis.read()) != -1) {
                // 将字节流写入当前zip目录
                bos.write(b);
            }
            bis.close();
            fis.close(); // 输入流关闭
        }
    }

    public static void main(String[] args) throws Exception {
        ZipAndCypherUtil util = new ZipAndCypherUtil(null);
        OutputStream os = new FileOutputStream("D:/abc");
        util.out(os, new File("D:/LzSpace/周报"));
    }
}
