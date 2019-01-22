/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.transputs.MoveResponse;

/**
 * @author martin
 *
 */
public class MoveResponseImpl extends BoxResponseImpl implements MoveResponse {

    private BoxAbstractFile boxAbstractFile;

    public BoxAbstractFile getFile() {
        return this.boxAbstractFile;
    }

    public void setFile(BoxAbstractFile boxAbstractFile) {
        this.boxAbstractFile = boxAbstractFile;
    }
}
