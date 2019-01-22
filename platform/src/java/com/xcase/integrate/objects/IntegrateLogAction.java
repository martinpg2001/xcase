/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.*;

/**
 *
 * @author martin
 */
public class IntegrateLogAction {

    public String actionName;

    public String actionType;

    public Integer duration;

    public Date eventTime;

    public Integer id;

    public String message;

    public String status;

    public String toString() {
        return actionName + ":" + actionType + ":" + duration + ":" + eventTime + ":" + id + ":" + message + ":" + status;
    }
}
