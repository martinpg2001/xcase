/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.GetFriendsResponse;
import java.util.List;

/**
 * @author martin
 *
 */
public class GetFriendsResponseImpl extends BoxResponseImpl implements
        GetFriendsResponse {

    /**
     * friend list, BoxFriend object list.
     */
    private List friendList;

    /**
     * base64 encoded string of XML.
     */
    private String encodedFriends;

    /**
     * @return the friendList
     */
    public List getFriendList() {
        return this.friendList;
    }

    /**
     * @param friendList the friendList to set
     */
    public void setFriendList(List friendList) {
        this.friendList = friendList;
    }

    /**
     * @return the encodedFriends
     */
    public String getEncodedFriends() {
        return this.encodedFriends;
    }

    /**
     * @param encodedFriends the encodedFriends to set
     */
    public void setEncodedFriends(String encodedFriends) {
        this.encodedFriends = encodedFriends;
    }
}
