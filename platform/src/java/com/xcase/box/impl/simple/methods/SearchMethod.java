/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.SearchRequest;
import com.xcase.box.transputs.SearchResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SearchMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SearchResponse search(SearchRequest searchRequest) throws IOException, BoxException {
        LOGGER.debug("starting search()");
        try {
            SearchResponse searchResponse = BoxResponseFactory.createSearchResponse();
            String accessToken = searchRequest.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String query = searchRequest.getQuery();
            LOGGER.debug("query is " + query);
            int limit = searchRequest.getLimit();
            int offset = searchRequest.getOffset();
            StringBuffer urlBuff = super.getApiUrl("search");
            urlBuff.append(CommonConstant.QUESTION_MARK_STRING);
            urlBuff.append("query=" + query);
            /* TODO: decide how to handle limit and offset */
            String searchUrl = urlBuff.toString();
            LOGGER.debug("searchUrl is " + searchUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            List<NameValuePair> data = new ArrayList<NameValuePair>();
            JsonObject searchJsonObject = (JsonObject) httpManager.doJsonGet(searchUrl, headers, data);
            String totalCount = searchJsonObject.get("total_count").getAsString();
            LOGGER.debug("totalCount is " + totalCount);
            searchResponse.setTotalCount(totalCount);
            List<BoxAbstractFile> abstractBoxFileList = new ArrayList<BoxAbstractFile>();
            JsonArray entriesArray = searchJsonObject.getAsJsonArray("entries");
            for (int i = 0; i < entriesArray.size(); i++) {
                BoxAbstractFile boxAbstractFile = BoxObjectFactory.createBoxAbstractFile();
                JsonObject abstractFileJsonObject = (JsonObject) entriesArray.get(i);
                JsonElement typeElement = abstractFileJsonObject.get("type");
                String type = typeElement.getAsString();
                if (type.equalsIgnoreCase("folder")) {
                    boxAbstractFile.setFolder(true);
                }

                JsonElement idElement = abstractFileJsonObject.get("id");
                String id = idElement.getAsString();
                boxAbstractFile.setId(id);
                JsonElement nameElement = abstractFileJsonObject.get("name");
                String name = nameElement.getAsString();
                boxAbstractFile.setName(name);
                abstractBoxFileList.add(boxAbstractFile);
            }

            searchResponse.setEntries(abstractBoxFileList);
            return searchResponse;
        } catch (Exception e) {
            throw new BoxException(e.getMessage());
        }
    }
}
