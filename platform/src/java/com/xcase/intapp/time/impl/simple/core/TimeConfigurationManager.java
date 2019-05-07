package com.xcase.intapp.time.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.intapp.time.constant.TimeConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager{

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static TimeConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static TimeConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new TimeConfigurationManager();
            instance.configFile = TimeConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = TimeConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = TimeConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private TimeConfigurationManager() {

    }
}
