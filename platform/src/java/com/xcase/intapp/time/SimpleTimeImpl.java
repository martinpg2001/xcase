package com.xcase.intapp.time;

import com.xcase.intapp.time.impl.simple.core.TimeConfigurationManager;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTimeImpl implements TimeExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public TimeConfigurationManager localConfigurationManager = TimeConfigurationManager.getConfigurationManager();

}
