package com.xcase.intapp.advanced;

import com.xcase.intapp.advanced.impl.simple.core.AdvancedConfigurationManager;
import com.xcase.intapp.advanced.impl.simple.methods.*;
import com.xcase.intapp.advanced.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleAdvancedImpl implements AdvancedExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public AdvancedConfigurationManager localConfigurationManager = AdvancedConfigurationManager.getConfigurationManager();

    /**
     * method implementation.
     */
    private InvokeOperationMethod invokeOperationMethod = new InvokeOperationMethod();

    @Override
    public InvokeOperationResponse invokeOperation(InvokeOperationRequest invokeOperationRequest) {
        return this.invokeOperationMethod.invokeOperation(invokeOperationRequest);
    }

}
