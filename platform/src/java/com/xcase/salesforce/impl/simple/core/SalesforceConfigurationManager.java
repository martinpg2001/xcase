/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.salesforce.constant.SalesforceConstant;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SalesforceConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static SalesforceConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static SalesforceConfigurationManager getConfigurationManager() {
//        LOGGER.debug("starting getConfigurationManager()");
        if (instance == null) {
            instance = new SalesforceConfigurationManager();
            instance.configFile = SalesforceConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = SalesforceConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = SalesforceConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

//        LOGGER.debug("about to return instance");
        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private SalesforceConfigurationManager() {

    }
}
