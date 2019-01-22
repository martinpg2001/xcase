/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxAbstractFile;
import java.util.*;

/**
 *
 * @author martin
 */
public interface SearchResponse extends BoxResponse {

    /**
     *
     * @return count
     */
    public String getTotalCount();

    /**
     *
     * @param totalCount
     */
    public void setTotalCount(String totalCount);

    /**
     *
     * @return boxAbstractFileList
     */
    public List<BoxAbstractFile> getEntries();

    /**
     *
     * @param boxAbstractFileList
     */
    public void setEntries(List<BoxAbstractFile> boxAbstractFileList);
}
