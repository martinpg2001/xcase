/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface GetAccessTokenRequest extends SharepointRequest {

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
     * @return the serverUrl
     */
    public String getServerUrl();

    /**
     * @param serverUrl the serverUrl to set
     */
    public void setServerUrl(String serverUrl);

    /**
     * @return the site
     */
    public String getSite();

    /**
     * @param site the site to set
     */
    public void setSite(String site);

    /**
     * @return the ticket
     */
    public String getTicket();

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket);
}
