package com.xcase.slack.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.slack.constant.SlackConstant;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlackConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static SlackConfigurationManager instance;
    
    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static SlackConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new SlackConfigurationManager();
            instance.configFile = SlackConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = SlackConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = SlackConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }
}
