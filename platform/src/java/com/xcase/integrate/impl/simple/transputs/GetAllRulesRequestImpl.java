/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetAllRulesRequest;

/**
 *
 * @author Administrator
 */
public class GetAllRulesRequestImpl extends IntegrateRequestImpl implements GetAllRulesRequest {

    private String locationSeparator;

    public String getLocationSeparator() {
        return this.locationSeparator;
    }

    public void setLocationSeparator(String locationSeparator) {
        this.locationSeparator = locationSeparator;
    }
}
