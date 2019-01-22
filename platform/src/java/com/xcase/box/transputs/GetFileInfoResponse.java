/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxFile;

/**
 *
 * @author martin
 */
public interface GetFileInfoResponse extends BoxResponse {

    /**
     * @return the file
     */
    public BoxFile getFile();

    /**
     * @param file the file to set
     */
    public void setFile(BoxFile file);
}
