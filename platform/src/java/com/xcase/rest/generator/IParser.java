package com.xcase.rest.generator;

import com.xcase.rest.generator.IAPIProxySettingsEndpoint;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.rest.generator.ProxyDefinition;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IParser { 
    ProxyDefinition parseDoc(String document, IAPIProxySettingsEndpoint endpoint) throws Exception;
    RESTServiceDefinition parseDocForRestServiceDefinition(String document, IAPIProxySettingsEndpoint endpoint, String objectsPackageName) throws Exception;
}
