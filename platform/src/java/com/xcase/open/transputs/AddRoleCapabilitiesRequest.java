/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface AddRoleCapabilitiesRequest {

    String getRoleId();

    void setRoleId(String roleId);

    String[] getCapabilitiesArray();

    void setCapabilitiesArray(String[] capabilitiesArray);
}
