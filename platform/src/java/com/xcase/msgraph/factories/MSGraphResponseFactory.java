/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.factories;

import com.xcase.msgraph.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class MSGraphResponseFactory extends BaseMSGraphFactory {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddGroupMemberResponse createAddGroupMemberResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.AddGroupMemberResponse");
        return (AddGroupMemberResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetCalendarResponse createGetCalendarResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetCalendarResponse");
        return (GetCalendarResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetCalendarsResponse createGetCalendarsResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetCalendarsResponse");
        return (GetCalendarsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetContactFolderResponse createGetContactFolderResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetContactFolderResponse");
        return (GetContactFolderResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetContactFoldersResponse createGetContactFoldersResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetContactFoldersResponse");
        return (GetContactFoldersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetDriveResponse createGetDriveResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetDriveResponse");
        return (GetDriveResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetGroupResponse createGetGroupResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetGroupResponse");
        return (GetGroupResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetGroupsResponse createGetGroupsResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetGroupsResponse");
        return (GetGroupsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMailFolderResponse createGetMailFolderResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetMailFolderResponse");
        return (GetMailFolderResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMailFoldersResponse createGetMailFoldersResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetMailFoldersResponse");
        return (GetMailFoldersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetMyProfileResponse createGetMyProfileResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetMyProfileResponse");
        return (GetMyProfileResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetUsersResponse createGetUsersResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetUsersResponse");
        return (GetUsersResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetUserResponse createGetUserResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.GetUserResponse");
        return (GetUserResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static InvokeAdvancedResponse createInvokeAdvancedResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.InvokeAdvancedResponse");
        return (InvokeAdvancedResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RemoveGroupMemberResponse createRemoveGroupMemberResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.RemoveGroupMemberResponse");
        return (RemoveGroupMemberResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateGroupResponse createUpdateGroupResponse() {
        Object obj = newInstanceOf("msgraph.config.responsefactory.UpdateGroupResponse");
        return (UpdateGroupResponse) obj;
    }
}
