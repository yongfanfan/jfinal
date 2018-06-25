package com.haitai.haitaitv.component.beetl.ext;

import com.haitai.haitaitv.component.util.StrUtil;
import com.haitai.haitaitv.component.util.TimeUtil;
import org.beetl.core.Format;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 增加对TemporalAccessor类型的格式化支持，但LocalDateTime,LocalDate,LocalTime以外的类型未做测试
 * Created by liuzhou on 2017/3/9.
 */
public class TemporalFormat implements Format {
    @Override
    public Object format(Object data, String pattern) {
        if (!(data instanceof TemporalAccessor)) {
            throw new RuntimeException("Arg Error:Type should be TemporalAccessor");
        }
        DateTimeFormatter formatter;
        // 允许模板设置为空串，简化前端书写
        if (StrUtil.isNotEmpty(pattern)) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = TimeUtil.getDateTimeFormatter(data);
        }
        return formatter.format((TemporalAccessor) data);
    }


}
