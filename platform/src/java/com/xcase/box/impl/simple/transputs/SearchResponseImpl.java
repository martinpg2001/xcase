/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.transputs.SearchResponse;
import java.util.*;

/**
 *
 * @author martin
 */
public class SearchResponseImpl extends BoxResponseImpl implements SearchResponse {

    private List<BoxAbstractFile> boxAbstractFileList;
    private String totalCount;

    public List<BoxAbstractFile> getEntries() {
        return boxAbstractFileList;
    }

    public void setEntries(List<BoxAbstractFile> boxAbstractFileList) {
        this.boxAbstractFileList = boxAbstractFileList;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
