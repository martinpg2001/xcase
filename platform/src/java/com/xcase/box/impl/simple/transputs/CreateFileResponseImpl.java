/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxFile;
import com.xcase.box.transputs.CreateFileResponse;

/**
 *
 * @author martin
 */
public class CreateFileResponseImpl extends BoxResponseImpl implements CreateFileResponse {

    /**
     * created file.
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
