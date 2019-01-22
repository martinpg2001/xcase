package com.xcase.rest.generator.swagger;

import com.xcase.rest.generator.RESTServiceDefinition;
import com.xcase.rest.generator.RESTApiProxySettingsEndpoint;
import java.lang.invoke.*;
import java.net.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class IntegrateSwaggerApplication {
   /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        try {
            IntegrateSwaggerProxy swaggerProxy = new IntegrateSwaggerProxy(new URL("https://nightlya/"), "e8beedd7096bcce19845edf050b449b7");
            LOGGER.debug("created swaggerProxy");
            swaggerProxy.buildHttpClient();
            LOGGER.debug("built HttpClient");
            String swaggerDocument = swaggerProxy.getSwaggerDocument();
            LOGGER.debug("swaggerDocument is " + swaggerDocument);
            String packageName = swaggerProxy.getClass().getPackage().getName();
            LOGGER.debug("packageName is " + packageName);
            SwaggerJavaProxyGenerator swaggerProxyGenerator = new SwaggerJavaProxyGenerator(packageName);
            RESTApiProxySettingsEndpoint swaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndpoint("Java", swaggerProxy.getClass().getSimpleName());
            swaggerApiProxySettingsEndPoint.Url = "https://nightlya/api/";
            RESTApiProxySettingsEndpoint[] endpoints = new RESTApiProxySettingsEndpoint[] { swaggerApiProxySettingsEndPoint };
            RESTServiceDefinition swaggerServiceDefinition = swaggerProxyGenerator.generateSourceString(swaggerApiProxySettingsEndPoint, swaggerDocument, "Admin", "pass2app", "stage1");
            LOGGER.debug("swaggerServiceDefinition EndPoint is " + swaggerServiceDefinition.getEndPoint());
        } catch (Exception e) {
            LOGGER.warn("exception generating from Swagger document: " + e.getMessage());
        }
    }
}
