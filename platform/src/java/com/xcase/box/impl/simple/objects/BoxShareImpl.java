/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.objects;

import com.xcase.box.objects.BoxShare;
import java.util.HashMap;

/**
 *
 * @author martin
 */
public class BoxShareImpl implements BoxShare {

    public static BoxShare createFileShareFromMap(HashMap hashMap) {
        BoxShare boxShare = new BoxShareImpl();
        if (hashMap.containsKey("access")) {
            boxShare.setAccess((String) hashMap.get("access"));
        }

        return boxShare;
    }
    private String access;

    public String getAccess() {
        return this.access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String toString() {
        return "{\"access\":\"" + access + "\"}";
    }
}
