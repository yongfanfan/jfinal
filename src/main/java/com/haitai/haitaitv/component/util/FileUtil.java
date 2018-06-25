package com.haitai.haitaitv.component.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.function.Predicate;

/**
 * 文件工具类
 *
 * @author liuzhou
 *         create at 2017-08-09 11:09
 */
public class FileUtil {

    /**
     * 删除文件或文件夹，文件夹将递归删除其所有内容
     *
     * @return 删除是否成功完成
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        File[] children = file.listFiles();
        if (children == null) {
            return file.delete();
        }
        for (File child : children) {
            boolean success = deleteFile(child);
            if (!success) {
                return false;
            }
        }
        return file.delete();
    }

    /**
     * 复制文件或文件夹
     *
     * @return 复制是否成功完成
     */
    public static boolean copy(File origin, File target) throws IOException {
        if (!origin.exists()) {
            return false;
        }
        if (origin.isFile()) {
            return copyFile(origin, target);
        } else {
            return copyFolder(origin, target);
        }
    }

    /**
     * 复制文件夹，且对于origin下的一级文件夹与文件，若与exclude匹配则不进行复制
     *
     * @return 复制是否成功完成
     */
    public static boolean copyFolder(File origin, File target, Predicate<String> exclude) throws IOException {
        if (!origin.exists()) {
            return false;
        }
        if (!origin.isDirectory()) {
            return false;
        }
        if (target.isFile() || // 目标文件夹路径实际是个文件
                (!target.exists() && !target.mkdirs())) { // 创建目标文件夹失败
            return false;
        }
        File[] children = origin.listFiles();
        if (children == null) {
            return true; // 无子文件（夹），不用递归
        }
        for (File child : children) {
            if (exclude != null && exclude.test(child.getName())) {
                continue;
            }
            boolean success = copy(child, new File(target, child.getName()));
            if (!success) {
                return false; // 复制子文件（夹）失败了
            }
        }
        return true;
    }

    /**
     * 向指定文件写入内容
     */
    public static void write(File file, String content, String charsetName) throws IOException {
        try (BufferedWriter writer =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charsetName))) {
            writer.write(content);
            writer.flush();
        }
    }

    private static boolean copyFile(File origin, File target) throws IOException {
        if (target.getParentFile().isFile() || // 目标文件的父文件夹路径实际是个文件
                (!target.getParentFile().exists() && !target.getParentFile().mkdirs())) { // 创建目标文件的父文件夹失败
            return false;
        }
        try (FileChannel in = new FileInputStream(origin).getChannel();
             FileChannel out = new FileOutputStream(target).getChannel()) {
            in.transferTo(0, in.size(), out);
        }
        return true; // 未抛异常，则复制成功
    }

    private static boolean copyFolder(File origin, File target) throws IOException {
        if (target.isFile() || // 目标文件夹路径实际是个文件
                (!target.exists() && !target.mkdirs())) { // 创建目标文件夹失败
            return false;
        }
        File[] children = origin.listFiles();
        if (children == null) {
            return true; // 无子文件（夹），不用递归
        }
        for (File child : children) {
            boolean success = copy(child, new File(target, child.getName()));
            if (!success) {
                return false; // 复制子文件（夹）失败了
            }
        }
        return true;
    }
}
