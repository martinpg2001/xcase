/**
 * Copyright 2018 Xcase, Inc. All rights reserved.
 */
package com.xcase.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface XcaseParameterAnnotation {

    String name();

    String displayType() default "text";
}
