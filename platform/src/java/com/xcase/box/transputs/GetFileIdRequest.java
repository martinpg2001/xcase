/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetFileIdRequest extends BoxRequest {

    /**
     *
     * @return filePath
     */
    public String getFilePath();

    /**
     *
     * @param filePath
     */
    public void setFilePath(String filePath);
}
