/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import com.google.gson.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author martin
 *
 */
public class ConverterUtils {

    /**
     * log4j logger.
     */
    static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static JsonElement parseStringToJson(String jsonString) {
        JsonElement jsonElement = null;
        if (jsonString != null && !jsonString.equals("")) {
            jsonElement = JsonParser.parseString(jsonString);
        } else {
            jsonElement = JsonNull.INSTANCE;
        }

        return jsonElement;
    }

    /**
     *
     */
    private ConverterUtils() {

    }
}
