/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateGroupData;

/**
 *
 * @author martin
 */
public interface UpdateGroupRequest {

    String getId();

    void setId(String id);

    UpdateGroupData getUpdateGroupData();

    void setUpdateGroupData(UpdateGroupData updateGroupData);
}
