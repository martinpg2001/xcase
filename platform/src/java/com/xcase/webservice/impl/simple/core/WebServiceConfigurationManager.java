/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.webservice.constant.WebServiceConstant;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class WebServiceConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static WebServiceConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static WebServiceConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new WebServiceConfigurationManager();
            instance.configFile = WebServiceConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = WebServiceConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = WebServiceConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private WebServiceConfigurationManager() {

    }
}
