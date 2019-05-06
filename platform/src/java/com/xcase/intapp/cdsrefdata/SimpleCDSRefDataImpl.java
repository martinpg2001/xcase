package com.xcase.intapp.cdsrefdata;

import com.xcase.intapp.cdsrefdata.impl.simple.core.CDSRefDataConfigurationManager;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleCDSRefDataImpl implements CDSRefDataExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSRefDataConfigurationManager localConfigurationManager = CDSRefDataConfigurationManager.getConfigurationManager();

}
