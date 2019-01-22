/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxFile;
import com.xcase.box.transputs.GetFileInfoResponse;

/**
 * @author martin
 *
 */
public class GetFileInfoResponseImpl extends BoxResponseImpl implements GetFileInfoResponse {

    /**
     * box file.
     */
    private BoxFile file;

    /**
     * @return the file
     */
    public BoxFile getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(BoxFile file) {
        this.file = file;
    }
}
