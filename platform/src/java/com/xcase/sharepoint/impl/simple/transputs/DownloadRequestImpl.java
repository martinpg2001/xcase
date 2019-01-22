/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.DownloadRequest;
import java.io.File;

/**
 * @author martin
 *
 */
public class DownloadRequestImpl extends SharepointRequestImpl implements DownloadRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * directory id.
     */
    private String directoryId;

    /**
     * file id.
     */
    private String fileId;

    /**
     * if true, will download as a File object, if false then download as bytes
     * array.
     */
    private boolean asFile;

    /**
     * if asFile is true, this parameter will specify a File object which the
     * content will write in.
     */
    private File inFile;

    private String serverUrl;

    private String site;

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
     * @return the directoryId
     */
    public String getDirectoryId() {
        return this.directoryId;
    }

    /**
     * @param directoryId the directoryId to set
     */
    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return this.fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
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
     * @return the inFile
     */
    public File getInFile() {
        return this.inFile;
    }

    /**
     * @param inFile the inFile to set
     */
    public void setInFile(File inFile) {
        this.inFile = inFile;
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
     * @return the site
     */
    public String getSite() {
        return this.site;
    }

    /**
     * @param site the inFile to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_DOWNLOAD;
    }
}
