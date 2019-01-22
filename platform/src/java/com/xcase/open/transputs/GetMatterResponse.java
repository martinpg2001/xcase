/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.MatterData;

/**
 *
 * @author martin
 */
public interface GetMatterResponse extends OpenResponse {

    MatterData getMatterData();

    void setMatterData(MatterData matterData);
}
