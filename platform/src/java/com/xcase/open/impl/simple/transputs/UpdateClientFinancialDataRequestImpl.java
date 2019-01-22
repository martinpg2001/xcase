/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.FinancialData;
import com.xcase.open.transputs.UpdateClientFinancialDataRequest;

public class UpdateClientFinancialDataRequestImpl implements UpdateClientFinancialDataRequest {

    private String clientId;
    private FinancialData financialData;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public FinancialData getFinancialData() {
        return this.financialData;
    }

    @Override
    public void setFinancialData(FinancialData financialData) {
        this.financialData = financialData;
    }

}
