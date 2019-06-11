package com.xcase.aws.impl.simple.core;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.aws.constant.AWSConstant;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;

public class AWSConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static AWSConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static AWSConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new AWSConfigurationManager();
            instance.configFile = AWSConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = AWSConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = AWSConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private AWSConfigurationManager() {

    }
}
