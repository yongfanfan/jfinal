package com.haitai.haitaitv.component.cache;

import com.haitai.haitaitv.component.constant.OtherConsts;
import com.haitai.haitaitv.component.util.serialize.FSTSerializer;
import com.haitai.haitaitv.component.util.serialize.Serializer;
import com.jfinal.plugin.activerecord.cache.ICache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.management.ManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.ext.SimpleCacheInterceptor;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Created by liuzhou on 2017/3/5.
 */
public class MyCacheKit {

    private static final ConcurrentHashMap<String, MyCache> CACHE_MAP = new ConcurrentHashMap<>();
    private static final Serializer SERIALIZER = new FSTSerializer();
    private static final Logger LOG = LogManager.getLogger(MyCacheKit.class);
    private static final JfinalCacheAdapter JFINAL_CACHE_ADAPTER = new JfinalCacheAdapter();
    private static final BeetlsqlCacheAdapter BEETLSQL_CACHE_ADAPTER = new BeetlsqlCacheAdapter();
    private static CacheManager cacheManager = CacheManager.create(MyCacheKit.class.getResource("/conf/ehcache.xml"));
    private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    /**
     * 标记缓存的值是null
     */
    private static final Object NULL_VALUE = new Object();

    static {
        // 注册ehcache自带的MBean
        if (OtherConsts.ENABLE_EHCACHE_JMX) {
            ManagementService.registerMBeans(cacheManager, mBeanServer, true, true, true, true);
        }
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    public static ICache getICache() {
        return JFINAL_CACHE_ADAPTER;
    }

    public static SimpleCacheInterceptor.CacheManager getSimpleCacheInterCacheMamager() {
        return BEETLSQL_CACHE_ADAPTER;
    }

    public static MyCache get(String name) {
        MyCache adapter = CACHE_MAP.get(name);
        if (adapter == null) {
            synchronized (MyCacheKit.class) {
                adapter = CACHE_MAP.get(name);
                if (adapter == null) {
                    cacheManager.addCacheIfAbsent(name);
                    adapter = new EhCacheImpl(cacheManager.getCache(name));
                    CACHE_MAP.put(name, adapter);
                    // 注册自定义的MBean
                    if (OtherConsts.ENABLE_EHCACHE_JMX) {
                        try {
                            ObjectName objectName = new ObjectName("net.sf.ehcache:type=CustomMBean,name=" + name);
                            EhcacheExtendWatcher watcher = new EhcacheExtendWatcher(cacheManager.getEhcache(name));
                            mBeanServer.registerMBean(watcher, objectName);
                        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
                            LOG.error("注册MBean失败", e);
                        }
                    }
                }
            }
        }
        return adapter;
    }

    public static class EhCacheImpl implements MyCache {
        private Cache cache;

        private EhCacheImpl(Cache cache) {
            this.cache = cache;
        }

        @Override
        public <T> Optional<T> get(Object key) {
            Objects.requireNonNull(key, "缓存的键不得为空");
            Element element = cache.get(key);
            if (element == null || element.getObjectValue() == NULL_VALUE) {
                return Optional.empty();
            }
            try {
                T value = SERIALIZER.deserialize((byte[]) element.getObjectValue());
                return Optional.of(value);
            } catch (IOException e) {
                LOG.warn("get时反序列化失败");
                return Optional.empty();
            }
        }

        @Override
        public <T> T orElseGet(Object key, Supplier<? extends T> other) {
            Objects.requireNonNull(key, "缓存的键不得为空");
            Element element = cache.get(key);
            if (element == null) {
                T value = other.get();
                put(key, value);
                return value;
            }
            if (element.getObjectValue() == NULL_VALUE) {
                return null;
            }
            try {
                return SERIALIZER.deserialize((byte[]) element.getObjectValue());
            } catch (IOException e) {
                LOG.warn("orElseGet时反序列化失败");
                return null;
            }
        }

        /**
         * @param key   缓存的key，不得为空
         * @param value 缓存的value，允许为空
         * @return 是否成功存入缓存
         */
        @Override
        public boolean put(Object key, Object value) {
            Objects.requireNonNull(key, "缓存的键不得为空");
            Element element;
            try {
                if (value == null) {
                    element = new Element(key, NULL_VALUE);
                } else {
                    element = new Element(key, SERIALIZER.serialize(value));
                }
            } catch (IOException e) {
                LOG.warn("put时序列化失败");
                return false;
            }
            cache.put(element);
            return true;
        }

        @Override
        public <T> T remove(Object key) {
            if (key == null) {
                return null;
            }
            Element element = cache.get(key);
            cache.remove(key);
            if (element == null || element.getObjectValue() == NULL_VALUE) {
                return null;
            }
            try {
                return SERIALIZER.deserialize((byte[]) element.getObjectValue());
            } catch (IOException e) {
                LOG.warn("remove时反序列化失败");
                return null;
            }
        }

        @Override
        public void removeAll() {
            cache.removeAll();
        }
    }

    public static class JfinalCacheAdapter implements ICache {

        private JfinalCacheAdapter() {
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(String cacheName, Object key) {
            return (T) MyCacheKit.get(cacheName).get(key).orElse(null);
        }

        @Override
        public void put(String cacheName, Object key, Object value) {
            MyCacheKit.get(cacheName).put(key, value);
        }

        @Override
        public void remove(String cacheName, Object key) {
            MyCacheKit.get(cacheName).remove(key);
        }

        @Override
        public void removeAll(String cacheName) {
            MyCacheKit.get(cacheName).removeAll();
        }
    }

    public static class BeetlsqlCacheAdapter implements SimpleCacheInterceptor.CacheManager {

        private BeetlsqlCacheAdapter() {
        }

        @Override
        public void initCache(String ns) {
            MyCacheKit.get("beetsql_" + ns);
        }

        @Override
        public void putCache(String ns, Object key, Object value) {
            MyCacheKit.get("beetsql_" + ns).put(key, value);
        }

        @Override
        public Object getCache(String ns, Object key) {
            return MyCacheKit.get("beetsql_" + ns).get(key).orElse(null);
        }

        @Override
        public void clearCache(String ns) {
            MyCacheKit.get("beetsql_" + ns).removeAll();
        }

        @Override
        public boolean containCache(String ns, Object key) {
            return MyCacheKit.get("beetsql_" + ns).get(key).isPresent();
        }
    }

}
