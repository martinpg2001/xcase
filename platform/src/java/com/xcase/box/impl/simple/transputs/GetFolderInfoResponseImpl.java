/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxFolder;
import com.xcase.box.transputs.GetFolderInfoResponse;

/**
 *
 * @author martin
 */
public class GetFolderInfoResponseImpl extends BoxResponseImpl implements GetFolderInfoResponse {

    /**
     * created folder.
     */
    private BoxFolder folder;

    /**
     * @return the folder
     */
    public BoxFolder getFolder() {
        return this.folder;
    }

    /**
     * @param folder the folder to set
     */
    public void setFolder(BoxFolder folder) {
        this.folder = folder;
    }
}
