/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class GroupSecurityData {

    public String[] groupIds;
    public securityValues security;

    public enum securityValues {
        None,
        View,
        All,
    }

}
