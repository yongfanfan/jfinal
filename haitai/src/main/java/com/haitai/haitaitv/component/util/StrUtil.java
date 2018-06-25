package com.haitai.haitaitv.component.util;

/**
 * Created by liuzhou on 2017/3/5.
 */
public class StrUtil {

    protected StrUtil() {
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0)
            return text;
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1)
            return text;
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase >= 0 ? increase : 0;
        increase *= max >= 0 ? max <= 64 ? max : 64 : 16;
        StringBuilder buf = new StringBuilder(text.length() + increase);
        do {
            if (end == -1)
                break;
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0)
                break;
            end = text.indexOf(searchString, start);
        } while (true);
        buf.append(text.substring(start));
        return buf.toString();
    }
}
