/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.objects;

import com.xcase.box.objects.BoxTag;

/**
 * @author martin
 *
 */
public class BoxTagImpl implements BoxTag {

    /**
     * tag id.
     */
    private String id;

    /**
     * tag name.
     */
    private String name;

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

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
