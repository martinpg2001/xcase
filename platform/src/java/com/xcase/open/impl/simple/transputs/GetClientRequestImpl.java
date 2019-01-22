/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetClientRequest;

/**
 *
 * @author martin
 */
public class GetClientRequestImpl extends OpenRequestImpl implements GetClientRequest {

    private String clientId;
    private String[] properties;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }
}
