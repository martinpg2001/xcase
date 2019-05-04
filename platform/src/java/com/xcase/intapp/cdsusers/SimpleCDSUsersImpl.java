package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleCDSUsersImpl implements CDSUsersExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSUsersConfigurationManager localConfigurationManager = CDSUsersConfigurationManager.getConfigurationManager();

}
