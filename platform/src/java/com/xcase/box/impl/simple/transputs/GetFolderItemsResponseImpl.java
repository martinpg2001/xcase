/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.transputs.GetFolderItemsResponse;
import java.util.*;

/**
 *
 * @author martin
 */
public class GetFolderItemsResponseImpl extends BoxResponseImpl implements GetFolderItemsResponse {

    private List<BoxAbstractFile> boxAbstractFileList;

    public List<BoxAbstractFile> getFolderItems() {
        return this.boxAbstractFileList;
    }

    public void setFolderItems(List<BoxAbstractFile> boxAbstractFileList) {
        this.boxAbstractFileList = boxAbstractFileList;
    }
}
