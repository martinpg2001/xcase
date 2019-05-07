package com.xcase.intapp.advanced.factories;

import com.xcase.intapp.advanced.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedRequestFactory extends BaseAdvancedFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static InvokeOperationRequest createInvokeOperationRequest() {
        Object obj = newInstanceOf("advanced.config.requestfactory.InvokeOperationRequest");
        return (InvokeOperationRequest) obj;
	}

}
