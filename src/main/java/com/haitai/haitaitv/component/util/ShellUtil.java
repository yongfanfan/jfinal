package com.haitai.haitaitv.component.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 封装shell操作
 *
 * @author liuzhou
 *         create at 2016-12-20 16:43
 */
public class ShellUtil {

    private static final Logger log = LogManager.getLogger(ShellUtil.class);

    public static String excuteWithResult(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
            BufferedReader buf;
            String line;
            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuilder sb = new StringBuilder();
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            // System.out.println(sb.toString());
            p.waitFor(); // 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            return sb.toString();
        } catch (IOException | InterruptedException e) {
            log.error("excuteWithResult-shell调用失败，命令为：" + command);
            return null;
        }
    }

    public static void excuteWithoutResult(List<String> command) {
        /*
        真正的原因是，每个应用程序都可以进行I/O操作(尤其是一些Debug信息的输出)，如果没有定义自己的I/O Stream(主要是输出位置，一般我们会将Debug信息输出到默认的输出流)，那么就会使用默认的I/O Stream，一般是谁启动该应用程序，谁就是目标的I/O Stream载体。而且，各个OS对Stream会进行Buffer，但是实现机制不同，效果也不同，有些OS的Buffer的大小有限制，如果 Buffer中的内容在一段时间内不清空的话，OS会利用超时机制自动清空Buffer，以便容纳其他信息(很不幸，MS Windows就是这样的OS)。

至此，上面的怪异现象得到了科学的解释。我们通过Java程序启动一个外部应用程序，而这个外部应用程序 在执行过程中不停的对Output Stream进行操作而且没有定义自己的I/O Stream。由于OS对Stream Buffer的容量有限制，而且Java程序没有进行相应的清空操作，导致这个外部应用程序在执行了一段时间后不能继续进行对Output Stream的操作而处于等待状态，直到Stream Buffer被OS清空而变得可用，这个外部应用程序才能继续正常运行。

        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            Process p = builder.start();
            p.waitFor(); // 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
        } catch (IOException | InterruptedException e) {
            log.debug("shell调用失败");
        }*/
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            Process p = builder.start();
            BufferedReader buf;
            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                if (buf.readLine() == null)
                    break;
            }
            // System.out.println(sb.toString());
            p.waitFor(); // 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
        } catch (IOException | InterruptedException e) {
            log.error("excuteWithoutResult-shell调用失败，命令为：" + command);
        }
    }
}
