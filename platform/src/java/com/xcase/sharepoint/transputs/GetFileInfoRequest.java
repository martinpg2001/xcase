/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface GetFileInfoRequest extends SharepointRequest {

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
     * @return the site
     */
    public String getSite();

    /**
     * @param site the site to set
     */
    public void setSite(String site);

    /**
     * @return the serverUrl
     */
    public String getServerUrl();

    /**
     * @param serverUrl the serverUrl to set
     */
    public void setServerUrl(String serverUrl);
}
