package com.xcase.rest.generator.raml.serialization;

import com.google.gson.*;
import com.xcase.rest.generator.raml.*;
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

public interface INodeDeserializer
{
    boolean deserialize(IParser reader, Class expectedType, IValueDeserializer nestedObjectDeserializer) throws YamlException;
}