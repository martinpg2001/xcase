/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.TermData;
import com.xcase.open.transputs.GetTermsForEntityResponse;

/**
 *
 * @author martin
 */
public class GetTermsForEntityResponseImpl extends OpenResponseImpl implements GetTermsForEntityResponse {

    private TermData[] termDataAray;

    public TermData[] getTermDataArray() {
        return this.termDataAray;
    }

    public void setTermDataArray(TermData[] termDataArray) {
        this.termDataAray = termDataArray;
    }
}
