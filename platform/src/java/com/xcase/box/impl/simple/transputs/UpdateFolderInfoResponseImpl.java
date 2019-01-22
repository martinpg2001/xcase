/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxFolder;
import com.xcase.box.transputs.UpdateFolderInfoResponse;

/**
 *
 * @author martin
 */
public class UpdateFolderInfoResponseImpl extends BoxResponseImpl implements UpdateFolderInfoResponse {

    private BoxFolder boxFolder;

    public BoxFolder getFolder() {
        return boxFolder;
    }

    public void setFolder(BoxFolder boxFolder) {
        this.boxFolder = boxFolder;
    }
}
