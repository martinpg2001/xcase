/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.CreateMatterData;

/**
 *
 * @author martin
 */
public interface CreateMattersRequest {

    CreateMatterData[] getCreateMatterDataArray();

    void setCreateMatterDataArray(CreateMatterData[] createMatterDataArray);
}
