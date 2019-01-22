package com.xcase.rest.generator;

import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IServiceDefinition
{
    String getEndPoint();

    String[] getSourceStrings() throws Exception;

    HashMap<String, ProxyDefinition> getProxyClasses();
}
