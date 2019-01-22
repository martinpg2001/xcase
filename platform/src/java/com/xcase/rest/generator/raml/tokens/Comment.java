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

public class Comment extends Token {
    /// <summary>
    /// Gets the value of the comment
    /// </summary>
    public String Value;

    /// <summary>
    /// Gets a value indicating whether the comment appears other tokens on that line.
    /// </summary>
    public boolean IsInline;

    /// <summary>
    /// Initializes a new instance of the <see cref="Comment"/> class.
    /// </summary>
    public Comment(String value, boolean isInline) {
        this(value, isInline, Mark.Empty, Mark.Empty);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Comment"/> class.
    /// </summary>
    public Comment(String value, boolean isInline, Mark start, Mark end) {
        super(start, end);
        IsInline = isInline;
        Value = value;
    }
}
