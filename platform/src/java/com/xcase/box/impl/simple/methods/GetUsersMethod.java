/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.GetUsersRequest;
import com.xcase.box.transputs.GetUsersResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class GetUsersMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetUsersResponse getUsers(GetUsersRequest getUsersRequest) throws IOException, BoxException {
        LOGGER.debug("starting getUsers()");
        GetUsersResponse getUsersResponse = BoxResponseFactory.createGetUsersResponse();
        String apiKey = getUsersRequest.getApiKey();
        String accessToken = getUsersRequest.getAccessToken();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("users");
            String usersApiUrl = urlBuff.toString();
            LOGGER.debug("usersApiUrl is " + usersApiUrl);
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                JsonElement jsonElement = httpManager.doJsonGet(usersApiUrl, headers, null);
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonArray entriesArray = jsonObject.getAsJsonArray("entries");
                List<BoxUser> boxUserList = new ArrayList<BoxUser>();
                for (int i = 0; i < entriesArray.size(); i++) {
                    BoxUser boxUser = BoxObjectFactory.createBoxUser();
                    JsonObject abstractFileJsonObject = (JsonObject) entriesArray.get(i);
                    JsonElement typeElement = abstractFileJsonObject.get("type");
                    String type = typeElement.getAsString();
                    JsonElement idElement = abstractFileJsonObject.get("id");
                    String id = idElement.getAsString();
                    boxUser.setUserId(id);
                    JsonElement loginElement = abstractFileJsonObject.get("login");
                    String login = loginElement.getAsString();
                    boxUser.setLogin(login);
                    JsonElement nameElement = abstractFileJsonObject.get("name");
                    String name = nameElement.getAsString();
                    boxUser.setName(name);
                    boxUserList.add(boxUser);
                }

                getUsersResponse.setUsers(boxUserList);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("Failed to parse to a document.", e);
                be.setStatus(getUsersResponse.getStatus());
                throw be;
            }
        }

        return getUsersResponse;
    }
}
