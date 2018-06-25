package com.haitai.haitaitv.component.beetl.ext;

import org.beetl.sql.core.InterceptorContext;
import org.beetl.sql.ext.DebugInterceptor;

/**
 * 扩展DebugInterceptor，使其打印位置时忽略自己的beetl扩展类
 *
 * @author liuzhou
 *         create at 2017-04-01 9:33
 */
public class MyDebugInterceptor extends DebugInterceptor {

    @Override
    public void before(InterceptorContext ctx) {
        String sqlId = ctx.getSqlId();
        if (this.isDebugEanble(sqlId)) {
            ctx.put("debug.time", System.currentTimeMillis());
        }
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator", "\n");
        sb.append("┏━━━━━ Debug [").append(this.getSqlId(sqlId)).append("] ━━━").append(lineSeparator)
                .append("┣ SQL：\t ").append(ctx.getSql().replaceAll("--.*", "")
                .replaceAll("\\s+", " ")).append(lineSeparator).append("┣ 参数：\t ")
                .append(formatParas(ctx.getParas())).append(lineSeparator);
        RuntimeException ex = new RuntimeException();
        StackTraceElement[] traces = ex.getStackTrace();
        boolean found = false;
        boolean breakFlag = false;
        for (StackTraceElement tr : traces) {
            if (!found && tr.getClassName().contains("SQLManager")) {
                found = true;
            }
            breakFlag = found && !tr.getClassName().startsWith("org.beetl.sql.core")
                    && !tr.getClassName().startsWith("com.sun")
                    // 忽略自定义扩展包
                    && !tr.getClassName().startsWith("com.haitai.haitaitv.component.beetl.ext");
            if (breakFlag) {
                String className = tr.getClassName();
                String mehodName = tr.getMethodName();
                int line = tr.getLineNumber();
                sb.append("┣ 位置：\t ").append(className).append(".").append(mehodName).append("(")
                        .append(tr.getFileName()).append(":").append(line).append(")").append(lineSeparator);
                break;
            }
        }
        ctx.put("logs", sb);
    }
}
