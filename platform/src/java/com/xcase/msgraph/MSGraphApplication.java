/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.msgraph;

import com.xcase.msgraph.transputs.*;
import com.xcase.msgraph.objects.*;
import com.xcase.msgraph.constant.MSGraphConstant;
import com.xcase.msgraph.factories.MSGraphRequestFactory;
import com.xcase.msgraph.impl.simple.core.MSGraphConfigurationManager;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class MSGraphApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        /**
         * Client information from local configuration file
         */
        String clientId = MSGraphConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MSGraphConstant.LOCAL_OAUTH2_CLIENT_ID);
        LOGGER.debug("clientId is " + clientId);
        String clientSecret = MSGraphConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MSGraphConstant.LOCAL_OAUTH2_CLIENT_SECRET);
        LOGGER.debug("clientSecret is " + clientSecret);
        String tenantId = MSGraphConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MSGraphConstant.LOCAL_OAUTH2_TENANT_ID);
        LOGGER.debug("tenantId is " + tenantId);
        String username = MSGraphConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MSGraphConstant.LOCAL_OAUTH2_USERNAME);
        LOGGER.debug("username is " + username);
        String password = MSGraphConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(MSGraphConstant.LOCAL_OAUTH2_PASSWORD);
        LOGGER.debug("password is " + password);
        MSGraphExternalAPI msGraphExternalAPI = new SimpleMSGraphImpl();
        LOGGER.debug("created msGraphExternalAPI");
        try {
            LOGGER.debug("about to get authorization code");
            GetAuthorizationCodeRequest getAuthorizationCodeRequest = MSGraphRequestFactory.createGetAuthorizationCodeRequest(clientId, tenantId, username, password);
            LOGGER.debug("created getAuthorizationCodeRequest");
            GetAuthorizationCodeResponse getAuthorizationCodeResponse = msGraphExternalAPI.getAuthorizationCode(getAuthorizationCodeRequest);
            LOGGER.debug("got authorization code " + getAuthorizationCodeResponse.getAuthorizationCode());
            LOGGER.debug("about to get access token");
            GetAccessTokenRequest getAccessTokenRequest = MSGraphRequestFactory.createGetAccessTokenRequest(clientId, clientSecret, tenantId);
            LOGGER.debug("created getAccessTokenRequest");
            GetAccessTokenResponse getAccessTokenResponse = msGraphExternalAPI.getAccessToken(getAccessTokenRequest);
            String accessToken = getAccessTokenResponse.getAccessToken();
            LOGGER.debug("got access token " + accessToken);
            GetMyProfileRequest getMyProfileRequest = MSGraphRequestFactory.createGetMyProfileRequest(accessToken);
            LOGGER.debug("created getMyProfileRequest");
            GetMyProfileResponse getMyProfileResponse = msGraphExternalAPI.getMyProfile(getMyProfileRequest);
            LOGGER.debug("got my profile");
            LOGGER.debug("about to get groups");
            String search = null;
            String select = "id,displayName";
            Integer top = 10;
            Integer skip = null;
            GetGroupsRequest getGroupsRequest = MSGraphRequestFactory.createGetGroupsRequest(accessToken, search, select, top, skip);
            LOGGER.debug("created getGroupsRequest");
            GetGroupsResponse getGroupsResponse = msGraphExternalAPI.getGroups(getGroupsRequest);
            LOGGER.debug("got groups");
            String groupId = null;
            MSGraphGroup updateGroup = null;
            MSGraphGroup[] groups = getGroupsResponse.getGroups();
            for(MSGraphGroup group : groups) {
                groupId = group.id;
                GetGroupRequest getGroupRequest = MSGraphRequestFactory.createGetGroupRequest(accessToken, groupId);
                LOGGER.debug("created getGroupRequest");
                GetGroupResponse getGroupResponse = msGraphExternalAPI.getGroup(getGroupRequest);
                updateGroup = getGroupResponse.getGroup();
                LOGGER.debug("got group " + updateGroup.displayName + ":" + updateGroup.description);
            }

            LOGGER.debug("about to update group");
            updateGroup.description = "Updated description";
            UpdateGroupRequest updateGroupRequest = MSGraphRequestFactory.createUpdateGroupRequest(accessToken, groupId, updateGroup);
            LOGGER.debug("created updateGroupRequest");
            UpdateGroupResponse updateGroupResponse = msGraphExternalAPI.updateGroup(updateGroupRequest);
            LOGGER.debug("updated group");
            GetGroupRequest getGroupRequest = MSGraphRequestFactory.createGetGroupRequest(accessToken, groupId);
            LOGGER.debug("created getGroupRequest");
            GetGroupResponse getGroupResponse = msGraphExternalAPI.getGroup(getGroupRequest);
            MSGraphGroup updatedGroup = getGroupResponse.getGroup();
            LOGGER.debug("got group " + updatedGroup.displayName + ":" + updatedGroup.description);
            LOGGER.debug("about to get users");
            search = null;
            select = "id,displayName";
            top = null;
            skip = null;
            GetUsersRequest getUsersRequest = MSGraphRequestFactory.createGetUsersRequest(accessToken, search, select, top, skip);
            LOGGER.debug("created getUsersRequest");
            GetUsersResponse getUsersResponse = msGraphExternalAPI.getUsers(getUsersRequest);
            LOGGER.debug("got users");
            String userId = null;
            String testUserid = null;
            MSGraphUser[] users = getUsersResponse.getUsers();
            for(MSGraphUser user : users) {
                userId = user.id;
                LOGGER.debug("userId is " + userId);
                GetUserRequest getUserRequest = MSGraphRequestFactory.createGetUserRequest(accessToken, userId);
                LOGGER.debug("created getUserRequest");
                GetUserResponse getUserResponse = msGraphExternalAPI.getUser(getUserRequest);
                LOGGER.debug("got user " + getUserResponse.getUser().displayName);
                if (getUserResponse.getUser().displayName.equals("Martin Gilchrist")) {
                    testUserid = userId;
                    LOGGER.debug("testUserid is " + testUserid);
                }
            }

            AddGroupMemberRequest addGroupMemberRequest = MSGraphRequestFactory.createAddGroupMemberRequest(accessToken, groupId, testUserid);
            LOGGER.debug("created addGroupMemberRequest");
            AddGroupMemberResponse AddGroupMemberResponse = msGraphExternalAPI.addGroupMember(addGroupMemberRequest);
            LOGGER.debug("added group member");
            RemoveGroupMemberRequest removeGroupMemberRequest = MSGraphRequestFactory.createRemoveGroupMemberRequest(accessToken, groupId, testUserid);
            LOGGER.debug("created removeGroupMemberRequest");
            RemoveGroupMemberResponse removeGroupMemberResponse = msGraphExternalAPI.removeGroupMember(removeGroupMemberRequest);
            LOGGER.debug("removed group member");
            LOGGER.debug("about to get calendars");
            search = null;
            select = "id,displayName";
            top = 10;
            skip = null;
            GetCalendarsRequest getCalendarsRequest = MSGraphRequestFactory.createGetCalendarsRequest(accessToken, testUserid);
            LOGGER.debug("created getCalendarsRequest");
            GetCalendarsResponse getCalendarsResponse = msGraphExternalAPI.getCalendars(getCalendarsRequest);
            LOGGER.debug("got calendars");
            String calendarId = null;
            MSGraphCalendar[] calendars = getCalendarsResponse.getCalendars();
            for(MSGraphCalendar calendar : calendars) {
                calendarId = calendar.id;
                LOGGER.debug("calendarId is " + calendarId);
                GetCalendarRequest getCalendarRequest = MSGraphRequestFactory.createGetCalendarRequest(accessToken, calendarId, testUserid);
                LOGGER.debug("created getCalendarRequest");
                GetCalendarResponse getCalendarResponse = msGraphExternalAPI.getCalendar(getCalendarRequest);
                LOGGER.debug("got calendar " + getCalendarResponse.getCalendar().id);
            }

            GetContactFoldersRequest getContactFoldersRequest = MSGraphRequestFactory.createGetContactFoldersRequest(accessToken, testUserid);
            LOGGER.debug("created getContactFoldersRequest");
            GetContactFoldersResponse getContactFoldersResponse = msGraphExternalAPI.getContactFolders(getContactFoldersRequest);
            LOGGER.debug("got contact folders");
            String contactFolderId = null;
            MSGraphContactFolder[] contactFolders = getContactFoldersResponse.getContactFolders();
            for(MSGraphContactFolder contactFolder : contactFolders) {
                contactFolderId = contactFolder.id;
                LOGGER.debug("contactFolderId is " + contactFolderId);
                GetContactFolderRequest getContactFolderRequest = MSGraphRequestFactory.createGetContactFolderRequest(accessToken, contactFolderId, testUserid);
                LOGGER.debug("created getContactFolderRequest");
                GetContactFolderResponse getContactFolderResponse = msGraphExternalAPI.getContactFolder(getContactFolderRequest);
                LOGGER.debug("got contact folder " + getContactFolderResponse.getContactFolder().id);
            }

            search = null;
            select = "id,displayName";
            top = 10;
            skip = null;
            GetMailFoldersRequest getMailFoldersRequest = MSGraphRequestFactory.createGetMailFoldersRequest(accessToken, testUserid);
            LOGGER.debug("created getMailFoldersRequest");
            GetMailFoldersResponse getMailFoldersResponse = msGraphExternalAPI.getMailFolders(getMailFoldersRequest);
            LOGGER.debug("got mail folders");
            String mailFolderId = null;
            MSGraphMailFolder[] mailFolders = getMailFoldersResponse.getMailFolders();
            for(MSGraphMailFolder mailFolder : mailFolders) {
                mailFolderId = mailFolder.id;
                LOGGER.debug("mailFolderId is " + mailFolderId);
                GetMailFolderRequest getMailFolderRequest = MSGraphRequestFactory.createGetMailFolderRequest(accessToken, mailFolderId, testUserid);
                LOGGER.debug("created getMailFolderRequest");
                GetMailFolderResponse getMailFolderResponse = msGraphExternalAPI.getMailFolder(getMailFolderRequest);
                LOGGER.debug("got mail folder " + getMailFolderResponse.getMailFolder().id);
            }

            LOGGER.debug("about to get drive for user");
            GetDriveRequest getDriveRequest = MSGraphRequestFactory.createGetDriveRequest(accessToken, testUserid);
            LOGGER.debug("created getDriveRequest");
            GetDriveResponse getDriveResponse = msGraphExternalAPI.getDrive(getDriveRequest);
            LOGGER.debug("got drive");
            LOGGER.debug("about to invoke advanced method");
            String method = "GET";
            LOGGER.debug("method is " + method);
            String advancedUrl = "groups/asdfsadfsadf";
            LOGGER.debug("advancedUrl is " + advancedUrl);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            String memberBody = null;
            LOGGER.debug("memberBody is " + memberBody);
            InvokeAdvancedRequest invokeAdvancedRequest = MSGraphRequestFactory.createInvokeAdvancedRequest(accessToken, advancedUrl, memberBody, method, parameters);
            LOGGER.debug("created invokeAdvancedRequest");
            InvokeAdvancedResponse invokeAdvancedResponse = msGraphExternalAPI.invokeAdvanced(invokeAdvancedRequest);
            LOGGER.debug("invoked advanced method");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }
}
