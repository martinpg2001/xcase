/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

import com.xcase.box.objects.BoxUser;

/**
 *
 * @author martin
 */
public interface GetAuthTokenResponse extends BoxResponse {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the user
     */
    public BoxUser getUser();

    /**
     * @param user the user to set
     */
    public void setUser(BoxUser user);
}
