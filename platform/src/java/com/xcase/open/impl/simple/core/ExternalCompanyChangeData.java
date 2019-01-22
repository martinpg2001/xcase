/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class ExternalCompanyChangeData {

    public changeTypeValues changeType;
    public String companyId;
    public String companyName;
    public String oldCompanyName;
    public String parentCompanyId;

    public enum changeTypeValues {
        Add,
        Edit,
        Delete,
    }

}
