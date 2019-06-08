package com.xcase.azure;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;
import com.xcase.azure.constant.AzureConstant;
import com.xcase.azure.impl.simple.core.AzureConfigurationManager;

public class AzureApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            String client = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_CLIENT);
            LOGGER.debug("client is " + client);
            String tenant = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_TENANT);
            LOGGER.debug("tenant is " + tenant);
            String key = AzureConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AzureConstant.LOCAL_AZURE_KEY);
            LOGGER.debug("key is " + key);
            ApplicationTokenCredentials applicationTokenCredentials = new ApplicationTokenCredentials(client, tenant, key, AzureEnvironment.AZURE);
            Azure azure = Azure.configure().withLogLevel(LogLevel.BASIC).authenticate(applicationTokenCredentials).withDefaultSubscription();
            LOGGER.debug("authenticated Azure");
        } catch (Exception e) {
            LOGGER.warn("exception executing Azure operations: " + e.getMessage());
        }

    }
}
