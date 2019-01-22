/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.integrate.constant.IntegrateConstant;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class IntegrateConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static IntegrateConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static IntegrateConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new IntegrateConfigurationManager();
            instance.configFile = IntegrateConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = IntegrateConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = IntegrateConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }
    
    /**
     * private constructor, singleton.
     */
    private IntegrateConfigurationManager() {

    }
}
