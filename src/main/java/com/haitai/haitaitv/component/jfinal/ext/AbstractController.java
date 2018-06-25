package com.haitai.haitaitv.component.jfinal.ext;

import com.alibaba.fastjson.JSON;
import com.haitai.haitaitv.module.back.common.AfterSetterAdapter;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 扩展jfinal的Controller
 */
public abstract class AbstractController extends Controller {

    /**
     * 重写getBean系列四个方法，以扩展JFinal原有的TypeConverter
     * 并且在不指定beanName的情况下，将null作为默认的beanName（jfinal是取的类名小驼峰作为默认的beanName）
     */
    @Override
    public <T> T getBean(Class<T> beanClass) {
        return getBean(beanClass, null, false);
    }

    @Override
    public <T> T getBean(Class<T> beanClass, boolean skipConvertError) {
        return getBean(beanClass, null, skipConvertError);
    }

    @Override
    public <T> T getBean(Class<T> beanClass, String beanName) {
        return getBean(beanClass, beanName, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(Class<T> beanClass, String beanName, boolean skipConvertError) {
        Object bean;
        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpServletRequest request = getRequest();
        String modelNameAndDot = StrKit.notBlank(beanName) ? beanName + "." : null;

        Map<String, String[]> parasMap = request.getParameterMap();
        Method[] methods = beanClass.getMethods();
        for (Method method : methods) {
            if (method.isBridge()) {
                // 对于桥方法直接不做任何操作（关于桥方法请百度）
                continue;
            }
            String methodName = method.getName();
            if (!methodName.startsWith("set") || methodName.length() <= 3) {    // only setter method
                continue;
            }
            Class<?>[] types = method.getParameterTypes();
            if (types.length != 1) {                        // only one parameter
                continue;
            }

            String attrName = StrKit.firstCharToLowerCase(methodName.substring(3));
            String paraName = modelNameAndDot != null ? modelNameAndDot + attrName : attrName;
            if (parasMap.containsKey(paraName)) {
                try {
                    String paraValue = request.getParameter(paraName);
                    // 使用自己的类型转换器
                    Object value = paraValue != null ? MyTypeConverter.convert(types[0], paraValue) : null;
                    method.invoke(bean, value);
                } catch (Exception e) {
                    if (!skipConvertError) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (bean instanceof AfterSetterAdapter) {
            // 处理CommonDTO类型的自定义逻辑
            ((AfterSetterAdapter) bean).afterSetter();
        }
        return (T) bean;
    }

    /**
     * 从请求体中获取bean（使用时请注意请求的输入流只能读一次这个事实）
     */
    public <T> T getBeanFromBody(Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = getRequest().getReader()) {
            char[] ch = new char[1024];
            int readCount;
            while ((readCount = br.read(ch)) != -1) {
                sb.append(ch, 0, readCount);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSON.parseObject(sb.toString(), clazz);
    }

}
