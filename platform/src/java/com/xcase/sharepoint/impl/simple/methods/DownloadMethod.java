/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointObjectFactory;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.impl.simple.core.SharepointConfigurationManager;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.objects.SharepointFile;
import com.xcase.sharepoint.objects.SharepointUrlGenerator;
import com.xcase.sharepoint.transputs.DownloadRequest;
import com.xcase.sharepoint.transputs.DownloadResponse;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.*;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class DownloadMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param downloadRequest request
     * @return response
     * @throws IOException IO exception
     */
    public DownloadResponse download(DownloadRequest downloadRequest) throws IOException, SharepointException {
        LOGGER.debug("starting download()");
        SharepointUrlGenerator sharepointUrlGenerator = SharepointObjectFactory.createSharepointUrlGenerator();
        DownloadResponse downloadResponse = SharepointResponseFactory.createDownloadResponse();
        String domain = downloadRequest.getDomain();
        LOGGER.debug("domain is " + domain);
        String username = downloadRequest.getUsername();
        LOGGER.debug("username is " + username);
        String password = downloadRequest.getPassword();
        LOGGER.debug("password is " + password);
        String serverUrl = downloadRequest.getServerUrl();
        String directoryId = downloadRequest.getDirectoryId();
        LOGGER.debug("directoryId is " + directoryId);
        String fileId = downloadRequest.getFileId();
        LOGGER.debug("fileId is " + fileId);
        String site = downloadRequest.getSite();
        LOGGER.debug("site is " + site);

        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            fileId = codec.encode(fileId, "ISO-8859-1");
            serverUrl = SharepointConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SharepointConstant.LOCAL_WEB_URL);
            String serverSiteUrl = serverUrl + "/" + site;
            LOGGER.debug("serverSiteUrl is " + serverSiteUrl);
            try {
                Credentials ntCredentials = new NTCredentials(username, password, InetAddress.getLocalHost().getHostName(), domain);
                LOGGER.debug("created authenticator");
                String escapedDirectoryId = directoryId.replace(" ", "%20");
                //String escapedDirectoryId = java.net.URLEncoder.encode(directoryId, "UTF-8");
                LOGGER.debug("escapedDirectoryId is " + escapedDirectoryId);
                if (fileId != null) {
                    LOGGER.debug("fileId is " + fileId);
                    String filesServerSiteSearchUrl = sharepointUrlGenerator.getSharepointGetFilesUrl(serverUrl, site, escapedDirectoryId);
                    LOGGER.debug("filesServerSiteSearchUrl is " + filesServerSiteSearchUrl);
                    String entityString = httpManager.doStringGet(filesServerSiteSearchUrl, ntCredentials);
                    Document document = DocumentHelper.parseText(entityString);
                    LOGGER.debug("responseDocument root element name is " + document.getRootElement().getName());
                    LOGGER.debug(document.asXML());
                    String getFileFromDirectoryUrl = sharepointUrlGenerator.getSharepointGetFileUrl(serverSiteUrl, site, escapedDirectoryId, fileId);
                    LOGGER.debug("getFileFromDirectoryUrl is " + getFileFromDirectoryUrl);
                    File downloadedFile = CommonHTTPManager.refreshCommonHTTPManager().doGetFile(getFileFromDirectoryUrl, new File("Download.txt"), null, null, ntCredentials);
                    LOGGER.debug("downloaded file");
                } else {
                    LOGGER.debug("fileId is null");
                    File targetDirectory = new File(escapedDirectoryId);
                    boolean createDirectoryIdDirectory = targetDirectory.mkdirs();
                    if (createDirectoryIdDirectory) {
                        LOGGER.debug("created directory " + escapedDirectoryId);
                    }

                    downloadDirectory(sharepointUrlGenerator, serverUrl, site, directoryId, targetDirectory, ntCredentials);
                }
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                SharepointException be = new SharepointException("Failed to parse to a document.", e);
                be.setStatus(downloadResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML);
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            Element fileIdElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_FILE_ID);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(fileIdElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_GET_FILE_INFO);
            fileIdElm.setText(fileId);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                downloadResponse.setStatus(status);
                if (SharepointConstant.STATUS_S_GET_FILE_INFO.equals(status)) {
                    Element infoElm = responseElm.element(SharepointConstant.PARAM_NAME_INFO);
//                    SharepointFile soapFileInfo = ConverterUtils.toSharepointFile(infoElm);
//                    downloadResponse.setFile(soapFileInfo);
                }
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(downloadResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return downloadResponse;
    }

    public void downloadDirectory(SharepointUrlGenerator sharepointUrlGenerator, String serverUrl, String site, String sourceDirectoryId, File targetDirectory, Credentials ntCredentials) throws Exception {
        LOGGER.debug("starting downloadDirectory");
        String escapedDirectoryId = sourceDirectoryId.replace(" ", "%20");
        /* First download files in directory */
        String getFilesFromDirectoryUrl = sharepointUrlGenerator.getSharepointGetFilesUrl(serverUrl, site, escapedDirectoryId);
        LOGGER.debug("getFilesFromDirectoryUrl is " + getFilesFromDirectoryUrl);
        String filesEntityString = httpManager.doStringGet(getFilesFromDirectoryUrl, ntCredentials);
        Document filesDocument = DocumentHelper.parseText(filesEntityString);
        LOGGER.debug("responseDocument root element name is " + filesDocument.getRootElement().getName());
        LOGGER.debug(filesDocument.asXML());
        Map uris = new HashMap();
        uris.put("Atom", "http://www.w3.org/2005/Atom");
        uris.put("m", "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata");
        uris.put("d", "http://schemas.microsoft.com/ado/2007/08/dataservices");
        org.dom4j.XPath xPath = filesDocument.createXPath("Atom:feed/Atom:entry");
        xPath.setNamespaceURIs(uris);
        List<org.dom4j.Node> entryNodeList = xPath.selectNodes(filesDocument);
        //List<org.dom4j.Node> entryNodeList = responseDocument.getRootElement().selectNodes("/entry");
        LOGGER.debug("file node list has size " + entryNodeList.size());
        org.dom4j.Node fileEntryNode = null;
        for (org.dom4j.Node entryNode : entryNodeList) {
            LOGGER.debug("next entryNode");
            SharepointFile sharepointFile = SharepointObjectFactory.createSharepointFile();
            sharepointFile.populateFromXmlNode(entryNode);
            try {
                File downloadedFile = CommonHTTPManager.refreshCommonHTTPManager().doGetFile(sharepointFile.getValueUrl(), new File(targetDirectory, sharepointFile.getFileName()), null, null, ntCredentials);
                LOGGER.debug("downloaded file " + sharepointFile.getFileName());
            } catch (Exception e) {
                LOGGER.debug("exception downloading file at " + sharepointFile.getValueUrl());
            }
        }

        LOGGER.debug("downloaded files in " + escapedDirectoryId);
        /* Now download child directories */
        String getFoldersFromDirectoryUrl = sharepointUrlGenerator.getSharepointGetFoldersUrl(serverUrl, site, escapedDirectoryId);
        LOGGER.debug("getFoldersFromDirectoryUrl is " + getFoldersFromDirectoryUrl);
        String foldersEntityString = httpManager.doStringGet(getFoldersFromDirectoryUrl, ntCredentials);
        Document foldersDocument = DocumentHelper.parseText(foldersEntityString);
        LOGGER.debug("responseDocument root element name is " + foldersDocument.getRootElement().getName());
        LOGGER.debug(foldersDocument.asXML());
        Map foldersUris = new HashMap();
        foldersUris.put("Atom", "http://www.w3.org/2005/Atom");
        foldersUris.put("d", "http://schemas.microsoft.com/ado/2007/08/dataservices");
        foldersUris.put("m", "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata");
        org.dom4j.XPath foldersXPath = foldersDocument.createXPath("Atom:feed/Atom:entry/Atom:content/m:properties/d:Name");
        foldersXPath.setNamespaceURIs(foldersUris);
        List<org.dom4j.Node> foldersEntryNameNodeList = foldersXPath.selectNodes(foldersDocument);
        LOGGER.debug("folders node list has size " + foldersEntryNameNodeList.size());
        for (org.dom4j.Node folderEntryNameNode : foldersEntryNameNodeList) {
            LOGGER.debug("node name is " + folderEntryNameNode.getName());
            String name = folderEntryNameNode.getText();
            LOGGER.debug("next entryIdNode text is " + name);
            String escapedName = java.net.URLEncoder.encode(name, "UTF-8");
            LOGGER.debug("next escapedName is " + escapedName);
            String childDirectoryName = escapedDirectoryId + "/" + escapedName;
            LOGGER.debug("childDirectoryName is " + childDirectoryName);
            try {
                File childDirectory = new File(escapedDirectoryId, escapedName);
                boolean createDirectoryIdDirectory = childDirectory.mkdirs();
                if (createDirectoryIdDirectory) {
                    LOGGER.debug("created directory " + childDirectory.getPath());
                }

                downloadDirectory(sharepointUrlGenerator, serverUrl, site, childDirectoryName, childDirectory, ntCredentials);
                LOGGER.debug("finished downloading directory " + childDirectoryName);
            } catch (Exception e) {
                LOGGER.debug("exception downloading directory at " + childDirectoryName);
            }
        }
    }
}
