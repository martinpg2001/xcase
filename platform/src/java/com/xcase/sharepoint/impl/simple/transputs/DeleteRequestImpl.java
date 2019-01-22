/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.DeleteRequest;
import java.util.Collections;
import java.util.Map;

/**
 * @author martin
 *
 */
public class DeleteRequestImpl extends SharepointRequestImpl implements DeleteRequest {

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
     * directory name.
     */
    private String site;

    /**
     * file name.
     */
    private String serverUrl;

    /**
     * etag token.
     */
    private String eTag;

    /**
     * recursive token.
     */
    private boolean recursive;

    /**
     * target, could be 'file' or 'folder'.
     */
    private String target;

    /**
     * target id, file id or folder id.
     */
    private String targetId;

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
     * @return the eTag
     */
    public String getETag() {
        return this.eTag;
    }

    /**
     * @param eTag the eTag to set
     */
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    /**
     * @return the recursive
     */
    public boolean getRecursive() {
        return this.recursive;
    }

    /**
     * @param recursive the recursive to set
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the targetId
     */
    public String getTargetId() {
        return this.targetId;
    }

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
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
        return SharepointConstant.ACTION_NAME_DELETE;
    }
}
