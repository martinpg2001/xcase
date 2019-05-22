package com.xcase.intapp.document.transputs;

public interface RenderDocumentResponse extends DocumentResponse {
    byte[] getBytes();
    
    void setBytes(byte[] byteArray);

}
