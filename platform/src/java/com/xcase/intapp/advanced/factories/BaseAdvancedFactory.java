package com.xcase.intapp.advanced.factories;

import com.xcase.common.impl.simple.core.ConfigurationManager;
import com.xcase.intapp.advanced.impl.simple.core.AdvancedConfigurationManager;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseAdvancedFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * integrate config.
     */
    private static final Properties CONFIG = AdvancedConfigurationManager.getConfigurationManager().getConfig();

    /**
     * fall-safe config.
     */
    private static Properties defaultConfig;

    /**
     *
     * @param interfaceKey interface key
     * @return implement object
     */
    protected static Object newInstanceOf(String interfaceKey) {
        LOGGER.debug("starting newInstanceOf()");
        Object obj = null;
        LOGGER.debug("interfaceKey is " + interfaceKey);
        String className = CONFIG.getProperty(interfaceKey);
        LOGGER.debug("className is " + className);
        try {
            Class clazz = Class.forName(className);
            obj = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException cnfe) {
            LOGGER.info("exception creating new instance: " + cnfe.getMessage());
        } catch (InstantiationException ie) {
            LOGGER.info("exception creating new instance: " + ie.getMessage());
        } catch (IllegalAccessException iae) {
            LOGGER.info("exception creating new instance: " + iae.getMessage());
        } catch (InvocationTargetException ite) {
            LOGGER.info("exception creating new instance: " + ite.getMessage());
        } catch (NoSuchMethodException nsme) {
            LOGGER.info("exception creating new instance: " + nsme.getMessage());
        }

        if (obj == null) {
            LOGGER.debug("obj is null");
            obj = getDefaultImpl(interfaceKey);
        }

        return obj;
    }

    /**
     * @param interfaceKey interface key
     * @return object
     */
    private static Object getDefaultImpl(String interfaceKey) {
        LOGGER.debug("starting getDefaultImpl()");
        Object obj = null;
        if (defaultConfig == null) {
            //LOGGER.debug("defaultConfig is null");
            try {
                ConfigurationManager.getConfigurationManager().loadDefaultConfig();
            } catch (Exception e) {
                LOGGER.info("exception loading default config: " + e.getMessage());
            }
        }

        String implString = (String) defaultConfig.get(interfaceKey);
        try {
            Class clazz = Class.forName(implString);
            obj = clazz.getDeclaredConstructor().newInstance();;
        } catch (ClassNotFoundException cnfe) {
            LOGGER.info("exception creating new instance: " + cnfe.getMessage());
        } catch (InstantiationException ie) {
            LOGGER.info("exception creating new instance: " + ie.getMessage());
        } catch (IllegalAccessException iae) {
            LOGGER.info("exception creating new instance: " + iae.getMessage());
        } catch (InvocationTargetException ite) {
            LOGGER.info("exception creating new instance: " + ite.getMessage());
        } catch (NoSuchMethodException nsme) {
            LOGGER.info("exception creating new instance: " + nsme.getMessage());
        }

        return obj;
    }
}
