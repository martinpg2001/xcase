/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import com.xcase.sharepoint.objects.SharepointFile;

/**
 *
 * @author martin
 */
public interface GetFileInfoResponse extends SharepointResponse {

    /**
     * @return the file
     */
    public SharepointFile getFile();

    /**
     * @param file the file to set
     */
    public void setFile(SharepointFile file);
}
