package com.xcase.rest.generator;

import com.xcase.rest.generator.IAPIProxySettingsEndpoint;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IProxyGenerator
{
    RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint[] endpoints) throws Exception;
    RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint endpoint, String document, String username, String password, String tenant) throws Exception;
    RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint endpoint, String document) throws Exception;
    RESTServiceDefinition generateSourceString(String document) throws Exception;
    List<RESTServiceDefinition> generateSourceStringForRestServiceDefinitionList(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, String swaggerDocument, String username, String password, String tenant) throws Exception;
}
