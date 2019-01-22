package com.xcase.rest.generator.raml;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.EventType;
import com.xcase.rest.generator.raml.IParsingEventVisitor;
import com.xcase.rest.generator.raml.Mark;
import com.xcase.rest.generator.raml.tokens.Token;
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

public interface IScanner
{
    Token getCurrent();
    /// <summary>
    /// Moves to the next token and consumes the current token.
    /// </summary>
    boolean moveNext();

    /// <summary>
    /// Moves to the next token without consuming the current token.
    /// </summary>
    boolean moveNextWithoutConsuming();

    /// <summary>
    /// Consumes the current token.
    /// </summary>
    void consumeCurrent();
}
