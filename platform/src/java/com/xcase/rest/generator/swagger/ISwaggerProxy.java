/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.rest.generator.swagger;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import org.apache.http.Header;

/**
 *
 * @author martin
 */
public interface ISwaggerProxy {
    void getAuthenticationToken(CommonHTTPManager httpClient);
    Header[] setHeaders();     
}
