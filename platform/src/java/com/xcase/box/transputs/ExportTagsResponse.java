/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import java.util.List;

/**
 *
 * @author martin
 */
public interface ExportTagsResponse extends BoxResponse {

    /**
     * @return the tagList
     */
    public List getTagList();

    /**
     * @param tagList the tagList to set
     */
    public void setTagList(List tagList);

    /**
     * @return the encodedTags
     */
    public String getEncodedTags();

    /**
     * @param encodedTags the encodedTags to set
     */
    public void setEncodedTags(String encodedTags);
}
