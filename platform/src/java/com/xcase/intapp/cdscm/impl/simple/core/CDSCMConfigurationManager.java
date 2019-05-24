package com.xcase.intapp.cdscm.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.intapp.cdscm.constant.CDSCMConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSCMConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager{

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static CDSCMConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static CDSCMConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new CDSCMConfigurationManager();
            instance.configFile = CDSCMConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = CDSCMConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = CDSCMConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private CDSCMConfigurationManager() {

    }
}
