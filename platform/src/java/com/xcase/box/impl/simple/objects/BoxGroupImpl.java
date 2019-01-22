/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.objects;

import com.xcase.box.objects.BoxGroup;

/**
 *
 * @author martin
 */
public class BoxGroupImpl extends BoxImpl implements BoxGroup {

    /**
     * name.
     */
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
