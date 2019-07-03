package com.xcase.klearexpress.impl.simple.core;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.klearexpress.constant.KlearExpressConstant;

public class KlearExpressConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static KlearExpressConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static KlearExpressConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new KlearExpressConfigurationManager();
            instance.configFile = KlearExpressConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = KlearExpressConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = KlearExpressConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }
    
    /**
     * private constructor, singleton.
     */
    private KlearExpressConfigurationManager() {

    }
}
