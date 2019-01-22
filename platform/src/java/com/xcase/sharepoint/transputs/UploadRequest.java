/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import java.util.Map;

/**
 *
 * @author martin
 */
public interface UploadRequest extends SharepointRequest {

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
    public Map getDataMap();

    /**
     * @param dataMap the dataMap to set
     */
    public void setDataMap(Map dataMap);

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
