package com.xcase.rest.generator;

import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RESTApiProxySettings
{
    public String BaseUrl;
    public String ProxyOutputFile;
    public String WebApiAssembly;
    public String WebApiConfig;
    public RESTApiProxySettingsEndpoint[] EndPoints;
}
