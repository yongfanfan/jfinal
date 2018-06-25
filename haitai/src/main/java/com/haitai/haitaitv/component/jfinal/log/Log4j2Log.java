package com.haitai.haitaitv.component.jfinal.log;

import com.jfinal.log.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 适配log4j2
 *
 * @author liuzhou
 *         create at 2017-06-16 10:36
 */
public class Log4j2Log extends Log {

    private Logger logger;

    Log4j2Log(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    Log4j2Log(String name) {
        logger = LogManager.getLogger(name);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void debug(String message, Throwable t) {
        logger.debug(message, t);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String message, Throwable t) {
        logger.info(message, t);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void warn(String message, Throwable t) {
        logger.warn(message, t);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Throwable t) {
        logger.error(message, t
        );
    }

    @Override
    public void fatal(String message) {
        logger.fatal(message);
    }

    @Override
    public void fatal(String message, Throwable t) {
        logger.fatal(message, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isFatalEnabled();
    }
}
