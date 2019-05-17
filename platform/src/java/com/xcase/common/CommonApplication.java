package com.xcase.common;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpManagerConfig;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            Options options = new Options();
            options.addOption("A", true, "user agent");
            options.addOption("a", false, "append");
            options.addOption("D", true, "dump headers");
            options.addOption("d", true, "data");
            options.addOption("F", true, "form data");
            Option optionHeader = new Option("H", true, "header");
            optionHeader.setArgs(Option.UNLIMITED_VALUES);
            options.addOption(optionHeader);
            options.addOption("h", false, "help");
            options.addOption("k", false, "insecure");
            options.addOption("o", true, "output file");
            options.addOption("s", false, "silent");
            options.addOption("u", true, "username:password");
            options.addOption("X", true, "method");
            options.addOption("x", true, "proxy");
            options.addOption("0", false, "HTTP 1.0");
            options.addOption("1", false, "TLS 1.0");
            options.addOption("2", false, "SSL 2");
            options.addOption("3", false, "SSL 3");
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("h")) {
                LOGGER.debug("help is requested");
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("CommonApplication", options);
                return;
            }
            
            Properties properties = new Properties();
            Credentials credentials = new NTCredentials("dummy","dummy", null, null);
            String userAgent = "";
            boolean redirect = false;
            if (commandLine.getOptionValue("A") != null) {
                LOGGER.debug("entity data is specified");
                userAgent = commandLine.getOptionValue("A");
                properties.setProperty("useragent", userAgent);
            }
            
            LOGGER.debug("userAgent is " + userAgent);
            String entityString = "";
            if (commandLine.getOptionValue("d") != null) {
                LOGGER.debug("entity data is specified");
                entityString = commandLine.getOptionValue("d");
            }
            
            LOGGER.debug("entityString is " + entityString);
            ArrayList<Header> headerList = new ArrayList<Header>();
            String referer = "";
            if (commandLine.getOptionValue("e") != null) {
                LOGGER.debug("referer is specified");
                referer = commandLine.getOptionValue("e");
                headerList.add(new BasicHeader("Referer", referer));
            }
            
            LOGGER.debug("referer is " + referer);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            if (commandLine.getOptionValue("F") != null) {
                headerList.add(new BasicHeader("Content-Type", "multipart/form-data"));
                String[] formsStringArray = commandLine.getOptionValues("F");
                for (String formString : formsStringArray) {
                    LOGGER.debug("formString is " + formString);
                    String[] formStringArray = formString.split("=");
                    BasicNameValuePair parameter = new BasicNameValuePair(formStringArray[0], formStringArray[1]);
                    parameters.add(parameter);
                }
            }
            
            if (commandLine.getOptionValue("H") != null) {
                String[] headersStringArray = commandLine.getOptionValues("H");
                for (String headerString : headersStringArray) {
                    LOGGER.debug("headerString is " + headerString);
                    String[] headerStringArray = headerString.split(":");
                    Header header = new BasicHeader(headerStringArray[0], headerStringArray[1]);
                    headerList.add(header);
                }
            }
            
            Header[] headers = headerList.toArray(new Header[0]);
            String usernamePassword = "";
            if (commandLine.getOptionValue("u") != null) {
                LOGGER.debug("username is specified");
                usernamePassword = commandLine.getOptionValue("u");
                LOGGER.debug("usernamePassword is " + usernamePassword);
                String username = usernamePassword.contains(":") ? usernamePassword.split(":")[0] : usernamePassword;
                String password = usernamePassword.contains(":") ? usernamePassword.split(":")[1] : "";
                String workstation = usernamePassword.contains(":") && usernamePassword.split(":").length > 2 ? usernamePassword.split(":")[2] : "";
                String domain = usernamePassword.contains(":")  && usernamePassword.split(":").length > 3 ? usernamePassword.split(":")[3] : "";
                credentials = new NTCredentials(username, password, workstation, domain);
            }
            
            String method = "GET";
            if (commandLine.getOptionValue("X") != null) {
                LOGGER.debug("method is specified");
                method = commandLine.getOptionValue("X");
            }
            
            LOGGER.debug("method is " + method);
            String proxy = "";
            if (commandLine.getOptionValue("x") != null) {
                LOGGER.debug("proxy is specified");
                proxy = commandLine.getOptionValue("x");
                if (!proxy.contains(":")) {
                    properties.setProperty("proxyserver", proxy);
                    properties.setProperty("proxyport", "1080");
                } else {
                    String[] proxyArray = proxy.split(":");
                    properties.setProperty("proxyserver", proxyArray[0]);
                    properties.setProperty("proxyport", proxyArray[1]);
                }
            }
            
            LOGGER.debug("proxy is " + proxy);
            ArrayList<String> supportedProtocolsList = new ArrayList<String>();
            supportedProtocolsList.add("TLSv1.2");
            if (commandLine.hasOption("1")) {
                supportedProtocolsList.add("TLSv1");
            }
            
            if (commandLine.hasOption("2")) {
                supportedProtocolsList.add("SSLv2");
            }
            
            if (commandLine.hasOption("3")) {
                supportedProtocolsList.add("SSLv3");
            }
            
            properties.setProperty("supportedprotocols", StringUtils.join(supportedProtocolsList, ","));
            String url = args[args.length-1];
            LOGGER.debug("url is " + url);
            CommonHttpManagerConfig commonHttpManagerConfig = new CommonHttpManagerConfig(properties);
            CommonHTTPManager httpManager = CommonHTTPManager.getCommonHTTPManager(commonHttpManagerConfig);
            CommonHttpResponse commonHTTPResponse = null;
            if (true) {
                commonHTTPResponse = httpManager.doCommonHttpResponseMethod(method, url, headers, parameters, entityString, credentials, redirect);
            } else {
                HashMap<String, byte[]> byteArrayHashMap = new HashMap<String, byte[]>();
                commonHTTPResponse = httpManager.doCommonHttpResponseMultipartByteArrayPost(method, byteArrayHashMap, headers, parameters, credentials);
            }
            
            if (commandLine.getOptionValue("o") != null) {
                String output = commandLine.getOptionValue("o");
                FileWriter fileWriter = null;
                if (commandLine.hasOption("a")) {
                    /* Append to existing file */
                    fileWriter = new FileWriter(output, true);                    
                } else {
                    /* Overwrite existing file */
                    fileWriter = new FileWriter(output, false);                   
                }
                
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                printWriter.println(commonHTTPResponse.getResponseEntityString());  
                printWriter.close();
                bufferedWriter.close();
                fileWriter.close();
            }
            
            if (commandLine.getOptionValue("D") != null) {
                String outputHeaders = commandLine.getOptionValue("D");
                LOGGER.debug("outputHeaders is " + outputHeaders);
                FileWriter fileWriter = new FileWriter(outputHeaders, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                for (Header header : commonHTTPResponse.getResponseHeaders()) {
                    LOGGER.debug("header is " + header.getName() + ":" + header.getValue());
                    printWriter.println(header.getName() + ":" + header.getValue());
                }
                
                printWriter.close();
                bufferedWriter.close();
                fileWriter.close();
            }            
        } catch (Exception e) {
            LOGGER.warn("exception executing method: " + e.getMessage());
        }
    }

}
