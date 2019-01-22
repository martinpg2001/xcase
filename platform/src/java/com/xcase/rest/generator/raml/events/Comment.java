package com.xcase.rest.generator.raml.events;

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

public class Comment extends ParsingEvent
{
    public String Value;
    public boolean IsInline;

    public Comment(String value, boolean isInline) {
        this(value, isInline, Mark.Empty, Mark.Empty);
    }

    public Comment(String value, boolean isInline, Mark start, Mark end) {
        super(start, end);
        Value = value;
        IsInline = isInline;
    }

    public EventType Type;

    public void accept(IParsingEventVisitor visitor)
    {
        visitor.visit(this);
    }

    /// <summary>
    /// Returns a <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
    /// </summary>
    /// <returns>
    /// A <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
    /// </returns>
    public String toString()
    {
        String inlineOrBlock = IsInline ? "Inline" : "Block";
        return inlineOrBlock + " Comment " + Value;
    }
}

