package com.xcase.rest.generator.raml;

import com.google.gson.*;
import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.serialization.*;
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

public class DeserializerBuilder {
    private IObjectFactory objectFactory = new DefaultObjectFactory();
    
    /// <summary>
    /// Initializes a new <see cref="DeserializerBuilder" /> using the default component registrations.
    /// </summary>
    public DeserializerBuilder() {

    }

    protected DeserializerBuilder self() { return this; }

    /// <summary>
    /// Sets the <see cref="IObjectFactory" /> that will be used by the deserializer.
    /// </summary>
    public DeserializerBuilder WithObjectFactory(IObjectFactory objectFactory) throws Exception {
        if (objectFactory == null) {
            throw new Exception("objectFactory");
        }

        this.objectFactory = objectFactory;
        return this;
    }

    /// <summary>
    /// Registers an additional <see cref="INodeDeserializer" /> to be used by the deserializer.
    /// </summary>
    public DeserializerBuilder WithNodeDeserializer(INodeDeserializer nodeDeserializer) {
        return this;
    }

    /// <summary>
    /// Registers an additional <see cref="INodeDeserializer" /> to be used by the deserializer.
    /// </summary>
    /// <param name="nodeDeserializer"></param>
    /// <param name="where">Configures the location where to insert the <see cref="INodeDeserializer" /></param>
    public DeserializerBuilder WithNodeDeserializer() {
        return this;
    }

    /// <summary>
    /// Creates a new <see cref="Deserializer" /> according to the current configuration.
    /// </summary>
    public Deserializer build() {
        return new Deserializer();
    }

    /// <summary>
    /// Creates a new <see cref="IValueDeserializer" /> that implements the current configuration.
    /// This method is available for advanced scenarios. The preferred way to customize the bahavior of the
    /// deserializer is to use the <see cref="Build" /> method.
    /// </summary>
    public Deserializer BuildValueDeserializer() {
        return new Deserializer();
    }
}
