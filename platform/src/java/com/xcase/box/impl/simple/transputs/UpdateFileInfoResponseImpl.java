/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxFile;
import com.xcase.box.transputs.UpdateFileInfoResponse;

/**
 *
 * @author martin
 */
public class UpdateFileInfoResponseImpl extends BoxResponseImpl implements UpdateFileInfoResponse {

    private BoxFile boxFile;

    public BoxFile getFile() {
        return boxFile;
    }

    public void setFile(BoxFile boxFile) {
        this.boxFile = boxFile;
    }
}
