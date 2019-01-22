/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.UpdateMatterData;

/**
 *
 * @author martin
 */
public interface UpdateMattersRequest {

    UpdateMatterData[] getUpdateMatterDataArray();

    void setUpdateMatterDataArray(UpdateMatterData[] updateMatterDataArray);
}
