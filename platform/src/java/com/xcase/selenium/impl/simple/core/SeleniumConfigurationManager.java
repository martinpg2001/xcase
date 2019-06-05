package com.xcase.selenium.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.selenium.constant.SeleniumConstant;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeleniumConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static SeleniumConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static SeleniumConfigurationManager getConfigurationManager() {
//        LOGGER.debug("starting getConfigurationManager()");
        if (instance == null) {
            instance = new SeleniumConfigurationManager();
            instance.configFile = SeleniumConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = SeleniumConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = SeleniumConstant.LOCAL_CONFIG_FILE_NAME;
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
    private SeleniumConfigurationManager() {

    }
}
