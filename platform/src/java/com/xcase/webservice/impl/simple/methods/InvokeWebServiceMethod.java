/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.impl.simple.methods;

import com.xcase.webservice.objects.WebServiceException;
import com.xcase.webservice.WebServiceStub;
import com.xcase.webservice.factories.WebServiceResponseFactory;
import com.xcase.webservice.transputs.InvokeWebServiceRequest;
import com.xcase.webservice.transputs.InvokeWebServiceResponse;
import java.io.*;
import java.lang.invoke.*;
import java.security.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.axis2.transport.http.security.SSLProtocolSocketFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class InvokeWebServiceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method invokes an IB Web service.
     *
     * @param invokeWebServiceRequest request
     * @return response
     * @throws IOException io exception
     * @throws Exception e exception
     */
    public InvokeWebServiceResponse invokeWebService(InvokeWebServiceRequest invokeWebServiceRequest) throws IOException, WebServiceException {
        LOGGER.debug("starting invokeWebServiceRequest()");
        InvokeWebServiceResponse invokeWebServiceResponse = WebServiceResponseFactory.createInvokeWebServiceResponse();
        String endpoint = invokeWebServiceRequest.getEndpoint();
        LOGGER.debug("endpoint is " + endpoint);
        String sslProtocol = invokeWebServiceRequest.getProtocol();
        LOGGER.debug("sslProtocol is " + sslProtocol);
        if (sslProtocol == null || sslProtocol.isEmpty()) {
            sslProtocol = "TLSv1.2";
        }

        String username = invokeWebServiceRequest.getUsername();
        LOGGER.debug("username is " + username);
        if (username == null || username.isEmpty()) {
            username = "admin";
        }

        LOGGER.debug("username is " + username);
        String password = invokeWebServiceRequest.getPassword();
        if (password == null) {
            password = "";
        }

        LOGGER.debug("password is " + password);
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };
            WebServiceStub webServiceStub = null;
            if (endpoint != null && !endpoint.isEmpty()) {
                webServiceStub = new WebServiceStub(endpoint);
            } else {
                webServiceStub = new WebServiceStub();
            }


            SSLContext sslContext = SSLContext.getInstance(sslProtocol);
            LOGGER.debug("created sslContext");
            sslContext.init(null, trustAllCerts, null);
            LOGGER.debug("initialized sslContext");
            ProtocolSocketFactory sslProtocolSocketFactory = new SSLProtocolSocketFactory(sslContext);
            Protocol protocol = new Protocol("https", sslProtocolSocketFactory, 443);
            LOGGER.debug("created protocol");
            webServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, protocol);
            LOGGER.debug("got service client");
            WebServiceStub.Login login = new WebServiceStub.Login();
            login.setUsername(username);
            login.setPassword(password);
            LOGGER.debug("about to log in");
            WebServiceStub.LoginResponse loginResponse = webServiceStub.login(login);
            LOGGER.debug("logged in");
            WebServiceStub.InvokeSyncWebService invokeSyncWebService = new WebServiceStub.InvokeSyncWebService();
            LOGGER.debug("invoked Web service");
            invokeSyncWebService.setSessionID(loginResponse.getSessionID());
            LOGGER.debug("about to invoke Web service");
            WebServiceStub.InvokeSyncWebServiceResponse invokeSyncWebServiceResponse = webServiceStub.invokeSyncWebService(invokeSyncWebService);
            LOGGER.debug("invoked Web service");
            LOGGER.debug(invokeSyncWebServiceResponse.getOutput().getOutput());
            invokeWebServiceResponse.setOutput(invokeSyncWebServiceResponse.getOutput());
        } catch (NoSuchAlgorithmException nsae) {
            LOGGER.error("catching NoSuchAlgorithmException exception " + nsae.getMessage());
        } catch (Exception e) {
            LOGGER.error("catching Exception exception " + e.getMessage());
        }

        return invokeWebServiceResponse;
    }
}
