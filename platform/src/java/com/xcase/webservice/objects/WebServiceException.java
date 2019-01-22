/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.objects;

/**
 *
 * @author martin
 */
public class WebServiceException extends Exception {

    /**
     * A wrapped <code>Throwable</code>.
     */
    private Throwable nestedException;

    /**
     * box.net status words.
     */
    private String status;

    /**
     *
     */
    public WebServiceException() {
        super("Error occurred when talk to Web service");
    }
}
