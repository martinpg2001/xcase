package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
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

public class SerializerState {
	/**
	 * log4j object.
	 */
	protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private HashMap<Class, Object> items = new HashMap<Class, Object>();

	public <T> Object get(Class T) throws Exception {
		Object value = null;
		if (!items.containsKey(T)) {
			try {
				value = T.getDeclaredConstructor().newInstance();
				items.put(T, value);
			} catch (InstantiationException ie) {
				LOGGER.info("exception creating new instance: " + ie.getMessage());
			} catch (IllegalAccessException iae) {
				LOGGER.info("exception creating new instance: " + iae.getMessage());
			} catch (InvocationTargetException ite) {
				LOGGER.info("exception creating new instance: " + ite.getMessage());
			} catch (NoSuchMethodException nsme) {
				LOGGER.info("exception creating new instance: " + nsme.getMessage());
			}
		}

		return (T) value;
	}

	/// <summary>
	/// Invokes <see cref="IPostDeserializationCallback.OnDeserialization" /> on all
	/// objects added to this collection that implement <see
	/// cref="IPostDeserializationCallback" />.
	/// </summary>
	public void OnDeserialization() {

	}

	public void dispose() {

	}
}
