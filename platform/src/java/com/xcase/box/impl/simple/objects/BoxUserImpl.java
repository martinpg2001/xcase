/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.objects;

import com.xcase.box.objects.BoxUser;

/**
 * @author martin
 *
 */
public class BoxUserImpl implements BoxUser {

    /**
     * login account id.
     */
    private String login;

    /**
     * name.
     */
    private String name;

    /**
     * email address.
     */
    private String email;

    /**
     * access id.
     */
    private String accessId;

    /**
     * user id.
     */
    private String userId;

    /**
     * space amount.
     */
    private long spaceAmount;

    /**
     * space used.
     */
    private long spaceUsed;

    /**
     * @return the login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the accessId
     */
    public String getAccessId() {
        return this.accessId;
    }

    /**
     * @param accessId the accessId to set
     */
    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the spaceAmount
     */
    public long getSpaceAmount() {
        return this.spaceAmount;
    }

    /**
     * @param spaceAmount the spaceAmount to set
     */
    public void setSpaceAmount(long spaceAmount) {
        this.spaceAmount = spaceAmount;
    }

    /**
     * @return the spaceUsed
     */
    public long getSpaceUsed() {
        return this.spaceUsed;
    }

    /**
     * @param spaceUsed the spaceUsed to set
     */
    public void setSpaceUsed(long spaceUsed) {
        this.spaceUsed = spaceUsed;
    }
}
