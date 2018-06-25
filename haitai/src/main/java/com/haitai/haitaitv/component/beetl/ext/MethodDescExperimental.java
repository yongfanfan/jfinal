package com.haitai.haitaitv.component.beetl.ext;

import org.beetl.sql.core.mapper.MethodDesc;

/**
 * 现在官方已经直接支持
 * <pre>
 * 扩展这个类的目的, 只是为了不用写更多重复的注解
 * 例如:
 * SqlStatement(params = "age,name,description"). 这个注解的params跟方法是一模一样的,没必要重复的写
 * 直接使用参数名 (约定优于配置)
 * </pre>
 * <pre>
 * 请在idea设置:java Compiler -> -parameters
 * 如果不明白请在搜索引擎输入关键字: java8 参数名
 * </pre>
 * create time : 2017-04-26 16:01
 *
 * @author luoyizhu@gmail.com
 */
public class MethodDescExperimental extends MethodDesc {

    /*protected void parseSqlReady(SQLManager sm, Class entityClass, Sql sql, Method m, String sqlId) {

        // 如果返回值是分页, 自己处理.
        Class methodRetType = m.getReturnType();
        if (Pageable.class.isAssignableFrom(methodRetType)) {
            type = 7;
            Class stRetType = (Class) ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0];
            this.getSelectRenturnType(methodRetType, stRetType, entityClass);

        } else {
            // 交给父类处理
            super.parseSqlReady(sm, entityClass, sql, m, sqlId);
        }

    }

    @Override
    protected void doParse(SQLManager sm, Class entityClass, Method m, String sqlId) {

        // 没有任何注解, 走md文件
        if (m.getAnnotations().length == 0) {
            experimentalParse(sm, entityClass, m, sqlId);
            return;
        }

        Sql sql = m.getAnnotation(Sql.class);
        if (sql != null) {
            this.sqlReady = sql.value();
            parseSqlReady(sm, entityClass, sql, m, sqlId);
            return;
        }

        super.doParse(sm, entityClass, m, sqlId);
    }

    private void experimentalParse(SQLManager sm, Class entityClass, Method m, String sqlId) {

        // 获取方法的参数列表
        Parameter[] parameters = m.getParameters();
        this.parasPos.clear();
        for (int i = 0; i < parameters.length; i++) {
            parasPos.put(parameters[i].getName(), i);
        }

        type = getTypeBySqlId(sm, sqlId);
        Class stRetType = Void.class;
        Class methodRetType = m.getReturnType();
        // 特殊处理翻页查询
        Class[] paraTypes = m.getParameterTypes();
        if (paraTypes.length > 0) {
            Class cls = paraTypes[0];
            if (PageQuery.class.isAssignableFrom(cls)) {
                type = 6;
                // 获取泛型信息
                stRetType = (Class) ((ParameterizedType) m.getGenericParameterTypes()[0]).getActualTypeArguments()[0];
            }
        }
        if (type == 0) {
            if (KeyHolder.class.isAssignableFrom(methodRetType)) {
                keyHolderPos = -1;
                type = 1;
            }
            return;
        } else if (type == 2) {
            if (List.class.isAssignableFrom(methodRetType)) {
                type = 3;
                // 如果是list, 获取List泛型类型
                stRetType = this.getReturnActualTypeArguments(m);
            }

            if (Pageable.class.isAssignableFrom(methodRetType)) {
                this.type = 7;
                stRetType = this.getReturnActualTypeArguments(m);
                // this.renturnType = stRetType;
            }
        }

        //确定查询返回需要映射类型
        if (type == 2 || type == 3 || type == 6 || type == 7) {
            this.getSelectRenturnType(methodRetType, stRetType, entityClass);
        }
    }

    *//**
     * 获取方法返回值对象的菱形内参数类型
     *
     * @param method 方法对象
     * @return 菱形内参数类型
     *//*
    protected Class<?> getReturnActualTypeArguments(Method method) {
        return (Class) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
    }*/

}
