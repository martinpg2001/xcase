package com.xcase.common.factories;

import com.xcase.common.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonRequestFactory extends BaseCommonFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static CommonRequest createCommonRequest() {
        Object obj = newInstanceOf("common.config.requestfactory.CommonRequest");
        return (CommonRequest) obj;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static RestRequest createRestRequest() {
        Object obj = newInstanceOf("common.config.requestfactory.RestRequest");
        return (RestRequest) obj;
    }
}
