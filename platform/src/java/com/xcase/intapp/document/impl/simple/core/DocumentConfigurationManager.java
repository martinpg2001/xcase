package com.xcase.intapp.document.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.intapp.document.constant.DocumentConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DocumentConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager{

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static DocumentConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static DocumentConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new DocumentConfigurationManager();
            instance.configFile = DocumentConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = DocumentConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = DocumentConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private DocumentConfigurationManager() {

    }
}
