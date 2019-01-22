package com.xcase.rest.generator.swagger;

import com.xcase.rest.generator.RESTServiceDefinition;
import com.xcase.rest.generator.RESTProxyGenerator;
import com.xcase.rest.generator.IAPIProxySettingsEndpoint;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.rest.generator.ClassDefinition;
import com.xcase.rest.generator.EnumDefinition;
import com.xcase.rest.generator.Operation;
import com.xcase.rest.generator.Parameter;
import com.xcase.rest.generator.ParameterIn;
import com.xcase.rest.generator.ProxyDefinition;
import com.xcase.rest.generator.RESTParser;
import com.xcase.rest.generator.TypeDefinition;
import java.io.File;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SwaggerProxyGenerator extends RESTProxyGenerator {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public abstract RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint[] endpoints) throws Exception;
    public abstract RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint endpoint, String document, String username, String password, String tenant) throws Exception;
    public abstract RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint endpoint, String document) throws Exception;
    public abstract RESTServiceDefinition generateSourceString(String document) throws Exception;
    public abstract List<RESTServiceDefinition> generateSourceStringForRestServiceDefinitionList(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, String swaggerDocument, String username, String password, String tenant) throws Exception;
}
