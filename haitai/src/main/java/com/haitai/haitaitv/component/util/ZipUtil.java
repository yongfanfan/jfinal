package com.haitai.haitaitv.component.util;


import com.haitai.haitaitv.component.exception.MyException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

/**
 * 相对路径未作测试，另外路径分隔符使用/以便兼容不同系统
 *
 * @author liuzhou
 *         create at 2017-03-14 11:36
 */
public class ZipUtil {

    private ZipFile zipFile;
    private ZipParameters parameters = new ZipParameters();
    private int mode; // 模式，1为压缩模式，2为解压模式，0为混合模式（预留）

    /**
     * @param path 生成zip文件的路径，对应路径若已有文件将会删除
     */
    public static ZipUtil ofCompress(String path) throws ZipException {
        return new ZipUtil(path, null, 1);
    }

    /**
     * @param path     生成zip文件的路径，对应路径若已有文件将会删除
     * @param password 密码
     */
    public static ZipUtil ofCompress(String path, String password) throws ZipException {
        return new ZipUtil(path, password, 1);
    }

    /**
     * @param path 待解压zip文件的路径
     */
    public static ZipUtil ofDecompress(String path) throws ZipException {
        return new ZipUtil(path, null, 2);
    }

    /**
     * @param path     待解压zip文件的路径
     * @param password 密码
     */
    public static ZipUtil ofDecompress(String path, String password) throws ZipException {
        return new ZipUtil(path, password, 2);
    }

    /**
     * @param file     待解压zip文件
     * @param password 密码
     */
    public static ZipUtil ofDecompress(File file, String password) throws ZipException {
        return new ZipUtil(file, password, 2);
    }

    private ZipUtil(String path, String password, int mode) throws ZipException {
        init(new File(path), password, mode);
    }

    private ZipUtil(File file, String password, int mode) throws ZipException {
        init(file, password, mode);
    }

    private void init(File file, String password, int mode) throws ZipException {
        this.mode = mode;

        if (mode == 1) {
            compressInit(file, password);
        } else {
            decompressInit(file, password);
        }
    }

    private void compressInit(File file, String password) throws ZipException {
        if (file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        zipFile = new ZipFile(file);
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if (password != null) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(password);
        }
    }

    private void decompressInit(File file, String password) throws ZipException {
        zipFile = new ZipFile(file);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
    }

    /**
     * 添加一个文件夹到zip，不允许解压模式进行此操作
     *
     * @param path 文件夹路径
     * @throws ZipException
     */
    public void addFolder(String path) throws ZipException {
        if (mode == 2) {
            throw new MyException("不允许解压模式进行添加文件夹操作");
        }
        zipFile.addFolder(path, parameters);
    }

    /**
     * 添加一个文件到zip，不允许解压模式进行此操作
     *
     * @param path 文件路径
     * @throws ZipException
     */
    public void addFile(File path) throws ZipException {
        if (mode == 2) {
            throw new MyException("不允许解压模式进行添加文件操作");
        }
        zipFile.addFile(path, parameters);
    }

    /**
     * 添加注释，不允许解压模式进行此操作
     */
    public void setComment(String comment) throws ZipException {
        if (mode == 2) {
            throw new MyException("不允许解压模式进行添加注释操作");
        }
        zipFile.setComment(comment);
    }

    /**
     * 取出注释，不允许压缩模式进行此操作
     */
    public String getComment(String encoding) throws ZipException {
        if (mode == 1) {
            throw new MyException("不允许压缩模式进行取出注释操作");
        }
        return zipFile.getComment(encoding);
    }

    /**
     * 解压所有文件，不允许压缩模式进行此操作
     */
    public void extractAll(String destPath) throws ZipException {
        if (mode == 1) {
            throw new MyException("不允许压缩模式进行取出注释操作");
        }
        zipFile.extractAll(destPath);
    }

    /**
     * 将zip解压缩，若无额外操作则优先使用此方法解压
     *
     * @param zipPath  zip文件路径
     * @param destPath 目标文件夹路径，若该路径实际上是一个文件将会抛出ZipException（路径不存在将自动创建文件夹）
     * @param password 解压密码（如果有的话）
     * @throws ZipException
     */
    public static void extractAll(String zipPath, String destPath, String password) throws ZipException {
        ZipFile zipFile = new ZipFile(zipPath);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(destPath);
    }

    /**
     * 解压单个文件
     *
     * @param zipPath  zip文件路径
     * @param destPath 目标文件夹路径，若该路径实际上是一个文件将会抛出ZipException（路径不存在将自动创建文件夹）
     * @param filename 要解压的文件名
     * @param password 解压密码（如果有的话）
     * @throws ZipException
     */
    public static void extractFile(String zipPath, String destPath, String filename, String password) throws ZipException {
        ZipFile zipFile = new ZipFile(zipPath);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractFile(filename, destPath);
    }

    public static void main(String[] args) {
        try {
            String folder = "D:\\WorkSpace\\haitaitv\\target\\haitaitv\\download\\qianmi_detail\\201611021626071";
            /*ZipUtil zipUtil = new ZipUtil(folder + ".zip");
            zipUtil.addFolder(folder);*/
            ZipUtil.extractAll(folder + ".zip", "D:\\WorkSpace\\haitaitv\\target\\haitaitv\\download\\qianmi_detail", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
