/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.FinancialData;
import com.xcase.open.transputs.UpdateClientFinancialDataRequest;
import com.xcase.open.transputs.UpdateClientFinancialDataResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateClientFinancialDataMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateClientFinancialDataMethod() {
        super();
//        LOGGER.debug("finishing UpdateClientFinancialDataMethod()");
    }

    public UpdateClientFinancialDataResponse updateClientFinancialData(UpdateClientFinancialDataRequest updateClientFinancialDataRequest) {
        LOGGER.debug("starting updateClientFinancialData()");
        try {
            String clientId = updateClientFinancialDataRequest.getClientId();
            FinancialData financialData = updateClientFinancialDataRequest.getFinancialData();
            LOGGER.debug("got updateClientFinancialDataData");
            UpdateClientFinancialDataResponse updateClientFinancialDataResponse = OpenResponseFactory.createUpdateClientFinancialDataResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateClientFinancialData() method */
//            commonApiWebProxy.UpdateClientFinancialData(clientId, financialData);
//            LOGGER.debug("updated client financial data");
            return updateClientFinancialDataResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating client financial data: " + e.getMessage());
        }

        return null;
    }
}
