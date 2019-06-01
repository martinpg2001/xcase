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
        GetAccessTokenRequest request = createGetAccessTokenRequest();
        request.setClientId(clientId);
        request.setClientSecret(clientSecret);
        request.setRefreshToken(refreshToken);
        return request;
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
        RefreshAccessTokenRequest request = createRefreshAccessTokenRequest();
        request.setClientId(clientId);
        request.setClientSecret(clientSecret);
        request.setRefreshToken(refreshToken);
        return request;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateAccountRequest createCreateAccountRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.CreateAccountRequest");
        return (CreateAccountRequest) obj;
    }
    
    public static CreateAccountRequest createCreateAccountRequest(String accessToken) {
        CreateAccountRequest request = createCreateAccountRequest();
        request.setAccessToken(accessToken);
        return request;
    }

    public static CreateAccountRequest createCreateAccountRequest(String accessToken, String accountName) {
        CreateAccountRequest request = createCreateAccountRequest();
        request.setAccessToken(accessToken);
        request.setAccountName(accountName);
        return request;
    }
    
    /**
     * create request object.
     *
     * @return request object
     */
    public static CreateRecordRequest createCreateRecordRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.CreateRecordRequest");
        return (CreateRecordRequest) obj;
    }

    public static CreateRecordRequest createCreateRecordRequest(String accessToken, String recordType, String recordBody) {
        CreateRecordRequest request = createCreateRecordRequest();
        request.setAccessToken(accessToken);
        request.setRecordType(recordType);
        request.setRecordBody(recordBody);
        return request;
    }
    
    public static DeleteAccountRequest createDeleteAccountRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.DeleteAccountRequest");
        return (DeleteAccountRequest) obj;
    }     

    public static DeleteAccountRequest createDeleteAccountRequest(String accessToken, String accountId) {
        DeleteAccountRequest request = createDeleteAccountRequest();
        request.setAccessToken(accessToken);
        request.setAccountId(accountId);
        return request;
    }
    
    public static DeleteRecordRequest createDeleteRecordRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.DeleteRecordRequest");
        return (DeleteRecordRequest) obj;
    }     

    public static DeleteRecordRequest createDeleteRecordRequest(String accessToken, String recordType, String recordId) {
        DeleteRecordRequest request = createDeleteRecordRequest();
        request.setAccessToken(accessToken);
        request.setRecordType(recordType);
        request.setRecordId(recordId);
        return request;
    }

    public static GetAccountRequest createGetAccountRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetAccountRequest");
        return (GetAccountRequest) obj;
    }

    public static GetAccountRequest createGetAccountRequest(String accessToken, String accountId) {
        GetAccountRequest request = createGetAccountRequest();
        request.setAccessToken(accessToken);
        request.setAccountId(accountId);
        return request;
    }
    
    public static GetRecordRequest createGetRecordRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetRecordRequest");
        return (GetRecordRequest) obj;
    }
    
    public static GetRecordRequest createGetRecordRequest(String accessToken) {
        GetRecordRequest request = createGetRecordRequest();
        request.setAccessToken(accessToken);
        return request;
    }    

    public static GetRecordRequest createGetRecordRequest(String accessToken, String recordType, String recordId) {
        GetRecordRequest request = createGetRecordRequest();
        request.setAccessToken(accessToken);
        request.setRecordType(recordType);
        request.setRecordId(recordId);
        return request;
    }
    
    public static GetUserRequest createGetUserRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.GetUserRequest");
        return (GetUserRequest) obj;
    }

    public static GetUserRequest createGetUserRequest(String accessToken, String userId) {
        GetUserRequest request = createGetUserRequest();
        request.setAccessToken(accessToken);
        request.setUserId(userId);
        return request;
    }
    
    public static QueryRecordRequest createQueryRecordRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.QueryRecordRequest");
        return (QueryRecordRequest) obj;
    }    

    public static QueryRecordRequest createQueryRecordRequest(String accessToken, String queryString) {
        QueryRecordRequest request = createQueryRecordRequest();
        request.setAccessToken(accessToken);
        request.setQueryString(queryString);
        return request;
    }
    
    public static SearchAccountRequest createSearchAccountRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.SearchAccountRequest");
        return (SearchAccountRequest) obj;
    }     

    public static SearchAccountRequest createSearchAccountRequest(String accessToken, String searchString) {
        SearchAccountRequest request = createSearchAccountRequest();
        request.setAccessToken(accessToken);
        request.setSearchString(searchString);
        return request;
    }
    
    public static SearchRecordRequest createSearchRecordRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.SearchRecordRequest");
        return (SearchRecordRequest) obj;
    }     

    public static SearchRecordRequest createSearchRecordRequest(String accessToken, String searchString) {
        SearchRecordRequest request = createSearchRecordRequest();
        request.setAccessToken(accessToken);
        request.setSearchString(searchString);
        return request;
    }
    
    public static UpdateRecordRequest createUpdateRecordRequest() {
        Object obj = newInstanceOf("salesforce.config.requestfactory.UpdateRecordRequest");
        return (UpdateRecordRequest) obj;
    }

    public static UpdateRecordRequest createUpdateRecordRequest(String accessToken, String recordType, String recordId, String recordBody) {
        UpdateRecordRequest request = createUpdateRecordRequest();
        request.setAccessToken(accessToken);
        request.setRecordType(recordType);
        request.setRecordId(recordId);
        request.setRecordBody(recordBody);
        return request;
    }
}
