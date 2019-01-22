package com.xcase.rest.generator.raml;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.events.*;
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

public interface IParsingEventVisitor {
    /* TODO: complete interface
    void Visit(AnchorAlias e);
    void Visit(StreamStart e);
    void Visit(StreamEnd e);
    void Visit(DocumentStart e);
    void Visit(DocumentEnd e);
    void Visit(Scalar e);
    void Visit(SequenceStart e);
    void Visit(SequenceEnd e);
    void Visit(MappingStart e);
    void Visit(MappingEnd e);
    */
    void visit(Comment e);
}
