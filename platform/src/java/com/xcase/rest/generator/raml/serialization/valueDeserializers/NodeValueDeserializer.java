package com.xcase.rest.generator.raml.serialization.valueDeserializers;

import com.google.gson.*;
import com.xcase.rest.generator.raml.*;
import com.xcase.rest.generator.raml.events.*;
import com.xcase.rest.generator.raml.serialization.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.raml.serialization.IValueDeserializer;
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

public class NodeValueDeserializer implements IValueDeserializer {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private List<INodeDeserializer> deserializers;
    private List<INodeTypeResolver> typeResolvers;

    public NodeValueDeserializer(List<INodeDeserializer> deserializers, List<INodeTypeResolver> typeResolvers) throws Exception {
        if (deserializers == null) {
            throw new Exception("deserializers");
        }

        this.deserializers = deserializers;
        if (typeResolvers == null) {
            throw new Exception("typeResolvers");
        }

        this.typeResolvers = typeResolvers;
    }

    public Object deserializeValue(IParser parser, Class expectedType, SerializerState state, IValueDeserializer nestedObjectDeserializer) throws YamlException {
        NodeEvent nodeEvent = (NodeEvent)parser.peek();
        Class nodeType = getTypeFromEvent(nodeEvent, expectedType);
        try {
            for (INodeDeserializer deserializer : deserializers) {
                Object value = null;
                if (deserializer.deserialize(parser, nodeType, nestedObjectDeserializer)) {
                    return value;
                }
            }
        } 
        catch (YamlException ye) {
            LOGGER.warn("Yaml exception deserializing value " + ye.getMessage());
            throw ye;
        }
        catch (Exception e) {
            LOGGER.warn("exception deserializing value {0}", e.getMessage());
            throw new YamlException(nodeEvent.Start, nodeEvent.End, "Exception during deserialization: ", e);
        }

        throw new YamlException(nodeEvent.Start, nodeEvent.End, "No node deserializer was able to deserialize the node into type " + expectedType.getCanonicalName());
    }

    private Class getTypeFromEvent(NodeEvent nodeEvent, Class currentType) {
        for (INodeTypeResolver typeResolver : typeResolvers) {
            if (typeResolver.resolve(nodeEvent, currentType)) {
                break;
            }
        }

        return currentType;
    }
}

