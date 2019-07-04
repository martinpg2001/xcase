package com.xcase.klearexpress.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHTTPManager;

public class BaseKlearExpressMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * core http manager.
     */
    protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
    
    public Header createContentTypeHeader() {
        return new BasicHeader("Content-Type", "application/json");
    }

    public Header createAccessTokenHeader(String accessToken) {
        return new BasicHeader("kxToken", accessToken);
    }
}
