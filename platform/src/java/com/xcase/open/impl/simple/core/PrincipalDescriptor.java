/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class PrincipalDescriptor {

    public String principalId;
    public principalTypeValues principalType;

    public enum principalTypeValues {
        User,
        Group,
    }

}
