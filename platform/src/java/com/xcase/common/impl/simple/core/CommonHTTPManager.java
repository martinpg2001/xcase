/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.utils.ConverterUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.*;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.*;
import org.dom4j.DocumentException;

/**
 * @author martin
 *
 */
public class CommonHTTPManager implements AutoCloseable {

    /**
     * log4j object.
     */
    static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * singleton instance.
     */
    private static CommonHTTPManager instance;
    //    /**
//     * Refresh the only one manager.
//     *
//     * @return CommonHTTPManager
//     */
//    public static CommonHTTPManager refreshCommonHTTPManager(boolean ssl) {
//        instance = new CommonHTTPManager(ssl);
//        return instance;
//    }

    /**
     * Refresh the only one manager.
     *
     * @return CommonHTTPManager
     */
    public static CommonHTTPManager refreshCommonHTTPManager() {
        instance = new CommonHTTPManager();
        return instance;
    }

    /**
     * Get the only one manager.
     *
     * @return CommonHTTPManager
     */
    public static CommonHTTPManager getCommonHTTPManager() {
//        LOGGER.debug("starting getCommonHTTPManager()");
        if (instance == null) {
            instance = new CommonHTTPManager();
        }

//        LOGGER.debug("about to return instance");
        return instance;
    }

    public static CommonHTTPManager getCommonHTTPManager(CommonHttpManagerConfig commonHttpManagerConfig) {
//        LOGGER.debug("starting getCommonHTTPManager()");
        if (instance == null) {
            instance = new CommonHTTPManager(commonHttpManagerConfig);
        }

//        LOGGER.debug("about to return instance");
        return instance;
    }

    /**
     * config properties.
     */
    private Properties config;

    /**
     * config properties.
     */
    private Properties localConfig;

    /**
     * only one instance in this application.
     */
    private HttpClient httpClient;

    /**
     * only one instance in this application.
     */
    private HttpClientBuilder httpClientBuilder;

    /**
     * only one instance in this application.
     */
    private HttpClientContext httpClientContext;

    /**
     * private constructor, singleton.
     */
    private CommonHTTPManager() {
//        LOGGER.debug("starting CommonHTTPManager() with no parameters");
        try {
            loadConfigProperties();
//            LOGGER.debug("loaded config properties");
            this.httpClientBuilder = HttpClientBuilder.create();
            /* Use system properties if specified */
            httpClientBuilder.useSystemProperties();
            /* Cookie spec provider */
            CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
                public CookieSpec create(HttpContext context) {
                    return new DefaultCookieSpec() {
                        @Override
                        public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                            /* Just accept everything */
                        }
                    };
                }
            };
            //Lookup<CookieSpecProvider> lookupCookieSpecProvider = httpClientContext.getCookieSpecRegistry();
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();
            Registry<CookieSpecProvider> lookupCookieSpecProvider = RegistryBuilder.<CookieSpecProvider>create()
                    .register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider(publicSuffixMatcher))
                    .register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(publicSuffixMatcher))
                    .register("easy", easySpecProvider)
                    .build();
            this.httpClientBuilder.setDefaultCookieSpecRegistry(lookupCookieSpecProvider);
            /* Disable content compression */
            this.httpClientBuilder.disableContentCompression();
            /* SSL hostname verifier */
            this.httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            /* Redirect strategy */
            this.httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
            /* Request config */
            Collection<String> preferredAuthSchemes = Arrays.asList(AuthSchemes.BASIC, AuthSchemes.KERBEROS, AuthSchemes.NTLM, AuthSchemes.SPNEGO);
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("easy").setTargetPreferredAuthSchemes(preferredAuthSchemes).build();
            this.httpClientBuilder.setDefaultRequestConfig(requestConfig);
            /* SSL context */
            SSLContext sslContext = new SSLContextBuilder().setProtocol("TLSv1.2").loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            this.httpClientBuilder.setSSLContext(sslContext);
            /* Connection manager */
            ConnectionSocketFactory plainConnectionSocketFactory = new PlainConnectionSocketFactory();
            String[] supportedProtocolsArray = new String[]{"TLSv1.1", "TLSv1.2"};
//            LOGGER.debug("default supportedProtocolsArray is " + Arrays.toString(supportedProtocolsArray));
            if (localConfig.getProperty("supportedprotocols") != null) {
                supportedProtocolsArray = ((String) localConfig.getProperty("supportedprotocols")).split(",");
            }

//            LOGGER.debug("supportedProtocolsArray is " + Arrays.toString(supportedProtocolsArray));
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, supportedProtocolsArray, null, NoopHostnameVerifier.INSTANCE);
            RegistryBuilder<ConnectionSocketFactory> connectionSocketFactoryRegistryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
            connectionSocketFactoryRegistryBuilder.register("http", plainConnectionSocketFactory);
            connectionSocketFactoryRegistryBuilder.register("https", sslConnectionSocketFactory);
            Registry<ConnectionSocketFactory> connectionSocketFactoryRegistry = connectionSocketFactoryRegistryBuilder.build();
            HttpClientConnectionManager httpClientConnectionManager = new BasicHttpClientConnectionManager(connectionSocketFactoryRegistry);
            this.httpClientBuilder.setConnectionManager(httpClientConnectionManager);
            /* User agent */
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36";
            if (localConfig.getProperty("useragent") != null) {
                userAgent = (String) localConfig.getProperty("useragent");
            }

            this.httpClientBuilder.setUserAgent(userAgent);
            /* Proxy server */
            if (localConfig.getProperty("proxy") != null && localConfig.getProperty("proxy").equalsIgnoreCase("yes")) {
                String proxyServer = localConfig.getProperty("proxyserver");
//                LOGGER.debug("proxyServer is " + proxyServer);
                String proxyPortString = localConfig.getProperty("proxyport");
//                LOGGER.debug("proxyPortString is " + proxyPortString);
                Integer proxyPort = Integer.valueOf(proxyPortString);
//                LOGGER.debug("proxyPort is " + proxyPort);
                String proxyScheme = localConfig.getProperty("proxyscheme");
//                LOGGER.debug("proxyScheme is " + proxyScheme);
                httpClientBuilder.setProxy(new HttpHost(proxyServer, proxyPort, proxyScheme));
            }

            this.httpClient = this.httpClientBuilder.build();
        } catch (KeyManagementException kme) {
            LOGGER.warn("KeyManagementException thrown building HttpClient", kme);
        } catch (KeyStoreException kse) {
            LOGGER.warn("KeyStoreException thrown building HttpClient", kse);
        } catch (NoSuchAlgorithmException nsae) {
            LOGGER.warn("NoSuchAlgorithmException thrown building HttpClient", nsae);
        } catch (Exception e) {
            LOGGER.warn("Exception thrown building HttpClient", e);
        }

