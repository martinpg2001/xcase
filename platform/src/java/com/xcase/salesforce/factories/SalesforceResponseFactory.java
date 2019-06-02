/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.factories;

import com.xcase.salesforce.transputs.*;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SalesforceResponseFactory extends BaseSalesforceFactory {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static RefreshAccessTokenResponse createRefreshAccessTokenResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.RefreshAccessTokenResponse");
        return (RefreshAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateAccountResponse createCreateAccountResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.CreateAccountResponse");
        return (CreateAccountResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static CreateRecordResponse createCreateRecordResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.CreateRecordResponse");
        return (CreateRecordResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteAccountResponse createDeleteAccountResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.DeleteAccountResponse");
        return (DeleteAccountResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteRecordResponse createDeleteRecordResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.DeleteRecordResponse");
        return (DeleteRecordResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccountResponse createGetAccountResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.GetAccountResponse");
        return (GetAccountResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetRecordResponse createGetRecordResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.GetRecordResponse");
        return (GetRecordResponse) obj;
    }
    
    /**
     * create response object.
     *
     * @return response object
     */
    public static GetUserResponse createGetUserResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.GetUserResponse");
        return (GetUserResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static QueryRecordResponse createQueryRecordResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.QueryRecordResponse");
        return (QueryRecordResponse) obj;
    }
    
    public static RevokeAccessTokenResponse createRevokeAccessTokenResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.RevokeAccessTokenResponse");
        return (RevokeAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SearchAccountResponse createSearchAccountResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.SearchAccountResponse");
        return (SearchAccountResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static SearchRecordResponse createSearchRecordResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.SearchRecordResponse");
        return (SearchRecordResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UpdateRecordResponse createUpdateRecordResponse() {
        Object obj = newInstanceOf("salesforce.config.responsefactory.UpdateRecordResponse");
        return (UpdateRecordResponse) obj;
    }
}
