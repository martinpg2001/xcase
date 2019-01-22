/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface GetTermRequest {

    String getClientId();

    void setClientId(String clientId);

    String getMatterId();

    void setMatterId(String matterId);

    String getDefinitionName();

    void setDefinitionName(String definitionName);
}
