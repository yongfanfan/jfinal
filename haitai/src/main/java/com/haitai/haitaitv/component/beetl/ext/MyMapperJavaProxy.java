package com.haitai.haitaitv.component.beetl.ext;

import com.haitai.haitaitv.component.beetl.annotation.Customize;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.*;
import org.beetl.sql.core.mapper.builder.MapperInvokeDataConfig;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 扩展dao，使其支持自定义实现
 *
 * @author liuzhou
 *         create at 2017-03-25 22:34
 */
public class MyMapperJavaProxy extends MapperJavaProxy {

    /*
     * 新增两个字段，分别表示自定义方法所存放的接口和实现类
     */
    private Class<?> customInterface;
    private Class<?> implementClass;

    public MyMapperJavaProxy(DefaultMapperBuilder builder, SQLManager sqlManager, Class<?> mapperInterface) {
        super(builder, sqlManager, mapperInterface);
    }

    protected void onResolveEntityClassFromMapperInterface(Class<?> mapperInterface) {
        if (mapperInterface.isInterface()) {
            /*
             * 修改开始
             */
            if (BaseCustom.class.isAssignableFrom(mapperInterface)) {
                Class<?>[] interfaces = mapperInterface.getInterfaces();
                customInterface = interfaces[0];
                if (!customInterface.isAnnotationPresent(Customize.class)) {
                    throw new IllegalArgumentException("若dao是BaseCustom的子接口，那么它的直接父接口应该带有@Customize注解");
                }
                // 获取注解给定的实现类
                implementClass = customInterface.getAnnotation(Customize.class).value();
                if (!customInterface.isAssignableFrom(implementClass)) {
                    throw new IllegalArgumentException("dao的直接父接口的@Customize注解的value应该是此父接口的实现类");
                }
                // 使用customInterface来获取泛型信息
                mapperInterface = customInterface;
            }
            /*
             * 修改结束
             */
            Type[] faces = mapperInterface.getGenericInterfaces();
            if (faces.length > 0 && faces[0] instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) faces[0];
                if (pt.getActualTypeArguments().length > 0) {
                    this.entityClass = (Class<?>) pt.getActualTypeArguments()[0];
                }
            }
        } else {
            throw new IllegalArgumentException("mapperInterface is not interface.");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class caller = method.getDeclaringClass();
        /*
         * 修改开始
         */
        if (caller == customInterface) {
            return method.invoke(implementClass.newInstance(), args);
        }
        if (caller == BaseCustom.class) {
            caller = BaseMapper.class;
        }
        /*
         * 修改结束
         */
        SqlResource resource = (SqlResource) caller.getAnnotation(SqlResource.class);
        String sqlId;
        if (resource != null) {
            String preffix = resource.value();
            String name = method.getName();
            sqlId = preffix + "." + name;
        } else {
            sqlId = this.builder.getIdGen().getId(method.getDeclaringClass(), entityClass, method);

        }

        String methodName = method.getName();
        MapperInvoke invoke = sqlManager.getMapperConfig().getAmi(caller, methodName);
        if (invoke != null) {
            //内置的方法，直接调用Invoke
            return invoke.call(this.sqlManager, this.entityClass, sqlId, method, args);
        } else {
            //解析方法以及注解，找到对应的处理类
            MethodDesc desc = MethodDesc.getMetodDesc(sqlManager, this.entityClass, method, sqlId);
            if (desc.sqlReady.length() == 0) {
                invoke = MapperInvokeDataConfig.getMethodDescProxy(desc.type);
                Object ret = invoke.call(this.sqlManager, this.entityClass, sqlId, method, args);
                return ret;
            } else {
                invoke = MapperInvokeDataConfig.getSQLReadyProxy();
                Object ret = invoke.call(this.sqlManager, this.entityClass, desc.sqlReady, method, args);
                return ret;
            }
        }
    }

}
