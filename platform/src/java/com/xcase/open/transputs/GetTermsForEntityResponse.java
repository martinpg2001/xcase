/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

import com.xcase.open.impl.simple.core.TermData;

/**
 *
 * @author martin
 */
public interface GetTermsForEntityResponse {

    TermData[] getTermDataArray();

    void setTermDataArray(TermData[] termDataArray);
}
