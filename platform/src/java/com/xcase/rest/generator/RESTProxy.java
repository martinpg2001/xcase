/**
 * Copyright 2019 Xcase All rights reserved.
 */
package com.xcase.rest.generator;

import java.net.URL;

/**
 *
 * @author martin
 */
public abstract class RESTProxy {
    public String _password = "";
    public String _tenantId = "";
    public String _username = "Admin";
    public String _redirectURL = "";
    public String token;
    public URL _baseUrl;
}
