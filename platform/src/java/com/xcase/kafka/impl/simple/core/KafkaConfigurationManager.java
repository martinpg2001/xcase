package com.xcase.kafka.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.kafka.constant.KafkaConstant;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static KafkaConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static KafkaConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new KafkaConfigurationManager();
            instance.configFile = KafkaConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = KafkaConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = KafkaConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }
    
    /**
     * private constructor, singleton.
     */
    private KafkaConfigurationManager() {

    }
}
