/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface GetMatterRequest extends OpenRequest {

    String getClientId();

    void setClientId(String clientId);

    String getMatterId();

    void setMatterId(String matterId);

    String[] getProperties();

    void setProperties(String[] properties);
}
