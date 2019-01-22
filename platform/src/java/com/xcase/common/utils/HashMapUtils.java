/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class HashMapUtils {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Object get(HashMap hashMap, Object key, Object defaultObject) {
        return hashMap != null && hashMap.get(key) != null ? hashMap.get(key) : defaultObject;
    }
    
    public static List<NameValuePair> createNameValuePairFromHashMap(HashMap<?, ?> hashmap) {
        if (hashmap == null) {
            LOGGER.warn("hashmap is null");
            return null;
        } else {
            LOGGER.warn("hashmap is not null");
            ArrayList<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (Object key : hashmap.keySet()) {
                LOGGER.debug("next key " + key.toString());
                NameValuePair nameValuePair = new BasicNameValuePair(key.toString(), hashmap.get(key).toString());
                nameValuePairList.add(nameValuePair);
                LOGGER.debug("added " + key.toString() + ":" + hashmap.get(key).toString());
            }
            
            return nameValuePairList;
        }
    }

    private HashMapUtils() {
        
    }
}
