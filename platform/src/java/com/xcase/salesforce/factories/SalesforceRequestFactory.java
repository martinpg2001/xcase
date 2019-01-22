/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.factories;

import com.xcase.salesforce.transputs.CreateAccountRequest;
import com.xcase.salesforce.transputs.CreateRecordRequest;
import com.xcase.salesforce.transputs.DeleteAccountRequest;
import com.xcase.salesforce.transputs.DeleteRecordRequest;
import com.xcase.salesforce.transputs.GetAccessTokenRequest;
import com.xcase.salesforce.transputs.GetAccountRequest;
import com.xcase.salesforce.transputs.GetRecordRequest;
import com.xcase.salesforce.transputs.QueryRecordRequest;
import com.xcase.salesforce.transputs.RefreshAccessTokenRequest;
import com.xcase.salesforce.transputs.SalesforceRecordRequest;
import com.xcase.salesforce.transputs.SalesforceRequest;
import com.xcase.salesforce.transputs.SearchAccountRequest;
import com.xcase.salesforce.transputs.SearchRecordRequest;
import com.xcase.salesforce.transputs.UpdateRecordRequest;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SalesforceRequestFactory extends BaseSalesforceFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAccessTokenRequest createGetAccessTokenRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetAccessTokenRequest createGetAccessTokenRequest(String clientId, String clientSecret, String refreshToken) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetAccessTokenRequest");
        ((SalesforceRequest) obj).setClientId(clientId);
        ((GetAccessTokenRequest) obj).setClientSecret(clientSecret);
        ((GetAccessTokenRequest) obj).setRefreshToken(refreshToken);
        return (GetAccessTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RefreshAccessTokenRequest createRefreshAccessTokenRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.RefreshAccessTokenRequest");
        return (RefreshAccessTokenRequest) obj;
    }

    /**
     * create request object.
     *
     * @return request object
     */
    public static RefreshAccessTokenRequest createRefreshAccessTokenRequest(String clientId, String clientSecret, String refreshToken) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.RefreshAccessTokenRequest");
        ((SalesforceRequest) obj).setClientId(clientId);
        ((RefreshAccessTokenRequest) obj).setClientSecret(clientSecret);
        ((RefreshAccessTokenRequest) obj).setRefreshToken(refreshToken);
        return (RefreshAccessTokenRequest) obj;
    }

    public static CreateAccountRequest createCreateAccountRequest(String accessToken, String accountName) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.CreateAccountRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((CreateAccountRequest) obj).setAccountName(accountName);
        return (CreateAccountRequest) obj;
    }

    public static CreateRecordRequest createCreateRecordRequest(String accessToken, String recordType, String recordBody) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.CreateRecordRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((SalesforceRecordRequest) obj).setRecordType(recordType);
        ((CreateRecordRequest) obj).setRecordBody(recordBody);
        return (CreateRecordRequest) obj;
    }

    public static GetAccountRequest createGetAccountRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetAccountRequest");
        return (GetAccountRequest) obj;
    }

    public static GetAccountRequest createGetAccountRequest(String accessToken, String accountId) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetAccountRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((GetAccountRequest) obj).setAccountId(accountId);
        return (GetAccountRequest) obj;
    }

    public static GetRecordRequest createGetRecordRequest(String accessToken, String recordType, String recordId) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetRecordRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((SalesforceRecordRequest) obj).setRecordType(recordType);
        ((GetRecordRequest) obj).setRecordId(recordId);
        return (GetRecordRequest) obj;
    }

    public static DeleteAccountRequest createDeleteAccountRequest(String accessToken, String accountId) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.DeleteAccountRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((DeleteAccountRequest) obj).setAccountId(accountId);
        return (DeleteAccountRequest) obj;
    }

    public static DeleteRecordRequest createDeleteRecordRequest(String accessToken, String recordType, String recordId) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.DeleteRecordRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((SalesforceRecordRequest) obj).setRecordType(recordType);
        ((DeleteRecordRequest) obj).setRecordId(recordId);
        return (DeleteRecordRequest) obj;
    }

    public static QueryRecordRequest createQueryRecordRequest(String accessToken, String queryString) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.QueryRecordRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((QueryRecordRequest) obj).setQueryString(queryString);
        return (QueryRecordRequest) obj;
    }

    public static SearchAccountRequest createSearchAccountRequest(String accessToken, String searchString) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.SearchAccountRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((SearchAccountRequest) obj).setSearchString(searchString);
        return (SearchAccountRequest) obj;
    }

    public static SearchRecordRequest createSearchRecordRequest(String accessToken, String searchString) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.SearchRecordRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((SearchRecordRequest) obj).setSearchString(searchString);
        return (SearchRecordRequest) obj;
    }

    public static UpdateRecordRequest createUpdateRecordRequest(String accessToken, String recordType, String recordId, String recordBody) {
        Object obj = newInstanceOf("salesforce.config.requestfactory.UpdateRecordRequest");
        ((SalesforceRequest) obj).setAccessToken(accessToken);
        ((SalesforceRecordRequest) obj).setRecordType(recordType);
        ((UpdateRecordRequest) obj).setRecordId(recordId);
        ((UpdateRecordRequest) obj).setRecordBody(recordBody);
        return (UpdateRecordRequest) obj;
    }
}
