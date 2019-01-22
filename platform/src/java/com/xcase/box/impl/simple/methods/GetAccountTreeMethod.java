/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetAccountTreeRequest;
import com.xcase.box.transputs.GetAccountTreeResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class GetAccountTreeMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * this parameter indicates if we only show one level of our file structure.
     */
    public static final String PARAMS_KEY_ONELEVEL = "onelevel";

    /**
     * this parameter decides whether show files or not. public static final
     * String PARAMS_KEY_NOFILES = "nofiles";
     *
     * /** this parameter decides whether zip the content with base64 encoding.
     */
    public static final String PARAMS_KEY_NOZIP = "nozip";

    /**
     * This method is used to get a user's files and folders tree.
     *
     * 'folder_id' param defines root folder from which the tree begins.
     * 'params' is array of string where you can set additional parameters,
     * which are: onelevel - make a tree of one level depth, so you will get
     * only files and folders stored in folder which folder_id you have
     * provided. nofiles - include folders only in result tree, no files. nozip
     * - do not zip tree xml.
     *
     * On successful result you will receive 'listing_ok' as status and base64
     * encoded zipped tree xml. So you have to decode the received tree, then
     * unzip it (if you haven't set 'nozip' param) and you will get xml like
     * this: (note that updatedand createdare UNIX timestamps in PST).
     *
     * @param getAccountTreeRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetAccountTreeResponse getAccountTree(GetAccountTreeRequest getAccountTreeRequest) throws IOException, BoxException {
        LOGGER.debug("starting getAccountTree()");
        GetAccountTreeResponse getAccountTreeResponse = BoxResponseFactory.createGetAccountTreeResponse();
        String apiKey = getAccountTreeRequest.getApiKey();
        String accessToken = getAccountTreeRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String authToken = getAccountTreeRequest.getAuthToken();
        String folderId = getAccountTreeRequest.getFolderId();
        String[] params = getAccountTreeRequest.getParams();

        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            folderId = codec.encode(folderId, "ISO-8859-1");
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersApiUrl = urlBuff.toString();
            LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
            urlBuff.append("/" + folderId + "/items");
            String folderItemsApiUrl = urlBuff.toString();
            LOGGER.debug("folderItemsApiUrl is " + folderItemsApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String response = httpManager.doStringGet(folderItemsApiUrl, headers, null);
                LOGGER.debug("response is " + response);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getAccountTreeResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element folderIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_FOLDER_ID);
            Element paramsElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_PARAMS);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(folderIdElm);
            requestElm.add(paramsElm);
            actionElm.setText(BoxConstant.ACTION_NAME_GET_ACCOUNT_TREE);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            folderIdElm.setText(folderId);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    String param = params[i];
                    Element itemElm = DocumentHelper
                            .createElement(BoxConstant.PARAM_NAME_ITEM);
                    itemElm.setText(param);
                    paramsElm.add(itemElm);
                }
            }

            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getAccountTreeResponse.setStatus(status);
                if (BoxConstant.STATUS_LISTING_OK.equals(status)) {
                    Element treeElm = responseElm.element(BoxConstant.PARAM_NAME_TREE);
                    if (params != null && Arrays.asList(params).contains(PARAMS_KEY_NOZIP)) {
                        DefaultMutableTreeNode treeModel = transferTree2Model(treeElm);
                        getAccountTreeResponse.setTree(treeModel);
                    } else {
                        getAccountTreeResponse.setEncodedTree(treeElm.getText());
                    }
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getAccountTreeResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getAccountTreeResponse;
    }

    /**
     * @param treeElm tree element
     * @return swing tree structure
     */
    private DefaultMutableTreeNode transferTree2Model(Element treeElm) {
        DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode();
        Element folderElm = treeElm.element("folder");
        recursiveOnFolder(folderElm, defaultMutableTreeNode);
        return defaultMutableTreeNode;
    }

    /**
     *
     * @param folderElm folder element
     * @param parentTreeNode parent tree node
     */
    private void recursiveOnFolder(Element folderElm, DefaultMutableTreeNode parentTreeNode) {
        BoxAbstractFile fileItem = BoxObjectFactory.createBoxAbstractFile();
        fileItem.setFolder(true);
        fileItem.setId(folderElm.attribute("id").getText());
        if (folderElm.attribute("created") != null && !"".equals(folderElm.attribute("created").getText())) {
            fileItem.setCreated(Long.parseLong(folderElm.attribute("created").getText()));
        }

        fileItem.setFolder(true);
        if (folderElm.attribute("keyword") != null) {
            fileItem.setKeyword(folderElm.attribute("keyword").getText());
        }

        fileItem.setName(folderElm.attribute("name").getText());
        if (folderElm.attribute("shared") != null) {
            fileItem.setShared(Boolean.parseBoolean(folderElm.attribute("shared").getText()));
        }

        if (folderElm.attribute("updated") != null & !"".equals(folderElm.attribute("updated").getText())) {
            fileItem.setUpdated(Long.parseLong(folderElm.attribute("updated").getText()));
        }

        parentTreeNode.setUserObject(fileItem);
        Element tagsElm = folderElm.element("tags");
        List tagsList = ConverterUtils.transferTags2List(tagsElm);
        fileItem.setTags(tagsList);
        Element filesElm = folderElm.element("files");
        if (filesElm != null) {
            for (int i = 0; i < filesElm.nodeCount(); i++) {
                Element fileNode = (Element) filesElm.node(i);
                DefaultMutableTreeNode theNode = new DefaultMutableTreeNode();
                parentTreeNode.add(theNode);
                recursiveOnFile(fileNode, theNode);
            }
        }

        Element foldersElm = folderElm.element("folders");
        if (foldersElm != null) {
            for (int i = 0; i < foldersElm.nodeCount(); i++) {
                Element folderNode = (Element) foldersElm.node(i);
                DefaultMutableTreeNode theNode = new DefaultMutableTreeNode();
                parentTreeNode.add(theNode);
                recursiveOnFolder(folderNode, theNode);
            }
        }
    }

    /**
     * @param fileNodeElm the file node element
     * @param parentTreeNode parent tree node
     */
    private void recursiveOnFile(Element fileNodeElm, DefaultMutableTreeNode parentTreeNode) {
        BoxAbstractFile fileItem = BoxObjectFactory.createBoxAbstractFile();
        fileItem.setId(fileNodeElm.attribute("id").getText());
        if (fileNodeElm.attribute("created") != null) {
            fileItem.setCreated(Long.parseLong(fileNodeElm.attribute("created").getText()));
        }

        fileItem.setFolder(false);
        if (fileNodeElm.attribute("keyword") != null) {
            fileItem.setKeyword(fileNodeElm.attribute("keyword").getText());
        }

        fileItem.setName(fileNodeElm.attribute("file_name").getText());
        if (fileNodeElm.attribute("shared") != null) {
            fileItem.setShared(Boolean.parseBoolean(fileNodeElm.attribute("shared").getText()));
        }

        fileItem.setSize(Long.parseLong(fileNodeElm.attribute("size").getText()));
        if (fileNodeElm.attribute("updated") != null) {
            fileItem.setUpdated(Long.parseLong(fileNodeElm.attribute("updated").getText()));
        }

        Element tagsElm = fileNodeElm.element("tags");
        List tagsList = ConverterUtils.transferTags2List(tagsElm);
        fileItem.setTags(tagsList);
        parentTreeNode.setUserObject(fileItem);
    }
}
