package com.xcase.intapp.cdsrefdata.impl.simple.core;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.IConfigurationManager;
import com.xcase.common.impl.simple.core.AbstractConfigurationManager;
import com.xcase.intapp.cdsrefdata.constant.CDSRefDataConstant;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.impl.simple.core.IntegrateConfigurationManager;

public class CDSRefDataConfigurationManager extends AbstractConfigurationManager implements IConfigurationManager{

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static CDSRefDataConfigurationManager instance;

    /**
     * get the only one manager.
     *
     * @return ConfigurationManager
     */
    public static CDSRefDataConfigurationManager getConfigurationManager() {
        if (instance == null) {
            instance = new CDSRefDataConfigurationManager();
            instance.configFile = CDSRefDataConstant.CONFIG_FILE_NAME;
            instance.defaultConfigFile = CDSRefDataConstant.CONFIG_FILE_DEFAULT_NAME;
            instance.localConfigFile = CDSRefDataConstant.LOCAL_CONFIG_FILE_NAME;
            LOGGER.debug("about to load config properties");
            instance.loadConfigProperties();
            LOGGER.debug("loaded config properties");
        }

        return instance;
    }

    /**
     * private constructor, singleton.
     */
    private CDSRefDataConfigurationManager() {

    }
}
