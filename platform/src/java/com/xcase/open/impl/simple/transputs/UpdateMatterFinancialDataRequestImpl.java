/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.FinancialData;
import com.xcase.open.transputs.UpdateMatterFinancialDataRequest;

public class UpdateMatterFinancialDataRequestImpl implements UpdateMatterFinancialDataRequest {

    private String clientId;
    private String matterId;
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

    @Override
    public String getMatterId() {
        return this.matterId;
    }

    @Override
    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }

}
