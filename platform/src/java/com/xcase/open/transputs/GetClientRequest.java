/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface GetClientRequest extends OpenRequest {

    String getClientId();

    void setClientId(String clientId);

    String[] getProperties();

    void setProperties(String[] properties);
}
