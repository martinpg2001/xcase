package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultObjectFactory implements IObjectFactory {
    private static HashMap<Class, Class> defaultInterfaceImplementations = new HashMap<Class, Class>();

    public Object create(Class type) throws Exception {
        if (type.isInterface()) {
            Class implementationType;
        }

        try {
            return null;
        }
        catch (Exception e) {
            String message = "Failed to create an instance of type " + type;
            throw new Exception(message);
        }
    }
}
