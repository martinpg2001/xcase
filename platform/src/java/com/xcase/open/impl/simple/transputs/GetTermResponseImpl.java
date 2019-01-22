/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.TermData;
import com.xcase.open.transputs.GetTermResponse;

/**
 *
 * @author martin
 */
public class GetTermResponseImpl extends OpenResponseImpl implements GetTermResponse {

    private TermData termData;

    public TermData getTermData() {
        return this.termData;
    }

    public void setTermData(TermData termData) {
        this.termData = termData;
    }
}
