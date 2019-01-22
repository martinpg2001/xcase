/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.MatterData;
import com.xcase.open.transputs.GetMatterResponse;

/**
 *
 * @author martin
 */
public class GetMatterResponseImpl extends OpenResponseImpl implements GetMatterResponse {

    private MatterData matterData;

    public MatterData getMatterData() {
        return this.matterData;
    }

    public void setMatterData(MatterData matterData) {
        this.matterData = matterData;
    }
}
