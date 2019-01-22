/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

/**
 *
 * @author martin
 */
public class CompareResult {
    private boolean result;
    private String message;

    public CompareResult(String message) {
        this.result = true;
        this.message = message;
    }

    public CompareResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    /**
     * @return the result
     */
    public boolean getResult() {
        return this.result;
    }

    /**
     * 
     * @param result 
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 
     * @param message 
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
