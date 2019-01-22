/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.objects.SharepointFile;
import com.xcase.sharepoint.transputs.GetFileInfoResponse;

/**
 * @author martin
 *
 */
public class GetFileInfoResponseImpl extends SharepointResponseImpl implements GetFileInfoResponse {

    /**
     * box file.
     */
    private SharepointFile file;

    /**
     * @return the file
     */
    public SharepointFile getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(SharepointFile file) {
        this.file = file;
    }
}
