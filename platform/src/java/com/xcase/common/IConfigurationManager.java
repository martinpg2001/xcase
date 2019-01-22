/**
 * Copyright 2018 Xcase All rights reserved.
 */
package com.xcase.common;

/**
 *
 * @author martin
 */
public interface IConfigurationManager {
    public String getLocalProperty(String name);
    public String getProperty(String name);
    public void loadConfigProperties();
    public void loadLocalConfigProperties();
    public void setLocalProperty(String name, String value);
    public void setProperty(String name, String value);
    public void storeConfigProperties();
    public void storeLocalConfigProperties();
}
