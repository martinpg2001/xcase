/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.objects;

import com.xcase.box.objects.BoxAccessibleBy;

/**
 *
 * @author martin
 */
public class BoxAccessibleByImpl implements BoxAccessibleBy {

    /**
     * id.
     */
    private String id;

    /**
     * role.
     */
    private String type;

    /**
     * name.
     */
    private String name;

    /**
     * login.
     */
    private String login;

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
}
