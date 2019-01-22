/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.ClientData;

/**
 *
 * @author martin
 */
public interface GetClientResponse extends OpenResponse {

    ClientData getClientData();

    void setClientData(ClientData clientData);
}
