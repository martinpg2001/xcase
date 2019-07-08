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
     * @param localConfig the config to set
     */
    public void setLocalConfig(Properties localConfig) {
        this.localConfig = localConfig;
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
            propertyPath = "/" + configFile;
            LOGGER.debug("propertyPath is " + propertyPath);
            InputStream configInputStream = getClass().getResourceAsStream(propertyPath);
            this.config.load(configInputStream);
            LOGGER.debug("loaded config from " + propertyPath);
            localPropertyPath = userDir + File.separator + localConfigFile;
            LOGGER.debug("localPropertyPath is " + localPropertyPath);
            try {
                InputStream localConfigInputStream = new FileInputStream(new File(localPropertyPath));
                this.localConfig.load(localConfigInputStream);
                LOGGER.debug("loaded local config from " + localPropertyPath);
            } catch (FileNotFoundException fnfe) {
                LOGGER.warn("FileNotFoundException happened when loading " + localPropertyPath, fnfe);
            } catch (Exception e) {
                LOGGER.warn("Exception happened when loading " + localPropertyPath, e);
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.debug("FileNotFoundException happened when loading " + propertyPath);
            LOGGER.warn(propertyPath + " not found in classpath, use default properties", fnfe);
            InputStream defaultConfigInputStream = this.getClass().getResourceAsStream(defaultConfigFile);
            try {
                this.config.load(defaultConfigInputStream);
            } catch (FileNotFoundException subfnfe) {
                LOGGER.fatal("FileNotFoundException happened when loading default properties " + defaultConfigFile, subfnfe);
            } catch (IOException subioe) {
                LOGGER.fatal("IOException happened when loading default properties " + defaultConfigFile, subioe);
            } catch (Exception sube) {
                LOGGER.fatal("Exception happened when loading default properties " + defaultConfigFile, sube);
            }
        } catch (IOException ioe) {
            LOGGER.fatal("IOException happened when loading properties " + propertyPath, ioe);
        } catch (Exception e) {
            LOGGER.fatal("Exception happened when loading properties " + propertyPath, e);
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
            InputStream defaultConfigInputStream = getClass().getResourceAsStream("/" + defaultConfigFile);
            this.config.load(defaultConfigInputStream);
            LOGGER.debug("loaded config from " + propertyPath);
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn("FileNotFoundException happened when loading " + propertyPath, fnfe);
        } catch (IOException ioe) {
            LOGGER.fatal("IOException happened when loading " + propertyPath, ioe);
        }  catch (Exception e) {
            LOGGER.warn(propertyPath + " not found in classpath");
            LOGGER.fatal("Exception happened when loading " + propertyPath, e);
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
            InputStream localConfigInputStream = new FileInputStream(new File(localPropertyPath));
            this.localConfig.load(localConfigInputStream);
            LOGGER.debug("loaded local config from " + localPropertyPath);
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn("FileNotFoundException happened when loading " + localPropertyPath, fnfe);
            LOGGER.debug(localPropertyPath + " not found in classpath, use " + defaultConfigFile);
            InputStream defaultConfigInputStream = this.getClass().getResourceAsStream(defaultConfigFile);
            try {
                this.config.load(defaultConfigInputStream);
            } catch (FileNotFoundException subfnfe) {
                LOGGER.fatal("FileNotFoundException happened when loading " + defaultConfigFile, subfnfe);
            } catch (IOException subioe) {
                LOGGER.fatal("IOException happened when loading " + defaultConfigFile, subioe);
            } catch (Exception sube) {
                LOGGER.fatal("Exception happened when loading " + defaultConfigFile, sube);
            }
        } catch (IOException ioe) {
            LOGGER.fatal("IOException happened when loading " + localPropertyPath, ioe);
        } catch (Exception e) {
            LOGGER.fatal("Exception happened when loading " + localPropertyPath, e);
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
            OutputStream configOutputStream = new FileOutputStream(new File(propertyPath));
            this.config.store(configOutputStream, "Storing new config");
            configOutputStream.close();
        } catch (FileNotFoundException fnfe) {
            LOGGER.debug("FileNotFoundException happened when storing " + propertyPath, fnfe);
        } catch (IOException ioe) {
            LOGGER.debug("IOException happened when storing " + propertyPath, ioe);
        } catch (Exception e) {
            LOGGER.fatal("Exception happened when storing " + propertyPath, e);
        } finally {
            LOGGER.debug("finally");
        }
    }
}
