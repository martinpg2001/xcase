package com.xcase.ebay.impl.simple.core;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.ebay.constant.EBayConstant;

public class EBayConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static EBayConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static EBayConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new EBayConfigurationManager();
            instance.configFile = EBayConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = EBayConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = EBayConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }
    
    /**
     * private constructor, singleton.
     */
    private EBayConfigurationManager() {

    }
}
