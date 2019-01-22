/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice;

import java.lang.invoke.*;
import javax.net.ssl.*;
import org.apache.axis2.transport.http.*;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.axis2.transport.http.security.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class WebServiceClient {

    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        LOGGER.debug("starting main()");
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
            WebServiceStub webServiceStub = new WebServiceStub();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
            ProtocolSocketFactory sslProtocolSocketFactory = new SSLProtocolSocketFactory(sslContext);
            Protocol protocol = new Protocol("https", sslProtocolSocketFactory, 443);
            LOGGER.debug("created protocol");
            webServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, protocol);
            WebServiceStub.Login login = new WebServiceStub.Login();
            login.localUsername = "admin";
            login.localPassword = "";
            LOGGER.debug("about to login");
            WebServiceStub.LoginResponse loginResponse = webServiceStub.login(login);
            LOGGER.debug("logged in with session " + loginResponse.getSessionID());
            WebServiceStub.InvokeSyncWebService invokeSyncWebService = new WebServiceStub.InvokeSyncWebService();
            invokeSyncWebService.setSessionID(loginResponse.getSessionID());
            LOGGER.debug("about to invoke Web service");
            WebServiceStub.InvokeSyncWebServiceResponse invokeSyncWebServiceResponse = webServiceStub.invokeSyncWebService(invokeSyncWebService);
            LOGGER.debug("invoked Web service");
            LOGGER.debug(invokeSyncWebServiceResponse.getOutput().getOutput());
        } catch (Exception e) {
        }
    }
}
