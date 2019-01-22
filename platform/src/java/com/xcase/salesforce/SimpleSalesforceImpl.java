/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce;

import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.impl.simple.methods.CreateAccountMethod;
import com.xcase.salesforce.impl.simple.methods.CreateRecordMethod;
import com.xcase.salesforce.impl.simple.methods.DeleteAccountMethod;
import com.xcase.salesforce.impl.simple.methods.DeleteRecordMethod;
import com.xcase.salesforce.impl.simple.methods.GetAccessTokenMethod;
import com.xcase.salesforce.impl.simple.methods.GetAccountMethod;
import com.xcase.salesforce.impl.simple.methods.GetRecordMethod;
import com.xcase.salesforce.impl.simple.methods.QueryRecordMethod;
import com.xcase.salesforce.impl.simple.methods.RefreshAccessTokenMethod;
import com.xcase.salesforce.impl.simple.methods.SearchAccountMethod;
import com.xcase.salesforce.impl.simple.methods.SearchRecordMethod;
import com.xcase.salesforce.impl.simple.methods.UpdateRecordMethod;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.CreateAccountRequest;
import com.xcase.salesforce.transputs.CreateAccountResponse;
import com.xcase.salesforce.transputs.CreateRecordRequest;
import com.xcase.salesforce.transputs.CreateRecordResponse;
import com.xcase.salesforce.transputs.DeleteAccountRequest;
import com.xcase.salesforce.transputs.DeleteAccountResponse;
import com.xcase.salesforce.transputs.DeleteRecordRequest;
import com.xcase.salesforce.transputs.DeleteRecordResponse;
import com.xcase.salesforce.transputs.GetAccessTokenRequest;
import com.xcase.salesforce.transputs.GetAccessTokenResponse;
import com.xcase.salesforce.transputs.GetAccountRequest;
import com.xcase.salesforce.transputs.GetAccountResponse;
import com.xcase.salesforce.transputs.GetRecordRequest;
import com.xcase.salesforce.transputs.GetRecordResponse;
import com.xcase.salesforce.transputs.QueryRecordRequest;
import com.xcase.salesforce.transputs.QueryRecordResponse;
import com.xcase.salesforce.transputs.RefreshAccessTokenRequest;
import com.xcase.salesforce.transputs.RefreshAccessTokenResponse;
import com.xcase.salesforce.transputs.SearchAccountRequest;
import com.xcase.salesforce.transputs.SearchAccountResponse;
import com.xcase.salesforce.transputs.SearchRecordRequest;
import com.xcase.salesforce.transputs.SearchRecordResponse;
import com.xcase.salesforce.transputs.UpdateRecordRequest;
import com.xcase.salesforce.transputs.UpdateRecordResponse;
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
