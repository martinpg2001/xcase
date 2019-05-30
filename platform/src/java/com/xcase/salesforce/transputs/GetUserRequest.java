package com.xcase.salesforce.transputs;

public interface GetUserRequest extends SalesforceRequest {

    void setUserId(String userId);

    String getUserId();

}