//        LOGGER.debug("finishing CommonHttpManager()");
    }

    private CommonHTTPManager(ICommonHttpManagerConfig commonHttpManagerConfig) {
//        LOGGER.debug("starting CommonHTTPManager() with commonHttpManagerConfig parameter");
        loadConfigProperties();
//        LOGGER.debug("loaded config properties");
        try {
            this.httpClientBuilder = HttpClientBuilder.create();
            /* Use system properties if specified */
            httpClientBuilder.useSystemProperties();
            /* Cookie spec provider */
            CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
                public CookieSpec create(HttpContext context) {
                    return new DefaultCookieSpec() {
                        @Override
                        public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                            /* Just accept everything */
                        }
                    };
                }
            };
            //Lookup<CookieSpecProvider> lookupCookieSpecProvider = httpClientContext.getCookieSpecRegistry();
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();
            Registry<CookieSpecProvider> lookupCookieSpecProvider = RegistryBuilder.<CookieSpecProvider>create()
                    .register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider(publicSuffixMatcher))
                    .register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(publicSuffixMatcher))
                    .register("easy", easySpecProvider)
                    .build();
            this.httpClientBuilder.setDefaultCookieSpecRegistry(lookupCookieSpecProvider);
            /* Disable content compression */
            this.httpClientBuilder.disableContentCompression();
            /* SSL hostname verifier */
            this.httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            /* Redirect strategy */
            this.httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
            /* Request config */
            Collection<String> preferredAuthSchemes = Arrays.asList(AuthSchemes.BASIC, AuthSchemes.KERBEROS, AuthSchemes.NTLM, AuthSchemes.SPNEGO);
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("easy").setTargetPreferredAuthSchemes(preferredAuthSchemes).build();
            this.httpClientBuilder.setDefaultRequestConfig(requestConfig);
            /* SSL context */
            SSLContextBuilder sslContexBuilder = new SSLContextBuilder().setProtocol("TLSv1.2").loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            });
            if (commonHttpManagerConfig.useClientCertificate()) {
                KeyStore keystore = null;
                InputStream keyStoreStream = this.getClass().getResourceAsStream(commonHttpManagerConfig.getKeystorePath());
                keystore = KeyStore.getInstance("JKS");
                keystore.load(keyStoreStream, commonHttpManagerConfig.getKeystorePass().toCharArray());
                sslContexBuilder.loadKeyMaterial(keystore, commonHttpManagerConfig.getKeyPass().toCharArray());
            }

            SSLContext sslContext = sslContexBuilder.build();
            this.httpClientBuilder.setSSLContext(sslContext);
            /* Connection manager */
            ConnectionSocketFactory plainConnectionSocketFactory = new PlainConnectionSocketFactory();
            String[] supportedProtocolsArray = new String[]{"TLSv1.1", "TLSv1.2"};
//            LOGGER.debug("default supportedProtocolsArray is " + Arrays.toString(supportedProtocolsArray));
            if (commonHttpManagerConfig.getSupportedProtocols() != null) {
                supportedProtocolsArray = (commonHttpManagerConfig.getSupportedProtocols()).split(",");
            }

//            LOGGER.debug("supportedProtocolsArray is " + Arrays.toString(supportedProtocolsArray));
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, supportedProtocolsArray, null, NoopHostnameVerifier.INSTANCE);
            RegistryBuilder<ConnectionSocketFactory> connectionSocketFactoryRegistryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
            connectionSocketFactoryRegistryBuilder.register("http", plainConnectionSocketFactory);
            connectionSocketFactoryRegistryBuilder.register("https", sslConnectionSocketFactory);
            Registry<ConnectionSocketFactory> connectionSocketFactoryRegistry = connectionSocketFactoryRegistryBuilder.build();
            HttpClientConnectionManager httpClientConnectionManager = new BasicHttpClientConnectionManager(connectionSocketFactoryRegistry);
            this.httpClientBuilder.setConnectionManager(httpClientConnectionManager);
            /* User agent */
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36";
            if (commonHttpManagerConfig.getUserAgent() != null) {
                userAgent = commonHttpManagerConfig.getUserAgent();
            }

            this.httpClientBuilder.setUserAgent(userAgent);
            /* Proxy server */
            if (commonHttpManagerConfig.getProxy() != null) {
                LOGGER.debug("proxy configuration is not null");
                httpClientBuilder.setProxy(commonHttpManagerConfig.getProxy());
            } else {
                LOGGER.debug("proxy configuration is null");
            }

            this.httpClient = this.httpClientBuilder.build();
        } catch (KeyManagementException kme) {
            LOGGER.warn("KeyManagementException thrown building HttpClient", kme);
        } catch (KeyStoreException kse) {
            LOGGER.warn("KeyStoreException thrown building HttpClient", kse);
        } catch (NoSuchAlgorithmException nsae) {
            LOGGER.warn("NoSuchAlgorithmException thrown building HttpClient", nsae);
        } catch (Exception e) {
            LOGGER.warn("Exception thrown building HttpClient", e);
        }

