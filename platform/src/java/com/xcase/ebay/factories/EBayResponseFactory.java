package com.xcase.ebay.factories;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.ebay.transputs.CreateApplicationAccessTokenResponse;
import com.xcase.ebay.transputs.InvokeAdvancedActionResponse;
import com.xcase.integrate.factories.BaseIntegrateFactory;

public class EBayResponseFactory extends BaseEBayFactory {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateApplicationAccessTokenResponse createCreateApplicationAccessTokenResponse() {
        Object obj = newInstanceOf("ebay.config.responsefactory.CreateApplicationAccessTokenResponse");
        return (CreateApplicationAccessTokenResponse) obj;
    }

	public static InvokeAdvancedActionResponse createInvokeAdvancedActionResponse() {
        Object obj = newInstanceOf("ebay.config.responsefactory.InvokeAdvancedActionResponse");
        return (InvokeAdvancedActionResponse) obj;
	}

}
