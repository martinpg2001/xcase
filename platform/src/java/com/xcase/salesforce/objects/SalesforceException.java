/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.objects;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author martin
 */
public class SalesforceException extends Exception {

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
    public SalesforceException() {
        super("Error occurred when talking to Salesforce.");
    }

    /**
     *
     * @param message exception message
     */
    public SalesforceException(String message) {
        super(message);
    }

    /**
     *
     * @param nestedException nested exception
     */
    public SalesforceException(Throwable nestedException) {
        super(nestedException.getMessage());
        this.nestedException = nestedException;
    }

    /**
     *
     * @param message exception message
     * @param nestedException nested exception
     */
    public SalesforceException(String message, Throwable nestedException) {
        super(message);
        this.nestedException = nestedException;
    }

    /**
     *
     * @return nested exception
     */
    public Throwable getNestedException() {
        return nestedException;
    }

    /**
     * @return exception message
     */
    public String getMessage() {
        if (nestedException != null) {
            return super.getMessage() + " Nested exception: "
                    + nestedException.getMessage();
        } else {
            return super.getMessage();
        }
    }

    /**
     *
     */
    public void printStackTrace() {

        if (nestedException != null) {
        }
    }

    /**
     * @param out print stream to be output
     */
    public void printStackTrace(PrintStream out) {
        super.printStackTrace(out);

        if (nestedException != null) {
            out.println("Nested exception: ");
            nestedException.printStackTrace(out);
        }
    }

    /**
     * @param writer writer to be output
     */
    public void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);

        if (nestedException != null) {
            writer.println("Nested exception: ");
            nestedException.printStackTrace(writer);
        }
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
