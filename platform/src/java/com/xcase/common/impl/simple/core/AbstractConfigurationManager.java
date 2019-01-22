/**
 * Copyright 2018 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AbstractConfigurationManager {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * config properties.
     */
    public Properties config;

    /**
     * config properties.
     */
    public Properties localConfig;

    public String configFile;
    public String defaultConfigFile;
    public String localConfigFile;
    
    /**
     * @return the config
     */
    public Properties getConfig() {
//        LOGGER.debug("starting getConfig()");
        return this.config;
    }

    /**
     * @return the localConfig
     */
    public Properties getLocalConfig() {
//        LOGGER.debug("starting getLocalConfig()");
        return this.localConfig;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(Properties config) {
        this.config = config;
    }
    
    /**
     * @param config the config to set
     */
    public void setLocalConfig(Properties config) {
        this.localConfig = config;
    }
    
    public String getLocalProperty(String name) {
        return getLocalConfig().getProperty(name);
    }
    
    public String getProperty(String name) {
        return getConfig().getProperty(name);
    }
    
    public void setLocalProperty(String name, String value) {
        getLocalConfig().setProperty(name, value);
    }
    
    public void setProperty(String name, String value) {
        getConfig().setProperty(name, value);
    }
    
    /**
     * load config file to properties.
     */
    public void loadConfigProperties() {
        LOGGER.debug("starting loadConfigProperties()");
        String propertyPath = null;
        String localPropertyPath = null;
        this.config = new Properties();
        this.localConfig = new Properties();
        try {
            String userDir = System.getProperty("user.dir");
            InputStream in = getClass().getResourceAsStream("/" + configFile);
            this.config.load(in);
            LOGGER.debug("loaded config from InputStream");
            localPropertyPath = userDir + File.separator + localConfigFile;
            LOGGER.debug("localPropertyPath is " + localPropertyPath);
            try {
                InputStream localIn = new FileInputStream(new File(localPropertyPath));
                this.localConfig.load(localIn);
                LOGGER.debug("loaded local config");
            } catch (FileNotFoundException fnfe) {
                LOGGER.warn("FileNotFoundException happened when reading " + localPropertyPath);
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.debug("FileNotFoundException happened when reading " + propertyPath);
            LOGGER.warn("common-config.properties not found in classpath, use default-common-config.properties.");
            InputStream in = this.getClass().getResourceAsStream(defaultConfigFile);
            try {
                this.config.load(in);
            } catch (IOException ioe) {
                LOGGER.fatal("IOException happened when loading default-common-config.properties", ioe);
            }
        } catch (IOException ioe) {
            LOGGER.fatal("IOException occurred when reading common-config.properties", ioe);
        }

        LOGGER.debug("finishing loadConfigProperties()");
    }
    
    /**
     * load default config file to properties.
     */
    public void loadDefaultConfig() {
        LOGGER.debug("starting loadDefaultConfig()");
        String propertyPath = null;
        this.config = new Properties();
        try {
            String userDir = System.getProperty("user.dir");
            propertyPath = userDir + File.separator + defaultConfigFile;
            LOGGER.debug("propertyPath is " + propertyPath);
            InputStream in = getClass().getResourceAsStream("/" + defaultConfigFile);
            this.config.load(in);
            LOGGER.debug("loaded inputstream");
        } catch (Exception e) {
            LOGGER.debug("FileNotFoundException happened when reading " + propertyPath);
            LOGGER.warn("common-config.properties not found in classpath, use common-config-default.properties.");
            LOGGER.fatal("Exception occurred when reading common-config-default.properties.properties", e);
        }

        LOGGER.debug("finishing loadDefaultConfig()");
    }
    
    /**
     * load local config file to properties.
     */
    public void loadLocalConfigProperties() {
        LOGGER.debug("starting loadLocalConfigProperties()");
        String localPropertyPath = null;
        this.localConfig = new Properties();
        try {
            String userDir = System.getProperty("user.dir");
            localPropertyPath = userDir + File.separator + localConfigFile;
            LOGGER.debug("localPropertyPath is " + localPropertyPath);
            InputStream localIn = new FileInputStream(new File(localPropertyPath));
            this.localConfig.load(localIn);
            LOGGER.debug("loaded local config");
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn("FileNotFoundException happened when reading " + localPropertyPath);
            LOGGER.debug("webservice-config.properties not found in classpath, use common-config-default.properties.");
            InputStream in = this.getClass().getResourceAsStream(defaultConfigFile);
            try {
                this.config.load(in);
            } catch (IOException ioe) {
                LOGGER.fatal("IOException happened when loading common-config-default.properties", ioe);
            }
        } catch (IOException ioe) {
            LOGGER.fatal("IOException occurred when reading common-local-config.properties", ioe);
        }
    }
    
    /**
     * save config file from properties.
     */
    public void storeLocalConfigProperties() {
        LOGGER.debug("starting storeLocalConfigProperties()");
        String localPropertyPath = null;
        try {
            String userDir = System.getProperty("user.dir");
            localPropertyPath = userDir + File.separator + localConfigFile;
            OutputStream localOutputStream = new FileOutputStream(new File(localPropertyPath));
            this.localConfig.store(localOutputStream, "Storing new local config");
            localOutputStream.close();
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn("FileNotFoundException happened when writing to: " + localPropertyPath);
        } catch (IOException ioe) {
            LOGGER.warn("IOException happened when writing to: " + localPropertyPath);
        } finally {
            LOGGER.debug("finally");
        }
    }

    /**
     * save config file from properties.
     */
    public void storeConfigProperties() {
        LOGGER.debug("starting storeConfigProperties()");
        String propertyPath = null;
        try {
            String userDir = System.getProperty("user.dir");
            propertyPath = userDir + File.separator + localConfigFile;
            OutputStream outputStream = new FileOutputStream(new File(propertyPath));
            this.config.store(outputStream, "Storing new config");
            outputStream.close();
        } catch (FileNotFoundException fnfe) {
            LOGGER.debug("FileNotFoundException happened when writing: " + propertyPath);
        } catch (IOException ioe) {
            LOGGER.debug("IOException happened when writing: " + ioe.getMessage());
            LOGGER.fatal("IOException occurred when writing local-common-config.properties", ioe);
        } finally {
            LOGGER.debug("finally");
        }
    }
}
