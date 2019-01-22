/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetDocumentsRequest;

/**
 *
 * @author martin
 */
public class GetDocumentsRequestImpl extends OpenRequestImpl implements GetDocumentsRequest {

    private String clientId;
    private String matterId;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMatterId() {
        return this.matterId;
    }

    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }
}
