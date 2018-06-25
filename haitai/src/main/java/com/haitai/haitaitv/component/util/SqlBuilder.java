package com.haitai.haitaitv.component.util;

/**
 * sql生成器
 */
@Deprecated
public class SqlBuilder {

    private StringBuilder sb;
    private String alias = "";

    public SqlBuilder() {
        sb = new StringBuilder();
    }

    public SqlBuilder(String sql) {
        sb = new StringBuilder(sql);
    }

    public SqlBuilder(String sql, String alias) {
        sb = new StringBuilder(sql);
        this.alias = alias;
    }

    public void whereLike(String attrName, String value) {
        if (StrUtil.isNotEmpty(value)) {
            sb.append(" AND ").append(getAttrName(attrName)).append(" LIKE '%").append(value).append("%'");
        }
    }

    public void whereEquals(String attrName, String value) {
        if (StrUtil.isNotEmpty(value)) {
            sb.append(" AND ").append(getAttrName(attrName)).append(" = '").append(value).append("'");
        }
    }

    public void whereEquals(String attrName, Integer value) {
        whereEquals(attrName, value, false);
    }

    /**
     * @param allowNonpositive 是否允许value非正时进行拼接
     */
    public void whereEquals(String attrName, Integer value, boolean allowNonpositive) {
        if (value != null && (allowNonpositive || value > 0)) {
            sb.append(" AND ").append(getAttrName(attrName)).append(" = ").append(value);
        }
    }

    public SqlBuilder append(CharSequence s) {
        sb.append(s);
        return this;
    }

    private String getAttrName(String attrName) {
        if (StrUtil.isEmpty(alias)) {
            return attrName;
        }
        return alias + "." + attrName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String toString() {
        return sb.toString();
    }


    /**
     * 生成符合条件的sql语句,解决in问题
     */
    public static String getSqlIn(String sqlParam, String columnName) {
        if (sqlParam == null || "".equals(sqlParam)) {
            return " ";
        }
        String[] str_arr = sqlParam.split(",");
        return getSqlIn(str_arr, columnName, true);
    }

    /**
     * 生成符合条件的sql语句,解决not in问题
     */
    public static String getNotSqlIn(String sqlParam, String columnName) {
        if (sqlParam == null || "".equals(sqlParam)) {
            return " ";
        }
        String[] str_arr = sqlParam.split(",");
        return getSqlIn(str_arr, columnName, false);
    }

    /**
     * 生成符合条件的sql语句,解决in问题
     *
     * @param flag ture:IN false:NOT IN
     */
    public static String getSqlIn(String[] str_arr, String columnName, boolean flag) {
        int buffLength;
        int maxItem = 500; // 一个(NOT )IN中的最大项数，更多的将用OR拼接多个(NOT )IN
        int width = str_arr.length;
        int arr_width = width / maxItem;
        if (width % maxItem != 0) {
            arr_width += 1;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr_width; i++) {
            if (flag) {
                sb.append(" ").append(columnName).append(" IN(");
            } else {
                sb.append(" ").append(columnName).append(" NOT IN(");
            }
            for (int j = i * maxItem, k = 0; j < width && k < maxItem; j++, k++) {
                sb.append("'").append(str_arr[j]).append("',");
            }
            buffLength = sb.length();
            sb = sb.delete(buffLength - 1, buffLength).append(") OR");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
