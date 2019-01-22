/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.MSGraphQueryRequest;

/**
 *
 * @author martinpg
 */
public class MSGraphQueryRequestImpl extends MSGraphRequestImpl implements MSGraphQueryRequest {
    private String search;
    private String select;
    private Integer skip;
    private Integer top;
    
    public String getSearch() {
        return this.search;
    }
    
    public String getSelect() {
        return this.select;
    }
    
    public Integer getSkip() {
        return this.skip;
    }
    
    public Integer getTop() {
        return this.top;
    }
    
    public void setSearch(String search) {
        this.search = search;
    }
    
    public void setSelect(String select) {
        this.select = select;
    } 
    
    public void setSkip(Integer skip) {
        this.skip = skip;
    }
    
    public void setTop(Integer top) {
        this.top = top;
    }    
}