//        LOGGER.debug("finishing CommonHttpManager()");
    }

    public void close() {
        /* TODO: work out if need to do anything here */
    }

    /**
     * load config file to properties.
     */
    private void loadConfigProperties() {
//        LOGGER.debug("starting loadConfigProperties()");
        String propertyPath = null;
        String localPropertyPath = null;
        this.config = new Properties();
        this.localConfig = new Properties();
        try {
            String userDir = System.getProperty("user.dir");
            propertyPath = userDir + File.separator + CommonConstant.CONFIG_FILE_NAME;
//            LOGGER.debug("propertyPath is " + propertyPath);
            InputStream in = getClass().getResourceAsStream("/" + CommonConstant.CONFIG_FILE_NAME);
            this.config.load(in);
            localPropertyPath = userDir + File.separator + CommonConstant.LOCAL_CONFIG_FILE_NAME;
//            LOGGER.debug("localPropertyPath is " + localPropertyPath);
            try {
                InputStream localIn = new FileInputStream(new File(localPropertyPath));
                this.localConfig.load(localIn);
//                LOGGER.debug("loaded local config");
            } catch (FileNotFoundException fnfe) {
                LOGGER.warn("FileNotFoundException happened when reading " + localPropertyPath);
            }
        } catch (FileNotFoundException fnfe) {
            LOGGER.warn("FileNotFoundException happened when reading " + propertyPath);
            LOGGER.debug(propertyPath + " not found in classpath, use common-config-default.properties");
            InputStream in = this.getClass().getResourceAsStream(CommonConstant.CONFIG_FILE_DEFAULT_NAME);
            try {
                this.config.load(in);
            } catch (IOException ioe) {
                LOGGER.fatal("IOException happened when loading common-config-default.properties", ioe);
            }
        } catch (IOException ioe) {
            LOGGER.fatal("IOException occurred when reading local-common-config.properties", ioe);
        }
    }

    public CommonHttpResponse doCommonHttpResponseDelete(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponseDelete()");
	    return doCommonHttpResponseMethod("DELETE", url, headers, parameters, entityString, credentials);
	}

	public CommonHttpResponse doCommonHttpResponseDelete(String url, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponseDelete()");
	    return doCommonHttpResponseMethod("DELETE", url, headers, parameters, null, credentials);
	}

	public CommonHttpResponse doCommonHttpResponseGet(String url, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponseGet()");
	    return doCommonHttpResponseMethod("GET", url, headers, parameters, null, credentials);
	}

    public CommonHttpResponse doCommonHttpResponseHead(String url, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
        LOGGER.debug("starting doCommonHttpResponseHead()");
        return doCommonHttpResponseMethod("HEAD", url, headers, parameters, null, credentials);
    }

	public CommonHttpResponse doCommonHttpResponseMethod(String method, String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials, boolean redirect) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponseMethod()");
	    try {
	        HttpResponse httpResponse = doHttpResponseMethod(method, url, headers, parameters, entityString, credentials, redirect);
	        LOGGER.debug("got httpResponse");
	        CommonHttpResponse commonHttpResponse = new CommonHttpResponse(httpResponse);
	        LOGGER.debug("created commonHttpResponse");
	        return commonHttpResponse;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing " + method, e);
	        throw e;
	    }
	}

	public CommonHttpResponse doCommonHttpResponseMethod(String method, String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponseMethod()");
	    return doCommonHttpResponseMethod(method, url, headers, parameters, entityString, credentials, true);
	}

	/**
	 * upload multiple files.
	 *
	 * @param url http URL
	 * @param byteArrayHashMap hashmap, key is string(file name), value is byte array.
	 * @param headers headers array
	 * @param parameters parameters list
	 * @param credentials credentials
	 * @return response
	 * @throws IOException exception
	 */
	public CommonHttpResponse doCommonHttpResponseMultipartByteArrayPost(String url, HashMap<String, byte[]> byteArrayHashMap, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponseMultipartByteArrayPost()");
	    try {
	        /* Local context is going to be used to pass credentials in to request */
	        HttpClientContext localContext = HttpClientContext.create();
	        if (credentials != null) {
	            LOGGER.debug("credentials is not null");
	            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	            credentialsProvider.setCredentials(AuthScope.ANY, credentials);
	            LOGGER.debug("set AuthScope.ANY");
	            localContext.setCredentialsProvider(credentialsProvider);
	            LOGGER.debug("set CredentialsProvider");
	        } else {
	            LOGGER.debug("credentials is null");
	        }

	        HttpPost postMethod = new HttpPost(url);
	        for (Header header : headers) {
	        	/* Exclude setting Content-Type from headers parameter */
	        	if (!header.getName().equals("Content-Type")) {
	                postMethod.addHeader(header);
	                LOGGER.debug("added header " + header.getName());
	        	}
	        }

	        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
	        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	        if (parameters != null) {
	            LOGGER.debug("parameters is not null");
	            for (NameValuePair parameter : parameters) {
	                String parameterName = parameter.getName();
	                LOGGER.debug("parameter name is " + parameterName);
	                String parameterValue = parameter.getValue();
	                LOGGER.debug("parameter value is " + parameterValue);
	                StringBody valueBody = new StringBody(parameterValue, ContentType.MULTIPART_FORM_DATA);
	                multipartEntityBuilder.addPart(parameterName, valueBody);
	                LOGGER.debug("added parameter " + parameterName + ":" + parameterValue);
	            }

	            LOGGER.debug("added parameters");
	        } else {
	            LOGGER.debug("parameters is null");
	        }

	        if (byteArrayHashMap != null) {
	            LOGGER.debug("byteArrayHashMap is not null");
	            Iterator iterator = byteArrayHashMap.keySet().iterator();
	            while (iterator.hasNext()) {
	                String key = (String) iterator.next();
	                LOGGER.debug("next key " + key);
	                byte[] byteArray = byteArrayHashMap.get(key);
	                multipartEntityBuilder.addBinaryBody(key, byteArray, ContentType.DEFAULT_BINARY, key);
	            }

	            LOGGER.debug("added byteArrayHashMap");
	        } else {
	            LOGGER.debug("byteArrayHashMap is null");
	        }

	        HttpEntity httpEntity = multipartEntityBuilder.build();
	        postMethod.setEntity(httpEntity);
	        LOGGER.debug("about to post multi-part message");
	        HttpResponse httpResponse = this.httpClient.execute(postMethod, localContext);
	        LOGGER.debug("httpResponse status code is " + httpResponse.getStatusLine().getStatusCode());
	        LOGGER.debug("httpResponse reason phrase is " + httpResponse.getStatusLine().getReasonPhrase());
	        CommonHttpResponse commonHttpResponse = new CommonHttpResponse(httpResponse);
	        LOGGER.debug("created commonHttpResponse");
	        return commonHttpResponse;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing multi-part POST", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

    /**
     * upload multiple files.
     *
     * @param url http URL
     * @param multiPartContentHashMap hashmap, key is string(file name), value is MultiPartContent.
     * @param headers headers array
     * @param parameters parameters list
     * @param credentials credentials
     * @return response
     * @throws IOException exception
     */
    public CommonHttpResponse doCommonHttpResponseMultipartContentHashMapPost(String url, HashMap<String, MultiPartContent> multiPartContentHashMap, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
        LOGGER.debug("starting doCommonHttpResponseMultipartContentHashMapPost()");
        try {
            /* Local context is going to be used to pass credentials in to request */
            HttpClientContext localContext = HttpClientContext.create();
            if (credentials != null) {
                LOGGER.debug("credentials is not null");
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, credentials);
                LOGGER.debug("set AuthScope.ANY");
                localContext.setCredentialsProvider(credentialsProvider);
                LOGGER.debug("set CredentialsProvider");
            } else {
                LOGGER.debug("credentials is null");
            }

            HttpPost postMethod = new HttpPost(url);
            for (Header header : headers) {
                /* Exclude setting Content-Type from headers parameter */
                if (!header.getName().equals("Content-Type")) {
                    postMethod.addHeader(header);
                    LOGGER.debug("added header " + header.getName());
                }
            }

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (parameters != null) {
                LOGGER.debug("parameters is not null");
                for (NameValuePair parameter : parameters) {
                    String parameterName = parameter.getName();
                    LOGGER.debug("parameter name is " + parameterName);
                    String parameterValue = parameter.getValue();
                    LOGGER.debug("parameter value is " + parameterValue);
                    StringBody valueBody = new StringBody(parameterValue, ContentType.MULTIPART_FORM_DATA);
                    multipartEntityBuilder.addPart(parameterName, valueBody);
                    LOGGER.debug("added parameter " + parameterName + ":" + parameterValue);
                }

                LOGGER.debug("added parameters");
            } else {
                LOGGER.debug("parameters is null");
            }

            if (multiPartContentHashMap != null) {
                LOGGER.debug("multiPartContentHashMap is not null");
                LOGGER.debug("multiPartContentHashMap has size " + multiPartContentHashMap.size());
                Iterator iterator = multiPartContentHashMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    LOGGER.debug("next key " + key);
                    MultiPartContent multiPartContent = multiPartContentHashMap.get(key);
                    LOGGER.debug("next content type is " + multiPartContent.getContentType());
                    if (multiPartContent.isText()) {
                        LOGGER.debug("content is text");
                        multipartEntityBuilder.addBinaryBody(key, multiPartContent.getContent(), ContentType.create(multiPartContent.getContentType()), multiPartContent.getFileName());
                    } else {
                        LOGGER.debug("content is binary");
                        multipartEntityBuilder.addBinaryBody(key, multiPartContent.getContent(), ContentType.create(multiPartContent.getContentType()), multiPartContent.getFileName());
                    }
                }

                LOGGER.debug("added multiPartContentHashMap");
            } else {
                LOGGER.debug("multiPartContentHashMap is null");
            }

            HttpEntity httpEntity = multipartEntityBuilder.build();
            postMethod.setEntity(httpEntity);
            LOGGER.debug("about to post multi-part message");
            HttpResponse httpResponse = this.httpClient.execute(postMethod, localContext);
            LOGGER.debug("httpResponse status code is " + httpResponse.getStatusLine().getStatusCode());
            LOGGER.debug("httpResponse reason phrase is " + httpResponse.getStatusLine().getReasonPhrase());
            CommonHttpResponse commonHttpResponse = new CommonHttpResponse(httpResponse);
            LOGGER.debug("created commonHttpResponse");
            return commonHttpResponse;
        } catch (Exception e) {
            LOGGER.warn("exception doing multi-part POST", e);
            throw e;
        } finally {
            LOGGER.debug("finally...");
        }
    }

	public CommonHttpResponse doCommonHttpResponsePatch(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
        LOGGER.debug("starting doCommonHttpResponsePatch()");
        return doCommonHttpResponseMethod("PATCH", url, headers, parameters, entityString, credentials);
    }

    public CommonHttpResponse doCommonHttpResponsePost(String url, List<NameValuePair> nvpList, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponsePost()");
	    return doCommonHttpResponsePost(url, null, nvpList, null, credentials);
	}

	/**
	 * This is the core method used to perform HTTP Posts.
	 *
	 * @param url
	 * @param headers
	 * @param parameters
	 * @param entityString
	 * @param credentials
	 * @return response
	 * @throws Exception
	 */
	public CommonHttpResponse doCommonHttpResponsePost(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponsePost()");
	    return doCommonHttpResponseMethod("POST", url, headers, parameters, entityString, credentials);
	}

	public CommonHttpResponse doCommonHttpResponsePut(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doCommonHttpResponsePut()");
	    return doCommonHttpResponseMethod("PUT", url, headers, parameters, entityString, null);
	}

	/**
	 * post to server and get byte array.
	 *
	 * @param url server URL
	 * @return byte array.
	 * @throws IOException IO exception
	 */
	public byte[] doGetByteArray(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doGetByteArry()");
	    byte[] result = null;
	    String content = doStringGet(url, headers, parameters);
	    LOGGER.debug("done GET");
	    result = content.getBytes("UTF8");
	    LOGGER.debug("got bytes");
	    return result;
	}

	/**
	 * post to server and get byte array.
	 *
	 * @param url server URL
	 * @return byte array.
	 * @throws IOException IO exception
	 */
	public byte[] doGetByteArray(String url) throws Exception, IOException {
	    LOGGER.debug("starting doGetByteArry()");
	    return doGetByteArray(url, null, null);
	}

	/**
	 * download as a file object.
	 *
	 * @param url server URL
	 * @param inFile input file object
	 * @return output file object
	 * @throws IOException IO exception
	 */
	public File doGetFile(String url, File inFile, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doGetFile()");
	    long t1 = System.currentTimeMillis();
	    if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("##### doGetFile-start #####, url=" + url);
	    }

	    try {
	        InputStream responseBodyInputStream = null;
	        LOGGER.debug("url is " + url);
	        HttpRequestBase httpRequestBase = new HttpGet(url);
	        if (headers != null) {
	            LOGGER.debug("headers is not null");
	            for (Header header : headers) {
	                httpRequestBase.addHeader(header);
	            }
	        } else {
	            LOGGER.debug("headers is null");
	        }

	        if (parameters != null) {
	            LOGGER.debug("parameters is not null");
	            if (httpRequestBase instanceof HttpEntityEnclosingRequest) {
	                LOGGER.debug("httpRequestBase instance of HttpEntityEnclosingRequest");
	                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
	                for (NameValuePair parameter : parameters) {
	                    String parameterName = parameter.getName();
	                    String parameterValue = parameter.getValue();
	                    nameValuePairList.add(new BasicNameValuePair(parameterName, parameterValue));
	                    LOGGER.debug("added parameter " + parameterName + ":" + parameterValue);
	                }

	                ((HttpEntityEnclosingRequest) httpRequestBase).setEntity(new UrlEncodedFormEntity(nameValuePairList));
	            } else {
	                LOGGER.debug("httpRequestBase not instance of HttpEntityEnclosingRequest");
	                if (parameters.size() > 0) {
	                    LOGGER.debug("parameters size is greater than zero");
	                    URIBuilder uriBuilder = new URIBuilder(httpRequestBase.getURI());
	                    uriBuilder.addParameters(parameters);
	                    LOGGER.debug("added parameters");
	                    httpRequestBase.setURI(uriBuilder.build());
	                } else {
	                    LOGGER.debug("parameters size is zero");
	                }
	            }
	        } else {
	            LOGGER.debug("parameters is null");
	        }

	        HttpClientContext localContext = HttpClientContext.create();
	        if (credentials != null) {
	            LOGGER.debug("created local context");
	            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	            credentialsProvider.setCredentials(AuthScope.ANY, credentials);
	            LOGGER.debug("set AuthScope.ANY");
	            localContext.setCredentialsProvider(credentialsProvider);
	            LOGGER.debug("set CredentialsProvider");
	        } else {
	            LOGGER.debug("credentials is null");
	        }

	        LOGGER.debug("about to execute method");
	        HttpResponse httpResponse = this.httpClient.execute(httpRequestBase, localContext);
	        LOGGER.debug("executed method");
	        HttpEntity httpEntity = httpResponse.getEntity();
	        responseBodyInputStream = httpEntity.getContent();
	        final int bufferSize = 2048;
	        FileOutputStream fileOutputStream = new FileOutputStream(inFile);
	        LOGGER.debug("created file output stream");
	        byte[] buffer = new byte[bufferSize];
	        int readCount = 0;
	        while ((readCount = responseBodyInputStream.read(buffer)) != -1) {
	            if (readCount < bufferSize) {
	                fileOutputStream.write(buffer, 0, readCount);
	            } else {
	                fileOutputStream.write(buffer);
	            }
	        }

	        LOGGER.debug("written out file");
	        fileOutputStream.close();
	    } catch (Exception e) {
	        LOGGER.warn("exception getting file", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }

	    if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("##### doGetFile-end #####, used time: " + (System.currentTimeMillis() - t1) + " ms\n");
	    }

	    return inFile;
	}

	/**
	 * download as a file object.
	 *
	 * @param url server URL
	 * @param inFile input file object
	 * @return output file object
	 * @throws IOException IO exception
	 */
	public File doGetFile(String url, File inFile, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doGetFile()");
	    return doGetFile(url, inFile, headers, parameters, null);
	}

	/**
	 * download as a file object.
	 *
	 * @param url server URL
	 * @param inFile input file object
	 * @return output file object
	 * @throws IOException IO exception
	 */
	public File doGetFile(String url, File inFile) throws Exception, IOException {
	    LOGGER.debug("starting doGetFile()");
	    return doGetFile(url, inFile, null, null, null);
	}

	public HttpResponse doHttpResponseDelete(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doHttpResponseDelete()");
	    return doHttpResponseMethod("DELETE", url, headers, parameters, null, null);
	}

	public HttpResponse doHttpResponseGet(String url, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doHttpResponseGet()");
	    return doHttpResponseMethod("GET", url, headers, parameters, null, credentials);
	}

	public HttpResponse doHttpResponseMethod(ICommonHttpRequest commonHttpRequest) throws Exception, IOException {
	    LOGGER.debug("starting doHttpResponseMethod()");
	    return doHttpResponseMethod(commonHttpRequest.getMethod(), commonHttpRequest.getUrl(), commonHttpRequest.getHeaders(), commonHttpRequest.getParameters(), commonHttpRequest.getEntity(), commonHttpRequest.getCredentials());
	}

	public HttpResponse doHttpResponseMethod(String method, String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials, boolean redirect) throws Exception, IOException {
	        LOGGER.debug("starting doHttpResponseMethod()");
	        long t1 = System.currentTimeMillis();
	        if (LOGGER.isDebugEnabled()) {
	            LOGGER.debug("##### doHttpResponseMethod-start #####, url=" + url);
	        }

	        HttpResponse httpResponse = null;
	        HttpRequestBase httpRequestBase = null;
	        if (method != null) {
	            LOGGER.debug("method is " + method);
	            if (method.equalsIgnoreCase("DELETE")) {
	                httpRequestBase = new HttpDelete(url);
	            } else if (method.equalsIgnoreCase("GET")) {
	                httpRequestBase = new HttpGet(url);
                } else if (method.equalsIgnoreCase("HEAD")) {
                    httpRequestBase = new HttpHead(url);
	            } else if (method.equalsIgnoreCase("PATCH")) {
	                httpRequestBase = new HttpPatch(url);
	            } else if (method.equalsIgnoreCase("POST")) {
	                httpRequestBase = new HttpPost(url);
	            } else if (method.equalsIgnoreCase("PUT")) {
	                httpRequestBase = new HttpPut(url);
	            }
	        } else {
	            LOGGER.warn("method is null");
	            return null;
	        }

	        try {
	            LOGGER.debug("url is " + url);
	            /* Local context is going to be used to pass credentials in to request */
	            HttpClientContext localContext = HttpClientContext.create();
	            if (credentials != null) {
	                LOGGER.debug("credentials is not null");
	                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	                LOGGER.debug("credentials username is " + credentials.getUserPrincipal().getName());
	                credentialsProvider.setCredentials(AuthScope.ANY, credentials);
	                LOGGER.debug("set AuthScope.ANY");
	                localContext.setCredentialsProvider(credentialsProvider);
	                LOGGER.debug("set CredentialsProvider");
	            } else {
	                LOGGER.debug("credentials is null");
	            }

	            if (headers != null) {
	                LOGGER.debug("headers is not null");
	                for (Header header : headers) {
	                    if (header != null) {
	                        httpRequestBase.addHeader(header);
	                        LOGGER.debug("added header " + header.getName() + ":" + header.getValue());
	                    }
	                }
	            } else {
	                LOGGER.debug("headers is null");
	            }

	            if (parameters != null) {
	                LOGGER.debug("parameters is not null");
	                if (httpRequestBase instanceof HttpEntityEnclosingRequest) {
	                    LOGGER.debug("httpRequestBase instance of HttpEntityEnclosingRequest");
	                    List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
	                    for (NameValuePair parameter : parameters) {
	                        String parameterName = parameter.getName();
	                        String parameterValue = parameter.getValue();
	                        nameValuePairList.add(new BasicNameValuePair(parameterName, parameterValue));
	                        LOGGER.debug("added parameter " + parameterName + ":" + parameterValue);
	                    }

	                    ((HttpEntityEnclosingRequest) httpRequestBase).setEntity(new UrlEncodedFormEntity(nameValuePairList));
	                } else {
	                    LOGGER.debug("httpRequestBase not instance of HttpEntityEnclosingRequest");
	                    if (parameters.size() > 0) {
	                        LOGGER.debug("parameters size is greater than zero");
	                        URIBuilder uriBuilder = new URIBuilder(httpRequestBase.getURI());
	                        uriBuilder.addParameters(parameters);
	                        LOGGER.debug("added parameters to URL");
	                        httpRequestBase.setURI(uriBuilder.build());
	                    } else {
	                        LOGGER.debug("parameters size is zero");
	                    }
	                }
	            } else {
	                LOGGER.debug("parameters is null");
	            }

	            if (entityString != null) {
	                LOGGER.debug("entityString is not null " + entityString);
	                if (httpRequestBase instanceof HttpEntityEnclosingRequest) {
	                    ((HttpEntityEnclosingRequest) httpRequestBase).setEntity(new StringEntity(entityString));
	//                    LOGGER.debug("set entity to " + entityString);
	                }
	            } else {
	                LOGGER.debug("entityString is null");
	            }

	            if (!redirect) {
	                LOGGER.debug("redirect is false");
	                httpRequestBase.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build());
	            }

	            LOGGER.debug("about to execute httpRequestBase");
	            httpResponse = this.httpClient.execute(httpRequestBase, localContext);
	            LOGGER.debug("httpResponse code is " + httpResponse.getStatusLine().getStatusCode());
	            LOGGER.debug("httpResponse reason phrase is " + httpResponse.getStatusLine().getReasonPhrase());
	            URI finalURI = httpRequestBase.getURI();
	            LOGGER.debug("finalURI is " + finalURI.toURL().toString());
	            List<URI> locations = localContext.getRedirectLocations();
	            if (locations != null) {
	                for (URI location : locations) {
	                    LOGGER.debug("location is " + location.toURL().toString());
	                }

	                URI finalRedirectURI = locations.get(locations.size() - 1);
	                LOGGER.debug("finalRedirectURI is " + finalRedirectURI.toString());
	                httpRequestBase.setURI(finalRedirectURI);
	                EntityUtils.consumeQuietly(httpResponse.getEntity());
	                httpResponse = this.httpClient.execute(httpRequestBase, localContext);
	                LOGGER.debug("executed redirected httpRequestBase");
	            }

	            LOGGER.debug("executed httpRequestBase");
	        } catch (Exception e) {
	            LOGGER.warn("exception doing " + method, e);
	            throw e;
	        } finally {
	            LOGGER.debug("finally...");
	        }

	        if (LOGGER.isDebugEnabled()) {
	            LOGGER.debug("##### doHttpResponseMethod-end #####, used time: " + (System.currentTimeMillis() - t1) + " ms\n");
	        }

	        return httpResponse;
	    }

	public HttpResponse doHttpResponseMethod(String method, String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doHttpResponseMethod()");
	    return doHttpResponseMethod(method, url, headers, parameters, entityString, credentials, true);
	}

	public HttpResponse doHttpResponsePatch(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doHttpResponsePatch()");
	    return doHttpResponseMethod("PATCH", url, headers, parameters, entityString, credentials);
	}

	/**
     * This is the core method used to perform HTTP Posts.
     *
     * @param url
     * @param headers
     * @param parameters
     * @param entityString
     * @param credentials
     * @return response
     * @throws Exception
     */
    public HttpResponse doHttpResponsePost(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
        LOGGER.debug("starting doHttpResponsePost()");
        return doHttpResponseMethod("POST", url, headers, parameters, entityString, credentials);
    }

    public HttpResponse doHttpResponsePost(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
        LOGGER.debug("starting doHttpResponsePost()");
        return doHttpResponsePost(url, headers, parameters, entityString, null);
    }

    public HttpResponse doHttpResponsePut(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doHttpResponsePut()");
	    return doHttpResponseMethod("PUT", url, headers, parameters, entityString, null);
	}

	public JsonElement doJsonDelete(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doJsonDelete()");
	    JsonElement jsonElement = null;
	    try {
	        String responseBodyString = doStringDelete(url, headers, parameters, entityString, credentials);
	        jsonElement = ConverterUtils.parseStringToJson(responseBodyString);
	    } catch (Exception e) {
	        LOGGER.warn("exception doing DELETE", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }

	    return jsonElement;
	}

	public JsonElement doJsonDelete(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doJsonDelete()");
	    JsonElement jsonElement = null;
	    try {
	        String responseBodyString = doStringDelete(url, headers, parameters, entityString, null);
	        jsonElement = ConverterUtils.parseStringToJson(responseBodyString);
	    } catch (Exception e) {
	        LOGGER.warn("exception doing DELETE", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }

	    return jsonElement;
	}

	public JsonElement doJsonGet(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doJsonGet()");
	    try {
	        String responseBodyString = doStringGet(url, headers, parameters);
	        LOGGER.debug("responseBodyString is " + responseBodyString);
	        JsonElement jsonElement = ConverterUtils.parseStringToJson(responseBodyString);
	        LOGGER.debug("finishing doJsonGet()");
	        return jsonElement;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing GET", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public JsonElement doJsonPatch(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doJsonPatch()");
	    try {
	        String responseEntityString = doStringPatch(url, headers, parameters, entityString, null);
	        JsonElement jsonElement = ConverterUtils.parseStringToJson(responseEntityString);
	        return jsonElement;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing PATCH", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public JsonElement doJsonPatch(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doJsonPatch()");
	    try {
	        return doJsonPatch(url, headers, parameters, entityString, null);
	    } catch (Exception e) {
	        LOGGER.warn("exception doing PATCH", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public JsonElement doJsonPut(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doJsonPut()");
	    try {
	        String responseEntityString = doStringPut(url, headers, parameters, entityString);
	        JsonElement jsonElement = ConverterUtils.parseStringToJson(responseEntityString);
	        return jsonElement;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing POST", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public JsonElement doJsonPost(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doJsonPost()");
	    try {
	        String responseEntityString = doStringPost(url, headers, parameters, entityString);
	        LOGGER.debug("responseEntityString is " + responseEntityString);
	        JsonElement jsonElement = null;
	        if (responseEntityString != null) {
	            LOGGER.debug("responseEntityString is not null");
	            jsonElement = ConverterUtils.parseStringToJson(responseEntityString);
	        }

	        return jsonElement;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing POST", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public JsonElement doJsonPost(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doJsonPost()");
	    return doJsonPost(url, headers, parameters, null);
	}

	public JsonElement doJsonPut(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doJsonPut()");
	    try {
	        String responseEntityString = doStringPut(url, headers, parameters, null);
	        JsonElement jsonElement = ConverterUtils.parseStringToJson(responseEntityString);
	        return jsonElement;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing PUT", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	/**
	 * upload multiple files.
	 *
	 * @param url http URL
	 * @param byteArrayHashMap hashmap, key is string(file name), value is byte array.
	 * @param headers headers array
	 * @param parameters parameters list
	 * @param credentials credentials
	 * @return response
	 * @throws IOException exception
	 */
	public String doMultipartByteArrayPost(String url, HashMap<String, byte[]> byteArrayHashMap, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    long t1 = System.currentTimeMillis();
	    if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("##### doHttpResponseMethod-start #####, url=" + url);
	    }

	    String response = null;
	    try {
	        /* Local context is going to be used to pass credentials in to request */
	        HttpClientContext localContext = HttpClientContext.create();
	        if (credentials != null) {
	            LOGGER.debug("credentials is not null");
	            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	            credentialsProvider.setCredentials(AuthScope.ANY, credentials);
	            LOGGER.debug("set AuthScope.ANY");
	            localContext.setCredentialsProvider(credentialsProvider);
	            LOGGER.debug("set CredentialsProvider");
	        } else {
	            LOGGER.debug("credentials is null");
	        }

	        HttpPost postMethod = new HttpPost(url);
	        for (Header header : headers) {
	        	/* Exclude setting Content-Type from headers parameter */
	        	if (!header.getName().equals("Content-Type")) {
	                postMethod.addHeader(header);
	                LOGGER.debug("added header " + header.getName());
	        	}
	        }

	        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
	        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	        if (parameters != null) {
	            LOGGER.debug("parameters is not null");
	            for (NameValuePair parameter : parameters) {
	                String parameterName = parameter.getName();
	                LOGGER.debug("parameter name is " + parameterName);
	                String parameterValue = parameter.getValue();
	                LOGGER.debug("parameter value is " + parameterValue);
	                StringBody valueBody = new StringBody(parameterValue, ContentType.MULTIPART_FORM_DATA);
	                multipartEntityBuilder.addPart(parameterName, valueBody);
	                LOGGER.debug("added parameter " + parameterName + ":" + parameterValue);
	            }

	            LOGGER.debug("added parameters");
	        } else {
	            LOGGER.debug("parameters is null");
	        }

	        if (byteArrayHashMap != null) {
	            LOGGER.debug("byteArrayHashMap is not null");
	            Iterator iterator = byteArrayHashMap.keySet().iterator();
	            while (iterator.hasNext()) {
	                String key = (String) iterator.next();
	                LOGGER.debug("next key " + key);
	                byte[] byteArray = byteArrayHashMap.get(key);
	                multipartEntityBuilder.addBinaryBody(key, byteArray, ContentType.DEFAULT_BINARY, key);
	            }

	            LOGGER.debug("added byteArrayHashMap");
	        } else {
	            LOGGER.debug("byteArrayHashMap is null");
	        }

	        HttpEntity httpEntity = multipartEntityBuilder.build();
	        postMethod.setEntity(httpEntity);
	        HttpResponse httpResponse = this.httpClient.execute(postMethod, localContext);
	        LOGGER.debug("httpResponse status code is " + httpResponse.getStatusLine().getStatusCode());
	        LOGGER.debug("httpResponse reason phrase is " + httpResponse.getStatusLine().getReasonPhrase());
	        String content = EntityUtils.toString(httpResponse.getEntity());
	        LOGGER.debug("content is " + content);
	        byte[] responseBody = content.getBytes("UTF8");
	        response = new String(responseBody);
	        LOGGER.debug("response is " + response);
	    } catch (Exception e) {
	        LOGGER.warn("exception doing multi-part POST", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }

	    if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("##### doHttpResponseMethod-end #####, used time: " + (System.currentTimeMillis() - t1) + " ms\n");
	    }

	    return response;
	}

	public String doMultipartByteArrayPost(String url, HashMap<String, byte[]> byteArrayHashMap, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    return doMultipartByteArrayPost(url, byteArrayHashMap, headers, parameters, null);
	}

	/**
	 * upload a file.
	 *
	 * @param url http URL
	 * @param file file to be uploaded
	 * @return response
	 * @throws IOException exception
	 */
	public String doMultipartPost(String url, File file, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    HashMap<String, File> fileHashMap = new HashMap<String, File>();
	    fileHashMap.put(file.getName(), file);
	    return doMultipartPost(url, fileHashMap, headers, parameters);
	}

	/**
	 * upload multiple files.
	 *
	 * @param url http URL
	 * @param filesHashMap hashmap, key is string(file name), value is byte
	 * @param headers headers array
	 * @param parameters parameters list
	 * @param credentials
	 * array.
	 * @return response
	 * @throws IOException exception
	 */
	public String doMultipartPost(String url, HashMap<String, File> filesHashMap, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    long t1 = System.currentTimeMillis();
	    if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("##### doHttpResponseMethod-start #####, url=" + url);
	    }

	    String response = null;
	    try {
	        /* Local context is going to be used to pass credentials in to request */
	        HttpClientContext localContext = HttpClientContext.create();
	        if (credentials != null) {
	            LOGGER.debug("credentials is not null");
	            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	            credentialsProvider.setCredentials(AuthScope.ANY, credentials);
	            LOGGER.debug("set AuthScope.ANY");
	            localContext.setCredentialsProvider(credentialsProvider);
	            LOGGER.debug("set CredentialsProvider");
	        } else {
	            LOGGER.debug("credentials is null");
	        }

	        HttpPost postMethod = new HttpPost(url);
	        for (Header header : headers) {
	        	/* Exclude setting Content-Type from headers parameter */
	        	if (!header.getName().equals("Content-Type")) {
	                postMethod.addHeader(header);
	                LOGGER.debug("added header " + header.getName());
	        	}
	        }

	        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
	        if (parameters != null) {
	            for (NameValuePair parameter : parameters) {
	                String parameterName = parameter.getName();
	                multipartEntityBuilder.addTextBody(parameterName, parameter.getValue());
	                LOGGER.debug("added parameter " + parameterName);
	            }

	            LOGGER.debug("added parameters");
	        }

	        Iterator iterator = filesHashMap.keySet().iterator();
	        while (iterator.hasNext()) {
	            String key = (String) iterator.next();
	            LOGGER.debug("next key " + key);
	            File file = filesHashMap.get(key);
	            multipartEntityBuilder.addBinaryBody(key, file);
	        }

	        HttpEntity httpEntity = multipartEntityBuilder.build();
	        postMethod.setEntity(httpEntity);
	        HttpResponse httpResponse = this.httpClient.execute(postMethod, localContext);
	        String content = EntityUtils.toString(httpResponse.getEntity());
	        byte[] responseBody = content.getBytes("UTF8");
	        response = new String(responseBody);
	        LOGGER.debug("executed postMethod");
	    } catch (Exception e) {
	        LOGGER.warn("exception doing multi-part POST", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }

	    if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("##### doHttpResponseMethod-end #####, used time: " + (System.currentTimeMillis() - t1) + " ms\n");
	    }

	    return response;
	}

	/**
	 * upload multiple files.
	 *
	 * @param url http URL
	 * @param fileHashMap HashMap
	 * @param headers headers array
	 * @param parameters parameters list
	 * @return response
	 * @throws IOException exception
	 */
	public String doMultipartPost(String url, HashMap<String, File> fileHashMap, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    return doMultipartPost(url, fileHashMap, headers, parameters, null);
	}

	/**
	 * upload multiple files.
	 *
	 * @param url http URL
	 * @param fileList file list(File list)
	 * @return response
	 * @throws IOException exception
	 */
	public String doMultipartPost(String url, List<File> fileList) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    return doMultipartPost(url, fileList, null, null);
	}

	/**
	 * upload multiple files.
	 *
	 * @param url http URL
	 * @param fileList file list(File list)
	 * @return response
	 * @throws IOException exception
	 */
	public String doMultipartPost(String url, List<File> fileList, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doMultipartPost()");
	    HashMap<String, File> fileHashMap = new HashMap<String, File>();
	    for (File file : fileList) {
	        LOGGER.debug("next file");
	        fileHashMap.put(file.getName(), file);
	        LOGGER.debug("put file in HashMap");
	    }

	    return doMultipartPost(url, fileHashMap, headers, parameters);
	}

	public String doStringDelete(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doStringDelete()");
	    String responseEntityString = null;
	    CommonHttpResponse commonHttpResponse = doCommonHttpResponseDelete(url, headers, parameters, entityString, credentials);
	    if (commonHttpResponse != null) {
	        LOGGER.debug("commonHttpResponse is not null");
	        HttpEntity httpEntity = commonHttpResponse.getResponseEntity();
	        LOGGER.debug("got httpEntity");
	        if (httpEntity != null) {
	            LOGGER.debug("httpEntity is not null");
	            responseEntityString = commonHttpResponse.getResponseEntityString();
	            LOGGER.debug("responseEntityString is " + responseEntityString);
	            return responseEntityString;
	        } else {
	            LOGGER.debug("httpEntity is null");
	        }
	    } else {
	        LOGGER.debug("commonHttpResponse is null");
	    }

	    return responseEntityString;
	}

	public String doStringGet(String url, Header[] headers, List<NameValuePair> parameters, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doStringGet()");
	    try {
	        CommonHttpResponse commonHttpResponse = doCommonHttpResponseGet(url, headers, parameters, credentials);
	        if (commonHttpResponse != null) {
	            LOGGER.debug("commonHttpResponse is not null");
	            HttpEntity httpEntity = commonHttpResponse.getResponseEntity();
	            if (httpEntity != null) {
	                LOGGER.debug("httpEntity is not null");
	                String responseEntityString = commonHttpResponse.getResponseEntityString();
	                LOGGER.debug("responseEntityString is " + responseEntityString);
	                return responseEntityString;
	            } else {
	                LOGGER.debug("httpEntity is null");
	            }
	        } else {
	            LOGGER.debug("commonHttpResponse is null");
	        }

	        return null;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing GET", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public String doStringGet(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
	    LOGGER.debug("starting doStringGet()");
	    return doStringGet(url, headers, parameters, null);
	}

	public String doStringGet(String url, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doStringGet()");
	    return doStringGet(url, null, null, credentials);
	}

	public String doStringPatch(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
	    LOGGER.debug("starting doStringPatch()");
	    try {
	        CommonHttpResponse commonHttpResponse = doCommonHttpResponsePatch(url, headers, parameters, entityString, credentials);
	        if (commonHttpResponse != null) {
	            LOGGER.debug("commonHttpResponse is not null");
	            HttpEntity httpEntity = commonHttpResponse.getResponseEntity();
	            if (httpEntity != null) {
	                LOGGER.debug("httpEntity is not null");
	                String responseEntityString = commonHttpResponse.getResponseEntityString();
	                LOGGER.debug("responseEntityString is " + responseEntityString);
	                return responseEntityString;
	            } else {
	                LOGGER.debug("httpEntity is null");
	            }
	        } else {
	            LOGGER.debug("commonHttpResponse is null");
	        }

	        return null;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing PATCH", e);
	        throw e;
	    } finally {
	        LOGGER.debug("finally...");
	    }
	}

	public String doStringPost(String url, Header[] headers, List<NameValuePair> parameters, String entityString, Credentials credentials) throws Exception, IOException {
        LOGGER.debug("starting doStringPost()");
        try {
            CommonHttpResponse commonHttpResponse = doCommonHttpResponsePost(url, headers, parameters, entityString, credentials);
            if (commonHttpResponse != null) {
                LOGGER.debug("commonHttpResponse is not null");
                HttpEntity httpEntity = commonHttpResponse.getResponseEntity();
                if (httpEntity != null) {
                    LOGGER.debug("httpEntity is not null");
                    String responseEntityString = commonHttpResponse.getResponseEntityString();
                    LOGGER.debug("responseEntityString is " + responseEntityString);
                    return responseEntityString;
                } else {
                    LOGGER.debug("httpEntity is null");
                }
            } else {
                LOGGER.debug("commonHttpResponse is null");
            }

            return null;
        } catch (Exception e) {
            LOGGER.warn("exception doing POST", e);
            throw e;
        } finally {
            LOGGER.debug("finally...");
        }
    }

    public String doStringPost(String url, Header[] headers, List<NameValuePair> parameters, NTCredentials ntCredentials) throws Exception, IOException {
        LOGGER.debug("starting doStringPost()");
        return doStringPost(url, headers, parameters, null, ntCredentials);
    }

    public String doStringPost(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
        LOGGER.debug("starting doStringPost()");
        return doStringPost(url, headers, parameters, entityString, null);
    }

    public String doStringPost(String url, Credentials credentials) throws Exception, IOException {
        LOGGER.debug("starting doStringPost()");
        return doStringPost(url, null, null, null, credentials);
    }

    public String doStringPut(String url, Header[] headers, List<NameValuePair> parameters, String entityString) throws Exception, IOException {
	    LOGGER.debug("starting doStringPut()");
	    try {
	        CommonHttpResponse commonHttpResponse = doCommonHttpResponsePut(url, headers, parameters, entityString);
	        LOGGER.debug("executed PUT");
	        if (commonHttpResponse != null) {
	            LOGGER.debug("httpResponse is not null");
	            int returnCode = commonHttpResponse.getStatusLine().getStatusCode();
	            LOGGER.debug("returnCode is " + returnCode);
	            Header[] responseHeaders = commonHttpResponse.getResponseHeaders();
	            for (Header responseHeader : responseHeaders) {
	                LOGGER.debug("responseHeader is " + responseHeader.getName() + ":" + responseHeader.getValue());
	            }

	            HttpEntity httpEntity = commonHttpResponse.getResponseEntity();
	            LOGGER.debug("got responseEntity");
	            if (httpEntity != null) {
	                LOGGER.debug("httpEntity is not null");
	                String responseEntityString = commonHttpResponse.getResponseEntityString();
	                LOGGER.debug("responseEntityString is " + responseEntityString);
	                return responseEntityString;
	            } else {
	                LOGGER.debug("httpEntity is null");
	            }
	        } else {
	            LOGGER.debug("commonHttpResponse is null");
	        }

	        return null;
	    } catch (Exception e) {
	        LOGGER.warn("exception doing PUT", e);
	        throw e;
	    }
	}

	public String doStringPut(String url, Header[] headers, List<NameValuePair> parameters) throws Exception, IOException {
        LOGGER.debug("starting doStringPut()");
        return doStringPut(url, headers, parameters, null);
    }

    /**
     * get the only http client instance of system, config it as what you want.
     *
     * @return the hc
     */
    public HttpClient getHttpClient() {
        LOGGER.debug("starting getHttpClient()");
        return this.httpClient;
    }
}
