/**
 * Copyright 2019 Xcase, Inc. All rights reserved.
 */
package com.xcase.webapp.helpers;

import com.xcase.annotations.XcaseParameterAnnotation;
import java.lang.annotation.*;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class WebApplicationControlHelper {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Object[] parameterObjects;

    public static Method matchMethodToParameters(Class clazz, String methodName, HashMap parametersHashMap) {
//        LOGGER.debug("starting matchMethodToParameters()");
        int parametersSize = 0;
        if (clazz == null) {
            LOGGER.warn("class is null");
            return null;
        } else {
//            LOGGER.debug("class name is " + clazz.getName());
        }

        if (methodName == null) {
            LOGGER.warn("method name is null");
            return null;
        } else {
//            LOGGER.debug("methodName is " + methodName);
        }

        if (parametersHashMap == null) {
            LOGGER.warn("parameters hash map is null");
            return null;
        } else {
//            LOGGER.debug("parametersHashMap is " + parametersHashMap.toString());
            parametersSize = parametersHashMap.size();
//            LOGGER.debug("parameters size is " + parametersSize);
        }

        Method[] classMethods = clazz.getMethods();
        for (int i = 0; i < classMethods.length; i++) {
            Method classMethod = classMethods[i];
//            LOGGER.debug("classMethod is " + classMethod.getName());
            if (isMatch(classMethod, methodName, parametersHashMap)) {
//                LOGGER.debug("finishing matchMethodToParameters()");
                return classMethod;
            }
        }

        return null;
    }

    public static boolean isMatch(Method classMethod, String methodName, HashMap parametersHashMap) {
//        LOGGER.debug("starting isMatch()");
        if (classMethod != null) {
            if (classMethod.getName().equals(methodName)) {
                LOGGER.debug("got method name match");
                Class[] parameterClassArray = classMethod.getParameterTypes();
                return isMatch(classMethod, parameterClassArray, parametersHashMap);
            } else {
//                LOGGER.warn("class method name does not match " + methodName);
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isMatch(Method classMethod, Class[] parameterClassArray, HashMap parametersHashMap) {
//        LOGGER.debug("starting isMatch()");
        if (parameterClassArray.length != parametersHashMap.size()) {
//            LOGGER.warn("parameters do not match number in method");
            return false;
        }

//        LOGGER.debug("parameter array length matches");
        parameterObjects = new Object[parameterClassArray.length];
        String[] methodParameterNames = new String[parameterClassArray.length];
        populateMethodParameterNames(methodParameterNames, classMethod);
        for (int i = 0; i < methodParameterNames.length; i++) {
            String methodParameterName = methodParameterNames[i];
            if (parametersHashMap.get(methodParameterName) != null) {
                Object parameterValue = parametersHashMap.get(methodParameterName);
//                LOGGER.debug("parameter value for " + methodParameterName + " is " + parameterValue);
                parameterObjects[i] = parameterValue;
            } else {
                LOGGER.warn("parameter value for " + methodParameterName + " is not in parametersHashMap");
                return false;
            }
        }

//        LOGGER.debug("finishing isMatch()");
        return true;
    }

    public static Object[] createParameterObjects(Class clazz, HashMap parameterHashMap) {
    	LOGGER.debug("starting createParameterObjects");
    	Object[] parameterObjectArray = new Object[parameterHashMap.size()];
    	Set keySet = parameterHashMap.keySet();
    	int i = 0;
    	for (Object key : keySet) {
    		LOGGER.debug("key is " + key);
    		Object parameterObject = parameterHashMap.get(key);
    		LOGGER.debug("parameterObject is " + parameterObject);
    		parameterObjectArray[i] = parameterObject;
    		i++;
    	}

    	return parameterObjectArray;
    }

    public static void populateMethodParameterNames(String[] methodParameterNames, Method classMethod) {
        Annotation[][] methodParameterAnnotations = classMethod.getParameterAnnotations();
        //LOGGER.debug("" + classMethod + " got " + methodParameterAnnotations.length + " method annotations");
        for (int j = 0; j < methodParameterNames.length; j++) {
            String name = null;
            Annotation[] parameterAnnotations = methodParameterAnnotations[j];
            //LOGGER.debug("got " + parameterAnnotations.length + " annotations");
            for (int k = 0; k < parameterAnnotations.length; k++) {
                XcaseParameterAnnotation xcaseParameterAnnotation = (XcaseParameterAnnotation) parameterAnnotations[k];
                //LOGGER.debug("parameterAnnotations[" + k + "] is " + parameterAnnotations[k]);
                name = xcaseParameterAnnotation.name();
                //LOGGER.debug("parameter name is " + name);
            }

            methodParameterNames[j] = name;
        }
    }

    private WebApplicationControlHelper() {

    }
}
