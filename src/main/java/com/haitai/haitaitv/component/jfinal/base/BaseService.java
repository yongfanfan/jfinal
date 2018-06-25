package com.haitai.haitaitv.component.jfinal.base;

import com.haitai.haitaitv.component.util.encryption.DES3Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 对于线程安全并且会被频繁调用的service推荐单例化。
 * 对于非线程安全的service，禁止单例化，也不能作为controller的静态成员。
 * （jfinal的controller是收到请求后new的，不是单例。若service作为静态成员，就和单例情形一样，需要考虑线程安全）
 * Created by liuzhou on 2017/3/5.
 */
public abstract class BaseService {

    protected static final Logger LOG = LogManager.getLogger(BaseService.class);
    protected static DES3Utils MQ_MSG_DES;
    protected static SQLManager sm;

    public static void init() {
        sm = JFinalBeetlSql.dao();
        MQ_MSG_DES = DES3Utils.INSTANCE;
    }

    protected String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    protected String produceUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 根据pageQuery的页码、分页大小和查询参数的toString(不显式调用以兼容null)来产生一个key用于拼接缓存的key
     */
    protected String keyOf(PageQuery<?> pageQuery) {
        return pageQuery.getPageNumber() + "_" + pageQuery.getPageSize() + "_" + pageQuery.getParas();
    }

}
