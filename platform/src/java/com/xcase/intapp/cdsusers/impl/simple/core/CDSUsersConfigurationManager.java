package com.xcase.intapp.cdsusers.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.intapp.cdsusers.constant.CDSUsersConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSUsersConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager{

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static CDSUsersConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static CDSUsersConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new CDSUsersConfigurationManager();
            instance.configFile = CDSUsersConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = CDSUsersConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = CDSUsersConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private CDSUsersConfigurationManager() {

    }
}
