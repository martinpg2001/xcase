package com.xcase.klearnow.impl.simple.core;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.klearnow.constant.KlearNowConstant;

public class KlearNowConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static KlearNowConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static KlearNowConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new KlearNowConfigurationManager();
            instance.configFile = KlearNowConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = KlearNowConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = KlearNowConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private KlearNowConfigurationManager() {

    }
}
