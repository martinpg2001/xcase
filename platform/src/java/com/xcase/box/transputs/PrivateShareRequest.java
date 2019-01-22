/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface PrivateShareRequest extends BoxRequest {

    /**
     * @return the authToken
     */
    public String getAuthToken();

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken);

    /**
     * @return the target
     */
    public String getTarget();

    /**
     * @param target the target to set
     */
    public void setTarget(String target);

    /**
     * @return the targetId
     */
    public String getTargetId();

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(String targetId);

    /**
     * @return the emails
     */
    public String[] getEmails();

    /**
     * @param emails the emails to set
     */
    public void setEmails(String[] emails);

    /**
     * @return the message
     */
    public String getMessage();

    /**
     * @param message the message to set
     */
    public void setMessage(String message);

    /**
     * @return the nofity
     */
    public boolean isNofity();

    /**
     * @param nofity the nofity to set
     */
    public void setNofity(boolean nofity);
}
