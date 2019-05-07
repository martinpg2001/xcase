package com.xcase.intapp.advanced.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.intapp.advanced.constant.AdvancedConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager{

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static AdvancedConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static AdvancedConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new AdvancedConfigurationManager();
            instance.configFile = AdvancedConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = AdvancedConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = AdvancedConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private AdvancedConfigurationManager() {

    }
}
