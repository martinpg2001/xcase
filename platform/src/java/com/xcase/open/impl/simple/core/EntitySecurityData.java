/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class EntitySecurityData {

    public defaultSecurityValues defaultSecurity;
    public EntitySecurityData parentSecurity;
    public Object securityByUser;

    public enum defaultSecurityValues {
        None,
        View,
        All,
    }

}
