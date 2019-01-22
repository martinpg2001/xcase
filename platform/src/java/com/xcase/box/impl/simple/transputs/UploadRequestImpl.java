/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.UploadRequest;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author martin
 *
 */
public class UploadRequestImpl extends BoxRequestImpl implements UploadRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * folder id.
     */
    private String directoryName;

    /**
     * file name.
     */
    private String fileName;

    /**
     * folder id.
     */
    private String folderId;

    /**
     * true means will upload Java File object rather than pure bytes array.
     */
    private boolean asFile;

    /**
     * map key is file name, value could be either Java File object or bytes
     * array.
     */
    private HashMap<String, File> dataMap;

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
     * @return the folderId
     */
    public String getFolderId() {
        return this.folderId;
    }

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(String folderId) {
        this.folderId = folderId;
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
    public HashMap<String, File> getDataMap() {
        return this.dataMap;
    }

    /**
     * @param dataMap the dataMap to set
     */
    public void setDataMap(HashMap<String, File> dataMap) {
        this.dataMap = dataMap;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_UPLOAD;
    }
}
