/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateClientData;

/**
 *
 * @author martin
 */
public interface CreateClientRequest extends OpenRequest {

    CreateClientData getCreateClientData();

    void setCreateClientData(CreateClientData createClientData);
}
