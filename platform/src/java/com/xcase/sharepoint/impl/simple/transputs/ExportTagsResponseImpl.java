/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.transputs.ExportTagsResponse;
import java.util.List;

/**
 * @author martin
 *
 */
public class ExportTagsResponseImpl extends SharepointResponseImpl implements
        ExportTagsResponse {

    /**
     * BoxTag object list.
     */
    private List tagList;

    /**
     * base64 encoded string.
     */
    private String encodedTags;

    /**
     * @return the tagList
     */
    public List getTagList() {
        return this.tagList;
    }

    /**
     * @param tagList the tagList to set
     */
    public void setTagList(List tagList) {
        this.tagList = tagList;
    }

    /**
     * @return the encodedTags
     */
    public String getEncodedTags() {
        return this.encodedTags;
    }

    /**
     * @param encodedTags the encodedTags to set
     */
    public void setEncodedTags(String encodedTags) {
        this.encodedTags = encodedTags;
    }
}
