/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import java.io.*;

/**
 *
 * @author martin
 */
public interface DownloadRequest extends SharepointRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the directoryId
     */
    public String getDirectoryId();

    /**
     * @param directoryId the directoryId to set
     */
    public void setDirectoryId(String directoryId);

    /**
     * @return the fileId
     */
    public String getFileId();

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId);

    /**
     * @return the asFile
     */
    public boolean isAsFile();

    /**
     * @param asFile the asFile to set
     */
    public void setAsFile(boolean asFile);

    /**
     * @return the inFile
     */
    public File getInFile();

    /**
     * @param inFile the inFile to set
     */
    public void setInFile(File inFile);

    /**
     * @return the serverUrl
     */
    public String getServerUrl();

    /**
     * @param serverUrl the serverUrl to set
     */
    public void setServerUrl(String serverUrl);

    /**
     * @return the site
     */
    public String getSite();

    /**
     * @param site the site to set
     */
    public void setSite(String site);
}
