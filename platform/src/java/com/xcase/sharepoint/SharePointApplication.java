/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint;

import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SharePointApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
        } catch (Exception e) {
        }
    }
}
