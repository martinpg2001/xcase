/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.CreateMatterData;
import com.xcase.open.transputs.CreateMatterRequest;

/**
 *
 * @author martin
 */
public class CreateMatterRequestImpl extends OpenRequestImpl implements CreateMatterRequest {

    private CreateMatterData createMatterData;

    public CreateMatterData getCreateMatterData() {
        return this.createMatterData;
    }

    public void setCreateMatterData(CreateMatterData createMatterData) {
        this.createMatterData = createMatterData;
    }
}
