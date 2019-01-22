package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.raml.serialization.IValueDeserializer;
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

public class Deserializer {
    private IValueDeserializer valueDeserializer;
    
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public Deserializer() {

    }
    
    private Deserializer(IValueDeserializer valueDeserializer) throws Exception {
        if (valueDeserializer == null) {
            throw new Exception("valueDeserializer");
        }

        this.valueDeserializer = valueDeserializer;
    }
    
    public <T> Object deserialize(String input) {
        Object result = null;

        return result;
    }

    /// <summary>
    /// Deserializes an object of the specified type.
    /// </summary>
    /// <param name="parser">The <see cref="IParser" /> from where to deserialize the object.</param>
    /// <param name="type">The static type of the object to deserialize.</param>
    /// <returns>Returns the deserialized object.</returns>
    public Object deserialize(IParser parser, Class type) throws Exception {
        if (parser == null) {
            throw new Exception("reader");
        }

        if (type == null) {
            throw new Exception("type");
        }

        Object result = null;

        return result;
    }
}
