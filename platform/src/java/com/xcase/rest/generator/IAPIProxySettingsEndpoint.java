package com.xcase.rest.generator;

import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IAPIProxySettingsEndpoint {
    boolean getAppendAsyncToMethodName();

    String getBasePath();

    String getBaseProxyClass();

    String getHost() throws Exception;

    String getId();

    String getNamespace();

    boolean getParseOperationIdForProxyName();

    String getProxyConstructorSuffix();

    String getUrl();
}
