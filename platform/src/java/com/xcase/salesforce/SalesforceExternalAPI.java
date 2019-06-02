/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.salesforce;

import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.*;
import java.io.IOException;

/**
 * @author martin
 *
 */
public interface SalesforceExternalAPI {

    /**
     * This method is used to get an access token.
     *
     * @param getAccessTokenRequest
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SalesforceException;
    
    /**
     * This method is used to get users.
     *
     * @param getUserRequest
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    GetUserResponse getUser(GetUserRequest getUserRequest) throws IOException, SalesforceException;

    /**
     * This method is used to create an account.
     *
     * @param createAccountRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws IOException, SalesforceException;

    /**
     * This method is used to create a record.
     *
     * @param createRecordRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) throws IOException, SalesforceException;

    /**
     * This method is used to delete an account.
     *
     * @param deleteAccountRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    DeleteAccountResponse deleteAccount(DeleteAccountRequest deleteAccountRequest) throws IOException, SalesforceException;

    /**
     * This method is used to delete a record.
     *
     * @param deleteRecordRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    DeleteRecordResponse deleteRecord(DeleteRecordRequest deleteRecordRequest) throws IOException, SalesforceException;

    /**
     * This method is used to get an account.
     *
     * @param getAccountRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    GetAccountResponse getAccount(GetAccountRequest getAccountRequest) throws IOException, SalesforceException;

    /**
     * This method is used to get an account.
     *
     * @param getRecordRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    GetRecordResponse getRecord(GetRecordRequest getRecordRequest) throws IOException, SalesforceException;

    /**
     * This method is used to query records.
     *
     * @param queryRecordRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    QueryRecordResponse queryRecord(QueryRecordRequest queryRecordRequest) throws IOException, SalesforceException;
    
    /**
     * This method is used to refresh an access token.
     *
     * @param refreshAccessTokenRequest
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) throws IOException, SalesforceException;
    
    /**
     * This method is used to revoke access tokens.
     *
     * @param revokeAcessTokenRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */    
    RevokeAccessTokenResponse revokeAccessToken(RevokeAccessTokenRequest revokeAcessTokenRequest) throws IOException, SalesforceException;

    /**
     * This method is used to search accounts.
     *
     * @param searchAccountRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    SearchAccountResponse searchAccount(SearchAccountRequest searchAccountRequest) throws IOException, SalesforceException;

    /**
     * This method is used to search records.
     *
     * @param searchRecordRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    SearchRecordResponse searchRecord(SearchRecordRequest searchRecordRequest) throws IOException, SalesforceException;

    /**
     * This method is used to update records.
     *
     * @param updateRecordRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SalesforceException Salesforce exception
     */
    UpdateRecordResponse updateRecord(UpdateRecordRequest updateRecordRequest) throws IOException, SalesforceException;
}
