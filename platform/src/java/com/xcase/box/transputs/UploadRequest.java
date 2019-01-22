/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author martin
 */
public interface UploadRequest extends BoxRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the directoryName
     */
    public String getDirectoryName();

    /**
     * @param directoryName the directoryName to set
     */
    public void setDirectoryName(String directoryName);

    /**
     * @return the fileName
     */
    public String getFileName();

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName);

    /**
     * @return the folderId
     */
    public String getFolderId();

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(String folderId);

    /**
     * @return the asFile
     */
    public boolean isAsFile();

    /**
     * @param asFile the asFile to set
     */
    public void setAsFile(boolean asFile);

    /**
     * @return the dataMap
     */
    public HashMap<String, File> getDataMap();

    /**
     * @param dataMap the dataMap to set
     */
    public void setDataMap(HashMap<String, File> dataMap);
}
