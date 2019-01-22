/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.factories;

import com.xcase.sharepoint.transputs.AddToMySharepointResponse;
import com.xcase.sharepoint.transputs.AddToTagResponse;
import com.xcase.sharepoint.transputs.CreateFolderResponse;
import com.xcase.sharepoint.transputs.DeleteResponse;
import com.xcase.sharepoint.transputs.DownloadResponse;
import com.xcase.sharepoint.transputs.ExportTagsResponse;
import com.xcase.sharepoint.transputs.GetAccessTokenResponse;
import com.xcase.sharepoint.transputs.GetAccountTreeResponse;
import com.xcase.sharepoint.transputs.GetAuthTokenResponse;
import com.xcase.sharepoint.transputs.GetAuthorizationResponse;
import com.xcase.sharepoint.transputs.GetFileInfoResponse;
import com.xcase.sharepoint.transputs.GetFolderInfoResponse;
import com.xcase.sharepoint.transputs.GetFriendsResponse;
import com.xcase.sharepoint.transputs.GetTicketResponse;
import com.xcase.sharepoint.transputs.LogoutResponse;
import com.xcase.sharepoint.transputs.MoveResponse;
import com.xcase.sharepoint.transputs.PrivateShareResponse;
import com.xcase.sharepoint.transputs.PublicShareResponse;
import com.xcase.sharepoint.transputs.PublicUnshareResponse;
import com.xcase.sharepoint.transputs.RefreshAccessTokenResponse;
import com.xcase.sharepoint.transputs.RegisterNewUserResponse;
import com.xcase.sharepoint.transputs.RenameResponse;
import com.xcase.sharepoint.transputs.RequestFriendsResponse;
import com.xcase.sharepoint.transputs.SetDescriptionResponse;
import com.xcase.sharepoint.transputs.UploadResponse;
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailResponse;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SharepointResponseFactory extends BaseSharepointFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddToMySharepointResponse createAddToMySharepointResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.AddToMySharepointResponse");
        return (AddToMySharepointResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static AddToTagResponse createAddToTagResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.AddToTagResponse");
        return (AddToTagResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateFolderResponse createCreateFolderResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.CreateFolderResponse");
        return (CreateFolderResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteResponse createDeleteResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.DeleteResponse");
        return (DeleteResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DownloadResponse createDownloadResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.DownloadResponse");
        return (DownloadResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static ExportTagsResponse createExportTagsResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.ExportTagsResponse");
        return (ExportTagsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccountTreeResponse createGetAccountTreeResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetAccountTreeResponse");
        return (GetAccountTreeResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAuthorizationResponse createGetAuthorizationResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetAuthorizationResponse");
        return (GetAuthorizationResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAuthTokenResponse createGetAuthTokenResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetAuthTokenResponse");
        return (GetAuthTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFileInfoResponse createGetFileInfoResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetFileInfoResponse");
        return (GetFileInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFolderInfoResponse createGetFolderInfoResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetFolderInfoResponse");
        return (GetFolderInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFriendsResponse createGetFriendsResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetFriendsResponse");
        return (GetFriendsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetTicketResponse createGetTicketResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetTicketResponse");
        return (GetTicketResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static LogoutResponse createLogoutResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.LogoutResponse");
        return (LogoutResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static MoveResponse createMoveResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.MoveResponse");
        return (MoveResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static PrivateShareResponse createPrivateShareResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.PrivateShareResponse");
        return (PrivateShareResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static PublicShareResponse createPublicShareResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.PublicShareResponse");
        return (PublicShareResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static PublicUnshareResponse createPublicUnshareResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.PublicUnshareResponse");
        return (PublicUnshareResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RefreshAccessTokenResponse createRefreshAccessTokenResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.RefreshAccessTokenResponse");
        return (RefreshAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RegisterNewUserResponse createRegisterNewUserResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.RegisterNewUserResponse");
        return (RegisterNewUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RenameResponse createRenameResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.RenameResponse");
        return (RenameResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RequestFriendsResponse createRequestFriendsResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.RequestFriendsResponse");
        return (RequestFriendsResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SetDescriptionResponse createSetDescriptionResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.SetDescriptionResponse");
        return (SetDescriptionResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UploadResponse createUploadResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.UploadResponse");
        return (UploadResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static VerifyRegistrationEmailResponse createVerifyRegistrationEmailResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.VerifyRegistrationEmailResponse");
        return (VerifyRegistrationEmailResponse) obj;
    }
}
