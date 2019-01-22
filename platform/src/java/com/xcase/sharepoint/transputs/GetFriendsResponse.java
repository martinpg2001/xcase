/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

import java.util.List;

/**
 *
 * @author martin
 */
public interface GetFriendsResponse extends SharepointResponse {

    /**
     * @return the friendList
     */
    public List getFriendList();

    /**
     * @param friendList the friendList to set
     */
    public void setFriendList(List friendList);

    /**
     * @return the encodedFriends
     */
    public String getEncodedFriends();

    /**
     * @param encodedFriends the encodedFriends to set
     */
    public void setEncodedFriends(String encodedFriends);
}
