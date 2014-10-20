/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ehcache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author xiajun.xj
 * @version $Id: EncacheTest.java, v 0.1 2014年8月8日 下午5:30:03 xiajun.xj Exp $
 */
public class SimpleUseEhcacheTest {
    private static CacheManager cacheManagerDefault;
    private static CacheManager cacheManager1;
    private static String       cacheName1             = "cache1";                                //name of cache
    private static String       cacheName2             = "cache2";
    private static String       log4jFileName          = "src/config/log4j.properties";
    private static String       cacheNameOfCustomPara  = "cacheOfCustomPara";
    private static String       configurationFileName1 = "src/config/ehcache.xml";                //cofiguration file name

    private static final Logger logger                 = LoggerFactory
                                                           .getLogger(SimpleUseEhcacheTest.class);

    public static void main(String[] args) {
        configProperty();

        ehcacheSetUp();

        ehcacheUse();

        cacheManagerShutdown();

        //        System.err.println(EncacheTest.class.getResource(""));
        //
        //        System.err.println(EncacheTest.class.getResource("/"));
        //        System.getProperties().list(System.out);
    }

    /**
     * setup:
     * initialize cacheManager
     * and cache
     * 
     */
    private static void ehcacheSetUp() {
        logger.info("Step1:setup");

        //1.About CacheManager
        //create a CachManager with default configuration
        //it will look for ehcache.xml in the classpath
        cacheManagerDefault = CacheManager.create();

        String[] cacheNames = cacheManagerDefault.getCacheNames();
        //System.out.println("cacheNames" + cacheNames);

        //create a CacheManager with a specified configuration file
        cacheManager1 = CacheManager.newInstance(configurationFileName1);

        //2. About Cache
        //create a cache that will be configured using defaultCache from the configuration
        //NOTE: that Caches are not usable until they have been added to a CacheManager.
        cacheManagerDefault.addCache(cacheName1);
        cacheNames = cacheManagerDefault.getCacheNames();
        System.out.println("caches num :" + cacheNames.length);

        //use the constructor to create a Cache
        //the patameter is :
        //        String name, int maxElementsInMemory, boolean overflowToDisk,
        //        boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds
        Cache cacheWithSomeParameters = new Cache(cacheName2, 5000, false, false, 5, 2);
        cacheManagerDefault.addCache(cacheWithSomeParameters);
        System.out.println("caches num :" + cacheNames.length);
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
    }

    /**
     * A sample use of Ehcache
     * 
     */
    @SuppressWarnings("deprecation")
    private static void ehcacheUse() {
        //use cache to store element
        Cache cacheDefault = cacheManagerDefault.getCache(cacheName1);

        //Get the number of elements currently in the MemoryStore
        long elementsInMemoryStore = cacheDefault.getMemoryStoreSize();
        System.out.println("the number of elements in MemoryStore is  " + elementsInMemoryStore);

        String key1 = "key1";
        String value1 = "value1";
        String value2 = "value2";

        writeSomeData(cacheDefault, key1, value1);
        Element element = readSomeData(cacheDefault, key1);
        System.out.println("The element of key " + key1 + " is :" + element);

        //This updates the entry for "key1"
        writeSomeData(cacheDefault, key1, value2);
        element = readSomeData(cacheDefault, key1);
        System.out.println("The element of key " + key1 + " is :" + element);

        //Get the number of elements currently in the MemoryStore
        elementsInMemoryStore = cacheDefault.getMemoryStoreSize();
        System.out.println("the number of elements in MemoryStore is  " + elementsInMemoryStore);
        //Get the number of elements currently in the DiskStore.

        long elementsInDiskStore = cacheDefault.getDiskStoreSize();
        System.out.println("the number of elements in DiskStore is  " + elementsInDiskStore);

        //Get a Serializable value from an element 
        Serializable serializableValue = element.getValue();
        System.out.println("serializableValue : " + serializableValue);

        //Get a NonSerializable value from an element
        Object objectValue = element.getValue();
        System.out.println("objectValue : " + objectValue);

        getCacheStatistic(element);

        //Remove an element from a cache
        cacheDefault.remove(key1);
        element = readSomeData(cacheDefault, key1);
        System.out.println("The element of key " + key1 + " is :" + element);

        Cache cacheWithSomeParameters = cacheManagerDefault.getCache(cacheName2);
        System.out.println("cacheWithSomeParameters : " + cacheWithSomeParameters);

        //remove cache from cacheManager
        cacheManagerDefault.removeCache(cacheName2);

        //add a cache
        cacheManager1.addCache(createCacheWithCustomPara());
    }

    /**
     * get some statistics
     * 
     * @param element
     */
    private static void getCacheStatistic(Element element) {
        if (null == element) {
            logger.error("Illegal Parameter!");
        }
        //Get the number of times requested items were found in the cache (cache hits).
        long hits = element.getHitCount();
        System.out.println("the hit count is : " + hits);

        //it seems that the following methods do not exist any more in Ehcache 2.8.3 version !!!
        //Get the number of times requested items were found in the MemoryStore of the cache
        //long hitsOfMemoryStore = element.getMemoryStoreHitCount();
        //Get the number of times requested items were found in the DiskStore of the cache.
        //int hitsOfDiskStore = cache.getDiskStoreHitCount();
        //Get the number of times requested items were not found in the cache due to expiry of the elements.
        //long hitsOfMissExpired = cache.getStatistics().cacheMissExpiredCount();
        //System.out.println("the hitsOfMissExpired count is : " + hitsOfMissExpired);
    }

    /**
     * do clear 
     * 
     * @param cacheManager
     */
    private static void cacheManagerShutdown() {
        //Ehcache should be shutdown after use. 
        //It does have a shutdown hook, but it is best practice to shut it down in your code.
        cacheManager1.shutdown();
        cacheManagerDefault.shutdown();
    }

    //Creating a new cache with custom parameters
    private static Cache createCacheWithCustomPara() {
        //some parameters of Cache to be created
        int maxEntriesLocalHeap = 2000;
        int timeToLiveSeconds = 60;
        int timeToIdleSeconds = 30;
        int diskExpiryThreadIntervalSeconds = 0;
        Cache cacheOfCustomPara = new Cache(new CacheConfiguration(cacheNameOfCustomPara,
            maxEntriesLocalHeap).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
            .eternal(false).timeToLiveSeconds(timeToLiveSeconds)
            .timeToIdleSeconds(timeToIdleSeconds)
            .diskExpiryThreadIntervalSeconds(diskExpiryThreadIntervalSeconds)
            .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)));

        return cacheOfCustomPara;
    }

    private static void writeSomeData(Cache cache, String key, String value) {
        cache.put(new Element(key, value));
    }

    private static Element readSomeData(Cache cache, String key) {
        return cache.get(key);
    }
}
