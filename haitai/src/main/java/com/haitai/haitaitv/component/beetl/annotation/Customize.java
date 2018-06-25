package com.haitai.haitaitv.component.beetl.annotation;

import org.beetl.sql.core.mapper.BaseMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果打算自定义AbcDao的某个/些方法，你需要定义一个接口，推荐命名为AbcDaoCustom，并让其继承BaseCustom<Abc>接口。
 * <p>
 * 原来的继承体系：AbcDao extends BaseMapper<Abc>，现在就变为了
 * AbcDao extends AbcDaoCustom，AbcDaoCustom extends BaseCustom<Abc>
 * <p>
 * 然后将想要自定义的方法定义在AbcDaoCustom接口中（BaseMapper中的方法也可以），并写一个实现类实现这个接口。
 * <p>
 * 最后使用此注解标记AbcDaoCustom，value的值为AbcDaoCustom的实现类（不能是抽象类，并且具有无参构造器）
 *
 * @see BaseMapper
 * Created by liuzhou on 2017/3/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Customize {

    /**
     * @return 指定实现了被标记接口的类，该类不能是抽象类，并且具有无参构造器
     */
    Class<?> value();

}
