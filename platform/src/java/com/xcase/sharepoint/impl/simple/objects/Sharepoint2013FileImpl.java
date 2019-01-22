/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.objects;

import com.xcase.sharepoint.objects.SharepointFile;
import java.lang.invoke.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class Sharepoint2013FileImpl implements SharepointFile {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * etag.
     */
    private String eTag;

    /**
     * file id.
     */
    private String fileId;

    /**
     * file name.
     */
    private String fileName;

    /**
     * folder id.
     */
    private String folderId;

    /**
     * shared flag.
     */
    private boolean shared;

    /**
     * shared name.
     */
    private String sharedName;

    /**
     * file size.
     */
    private long size;

    /**
     * file description.
     */
    private String description;

    /**
     * sha1 value, used to verify file content.
     */
    private String sha1;

    /**
     * created time.
     */
    private long created;

    /**
     * updated time.
     */
    private long updated;

    private String valueUrl;

    public void populateFromXmlNode(org.dom4j.Node fileNode) {
        Map uris = new HashMap();
        uris.put("Atom", "http://www.w3.org/2005/Atom");
        uris.put("m", "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata");
        uris.put("d", "http://schemas.microsoft.com/ado/2007/08/dataservices");
        org.dom4j.XPath idXPath = fileNode.createXPath("Atom:id");
        idXPath.setNamespaceURIs(uris);
        org.dom4j.Node entryIdNode = idXPath.selectSingleNode(fileNode);
        LOGGER.debug("selected entryIdNode");
        String entryIDText = entryIdNode.getText();
        LOGGER.debug("next entryIdNode text is " + entryIDText);
        String entryIDUrl = entryIDText;
        LOGGER.debug("next entryIDUrl is " + entryIDUrl);
        String getFileFromDirectoryUrl = entryIDUrl + "/$value";
        getFileFromDirectoryUrl = getFileFromDirectoryUrl.replace(" ", "%20");
        LOGGER.debug("getFileFromDirectoryUrl is " + getFileFromDirectoryUrl);
        setValueUrl(getFileFromDirectoryUrl);
        /* Parse the entryNode to get the file name */
        org.dom4j.XPath contentXPath = fileNode.createXPath("Atom:content");
        contentXPath.setNamespaceURIs(uris);
        LOGGER.debug("set name space URIs for contentXPath");
        org.dom4j.Node entryContentNode = contentXPath.selectSingleNode(fileNode);
        /* Got entry content node */
        org.dom4j.XPath propertiesXPath = entryContentNode.createXPath("m:properties");
        propertiesXPath.setNamespaceURIs(uris);
        LOGGER.debug("set name space URIs for propertiesXPath");
        org.dom4j.Node entryPropertiesNode = propertiesXPath.selectSingleNode(entryContentNode);
        /* Got entry properties node */
        org.dom4j.XPath nameXPath = entryPropertiesNode.createXPath("d:Name");
        nameXPath.setNamespaceURIs(uris);
        LOGGER.debug("set name space URIs for nameXPath");
        org.dom4j.Node entryNameNode = nameXPath.selectSingleNode(entryPropertiesNode);
        /* Got entry name node */
        String fileName = entryNameNode.getText();
        LOGGER.debug("fileName is " + fileName);
        setFileName(fileName);
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
     * @return the shared
     */
    public boolean isShared() {
        return this.shared;
    }

    /**
     * @param shared the shared to set
     */
    public void setShared(boolean shared) {
        this.shared = shared;
    }

    /**
     * @return the sharedName
     */
    public String getSharedName() {
        return this.sharedName;
    }

    /**
     * @param sharedName the sharedName to set
     */
    public void setSharedName(String sharedName) {
        this.sharedName = sharedName;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return this.size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the sha1
     */
    public String getSha1() {
        return this.sha1;
    }

    /**
     * @param sha1 the sha1 to set
     */
    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     * @return the created
     */
    public long getCreated() {
        return this.created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * @return the updated
     */
    public long getUpdated() {
        return this.updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(long updated) {
        this.updated = updated;
    }

    /**
     * @return the valueUrl
     */
    public String getValueUrl() {
        return this.valueUrl;
    }

    /**
     * @param valueUrl the valueUrl to set
     */
    public void setValueUrl(String valueUrl) {
        this.valueUrl = valueUrl;
    }
}
