/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ehcache.bootstrapcacheloader;

import java.util.Properties;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiajun.test.ehcache.constants.CacheConstants;

/**
 * bootstrap cache loader
 * 
 * @author xiajun.xj
 * @version $Id: MyBootstrapCacheLoaderFactory.java, v 0.1 2014年10月17日 下午8:02:55 xiajun.xj Exp $
 */
public class MyBootstrapCacheLoaderFactory extends BootstrapCacheLoaderFactory {

    @Autowired
    private StatesDAO statesDAO;

    public MyBootstrapCacheLoaderFactory() {
        super();
        // TODO Auto-generated constructor stub 
    }

    private static final Logger logger = LoggerFactory
                                           .getLogger(MyBootstrapCacheLoaderFactory.class);

    @Override
    public BootstrapCacheLoader createBootstrapCacheLoader(Properties properties) {
        logger.info("MyBootstrapCacheLoaderFactory : create a BootstrapCacheLoader");
        MyBootstrapCacheLoader loader = new MyBootstrapCacheLoader();
        statesDAO = new StatesDAO();
        loader.setStatesDAO(statesDAO);
        loader.setAsynchronous(getAsyncFromProperty(properties));

        return loader;
    }

    private boolean getAsyncFromProperty(Properties properties) {
        String asynchronous = properties.getProperty(CacheConstants.ASYNCHRONOUS_PROPERTY_KEY);

        return Boolean.valueOf(asynchronous);
    }
}
