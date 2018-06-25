package com.haitai.haitaitv.component.shiro.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 生成验证码
 *
 * @author liuzhou
 *         create at 2016-12-12 9:48
 */
public class ImageCodeFilter extends OncePerRequestFilter {

    private Font mFont = new Font("Arial", Font.BOLD, 15); // 设置字体
    private static final int LINE_WIDTH = 2; // 干扰线的长度=1.414*LINE_WIDTH
    private static final int WIDTH = 60; // 定义图形大小
    private static final int HEIGHT = 20; // 定义图形大小
    private static final int COUNT = 200;

    /**
     * 描述：
     *
     * @param fc 描述：
     * @param bc 描述：
     * @return 描述：
     */
    private Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // System.out.println("**********image code start*********");
        doFilterInternal((HttpServletRequest) request, (HttpServletResponse) response);
        // System.out.println("**********image code end*********");
    }

    private void doFilterInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/gif");
        // 在内存中创建图象
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250)); // ---1
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // 设定字体
        g.setFont(mFont);
        // 画边框
        g.setColor(getRandColor(0, 20)); // ---2
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < COUNT; i++) {
            g.setColor(getRandColor(150, 200)); // ---3
            int x = random.nextInt(WIDTH - LINE_WIDTH - 1) + 1; // 保证画在边框之内
            int y = random.nextInt(HEIGHT - LINE_WIDTH - 1) + 1;
            int xl = random.nextInt(LINE_WIDTH);
            int yl = random.nextInt(LINE_WIDTH);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(4位数字)
        StringBuilder sRand = new StringBuilder();
        // zb 2015-1-4 去除奇异字符 去除0 O o I i L l
        char[] selectChar = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
                'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        for (int i = 0; i < 4; i++) {
            int charIndex = random.nextInt(selectChar.length);
            String rand = String.valueOf(selectChar[charIndex]);
            sRand.append(rand);
            // 将认证码显示到图象中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(20 + random.nextInt(130), 20 + random.nextInt(130), 20 + random.nextInt(130))); // --4--50-100
            g.drawString(rand, (13 * i) + 6, 16);
        }
        // 将认证码存入SESSION
        request.getSession().setAttribute(ImageCodeFilter.class.getName(), sRand.toString());
        // 图象生效
        g.dispose();
        // 输出图象到页面
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "PNG", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
