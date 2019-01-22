/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.constant.CommonConstant;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class ConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static ConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static ConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new ConfigurationManager();
            instance.configFile = CommonConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = CommonConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = CommonConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private ConfigurationManager() {

    }
}
