/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

/**
 *
 * @author martinpg
 */
public interface InvokeAdvancedResponse extends MSGraphResponse {
    String getResponseEntityString();
    void setResponseEntityString(String responseEntityString);
}
