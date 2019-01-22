/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.objects.SharepointFolder;
import com.xcase.sharepoint.transputs.GetFolderInfoResponse;

/**
 *
 * @author martin
 */
public class GetFolderInfoResponseImpl extends SharepointResponseImpl implements GetFolderInfoResponse {

    /**
     * created folder.
     */
    private SharepointFolder folder;

    /**
     * @return the folder
     */
    public SharepointFolder getFolder() {
        return this.folder;
    }

    /**
     * @param folder the folder to set
     */
    public void setFolder(SharepointFolder folder) {
        this.folder = folder;
    }
}
