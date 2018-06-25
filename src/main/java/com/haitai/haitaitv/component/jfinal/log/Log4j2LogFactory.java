package com.haitai.haitaitv.component.jfinal.log;

import com.jfinal.log.ILogFactory;
import com.jfinal.log.Log;

/**
 * @author liuzhou
 *         create at 2017-06-16 10:46
 */
public class Log4j2LogFactory implements ILogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Log4j2Log(clazz);
    }

    @Override
    public Log getLog(String name) {
        return new Log4j2Log(name);
    }
}
