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

public abstract class ParsingEvent
{
    /// <summary>
    /// Gets a value indicating the variation of depth caused by this event.
    /// The value can be either -1, 0 or 1. For start events, it will be 1,
    /// for end events, it will be -1, and for the remaining events, it will be 0.
    /// </summary>
    public int NestingIncrease;

    /// <summary>
    /// Gets the event type, which allows for simpler type comparisons.
    /// </summary>
    public EventType Type;

    /// <summary>
    /// Gets the position in the input stream where the event starts.
    /// </summary>
    public Mark Start;

    /// <summary>
    /// Gets the position in the input stream where the event ends.
    /// </summary>
    public Mark End;

    /// <summary>
    /// Accepts the specified visitor.
    /// </summary>
    /// <param name="visitor">Visitor to accept, may not be null</param>
    public abstract void accept(IParsingEventVisitor visitor);

    /// <summary>
    /// Initializes a new instance of the <see cref="ParsingEvent"/> class.
    /// </summary>
    /// <param name="start">The start position of the event.</param>
    /// <param name="end">The end position of the event.</param>
    public ParsingEvent(Mark start, Mark end)
    {
        this.Start = start;
        this.End = end;
    }
}
