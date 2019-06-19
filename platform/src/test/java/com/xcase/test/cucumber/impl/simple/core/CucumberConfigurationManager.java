package com.xcase.test.cucumber.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.test.cucumber.constant.CucumberConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CucumberConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static CucumberConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static CucumberConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new CucumberConfigurationManager();
            instance.configFile = CucumberConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = CucumberConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = CucumberConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }
    
    /**
     * private constructor, singleton.
     */
    private CucumberConfigurationManager() {

    }
}
