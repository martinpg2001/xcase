/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateMatterData;
import com.xcase.open.transputs.CreateMattersRequest;

/**
 *
 * @author martin
 */
public class CreateMattersRequestImpl extends OpenRequestImpl implements CreateMattersRequest {

    private CreateMatterData[] createMatterDataArray;

    public CreateMatterData[] getCreateMatterDataArray() {
        return this.createMatterDataArray;
    }

    public void setCreateMatterDataArray(CreateMatterData[] createMatterDataArray) {
        this.createMatterDataArray = createMatterDataArray;
    }
}
