/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.core;

import com.xcase.box.constant.BoxConstant;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import java.io.*;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class BoxConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static BoxConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static BoxConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new BoxConfigurationManager();
            instance.configFile = BoxConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = BoxConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = BoxConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private BoxConfigurationManager() {

    }
}
