package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.RenderDocumentResponse;

public class RenderDocumentResponseImpl extends DocumentResponseImpl implements RenderDocumentResponse {
    private byte[] entityBytes;
    
    @Override
    public byte[] getBytes() {
        return entityBytes;
    }

    @Override
    public void setBytes(byte[] entityBytes) {
        this.entityBytes = entityBytes;
    }

}
