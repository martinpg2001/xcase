/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.objects;

import com.xcase.sharepoint.objects.SharepointFile;
import com.xcase.sharepoint.objects.UploadResult;

/**
 * @author martin
 *
 */
public class UploadResultImpl implements UploadResult {

    /**
     * the Sharepoint file object.
     */
    private SharepointFile file;

    /**
     * error information, if any.
     */
    private String errorInfo;

    /**
     * true if any error occurred.
     */
    private boolean hasError;

    /**
     * @return the file
     */
    public SharepointFile getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(SharepointFile file) {
        this.file = file;
    }

    /**
     * @return the errorInfo
     */
    public String getErrorInfo() {
        return this.errorInfo;
    }

    /**
     * @param errorInfo the errorInfo to set
     */
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * @return the hasError
     */
    public boolean isHasError() {
        return this.hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
