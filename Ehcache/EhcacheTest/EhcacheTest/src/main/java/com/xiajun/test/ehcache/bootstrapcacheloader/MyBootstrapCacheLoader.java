/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ehcache.bootstrapcacheloader;

import java.util.List;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiajun.test.ehcache.constants.CacheConstants;

/**
 * 
 * @author xiajun.xj
 * @version $Id: CustomBootstrapCacheLoader.java, v 0.1 2014年10月18日 上午10:57:26 xiajun.xj Exp $
 */
public class MyBootstrapCacheLoader implements BootstrapCacheLoader {
    private static final Logger logger = LoggerFactory
                                           .getLogger(MyBootstrapCacheLoaderFactory.class);

    StatesDAO                   statesDAO;

    boolean                     asynchronous;

    /** 
     * @see net.sf.ehcache.bootstrap.BootstrapCacheLoader#load(net.sf.ehcache.Ehcache)
     */
    public void load(Ehcache cache) throws CacheException {
        logger.info("load your cache with whatever you want....");

        List keys = cache.getKeys();
        for (int i = 0; i < keys.size(); i++) {
            logger.info("keys->" + keys.get(i));
        }

        try {
            List<String> dataList = getStatesDAO().findAllStates();
            cache.put(new Element(CacheConstants.KEY_ARRAY[0], dataList.get(0)));
            cache.put(new Element(CacheConstants.KEY_ARRAY[1], dataList.get(1)));

        } catch (Exception e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
        }

        logger.info("load end....");
    }

    /** 
     * @see net.sf.ehcache.bootstrap.BootstrapCacheLoader#isAsynchronous()
     */
    public boolean isAsynchronous() {
        return asynchronous;
    }

    /** 
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();

    }

    public StatesDAO getStatesDAO() {
        return statesDAO;
    }

    public void setStatesDAO(StatesDAO statesDAO) {
        this.statesDAO = statesDAO;
    }

    /**
     * Setter method for property <tt>asynchronous</tt>.
     * 
     * @param asynchronous value to be assigned to property asynchronous
     */
    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

}
