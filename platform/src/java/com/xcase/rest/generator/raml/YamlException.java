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

public class YamlException extends Exception {
    /// <summary>
    /// Gets the position in the input stream where the event that originated the exception starts.
    /// </summary>
    public Mark Start;

    /// <summary>
    /// Gets the position in the input stream where the event that originated the exception ends.
    /// </summary>
    public Mark End;

    /// <summary>
    /// Initializes a new instance of the <see cref="YamlException"/> class.
    /// </summary>
    public YamlException()
    {
        
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="YamlException"/> class.
    /// </summary>
    /// <param name="message">The message.</param>
    public YamlException(String message) {
        super(message);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="YamlException"/> class.
    /// </summary>
    public YamlException(Mark start, Mark end, String message) {
        this(start, end, message, null);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="YamlException"/> class.
    /// </summary>
    public YamlException(Mark start, Mark end, String message, Exception innerException) {
        super("(" + start + ") - (" + end + "): " + message, innerException);
        Start = start;
        End = end;
    }
    /// <summary>
    /// Initializes a new instance of the <see cref="YamlException"/> class.
    /// </summary>
    /// <param name="message">The message.</param>
    /// <param name="inner">The inner.</param>
    public YamlException(String message, Exception innerException) {
        super(message, innerException);
    }
}
