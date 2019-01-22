/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface UpdateRuleResponse extends IntegrateResponse {

    public String getOperationMessage();

    public void setOperationMessage(String message);

    public String getOperationStatus();

    public void setOperationStatus(String status);

}
