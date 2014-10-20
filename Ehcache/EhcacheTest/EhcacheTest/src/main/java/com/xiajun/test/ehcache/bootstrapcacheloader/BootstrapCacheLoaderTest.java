/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ehcache.bootstrapcacheloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xiajun.test.ehcache.constants.CacheConstants;

/**
 * 
 * @author xiajun.xj
 * @version $Id: BootstrapCacheLoader.java, v 0.1 2014年10月18日 上午11:31:06 xiajun.xj Exp $
 */
public class BootstrapCacheLoaderTest {
    private static String       log4jFileName      = "src/config/log4j.properties";
    private static String       xmlFileName        = "src/config/ApplicationContext.xml";
    private static String       ehcacheXmlFileName = "src/config/myehcache.xml";

    private static final Logger logger             = LoggerFactory
                                                       .getLogger(BootstrapCacheLoaderTest.class);

    private static CacheManager ehCacheManager;

    public static void main(String[] args) {
        configProperty();

        xmlLoad(ehcacheXmlFileName);

        String[] cacheNamesStrings = ehCacheManager.getCacheNames();
        logger.info("the number of caches in ehCacheManager : " + cacheNamesStrings.length);
        Cache cache = ehCacheManager.getCache(CacheConstants.CACHE_NAME1);
        Element element = cache.get(CacheConstants.KEY_ARRAY[0]);
        logger.info("the element of key  " + CacheConstants.KEY_ARRAY[0] + " is " + element);

    }

    /**
     * config properties
     * 
     */
    private static void configProperty() {
        Properties properties = new Properties();
        FileInputStream istream;
        try {
            istream = new FileInputStream(log4jFileName);
            properties.load(istream);
            istream.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        } catch (IOException e) {
            logger.error("load file erroe", e);
        } finally {

        }

        //properties.setProperty("log4j.appender.file.File",logFile); 
        PropertyConfigurator.configure(properties);
        logger.info("config properties success.");
    }

    private static void xmlLoad(String fileName) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext(xmlFileName);

        ehCacheManager = (CacheManager) ctx.getBean("ehCacheManager");

    }
}
