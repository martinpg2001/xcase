/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.InvokeAdvancedResponse;

/**
 *
 * @author martinpg
 */
public class InvokeAdvancedResponseImpl extends MSGraphResponseImpl implements InvokeAdvancedResponse {
    private String responseEntityString;
    
    public String getResponseEntityString() {
        return this.responseEntityString;
    }
    
    public void setResponseEntityString(String responseEntityString) {
        this.responseEntityString = responseEntityString;
    }   
}
