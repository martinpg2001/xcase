/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxFolder;

/**
 *
 * @author martin
 */
public interface CreateFolderResponse extends BoxResponse {

    /**
     * @return the folder
     */
    public BoxFolder getFolder();

    /**
     * @param folder the folder to set
     */
    public void setFolder(BoxFolder folder);
}
