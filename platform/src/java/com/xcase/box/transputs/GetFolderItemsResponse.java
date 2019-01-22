/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxAbstractFile;
import java.util.List;

/**
 *
 * @author martin
 */
public interface GetFolderItemsResponse extends BoxResponse {

    /**
     *
     * @return boxAbstractFileList
     */
    public List<BoxAbstractFile> getFolderItems();

    /**
     *
     * @param boxAbstractFileList
     */
    public void setFolderItems(List<BoxAbstractFile> boxAbstractFileList);
}
