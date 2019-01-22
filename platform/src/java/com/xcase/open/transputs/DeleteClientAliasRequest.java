/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.DeleteAliasentityType;

/**
 *
 * @author martin
 */
public interface DeleteClientAliasRequest {

    DeleteAliasentityType getDeleteAliasentityType();

    void setDeleteAliasentityType(DeleteAliasentityType deleteAliasentityType);

    String getEntityId();

    void setEntityId(String entityId);

    String getAliasName();

    void setAliasName(String aliasName);
}
