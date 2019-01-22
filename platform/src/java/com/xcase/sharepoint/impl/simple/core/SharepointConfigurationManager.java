/**
 * Copyright 2018 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.sharepoint.constant.SharepointConstant;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SharepointConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static SharepointConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static SharepointConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new SharepointConfigurationManager();
            instance.configFile = SharepointConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = SharepointConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = SharepointConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private SharepointConfigurationManager() {

    }
}
