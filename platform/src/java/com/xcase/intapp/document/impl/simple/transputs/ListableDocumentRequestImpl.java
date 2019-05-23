package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.ListableDocumentRequest;

public class ListableDocumentRequestImpl extends DocumentRequestImpl implements ListableDocumentRequest {
    private int limit;
    private int skip;
    
    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getSkip() {
        return skip;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void setSkip(int skip) {
        this.skip = skip;
    }

}
