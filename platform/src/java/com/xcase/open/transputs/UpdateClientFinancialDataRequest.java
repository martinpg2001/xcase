/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.FinancialData;

/**
 *
 * @author martin
 */
public interface UpdateClientFinancialDataRequest {

    String getClientId();

    void setClientId(String clientId);

    FinancialData getFinancialData();

    void setFinancialData(FinancialData financialData);
}
