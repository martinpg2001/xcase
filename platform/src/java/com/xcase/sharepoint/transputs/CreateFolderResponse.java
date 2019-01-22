/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import com.xcase.sharepoint.objects.SharepointFolder;

/**
 *
 * @author martin
 */
public interface CreateFolderResponse extends SharepointResponse {

    /**
     * @return the folder
     */
    public SharepointFolder getFolder();

    /**
     * @param folder the folder to set
     */
    public void setFolder(SharepointFolder folder);
}
