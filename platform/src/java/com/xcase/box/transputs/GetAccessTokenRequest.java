/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface GetAccessTokenRequest extends BoxRequest {

    /**
     * @return the authorizationCode
     */
    public String getAuthorizationCode();

    /**
     * @param authorizationCode the authorizationCode to set
     */
    public void setAuthorizationCode(String authorizationCode);

    /**
     * @return the clientSecret
     */
    public String getClientSecret();

    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(String clientSecret);

    /**
     * @return the isEnterprise
     */
    public boolean getIsEnterprise();

    /**
     * @param isEnterprise the isEnterprise to set
     */
    public void setIsEnterprise(boolean isEnterprise);

    /**
     * @return the ticket
     */
    public String getTicket();

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket);
}
