/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.UploadResponse;
import java.util.List;

/**
 * @author martin
 *
 */
public class UploadResponseImpl extends BoxResponseImpl implements
        UploadResponse {

    /**
     * a list of UploadResult object.
     */
    private List uploadResultList;

    /**
     * @return the uploadResultList
     */
    public List getUploadResultList() {
        return this.uploadResultList;
    }

    /**
     * @param uploadResultList the uploadResultList to set
     */
    public void setUploadResultList(List uploadResultList) {
        this.uploadResultList = uploadResultList;
    }
}
