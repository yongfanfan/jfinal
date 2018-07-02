package com.haitai.haitaitv.component.util.encryption;

import java.security.*;  
import java.util.Base64;

import javax.crypto.*;  
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
  
/** 
 * DES3加解密算法 ,针对第三方的des3加密
 */  
public class DES3Third {  
    
    public static void main(String[] args) throws Exception {  
        DES3Third des = new DES3Third("a8ae69b0a99d4f116abbc358ce74dc4b");
        String s = "vktyEEa9+Q8G9tAhui4dInSp8FaQXhGeabuBhCfLb8tXuvgxkWXpZKI0scJnv+MiVroH53eGXt5UcN4n08XUI+mhSRtt+hkN5quwxhuAi9f"
                + "ovAgY14QzSEr8pafCcvZHHjC6LD3Ppnp3Uf9UDG8f1TZCNqbktWoUTJ0JlNwIJHlds9qF5UN1hxzCCXkpvrn/ZkBvwjppWBGiH+aJLfmF/4coABSAJH"
                + "N2nbJpsIwLeB990S3jNMdpNWBXb6j4cW0ZxzRjViYc9FNj"
                + "rQuK5T3IvJ5B9Dbme3rqhODDaebSLAajCiI6JkFdyHeU182oQLzagvldul5/VjMislWyWUBH1cc0Y1YmHPRTQWJtYqOxXgk=";
        String enc = new String(des.des3EncodeECB(s));
        System.out.println(enc);
        System.out.println(new String(des.des3DecodeECB(enc)));
    }  
    
    private static Key deskey = null;  
    
    public DES3Third() {
        this("123456");
    }
    
    public DES3Third(String key){
        try{
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("utf-8"));  
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
            deskey = keyfactory.generateSecret(spec); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /** 
     * ECB加密,不要IV 
     * @param key 密钥 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    public String des3EncodeECB(String data)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data.getBytes("utf-8"));  
        return Base64.getEncoder().encodeToString(bOut);
    }  
    /** 
     * ECB解密,不要IV 
     * @param key 密钥 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    public String des3DecodeECB(String data)  
            throws Exception {  
        byte[] bytes = Base64.getDecoder().decode(data);
        Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(bytes);  
        return new String(bOut, "utf-8");  
    }  
    /** 
     * CBC加密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    public String des3EncodeCBC(String keyiv, String data)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv.getBytes("utf-8"));  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data.getBytes("utf-8"));  
        return Base64.getEncoder().encodeToString(bOut);
    }  
    /** 
     * CBC解密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    public String des3DecodeCBC(String keyiv, String data)  
            throws Exception {  
        byte[] bytes = Base64.getDecoder().decode(data);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv.getBytes("utf-8"));  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(bytes);  
        return new String(bOut, "utf-8");  
    }  
  
  
}  