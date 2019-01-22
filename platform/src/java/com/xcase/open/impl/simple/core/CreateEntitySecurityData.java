/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class CreateEntitySecurityData {

    public PrincipalDescriptor principal;
    public securityAccessValues securityAccess;

    public enum securityAccessValues {
        None,
        View,
        All,
    }

}
