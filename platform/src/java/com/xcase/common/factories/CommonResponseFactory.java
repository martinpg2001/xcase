package com.xcase.common.factories;

import com.xcase.common.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonResponseFactory extends BaseCommonFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static CommonResponse createCommonResponse() {
        Object obj = newInstanceOf("common.config.responsefactory.CommonResponse");
        return (CommonResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static RestResponse createRestResponse() {
        Object obj = newInstanceOf("common.config.responsefactory.RestResponse");
        return (RestResponse) obj;
    }
}
