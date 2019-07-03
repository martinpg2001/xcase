package com.xcase.klearexpress.factories;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.klearexpress.transputs.*;

public class KlearExpressResponseFactory extends BaseKlearExpressFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
   
    public static SendMessageResponse createSendMessageResponse() {
        Object obj = newInstanceOf("klearexpress.config.responsefactory.SendMessageResponse");
        return (SendMessageResponse) obj;
    }

    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("klearexpress.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

}
