package com.xcase.test.appium.impl.simple.core;

import com.xcase.test.appium.constant.AppiumConstant;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppiumConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static AppiumConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static AppiumConfigurationManager getConfigurationManager() {
//        LOGGER.debug("starting getConfigurationManager()");
        if (instance == null) {
            instance = new AppiumConfigurationManager();
            instance.configFile = AppiumConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = AppiumConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = AppiumConstant.LOCAL_CONFIG_FILE_NAME;
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
    private AppiumConfigurationManager() {

    }
}
