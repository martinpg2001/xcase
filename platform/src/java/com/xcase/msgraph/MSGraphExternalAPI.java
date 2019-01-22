/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph;

import com.xcase.msgraph.transputs.*;

/**
 *
 * @author martin
 */
public interface MSGraphExternalAPI {
    AddGroupMemberResponse addGroupMember(AddGroupMemberRequest addGroupMemberRequest);
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest);
    GetAuthorizationCodeResponse getAuthorizationCode(GetAuthorizationCodeRequest getAuthorizationCodeRequest);
    GetCalendarResponse getCalendar(GetCalendarRequest getCalendarRequest);
    GetCalendarsResponse getCalendars(GetCalendarsRequest getCalendarsRequest);
    GetContactFolderResponse getContactFolder(GetContactFolderRequest getContactFolderRequest);
    GetContactFoldersResponse getContactFolders(GetContactFoldersRequest getContactFoldersRequest);
    GetDriveResponse getDrive(GetDriveRequest getDriveRequest);
    GetGroupResponse getGroup(GetGroupRequest getGroupRequest);
    GetGroupsResponse getGroups(GetGroupsRequest getGroupsRequest);
    GetMailFolderResponse getMailFolder(GetMailFolderRequest getMailFolderRequest);
    GetMailFoldersResponse getMailFolders(GetMailFoldersRequest getMailFoldersRequest);
    GetMyProfileResponse getMyProfile(GetMyProfileRequest getMyProfileRequest);
    GetUserResponse getUser(GetUserRequest getUserRequest);
    GetUsersResponse getUsers(GetUsersRequest getUsersRequest);
    InvokeAdvancedResponse invokeAdvanced(InvokeAdvancedRequest invokeAdvancedRequest);
    RemoveGroupMemberResponse removeGroupMember(RemoveGroupMemberRequest removeGroupMemberRequest);
    UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest);
}
