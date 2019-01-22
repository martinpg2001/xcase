/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.URLUtils;
import java.io.*;
import java.lang.invoke.*;
import java.net.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.logging.log4j.*;

/*
/// Web Proxy for CommonApi
 */
public class CommonApiWebProxy extends SwaggerProxy implements ICommonApiWebProxy {

    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CommonApiWebProxy(URL baseUrl) {
        super(baseUrl);
    }
    
    public CommonApiWebProxy(URL baseUrl, String accessToken) {
        super(baseUrl, accessToken);
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="ClientUserData[]">The client users.</param>
    public void SetClientUsers(String clientId, ClientUserData[] body) {
        LOGGER.debug("starting SetClientUsers()");
        String url = "api/common/v1/clients/{clientId}/users"
            .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient(accessToken)) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="ClientUserData[]">The client users.</param>
    public void AddClientUsers(String clientId, ClientUserData[] body) {
        LOGGER.debug("starting AddClientUsers()");
        String url = "api/common/v1/clients/{clientId}/users"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="ClientUserData[]">The client users.</param>
    public void RemoveClientUsers(String clientId, ClientUserData[] body) {
        LOGGER.debug("starting RemoveClientUsers()");
        String url = "api/common/v1/clients/{clientId}/users"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="String[]">The roles.</param>
    public void SetGroupRoles(String groupId, String[] roleNames) {
        LOGGER.debug("starting SetGroupRoles()");
        String url = "api/common/v1/groups/{groupId}/roles"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(roleNames);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="String[]">The roles to add.</param>
    public void AddGroupRoles(String groupId, String[] roleNames) {
        LOGGER.debug("starting AddGroupRoles()");
        String url = "api/common/v1/groups/{groupId}/roles"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(roleNames);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="String[]">The roles to remove.</param>
    public void RemoveGroupRoles(String groupId, String[] roleNames) {
        LOGGER.debug("starting RemoveGroupRoles()");
        String url = "api/common/v1/groups/{groupId}/roles"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="String[]">The users.</param>
    public void SetGroupUsers(String groupId, String[] users) {
        LOGGER.debug("starting SetGroupUsers()");
        String url = "api/common/v1/groups/{groupId}/users"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(users);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="String[]">The users.</param>
    public void AddGroupUsers(String groupId, String[] users) {
        LOGGER.debug("starting AddGroupUsers()");
        String url = "api/common/v1/groups/{groupId}/users"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(users);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="String[]">The users.</param>
    public void RemoveGroupUsers(String groupId, String[] users) {
        LOGGER.debug("starting RemoveGroupUsers()");
        String url = "api/common/v1/groups/{groupId}/users"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="MatterUserData[]">The matter users.</param>
    /// <param name="String">The client ID.</param>
    public void SetMatterUsers(String matterId, MatterUserData[] body, String clientId) {
        LOGGER.debug("starting SetMatterUsers()");
        String url = "api/common/v1/matters/{matterId}/users"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="MatterUserData[]">The matter users.</param>
    /// <param name="String">The client ID.</param>
    public void AddMatterUsers(String matterId, MatterUserData[] body, String clientId) {
        LOGGER.debug("starting AddMatterUsers()");
        String url = "api/common/v1/matters/{matterId}/users"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="MatterUserData[]">The matter users.</param>
    /// <param name="String">The client ID.</param>
    public void RemoveMatterUsers(String matterId, MatterUserData[] body, String clientId) {
        LOGGER.debug("starting RemoveMatterUsers()");
        String url = "api/common/v1/matters/{matterId}/users"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The role name.</param>
    /// <param name="String[]">The capabilities.</param>
    public void SetRoleCapabilities(String roleName, String[] capabilities) {
        LOGGER.debug("starting SetRoleCapabilities()");
        String url = "api/common/v1/roles/{roleName}/capabilities"
                .replace("{roleName}", URLUtils.encodeUrl(roleName.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(capabilities);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The role name.</param>
    /// <param name="String[]">The capabilities to add.</param>
    public void AddRoleCapabilities(String roleName, String[] capabilities) {
        LOGGER.debug("starting AddRoleCapabilities()");
        String url = "api/common/v1/roles/{roleName}/capabilities"
                .replace("{roleName}", URLUtils.encodeUrl(roleName.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(capabilities);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The role name.</param>
    /// <param name="String[]">The capabilities to remove.</param>
    public void RemoveRoleCapabilities(String roleName, String[] capabilities) {
        LOGGER.debug("starting RemoveRoleCapabilities()");
        String url = "api/common/v1/roles/{roleName}/capabilities"
                .replace("{roleName}", URLUtils.encodeUrl(roleName.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String[]">The roles to add.</param>
    /// <param name="String"></param>
    public void AddUserRoles(String userId, String[] roleNames, String roleId) {
        LOGGER.debug("starting AddUserRoles()");
        String url = "api/common/v1/roles/{roleId}/users"
                .replace("{roleId}", URLUtils.encodeUrl(roleId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "userId", userId.toString());

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(roleId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="CreateAddressData">The address.</param>
    public int CreateClientAddress(String clientId, CreateAddressData body) {
        LOGGER.debug("starting CreateClientAddress()");
        String url = "api/common/v1/clients/{clientId}/addresses"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="String">The address type.</param>
    /// <param name="String">The remote Id.</param>
    public void DeleteClientAddress(String clientId, String addressType, String remoteId) {
        LOGGER.debug("starting DeleteClientAddress()");
        String url = "api/common/v1/clients/{clientId}/addresses"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "addressType", addressType.toString());
        if (remoteId != null) {
            url = AppendQuery(url, "remoteId", remoteId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="String">The address type.</param>
    /// <param name="UpdateAddressData">The address.</param>
    /// <param name="String">The remote Id.</param>
    public void UpdateClientAddress(String clientId, String addressType, UpdateAddressData body, String remoteId) {
        LOGGER.debug("starting UpdateClientAddress()");
        String url = "api/common/v1/clients/{clientId}/addresses"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "addressType", addressType.toString());
        if (remoteId != null) {
            url = AppendQuery(url, "remoteId", remoteId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(remoteId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="CreateAddressData">The address.</param>
    /// <param name="String">The matter client ID.</param>
    public int CreateMatterAddress(String matterId, CreateAddressData body, String clientId) {
        LOGGER.debug("starting CreateMatterAddress()");
        String url = "api/common/v1/matters/{matterId}/addresses"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="String">The address type.</param>
    /// <param name="String">The matter client ID.</param>
    /// <param name="String">The remote Id.</param>
    public void DeleteMatterAddress(String matterId, String addressType, String clientId, String remoteId) {
        LOGGER.debug("starting DeleteMatterAddress()");
        String url = "api/common/v1/matters/{matterId}/addresses"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "addressType", addressType.toString());
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }
        if (remoteId != null) {
            url = AppendQuery(url, "remoteId", remoteId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="String">The address type.</param>
    /// <param name="UpdateAddressData">The address.</param>
    /// <param name="String">The matter client ID.</param>
    /// <param name="String">The remote Id.</param>
    public void UpdateMatterAddress(String matterId, String addressType, UpdateAddressData body, String clientId, String remoteId) {
        LOGGER.debug("starting UpdateMatterAddress()");
        String url = "api/common/v1/matters/{matterId}/addresses"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "addressType", addressType.toString());
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }
        if (remoteId != null) {
            url = AppendQuery(url, "remoteId", remoteId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(remoteId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="CreateAddressData">The address.</param>
    public int CreatePartyAddress(String partyId, CreateAddressData body) {
        LOGGER.debug("starting CreatePartyAddress()");
        String url = "api/common/v1/parties/{partyId}/addresses"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="String">The address type.</param>
    /// <param name="String">The remote Id.</param>
    public void DeletePartyAddress(String partyId, String addressType, String remoteId) {
        LOGGER.debug("starting DeletePartyAddress()");
        String url = "api/common/v1/parties/{partyId}/addresses"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "addressType", addressType.toString());
        if (remoteId != null) {
            url = AppendQuery(url, "remoteId", remoteId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="String">The address type.</param>
    /// <param name="UpdateAddressData">The address.</param>
    /// <param name="String">The remote Id.</param>
    public void UpdatePartyAddress(String partyId, String addressType, UpdateAddressData body, String remoteId) {
        LOGGER.debug("starting UpdatePartyAddress()");
        String url = "api/common/v1/parties/{partyId}/addresses"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "addressType", addressType.toString());
        if (remoteId != null) {
            url = AppendQuery(url, "remoteId", remoteId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(remoteId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="CreateAliasData">The alias.</param>
    public int CreateClientAlias(String clientId, CreateAliasData body) {
        LOGGER.debug("starting CreateClientAlias()");
        String url = "api/common/v1/clients/{clientId}/aliases"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="String">The alias name.</param>
    public void DeleteClientAlias(String clientId, String aliasName) {
        LOGGER.debug("starting DeleteClientAlias()");
        String url = "api/common/v1/clients/{clientId}/aliases"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "aliasName", aliasName.toString());

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="String">The alias name.</param>
    /// <param name="UpdateAliasData">The alias.</param>
    public void UpdateClientAlias(String clientId, String aliasName, UpdateAliasData body) {
        LOGGER.debug("starting UpdateClientAlias()");
        String url = "api/common/v1/clients/{clientId}/aliases"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "aliasName", aliasName.toString());

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="CreateAliasData">The alias.</param>
    public int CreatePartyAlias(String partyId, CreateAliasData body) {
        LOGGER.debug("starting CreatePartyAlias()");
        String url = "api/common/v1/parties/{partyId}/aliases"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="String">The alias name.</param>
    public void DeletePartyAlias(String partyId, String aliasName) {
        LOGGER.debug("starting DeletePartyAlias()");
        String url = "api/common/v1/parties/{partyId}/aliases"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "aliasName", aliasName.toString());

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="String">The alias name.</param>
    /// <param name="UpdateAliasData">The alias.</param>
    public void UpdatePartyAlias(String partyId, String aliasName, UpdateAliasData body) {
        LOGGER.debug("starting UpdatePartyAlias()");
        String url = "api/common/v1/parties/{partyId}/aliases"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "aliasName", aliasName.toString());

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="CreateAttachmentData">The attachment.</param>
    public int CreateClientAttachment(String clientId, CreateAttachmentData body) {
        LOGGER.debug("starting CreateClientAttachment()");
        String url = "api/common/v1/clients/{clientId}/attachments"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="CreateAttachmentData">The attachment.</param>
    /// <param name="String">The matter client ID.</param>
    public int CreateMatterAttachment(String matterId, CreateAttachmentData body, String clientId) {
        LOGGER.debug("starting CreateMatterAttachment()");
        String url = "api/common/v1/matters/{matterId}/attachments"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="CreateAttachmentData">The attachment.</param>
    public int CreatePartyAttachment(String partyId, CreateAttachmentData body) {
        LOGGER.debug("starting CreatePartyAttachment()");
        String url = "api/common/v1/parties/{partyId}/attachments"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String">The new password.</param>
    public void ChangePassword(String userId, String password) {
        LOGGER.debug("starting ChangePassword()");
        String url = "api/common/v1/users/{userId}/password"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(password);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateClientData">Client.</param>
    public ClientData CreateClient(CreateClientData body)
    {
        LOGGER.debug("starting CreateClient()");
        String url = "api/common/v1/clients"
        ;

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient(accessToken))
        {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[] { acceptHeader, bearerHeader, contentTypeHeader };
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            ClientData entity = responseGson.fromJson(response, ClientData.class);
            return entity;
        }
        catch (IOException ioe)
        {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        }
        catch (Exception e)
        {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateClientData[]">The clients.</param>
    public void CreateClients(CreateClientData[] body) {
        LOGGER.debug("starting CreateClients()");
        String url = "api/common/v1/clients/_bulk";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="UpdateClientData[]">The clients.</param>
    public void UpdateClients(UpdateClientData[] body) {
        LOGGER.debug("starting UpdateClients()");
        String url = "api/common/v1/clients/_bulk";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="CreateWarningData">The warning.</param>
    public int CreateClientWarning(String clientId, CreateWarningData body) {
        LOGGER.debug("starting CreateClientWarning()");
        String url = "api/common/v1/clients/{clientId}/warnings"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="int">The warning Id.</param>
    public void DeleteClientWarning(String clientId, int warningId) {
        LOGGER.debug("starting DeleteClientWarning()");
        String url = "api/common/v1/clients/{clientId}/warnings"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "warningId", String.valueOf(warningId));

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="int">The warning ID.</param>
    /// <param name="UpdateWarningData">The warning.</param>
    public void UpdateClientWarning(String clientId, int warningId, UpdateWarningData body) {
        LOGGER.debug("starting UpdateClientWarning()");
        String url = "api/common/v1/clients/{clientId}/warnings"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "warningId", String.valueOf(warningId));

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="CreateWarningData">The warning.</param>
    /// <param name="String">The matter client id.</param>
    public int CreateMatterWarning(String matterId, CreateWarningData body, String clientId) {
        LOGGER.debug("starting CreateMatterWarning()");
        String url = "api/common/v1/matters/{matterId}/warnings"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="int">The warning Id.</param>
    /// <param name="String">The matter client id.</param>
    public void DeleteMatterWarning(String matterId, int warningId, String clientId) {
        LOGGER.debug("starting DeleteMatterWarning()");
        String url = "api/common/v1/matters/{matterId}/warnings"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "warningId", String.valueOf(warningId));
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="int">The warning ID.</param>
    /// <param name="UpdateWarningData">The warning.</param>
    /// <param name="String">The matter client id.  This is only necessary if the entity ID does not
///            uniquely identity the entity.</param>
    public void UpdateMatterWarning(String matterId, int warningId, UpdateWarningData body, String clientId) {
        LOGGER.debug("starting UpdateMatterWarning()");
        String url = "api/common/v1/matters/{matterId}/warnings"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "warningId", String.valueOf(warningId));
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="CreateWarningData">The warning.</param>
    public int CreatePartyWarning(String partyId, CreateWarningData body) {
        LOGGER.debug("starting CreatePartyWarning()");
        String url = "api/common/v1/parties/{partyId}/warnings"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="int">The warning Id.</param>
    public void DeletePartyWarning(String partyId, int warningId) {
        LOGGER.debug("starting DeletePartyWarning()");
        String url = "api/common/v1/parties/{partyId}/warnings"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "warningId", String.valueOf(warningId));

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="int">The warning ID.</param>
    /// <param name="UpdateWarningData">The warning.</param>
    public void UpdatePartyWarning(String partyId, int warningId, UpdateWarningData body) {
        LOGGER.debug("starting UpdatePartyWarning()");
        String url = "api/common/v1/parties/{partyId}/warnings"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "warningId", String.valueOf(warningId));

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateGroupData">The user group details.</param>
    public int CreateGroup(CreateGroupData body) {
        LOGGER.debug("starting CreateGroup()");
        String url = "api/common/v1/groups";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateMatterData">The matter.</param>
    public int CreateMatter(CreateMatterData body) {
        LOGGER.debug("starting CreateMatter()");
        String url = "api/common/v1/matters";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateMatterData[]">The list of matters.</param>
    public void CreateMatters(CreateMatterData[] body) {
        LOGGER.debug("starting CreateMatters()");
        String url = "api/common/v1/matters/_bulk";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="UpdateMatterData[]">The list of matters.</param>
    public void UpdateMatters(UpdateMatterData[] body) {
        LOGGER.debug("starting UpdateMatters()");
        String url = "api/common/v1/matters/_bulk";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="CreateNoteData">The note.</param>
    public int CreateClientNote(String clientId, CreateNoteData body) {
        LOGGER.debug("starting CreateClientNote()");
        String url = "api/common/v1/clients/{clientId}/notes"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="CreateNoteData">The note.</param>
    /// <param name="String">The matter client ID.</param>
    public int CreateMatterNote(String matterId, CreateNoteData body, String clientId) {
        LOGGER.debug("starting CreateMatterNote()");
        String url = "api/common/v1/matters/{matterId}/notes"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="CreateNoteData">The note.</param>
    public int CreatePartyNote(String partyId, CreateNoteData body) {
        LOGGER.debug("starting CreatePartyNote()");
        String url = "api/common/v1/parties/{partyId}/notes"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="GroupSecurityData[]">The security details.</param>
    public void CreateOrReplaceGroupClientSecurity(String clientId, GroupSecurityData[] body) {
        LOGGER.debug("starting CreateOrReplaceGroupClientSecurity()");
        String url = "api/common/v1/clients/{clientId}/groupsecurity"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String"></param>
    /// <param name="DeleteEntitySecurityentityType"></param>
    /// <param name="String[]"></param>
    public void DeleteEntitySecurity(String clientId, DeleteEntitySecurityentityType entityType, String[] groupIds) {
        LOGGER.debug("starting DeleteEntitySecurity()");
        String url = "api/common/v1/clients/{clientId}/groupsecurity"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "entityType", String.valueOf(entityType));

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="GroupSecurityData[]">The security details.</param>
    /// <param name="String">The matter client ID.</param>
    public void CreateOrReplaceGroupMatterSecurity(String matterId, GroupSecurityData[] body, String clientId) {
        LOGGER.debug("starting CreateOrReplaceGroupMatterSecurity()");
        String url = "api/common/v1/matters/{matterId}/groupsecurity"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String"></param>
    /// <param name="String[]"></param>
    /// <param name="String"></param>
    public void DeleteEntitySecurity(String matterId, String[] groupIds, String clientId) {
        LOGGER.debug("starting DeleteEntitySecurity()");
        String url = "api/common/v1/matters/{matterId}/groupsecurity"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String"></param>
    /// <param name="String"></param>
    public EntitySecurityData GetEntitySecurity(String clientId, String userIds) {
        LOGGER.debug("starting GetEntitySecurity()");
        String url = "api/common/v1/clients/{clientId}/usersecurity"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "userIds", userIds.toString());

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            EntitySecurityData entity = responseGson.fromJson(response, EntitySecurityData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="SecurityData[]">The security details.</param>
    public void CreateOrReplaceUserClientSecurity(String clientId, SecurityData[] body) {
        LOGGER.debug("starting CreateOrReplaceUserClientSecurity()");
        String url = "api/common/v1/clients/{clientId}/usersecurity"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String"></param>
    /// <param name="String[]"></param>
    public void DeleteUserEntitySecurity(String clientId, String[] userIds) {
        LOGGER.debug("starting DeleteUserEntitySecurity()");
        String url = "api/common/v1/clients/{clientId}/usersecurity"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String"></param>
    /// <param name="String"></param>
    /// <param name="String"></param>
    public EntitySecurityData GetEntitySecurity(String matterId, String userIds, String clientId) {
        LOGGER.debug("starting GetEntitySecurity()");
        String url = "api/common/v1/matters/{matterId}/usersecurity"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        url = AppendQuery(url, "userIds", userIds.toString());
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            EntitySecurityData entity = responseGson.fromJson(response, EntitySecurityData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="SecurityData[]">The security details.</param>
    /// <param name="String">The matter client ID.</param>
    public void CreateOrReplaceUserMatterSecurity(String matterId, SecurityData[] body, String clientId) {
        LOGGER.debug("starting CreateOrReplaceUserMatterSecurity()");
        String url = "api/common/v1/matters/{matterId}/usersecurity"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String"></param>
    /// <param name="String[]"></param>
    /// <param name="String"></param>
    public void DeleteUserEntitySecurity(String matterId, String[] userIds, String clientId) {
        LOGGER.debug("starting DeleteUserEntitySecurity()");
        String url = "api/common/v1/matters/{matterId}/usersecurity"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreatePartyData[]">The parties.</param>
    public void CreateParties(CreatePartyData[] body) {
        LOGGER.debug("starting CreateParties()");
        String url = "api/common/v1/parties/_bulk";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreatePartyData">The party.</param>
    public int CreateParty(CreatePartyData body) {
        LOGGER.debug("starting CreateParty()");
        String url = "api/common/v1/parties";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateRoleData">The role details.</param>
    public int CreateRole(CreateRoleData body) {
        LOGGER.debug("starting CreateRole()");
        String url = "api/common/v1/roles";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="CreateUserData">The user details.</param>
    public int CreateUser(CreateUserData body) {
        LOGGER.debug("starting CreateUser()");
        String url = "api/common/v1/users";

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPost(url, headers, null, entityBody);
            Gson responseGson = new Gson();
            int entity = responseGson.fromJson(response, int.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return -1;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user id.</param>
    public void DeactivateUser(String userId) {
        LOGGER.debug("starting DeactivateUser()");
        String url = "api/common/v1/users/{userId}/deactivate"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonPut(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    public void DisableUserLogin(String userId) {
        LOGGER.debug("starting DisableUserLogin()");
        String url = "api/common/v1/users/{userId}/disable"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonPut(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    public void EnableUserLogin(String userId) {
        LOGGER.debug("starting EnableUserLogin()");
        String url = "api/common/v1/users/{userId}/enable"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonPut(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="String[]">The extended properties to load.</param>
    public ClientData GetClient(String clientId, String[] properties) {
        LOGGER.debug("starting GetClient()");
        String url = "api/common/v1/clients/{clientId}"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;
        if (properties != null) {
            for (String item : properties) {
                url = AppendQuery(url, "properties", item.toString());
            }
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient(accessToken)) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            ClientData entity = responseGson.fromJson(response, ClientData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The client ID.</param>
    /// <param name="UpdateClientData">The updated client information.</param>
    public void UpdateClient(String clientId, UpdateClientData body) {
        LOGGER.debug("starting UpdateClient()");
        String url = "api/common/v1/clients/{clientId}"
                .replace("{clientId}", URLUtils.encodeUrl(clientId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    public GroupData GetGroup(String groupId) {
        LOGGER.debug("starting GetGroup()");
        String url = "api/common/v1/groups/{groupId}"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            GroupData entity = responseGson.fromJson(response, GroupData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The group ID.</param>
    /// <param name="UpdateGroupData">The group details.</param>
    public void UpdateGroup(String groupId, UpdateGroupData body) {
        LOGGER.debug("starting UpdateGroup()");
        String url = "api/common/v1/groups/{groupId}"
                .replace("{groupId}", URLUtils.encodeUrl(groupId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="String[]">The extended properties to load.</param>
    /// <param name="String">The client ID.</param>
    public MatterData GetMatter(String matterId, String[] properties, String clientId) {
        LOGGER.debug("starting GetMatter()");
        String url = "api/common/v1/matters/{matterId}"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (properties != null) {
            for (String item : properties) {
                url = AppendQuery(url, "properties", item.toString());
            }
        }
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient(accessToken)) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            MatterData entity = responseGson.fromJson(response, MatterData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The matter ID.</param>
    /// <param name="UpdateMatterData">The matter.</param>
    /// <param name="String">The client ID.</param>
    public void UpdateMatter(String matterId, UpdateMatterData body, String clientId) {
        LOGGER.debug("starting UpdateMatter()");
        String url = "api/common/v1/matters/{matterId}"
                .replace("{matterId}", URLUtils.encodeUrl(matterId.toString()));

        url = baseUrl + url;
        if (clientId != null) {
            url = AppendQuery(url, "clientId", clientId.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(clientId);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="String[]">The extended properties to load.</param>
    public PartyData GetParty(String partyId, String[] properties) {
        LOGGER.debug("starting GetParty()");
        String url = "api/common/v1/parties/{partyId}"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;
        if (properties != null) {
            for (String item : properties) {
                url = AppendQuery(url, "properties", item.toString());
            }
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            PartyData entity = responseGson.fromJson(response, PartyData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The party ID.</param>
    /// <param name="UpdatePartyData">The party.</param>
    public void UpdateParty(String partyId, UpdatePartyData body) {
        LOGGER.debug("starting UpdateParty()");
        String url = "api/common/v1/parties/{partyId}"
                .replace("{partyId}", URLUtils.encodeUrl(partyId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    public UserData GetUser(String userId) {
        LOGGER.debug("starting GetUser()");
        String url = "api/common/v1/users/{userId}"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            UserData entity = responseGson.fromJson(response, UserData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="UpdateUserData">The user details.</param>
    public void UpdateUser(String userId, UpdateUserData body) {
        LOGGER.debug("starting UpdateUser()");
        String url = "api/common/v1/users/{userId}"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The role name.</param>
    public RoleData GetRole(String roleName) {
        LOGGER.debug("starting GetRole()");
        String url = "api/common/v1/roles/{roleName}"
                .replace("{roleName}", URLUtils.encodeUrl(roleName.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            RoleData entity = responseGson.fromJson(response, RoleData.class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The role name.</param>
    /// <param name="UpdateRoleData">The role details.</param>
    public void UpdateRole(String roleName, UpdateRoleData body) {
        LOGGER.debug("starting UpdateRole()");
        String url = "api/common/v1/roles/{roleName}"
                .replace("{roleName}", URLUtils.encodeUrl(roleName.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(body);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPatch(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String[]">The roles.</param>
    public void SetUserRoles(String userId, String[] roleNames) {
        LOGGER.debug("starting SetUserRoles()");
        String url = "api/common/v1/users/{userId}/roles"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(roleNames);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String[]">The roles to remove.</param>
    public void RemoveUserRoles(String userId, String[] roleNames) {
        LOGGER.debug("starting RemoveUserRoles()");
        String url = "api/common/v1/users/{userId}/roles"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonDelete(url, headers, null);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String[]">The user&apos;s departments.</param>
    public void SetUserDepartments(String userId, String[] departments) {
        LOGGER.debug("starting SetUserDepartments()");
        String url = "api/common/v1/users/{userId}/departments"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(departments);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String">The user&apos;s image.</param>
    public void SetUserImage(String userId, String image) {
        LOGGER.debug("starting SetUserImage()");
        String url = "api/common/v1/users/{userId}/image"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(image);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">The user ID.</param>
    /// <param name="String[]">The user&apos;s practice areas.</param>
    public void SetUserPracticeAreas(String userId, String[] practiceAreas) {
        LOGGER.debug("starting SetUserPracticeAreas()");
        String url = "api/common/v1/users/{userId}/practiceareas"
                .replace("{userId}", URLUtils.encodeUrl(userId.toString()));

        url = baseUrl + url;

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            Gson gson = new Gson();
            String entityBody = gson.toJson(practiceAreas);
            LOGGER.debug("entityBody is " + entityBody);
            JsonElement response = apiClient.doJsonPut(url, headers, null, entityBody);
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

    }

    @Override
    public void SyncBoardmembers(CompanyBoardMembersData[] body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SyncShareholder(CompanyShareholdersData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SyncShareholders(CompanyShareholdersData[] body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void AddUserRoles(String userId, String[] roleNames) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GetEntitySecurityData[] GetClientSecurity(String clientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateOrReplaceClientSecurity(String clientId, CreateEntitySecurityData[] body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteClientSecurity(String clientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GetEntitySecurityData[] GetMatterSecurity(String matterId, String clientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateOrReplaceMatterSecurity(String matterId, CreateEntitySecurityData[] body, String clientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteMatterSecurity(String matterId, String clientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LookupData[] GetOffices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateOffice(CreateOfficeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateOffice(String key, UpdateOfficeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IndustryCodeData[] GetStandardIndustryCodes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateStandardIndustryCode(CreateIndustryCodeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateStandardIndustryCode(String key, UpdateIndustryCodeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IndustrySectorData[] GetIndustrySectors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateIndustrySector(CreateIndustrySectorData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateIndustrySector(String key, UpdateIndustrySectorData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClientUserTypeData[] GetClientUserTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateClientUserType(CreateClientUserTypeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateClientUserType(String key, UpdateClientUserTypeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MatterUserTypeData[] GetMatterUserTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateMatterUserType(CreateMatterUserTypeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateMatterUserType(String key, UpdateMatterUserTypeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PartyTypeData[] GetPartyTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LookupData[] GetClientStatuses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateClientStatus(ClientStatusData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateClientStatus(String key, ClientStatusData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LookupData[] GetMatterStatuses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateMatterStatus(MatterStatusData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateMatterStatus(String key, MatterStatusData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PracticeAreaData[] GetPracticeAreas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreatePracticeArea(CreatePracticeAreaData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdatePracticeArea(String key, UpdatePracticeAreaData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AddressTypeData[] GetAddressTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateAddressType(CreateAddressTypeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateAddressType(String key, UpdateAddressTypeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepartmentData[] GetDepartments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateDepartment(CreateDepartmentData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateDepartment(String key, UpdateDepartmentData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserTitleData[] GetUserTitles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void CreateUserTitle(CreateUserTitleData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateUserTitle(String key, UpdateUserTitleData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ActivateUser(String userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteClientSecurityForPrincipal(String clientId, String principalId, DeleteClientSecurityForPrincipalprincipalType principalType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteMatterSecurityForPrincipal(String matterId, String principalId, String clientId, DeleteMatterSecurityForPrincipalprincipalType principalType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupData GetGroup(String groupId, String[] properties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SyncCorporateTree(CorporateTreeData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SyncCorporateTrees(CorporateTreeData[] body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SyncBoardmember(CompanyBoardMembersData body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
