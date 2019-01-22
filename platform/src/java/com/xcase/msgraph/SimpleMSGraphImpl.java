/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph;

import com.xcase.msgraph.impl.simple.methods.*;
import com.xcase.msgraph.transputs.*;
import com.xcase.msgraph.impl.simple.core.MSGraphConfigurationManager;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SimpleMSGraphImpl implements MSGraphExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public MSGraphConfigurationManager LocalConfigurationManager = MSGraphConfigurationManager.getConfigurationManager();

    /**
     * MSGraph action implementation.
     */
    private AddGroupMemberMethod addGroupMemberMethod = new AddGroupMemberMethod();
    
    /**
     * MSGraph action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();

    /**
     * MSGraph action implementation.
     */
    private GetAuthorizationCodeMethod getAuthorizationCodeMethod = new GetAuthorizationCodeMethod();
    
    /**
     * MSGraph action implementation.
     */
    private GetCalendarMethod getCalendarMethod = new GetCalendarMethod();

    /**
     * MSGraph action implementation.
     */
    private GetCalendarsMethod getCalendarsMethod = new GetCalendarsMethod();

    /**
     * MSGraph action implementation.
     */
    private GetContactFolderMethod getContactFolderMethod = new GetContactFolderMethod();

    /**
     * MSGraph action implementation.
     */
    private GetContactFoldersMethod getContactFoldersMethod = new GetContactFoldersMethod();

    /**
     * MSGraph action implementation.
     */
    private GetDriveMethod getDriveMethod = new GetDriveMethod();

    /**
     * MSGraph action implementation.
     */
    private GetGroupMethod getGroupMethod = new GetGroupMethod();

    /**
     * MSGraph action implementation.
     */
    private GetGroupsMethod getGroupsMethod = new GetGroupsMethod();

    /**
     * MSGraph action implementation.
     */
    private GetMailFolderMethod getMailFolderMethod = new GetMailFolderMethod();

    /**
     * MSGraph action implementation.
     */
    private GetMailFoldersMethod getMailFoldersMethod = new GetMailFoldersMethod();

    /**
     * MSGraph action implementation.
     */
    private GetMyProfileMethod getMyProfileMethod = new GetMyProfileMethod();

    /**
     * MSGraph action implementation.
     */
    private GetUserMethod getUserMethod = new GetUserMethod();

    /**
     * MSGraph action implementation.
     */
    private GetUsersMethod getUsersMethod = new GetUsersMethod();
    
    /**
     * MSGraph action implementation.
     */
    private InvokeAdvancedMethod invokeAdvancedMethod = new InvokeAdvancedMethod();

    /**
     * MSGraph action implementation.
     */
    private RemoveGroupMemberMethod removeGroupMemberMethod = new RemoveGroupMemberMethod();

    /**
     * MSGraph action implementation.
     */
    private UpdateGroupMethod updateGroupMethod = new UpdateGroupMethod();

    public AddGroupMemberResponse addGroupMember(AddGroupMemberRequest addGroupMemberRequest) {
        return addGroupMemberMethod.addGroupMember(addGroupMemberRequest);
    }

    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) {
        return getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }
    
    public GetAuthorizationCodeResponse getAuthorizationCode(GetAuthorizationCodeRequest getAuthorizationCodeRequest) {
        return getAuthorizationCodeMethod.getAuthorizationCode(getAuthorizationCodeRequest);
    }

    public GetCalendarResponse getCalendar(GetCalendarRequest getCalendarRequest) {
        return getCalendarMethod.getCalendar(getCalendarRequest);
    }

    public GetCalendarsResponse getCalendars(GetCalendarsRequest getCalendarsRequest) {
        return getCalendarsMethod.getCalendars(getCalendarsRequest);
    }

    public GetContactFolderResponse getContactFolder(GetContactFolderRequest getContactFolderRequest) {
        return getContactFolderMethod.getContactFolder(getContactFolderRequest);
    }

    public GetContactFoldersResponse getContactFolders(GetContactFoldersRequest getContactFoldersRequest) {
        return getContactFoldersMethod.getContactFolders(getContactFoldersRequest);
    }

    public GetDriveResponse getDrive(GetDriveRequest getDriveRequest) {
        return getDriveMethod.getDrive(getDriveRequest);
    }

    public GetGroupResponse getGroup(GetGroupRequest getGroupRequest) {
        return getGroupMethod.getGroup(getGroupRequest);
    }

    public GetGroupsResponse getGroups(GetGroupsRequest getGroupsRequest) {
        return getGroupsMethod.getGroups(getGroupsRequest);
    }

    public GetMailFolderResponse getMailFolder(GetMailFolderRequest getMailFolderRequest) {
        return getMailFolderMethod.getMailFolder(getMailFolderRequest);
    }

    public GetMailFoldersResponse getMailFolders(GetMailFoldersRequest getMailFoldersRequest) {
        return getMailFoldersMethod.getMailFolders(getMailFoldersRequest);
    }

    public GetMyProfileResponse getMyProfile(GetMyProfileRequest getMyProfileRequest) {
        return getMyProfileMethod.getMyProfile(getMyProfileRequest);
    }

    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        return getUserMethod.getUser(getUserRequest);
    }

    public GetUsersResponse getUsers(GetUsersRequest getUsersRequest) {
        return getUsersMethod.getUsers(getUsersRequest);
    }
    
    public InvokeAdvancedResponse invokeAdvanced(InvokeAdvancedRequest invokeAdvancedRequest) {
        return invokeAdvancedMethod.invokeAdvanced(invokeAdvancedRequest);
    }

    public RemoveGroupMemberResponse removeGroupMember(RemoveGroupMemberRequest removeGroupMemberRequest) {
        return removeGroupMemberMethod.removeGroupMember(removeGroupMemberRequest);
    }

    public UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest) {
        return updateGroupMethod.updateGroup(updateGroupRequest);
    }
}
