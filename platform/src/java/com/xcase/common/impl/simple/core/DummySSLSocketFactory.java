package com.xcase.common.impl.simple.core;

import java.io.IOException;
import java.lang.invoke.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import javax.net.SocketFactory;
import javax.net.ssl.*;
import org.apache.logging.log4j.*;

/**
 * This class is required to connect to a secure IMAP server.
 */
public class DummySSLSocketFactory extends SSLSocketFactory {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static SocketFactory getDefault() {
        return new DummySSLSocketFactory();
    }

    private SSLSocketFactory factory;

    public DummySSLSocketFactory() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new DummyTrustManager()}, new java.security.SecureRandom());
            factory = sslcontext.getSocketFactory();
        } catch (Exception e) {
            LOGGER.warn("exception creating DummySSLSocketFactory: " + e.getMessage());
        }
    }
    
    public Socket createSocket() throws IOException {
        Socket socket;
        try {
            socket = factory.createSocket();
        } catch (SocketException se) {
            socket = new Socket();
        }
        
        return socket;
    }

    public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
        return factory.createSocket(socket, s, i, flag);
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int j) throws IOException {
        return factory.createSocket(inetAddress, i, inetAddress1, j);
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return factory.createSocket(inetAddress, i);
    }

    public Socket createSocket(String s, int i, InetAddress inetAddress, int j) throws IOException {
        return factory.createSocket(s, i, inetAddress, j);
    }

    public Socket createSocket(String s, int i) throws IOException {
        return factory.createSocket(s, i);
    }

    public String[] getDefaultCipherSuites() {
        return factory.getSupportedCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return factory.getSupportedCipherSuites();
    }
}