package com.haitai.haitaitv.component.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描：借鉴Jfinal ext
 */
public class ClassSearcher {

    private static final Logger LOG = LogManager.getLogger(ClassSearcher.class);
    private static final URL CLASS_PATH_URL = ClassSearcher.class.getResource("/");
    private static final String LIB = new File(CLASS_PATH_URL.getFile()).getParent() + "/lib/";

    private ClassSearcher() {
    }

    public static <T> List<Class<? extends T>> findInClasspathAndJars(Class<T> clazz, List<String> includeJars) {
        List<String> classFileList = null;
        try {
            String file = URLDecoder.decode(CLASS_PATH_URL.getFile(), "UTF-8");
            String findLib = URLDecoder.decode(LIB, "UTF-8");
            classFileList = findFiles(file, "*.class");
            classFileList.addAll(findjarFiles(findLib, includeJars));
        } catch (UnsupportedEncodingException e) {
            LOG.error("findInClasspathAndJars方法发生异常", e);
        }
        return extraction(clazz, classFileList);
    }

    public static <T> List<Class<? extends T>> findInClasspath(Class<T> clazz) {
        List<String> classFileList = null;
        try {
            String file = URLDecoder.decode(CLASS_PATH_URL.getFile(), "UTF-8");
            classFileList = findFiles(file, "*.class");
        } catch (UnsupportedEncodingException e) {
            LOG.error("findInClasspath方法发生异常", e);
        }
        return extraction(clazz, classFileList);
    }

    public static <T> List<Class<? extends T>> findInPackage(Class<T> clazz, String pkg) {
        List<String> classFileList = null;
        try {
            String file = URLDecoder.decode(CLASS_PATH_URL.getFile(), "UTF-8") + pkg;
            classFileList = findFiles(file, "*.class");
        } catch (UnsupportedEncodingException e) {
            LOG.error("findInClasspath方法发生异常", e);
        }
        return extraction(clazz, classFileList);
    }

    /**
     * 递归查找文件
     *
     * @param baseDirName    查找的文件夹路径
     * @param targetFileName 需要查找的文件名
     */
    private static List<String> findFiles(String baseDirName, String targetFileName) {
        /**
         * 算法简述： 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件，
         * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。 队列不空，重复上述操作，队列为空，程序结束，返回结果。
         */
        List<String> classFiles = new ArrayList<String>();
        String tempName = null;
        // 判断目录是否存在
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            LOG.error("search error:" + baseDirName + "is not a dir!");
        } else {
            String[] filelist = baseDir.list();
            if (filelist != null) {
                for (String aFilelist : filelist) {
                    File readfile = new File(baseDirName + File.separator + aFilelist);
                    if (readfile.isDirectory()) {
                        classFiles.addAll(findFiles(baseDirName + File.separator + aFilelist, targetFileName));
                    } else {
                        tempName = readfile.getName();
                        if (ClassSearcher.wildcardMatch(targetFileName, tempName)) {
                            String classname = "";
                            String tem = readfile.getAbsoluteFile().toString().replaceAll("\\\\", "/");
                            String[] rootClassPath = new String[]{ //
                                    "/classes" // java web
                                    , "/test-classes" // maven test
                                    , "/bin" // java project
                            };

                            // scan class，set root path
                            for (String tmp : rootClassPath) {
                                if (tem.contains(tmp)) {
                                    classname = tem.substring(tem.indexOf(tmp) + tmp.length(), tem.indexOf(".class"));
                                    break;
                                }
                            }

                            if (classname.startsWith("/")) {
                                classname = classname.substring(classname.indexOf("/") + 1);
                            }
                            classname = className(classname);
                            classFiles.add(classname);
                        }
                    }
                }
            }
        }
        return classFiles;
    }

    private static String className(String classFile) {
        String objStr = classFile.replaceAll("\\\\", ".");
        return objStr.replaceAll("/", ".");
    }

    /**
     * 查找jar包中的class
     *
     * @param baseDirName jar路径
     */
    public static List<String> findjarFiles(String baseDirName, final List<String> includeJars) {
        List<String> classFiles = new ArrayList<>();
        try {
            // 判断目录是否存在
            File baseDir = new File(baseDirName);
            if (!baseDir.exists() || !baseDir.isDirectory()) {
                LOG.error("####warn####file serach error：" + baseDirName + "is not a dir！");
            } else {
                String[] filelist = baseDir.list((dir, name) -> includeJars.contains(name));
                if (filelist != null) {
                    for (String aFilelist : filelist) {
                        JarFile localJarFile = new JarFile(new File(baseDirName + File.separator + aFilelist));
                        Enumeration<JarEntry> entries = localJarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry jarEntry = entries.nextElement();
                            String entryName = jarEntry.getName();
                            if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                                String className = entryName.replaceAll("/", ".").substring(0, entryName.length() - 6);
                                classFiles.add(className);
                            }
                        }
                        localJarFile.close();
                    }
                }
            }

        } catch (IOException e) {
            LOG.error("findjarFiles方法发生异常", e);
        }
        return classFiles;

    }

    @SuppressWarnings("unchecked")
    private static <T> List<Class<? extends T>> extraction(Class<T> clazz, List<String> classFileList) {
        List<Class<? extends T>> classList = new ArrayList<>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                if (clazz.isAssignableFrom(classInFile) && clazz != classInFile) {
                    classList.add((Class<? extends T>) classInFile);
                }
            } catch (ClassNotFoundException e) {
                LOG.error("extraction方法发生异常", e);
            }
        }
        return classList;
    }

    /**
     * 通配符匹配
     *
     * @param pattern 通配符模式
     * @param str     待匹配的字符串
     *                匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                // 通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                // 通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    // 表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return strIndex == strLength;
    }

}
