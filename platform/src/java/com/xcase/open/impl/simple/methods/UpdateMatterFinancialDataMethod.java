/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.FinancialData;
import com.xcase.open.transputs.UpdateMatterFinancialDataRequest;
import com.xcase.open.transputs.UpdateMatterFinancialDataResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateMatterFinancialDataMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateMatterFinancialDataMethod() {
        super();
//        LOGGER.debug("finishing UpdateMatterFinancialDataMethod()");
    }

    public UpdateMatterFinancialDataResponse updateMatterFinancialData(UpdateMatterFinancialDataRequest updateMatterFinancialDataRequest) {
        LOGGER.debug("starting updateMatterFinancialData()");
        try {
            String clientId = updateMatterFinancialDataRequest.getClientId();
            FinancialData financialData = updateMatterFinancialDataRequest.getFinancialData();
            String matterId = updateMatterFinancialDataRequest.getMatterId();
            LOGGER.debug("got updateMatterFinancialDataData");
            UpdateMatterFinancialDataResponse updateMatterFinancialDataResponse = OpenResponseFactory.createUpdateMatterFinancialDataResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateMatterFinancialData() method */
//            commonApiWebProxy.UpdateMatterFinancialData(matterId, financialData, clientId);
//            LOGGER.debug("updated client financial data");
            return updateMatterFinancialDataResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating matter financial data: " + e.getMessage());
        }

        return null;
    }
}
