/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce;

import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.impl.simple.methods.*;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.*;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SimpleSalesforceImpl implements SalesforceExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public SalesforceConfigurationManager LocalConfigurationManager = SalesforceConfigurationManager.getConfigurationManager();

    /**
     * Salesforce action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
    
    /**
     * Salesforce action implementation.
     */
    private GetUserMethod getUserMethod = new GetUserMethod();

    /**
     * Salesforce action implementation.
     */
    private RefreshAccessTokenMethod refreshAccessTokenMethod = new RefreshAccessTokenMethod();

    /**
     * Salesforce action implementation.
     */
    private CreateAccountMethod createAccountMethod = new CreateAccountMethod();

    /**
     * Salesforce action implementation.
     */
    private CreateRecordMethod createRecordMethod = new CreateRecordMethod();

    /**
     * Salesforce action implementation.
     */
    private DeleteAccountMethod deleteAccountMethod = new DeleteAccountMethod();

    /**
     * Salesforce action implementation.
     */
    private DeleteRecordMethod deleteRecordMethod = new DeleteRecordMethod();

    /**
     * Salesforce action implementation.
     */
    private GetAccountMethod getAccountMethod = new GetAccountMethod();

    /**
     * Salesforce action implementation.
     */
    private GetRecordMethod getRecordMethod = new GetRecordMethod();

    /**
     * Salesforce action implementation.
     */
    private QueryRecordMethod queryRecordMethod = new QueryRecordMethod();
    
    /**
     * Salesforce action implementation.
     */
    private RevokeAccessTokenMethod revokeAccessTokenMethod = new RevokeAccessTokenMethod();

    /**
     * Salesforce action implementation.
     */
    private SearchAccountMethod searchAccountMethod = new SearchAccountMethod();

    /**
     * Salesforce action implementation.
     */
    private SearchRecordMethod searchRecordMethod = new SearchRecordMethod();

    /**
     * Salesforce action implementation.
     */
    private UpdateRecordMethod updateRecordMethod = new UpdateRecordMethod();

    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SalesforceException {
        return this.getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }
    
    @Override
    public GetUserResponse getUser(GetUserRequest getUserRequest) throws IOException, SalesforceException {
        return this.getUserMethod.getUser(getUserRequest);
    }

    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) throws IOException, SalesforceException {
        return this.refreshAccessTokenMethod.refreshAccessToken(refreshAccessTokenRequest);
    }

    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws IOException, SalesforceException {
        return this.createAccountMethod.createAccount(createAccountRequest);
    }

    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) throws IOException, SalesforceException {
        return this.createRecordMethod.createRecord(createRecordRequest);
    }

    public DeleteAccountResponse deleteAccount(DeleteAccountRequest deleteAccountRequest) throws IOException, SalesforceException {
        return this.deleteAccountMethod.deleteAccount(deleteAccountRequest);
    }

    public DeleteRecordResponse deleteRecord(DeleteRecordRequest deleteRecordRequest) throws IOException, SalesforceException {
        return this.deleteRecordMethod.deleteRecord(deleteRecordRequest);
    }

    public GetAccountResponse getAccount(GetAccountRequest getAccountRequest) throws IOException, SalesforceException {
        return this.getAccountMethod.getAccount(getAccountRequest);
    }

    public GetRecordResponse getRecord(GetRecordRequest getRecordRequest) throws IOException, SalesforceException {
        return this.getRecordMethod.getRecord(getRecordRequest);
    }

    public QueryRecordResponse queryRecord(QueryRecordRequest queryRecordRequest) throws IOException, SalesforceException {
        return this.queryRecordMethod.queryRecord(queryRecordRequest);
    }
    
    @Override
    public RevokeAccessTokenResponse revokeAccessToken(RevokeAccessTokenRequest revokeAcessTokenRequest) {
        return this.revokeAccessTokenMethod.revokeAccessToken(revokeAcessTokenRequest);
    }

    public SearchAccountResponse searchAccount(SearchAccountRequest searchAccountRequest) throws IOException, SalesforceException {
        return this.searchAccountMethod.searchAccount(searchAccountRequest);
    }

    public SearchRecordResponse searchRecord(SearchRecordRequest searchRecordRequest) throws IOException, SalesforceException {
        return this.searchRecordMethod.searchRecord(searchRecordRequest);
    }

    public UpdateRecordResponse updateRecord(UpdateRecordRequest updateRecordRequest) throws IOException, SalesforceException {
        return this.updateRecordMethod.updateRecord(updateRecordRequest);
    }
}
