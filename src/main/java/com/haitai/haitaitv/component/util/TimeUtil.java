package com.haitai.haitaitv.component.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 用于支持java8的time api
 *
 * @author liuzhou
 *         create at 2017-03-17 15:15
 */
public class TimeUtil {

    /**
     * 根据实例获取DateTimeFormatter
     */
    public static DateTimeFormatter getDateTimeFormatter(Object data) {
        if (!(data instanceof TemporalAccessor)) {
            throw new RuntimeException("Arg Error:Type should be TemporalAccessor");
        }
        if (data instanceof LocalDate) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        if (data instanceof LocalTime) {
            return DateTimeFormatter.ofPattern("HH:mm:ss");
        }
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据类型获取DateTimeFormatter
     */
    public static DateTimeFormatter getDateTimeFormatter(Class<?> clazz) {
        if (!(TemporalAccessor.class.isAssignableFrom(clazz))) {
            throw new RuntimeException("clazz应该是TemporalAccessor或其子类型");
        }
        if (clazz == LocalDate.class) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        if (clazz == LocalTime.class) {
            return DateTimeFormatter.ofPattern("HH:mm:ss");
        }
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }


}
