package com.xcase.rest.generator;

import com.xcase.rest.generator.IAPIProxySettingsEndpoint;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RESTApiProxySettingsEndpoint implements IAPIProxySettingsEndpoint
{
    public String Id;
    public String Url;
    public String Namespace;
    public String Suffix;
    public String BaseProxyClass;
    public String ProxyConstructorSuffix;
    public boolean ParseOperationIdForProxyName;
    public boolean AppendAsyncToMethodName;

    public RESTApiProxySettingsEndpoint() {
        BaseProxyClass = "SwaggerProxy";
        Id = "SwaggerProxy";
        Namespace = "com.xcase.rest.generator.objects";
        ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
        ParseOperationIdForProxyName = true;
        AppendAsyncToMethodName = true;
    }

    public RESTApiProxySettingsEndpoint(String language, String baseProxyClass) {
        BaseProxyClass = baseProxyClass;
        Id = "RESTProxy";
        Namespace = "com.xcase.rest.generator.objects";
        switch (language) {
            case "CSharp":
                ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                ParseOperationIdForProxyName = true;
                AppendAsyncToMethodName = false;
                break;
            case "Java":
                //Namespace = "com.intapp.harness.actions.integrate.objects";
                ProxyConstructorSuffix = "(Uri baseUrl) extends base(baseUrl)";
                ParseOperationIdForProxyName = true;
                AppendAsyncToMethodName = false;
                break;
            default:
                ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                ParseOperationIdForProxyName = true;
                AppendAsyncToMethodName = false;
                break;
        }
    }

    public RESTApiProxySettingsEndpoint(String language) {
        BaseProxyClass = "OpenCloudSwaggerProxy";
        Id = "SwaggerProxy";
        Namespace = "com.intapp.open.actions.objects";
        switch (language) {
            case "CSharp":
                ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                ParseOperationIdForProxyName = true;
                AppendAsyncToMethodName = false;
                break;
            case "Java":
                ProxyConstructorSuffix = "(Uri baseUrl) extends base(baseUrl)";
                ParseOperationIdForProxyName = true;
                AppendAsyncToMethodName = false;
                break;
            default:
                ProxyConstructorSuffix = "(Uri baseUrl) : base(baseUrl)";
                ParseOperationIdForProxyName = true;
                AppendAsyncToMethodName = false;
                break;
        }
    }

    public String getBasePath() {
        /* TODO: work out what to do here. */
        return "";
    }

    public String getId() {
        return Id;
    }

    public String getNamespace() {
        return Namespace;
    }

    public String getBaseProxyClass() {
        return BaseProxyClass;
    }

    public String getProxyConstructorSuffix() {
        return ProxyConstructorSuffix;
    }

    public String getUrl() {
        return Url;
    }

    public boolean getAppendAsyncToMethodName() {
        return AppendAsyncToMethodName;
    }

    public boolean getParseOperationIdForProxyName() {
        return ParseOperationIdForProxyName;
    }

    @Override
    public String getHost() throws Exception {
        if (Url == null) {
            return null;
        }

        URI uri = new URI(Url);
        return uri.getAuthority();
    }

    public String getBaseBath() throws Exception {
        if (Url == null) {
            return null;
        }

        URI uri = new URI(Url);
        return uri.getPath();
    }
}
