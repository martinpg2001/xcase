package com.xcase.azure.impl.simple.core;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.azure.constant.AzureConstant;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;

public class AzureConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static AzureConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static AzureConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new AzureConfigurationManager();
            instance.configFile = AzureConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = AzureConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = AzureConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private AzureConfigurationManager() {

    }
}
