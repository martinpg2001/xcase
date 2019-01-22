/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.UploadRequest;
import java.util.Collections;
import java.util.Map;

/**
 * @author martin
 *
 */
public class UploadRequestImpl extends SharepointRequestImpl implements UploadRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * directory name.
     */
    private String directoryName;

    /**
     * file name.
     */
    private String fileName;

    /**
     * true means will upload Java File object rather than pure bytes array.
     */
    private boolean asFile;

    /**
     * map key is file name, value could be either Java File object or bytes
     * array.
     */
    private Map dataMap;

    /**
     * site name.
     */
    private String site;

    /**
     * serverUrl name.
     */
    private String serverUrl;

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the directoryName
     */
    public String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * @param directoryName the directoryName to set
     */
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the asFile
     */
    public boolean isAsFile() {
        return this.asFile;
    }

    /**
     * @param asFile the asFile to set
     */
    public void setAsFile(boolean asFile) {
        this.asFile = asFile;
    }

    /**
     * @return the dataMap
     */
    public Map getDataMap() {
        return this.dataMap;
    }

    /**
     * @param dataMap the dataMap to set
     */
    public void setDataMap(Map dataMap) {
        this.dataMap = dataMap;
    }

    /**
     * @return the site
     */
    public String getSite() {
        return this.site;
    }

    /**
     * @param site the directoryName to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return the serverUrl
     */
    public String getServerUrl() {
        return this.serverUrl;
    }

    /**
     * @param serverUrl the serverUrl to set
     */
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_UPLOAD;
    }
}
