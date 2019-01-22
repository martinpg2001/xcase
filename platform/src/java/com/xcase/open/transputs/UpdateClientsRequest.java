/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateClientData;

/**
 *
 * @author martin
 */
public interface UpdateClientsRequest {

    UpdateClientData[] getUpdateClientDataArray();

    void setUpdateClientDataArray(UpdateClientData[] updateClientDataArray);
}
