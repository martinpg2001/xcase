package com.xcase.mail.impl.simple.core;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.mail.constant.MailConstant;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static MailConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static MailConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new MailConfigurationManager();
            instance.configFile = MailConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = MailConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = MailConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private MailConfigurationManager() {

    }
}
