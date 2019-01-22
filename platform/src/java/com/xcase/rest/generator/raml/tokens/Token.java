package com.xcase.rest.generator.raml.tokens;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.raml.EventType;
import com.xcase.rest.generator.raml.IParsingEventVisitor;
import com.xcase.rest.generator.raml.Mark;
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

public abstract class Token {

    /// <summary>
    /// Gets the start of the token in the input stream.
    /// </summary>
    public Mark Start;

    /// <summary>
    /// Gets the end of the token in the input stream.
    /// </summary>
    public Mark End;

    /// <summary>
    /// Initializes a new instance of the <see cref="Token"/> class.
    /// </summary>
    /// <param name="start">The start position of the token.</param>
    /// <param name="end">The end position of the token.</param>
    public Token(Mark start, Mark end) {
        this.Start = start;
        this.End = end;
    }
}
