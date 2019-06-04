package com.xcase.common.impl.simple.core;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * This class is required to connect to a secure IMAP server.
 */
public class DummyTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] x509CertificateArray, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArray, String authType) {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
