/**
 * Copyright 2019 Xcase All rights reserved.
 */
package com.xcase.rest.generator.swagger;

import com.xcase.rest.generator.*;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public abstract class SwaggerProxy extends RESTProxy {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    // helper function for building uris.
    public String appendQuery(String currentUrl, String paramName, String value) {
        try {
            LOGGER.debug("value is " + value);
            if (currentUrl.contains("?")) {
                currentUrl += String.format("&" + paramName + "=" + URLEncoder.encode(value, "UTF-8"));
            } else {
                currentUrl += String.format("?" + paramName + "=" + URLEncoder.encode(value, "UTF-8"));
            }
            
            LOGGER.debug("currentUrl is " + currentUrl);
        } catch (Exception e) {
            LOGGER.warn("exception appending query " + e.getMessage());
        }

        return currentUrl;
    }

    public abstract String getSwaggerDocument() throws Exception;
}
