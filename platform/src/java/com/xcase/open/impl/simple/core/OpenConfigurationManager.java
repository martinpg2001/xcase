/**
 * Copyright 2018 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.open.constant.OpenConstant;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class OpenConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static OpenConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static OpenConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new OpenConfigurationManager();
            instance.configFile = OpenConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = OpenConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = OpenConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private OpenConfigurationManager() {

    }
}
