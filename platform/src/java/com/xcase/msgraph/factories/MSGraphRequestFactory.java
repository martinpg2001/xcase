/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.factories;

import com.xcase.msgraph.objects.*;
import com.xcase.msgraph.transputs.*;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class MSGraphRequestFactory extends BaseMSGraphFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static AddGroupMemberRequest createAddGroupMemberRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.AddGroupMemberRequest");
        return (AddGroupMemberRequest) obj;
    }

    public static AddGroupMemberRequest createAddGroupMemberRequest(String accessToken, String groupId, String memberId) {
        AddGroupMemberRequest request = createAddGroupMemberRequest();
        request.setAccessToken(accessToken);
        request.setGroupId(groupId);
        request.setMemberId(memberId);
        return request;
    }

    public static GetAccessTokenRequest createGetAccessTokenRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }

    public static GetAccessTokenRequest createGetAccessTokenRequest(String clientId, String clientSecret, String tenantId) {
        GetAccessTokenRequest request = createGetAccessTokenRequest();
        request.setClientId(clientId);
        request.setClientSecret(clientSecret);
        request.setTenantId(tenantId);
        return request;
    }
    
    public static GetAuthorizationCodeRequest createGetAuthorizationCodeRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetAuthorizationCodeRequest");
        return (GetAuthorizationCodeRequest) obj;
    }

    public static GetAuthorizationCodeRequest createGetAuthorizationCodeRequest(String clientId, String tenantId, String username, String password) {
        GetAuthorizationCodeRequest request = createGetAuthorizationCodeRequest();
        request.setClientId(clientId);
        request.setTenantId(tenantId);
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }

    public static GetCalendarRequest createGetCalendarRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetCalendarRequest");
        return (GetCalendarRequest) obj;
    }

    public static GetCalendarRequest createGetCalendarRequest(String accessToken, String calendarId, String userId) {
        GetCalendarRequest request = createGetCalendarRequest();
        request.setAccessToken(accessToken);
        request.setCalendarId(calendarId);
        request.setUserId(userId);
        return request;
    }

    public static GetCalendarsRequest createGetCalendarsRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetCalendarsRequest");
        return (GetCalendarsRequest) obj;
    }

    public static GetCalendarsRequest createGetCalendarsRequest(String accessToken, String userId) {
        GetCalendarsRequest request = createGetCalendarsRequest();
        request.setAccessToken(accessToken);
        request.setUserId(userId);
        return request;
    }

    public static GetContactFolderRequest createGetContactFolderRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetContactFolderRequest");
        return (GetContactFolderRequest) obj;
    }

    public static GetContactFolderRequest createGetContactFolderRequest(String accessToken, String contactFolderId, String userId) {
        GetContactFolderRequest request = createGetContactFolderRequest();
        request.setAccessToken(accessToken);
        request.setContactFolderId(contactFolderId);
        request.setUserId(userId);
        return request;
    }

    public static GetContactFoldersRequest createGetContactFoldersRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetContactFoldersRequest");
        return (GetContactFoldersRequest) obj;
    }

    public static GetContactFoldersRequest createGetContactFoldersRequest(String accessToken, String userId) {
        GetContactFoldersRequest request = createGetContactFoldersRequest();
        request.setAccessToken(accessToken);
        request.setUserId(userId);
        return request;
    }

    public static GetDriveRequest createGetDriveRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetDriveRequest");
        return (GetDriveRequest) obj;
    }

    public static GetDriveRequest createGetDriveRequest(String accessToken, String userId) {
        GetDriveRequest request = createGetDriveRequest();
        request.setAccessToken(accessToken);
        request.setUserId(userId);
        return request;
    }

    public static GetGroupRequest createGetGroupRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetGroupRequest");
        return (GetGroupRequest) obj;
    }

    public static GetGroupRequest createGetGroupRequest(String accessToken, String groupId) {
        GetGroupRequest request = createGetGroupRequest();
        request.setAccessToken(accessToken);
        request.setGroupId(groupId);
        return request;
    }

    public static GetGroupsRequest createGetGroupsRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetGroupsRequest");
        return (GetGroupsRequest) obj;
    }

    public static GetGroupsRequest createGetGroupsRequest(String accessToken) {
        GetGroupsRequest request = createGetGroupsRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetGroupsRequest createGetGroupsRequest(String accessToken, String search, String select, Integer top, Integer skip) {
        GetGroupsRequest request = createGetGroupsRequest();
        request.setAccessToken(accessToken);
        request.setSearch(search);
        request.setSelect(select);
        request.setTop(top);
        request.setSkip(skip);
        return request;
    }

    public static GetMailFolderRequest createGetMailFolderRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetMailFolderRequest");
        return (GetMailFolderRequest) obj;
    }

    public static GetMailFolderRequest createGetMailFolderRequest(String accessToken, String mailFolderId, String userId) {
        GetMailFolderRequest request = createGetMailFolderRequest();
        request.setAccessToken(accessToken);
        request.setMailFolderId(mailFolderId);
        request.setUserId(userId);
        return request;
    }

    public static GetMailFoldersRequest createGetMailFoldersRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetMailFoldersRequest");
        return (GetMailFoldersRequest) obj;
    }

    public static GetMailFoldersRequest createGetMailFoldersRequest(String accessToken, String userId) {
        GetMailFoldersRequest request = createGetMailFoldersRequest();
        request.setAccessToken(accessToken);
        request.setUserId(userId);
        return request;
    }

    public static GetMailFoldersRequest createGetMailFoldersRequest(String accessToken, String search, String select, Integer top, Integer skip) {
        GetMailFoldersRequest request = createGetMailFoldersRequest();
        request.setAccessToken(accessToken);
        request.setSearch(search);
        request.setSelect(select);
        request.setTop(top);
        request.setSkip(skip);
        return request;
    }

    public static GetMyProfileRequest createGetMyProfileRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetMyProfileRequest");
        return (GetMyProfileRequest) obj;
    }

    public static GetMyProfileRequest createGetMyProfileRequest(String accessToken) {
        GetMyProfileRequest request = createGetMyProfileRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetUserRequest createGetUserRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetUserRequest");
        return (GetUserRequest) obj;
    }

    public static GetUserRequest createGetUserRequest(String accessToken, String userId) {
        GetUserRequest request = createGetUserRequest();
        request.setAccessToken(accessToken);
        request.setUserId(userId);
        return request;
    }

    public static GetUsersRequest createGetUsersRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.GetUsersRequest");
        return (GetUsersRequest) obj;
    }

    public static GetUsersRequest createGetUsersRequest(String accessToken) {
        GetUsersRequest request = createGetUsersRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static GetUsersRequest createGetUsersRequest(String accessToken, String search, String select, Integer top, Integer skip) {
        GetUsersRequest request = createGetUsersRequest();
        request.setAccessToken(accessToken);
        request.setSearch(search);
        request.setSelect(select);
        request.setTop(top);
        request.setSkip(skip);
        return request;
    }
    
    public static InvokeAdvancedRequest createInvokeAdvancedRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.InvokeAdvancedRequest");
        return (InvokeAdvancedRequest) obj;
    }

    public static InvokeAdvancedRequest createInvokeAdvancedRequest(String accessToken, String advancedUrl, String memberBody, String method, List<NameValuePair> parameters) {
        InvokeAdvancedRequest request = createInvokeAdvancedRequest();
        request.setAccessToken(accessToken);
        request.setAdvancedUrl(advancedUrl);
        request.setMemberBody(memberBody);
        request.setMethod(method);
        request.setParameters(parameters);
        return request;
    }

    public static RemoveGroupMemberRequest createRemoveGroupMemberRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.RemoveGroupMemberRequest");
        return (RemoveGroupMemberRequest) obj;
    }

    public static RemoveGroupMemberRequest createRemoveGroupMemberRequest(String accessToken, String groupId, String memberId) {
        RemoveGroupMemberRequest request = createRemoveGroupMemberRequest();
        request.setAccessToken(accessToken);
        request.setGroupId(groupId);
        request.setMemberId(memberId);
        return request;
    }

    public static UpdateGroupRequest createUpdateGroupRequest() {
        Object obj = newInstanceOf("msgraph.config.requestfactory.UpdateGroupRequest");
        return (UpdateGroupRequest) obj;
    }

    public static UpdateGroupRequest createUpdateGroupRequest(String accessToken, String groupId, MSGraphGroup group) {
        UpdateGroupRequest request = createUpdateGroupRequest();
        request.setAccessToken(accessToken);
        request.setGroupId(groupId);
        request.setGroup(group);
        return request;
    }
}
