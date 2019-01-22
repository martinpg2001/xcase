/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.objects;

import com.xcase.salesforce.objects.SalesforceObject;

/**
 *
 * @author martin
 */
public class SalesforceObjectImpl implements SalesforceObject {

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
