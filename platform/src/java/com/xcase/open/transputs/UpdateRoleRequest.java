/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateRoleData;

/**
 *
 * @author martin
 */
public interface UpdateRoleRequest {

    String getId();

    void setId(String id);

    UpdateRoleData getUpdateRoleData();

    void setUpdateRoleData(UpdateRoleData updateRoleData);
}
