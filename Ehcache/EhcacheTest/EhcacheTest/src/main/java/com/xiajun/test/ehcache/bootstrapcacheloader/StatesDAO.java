/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ehcache.bootstrapcacheloader;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * 
 * @author xiajun.xj
 * @version $Id: StatesDAO.java, v 0.1 2014年10月17日 下午8:07:05 xiajun.xj Exp $
 */
public class StatesDAO {
    //annotation based caching and the name of cache should be defined in ehcache.xml. 

    @Cacheable(cacheName = "stateCache")
    public List<String> findAllStates() {
        List<String> dataList = new ArrayList<String>();

        //your call to database that returns a list of objects  
        dataList.add("value1");
        dataList.add("value2");

        return dataList;
    }
}
