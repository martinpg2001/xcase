/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import java.lang.invoke.MethodHandles;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jjia
 *
 */
public class URLUtils {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     */
    private static URLCodec iso88591Codec = new URLCodec("ISO-8859-1");

    /**
     * encode a string to url namespace.
     *
     * @param input input string
     * @return encoded string
     */
    public static String encodeUrl(String input) {
        String output = null;
        try {
            output = iso88591Codec.encode(input);
        } catch (EncoderException e) {
            // unreachable code because "ISO-8859-1" is a valid encoding.
        }

        return output;
    }
    
    /**
     * encode a string to url namespace.
     *
     * @param input input string
     * @return encoded string
     */
    public static String encodeUrl(URLCodec urlCodec, String input) {
        String output = null;
        if (urlCodec != null) {
            try {
                output = urlCodec.encode(input);
            } catch (EncoderException ee) {
                LOGGER.warn("exception encoding string: " + ee.getMessage());
            }
        }

        return output;
    }

    private URLUtils() {
    }
}
