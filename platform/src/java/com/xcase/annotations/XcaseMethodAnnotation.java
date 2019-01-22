/**
 * Copyright 2018 Xcase, Inc. All rights reserved.
 */
package com.xcase.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface XcaseMethodAnnotation {

    String group();
}
