package com.xcase.rest.generator.raml.serialization;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.raml.IParser;
import com.xcase.rest.generator.raml.SerializerState;
import com.xcase.rest.generator.raml.YamlException;
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

public interface IValueDeserializer
{
    Object deserializeValue(IParser parser, Class expectedType, SerializerState state, IValueDeserializer nestedObjectDeserializer) throws YamlException ;
}
