/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateMatterData;
import com.xcase.open.transputs.UpdateMattersRequest;

/**
 *
 * @author martin
 */
public class UpdateMattersRequestImpl extends OpenRequestImpl implements UpdateMattersRequest {

    private UpdateMatterData[] updateMatterDataArray;

    public UpdateMatterData[] getUpdateMatterDataArray() {
        return updateMatterDataArray;
    }

    public void setUpdateMatterDataArray(UpdateMatterData[] updateMatterDataArray) {
        this.updateMatterDataArray = updateMatterDataArray;
    }
}
