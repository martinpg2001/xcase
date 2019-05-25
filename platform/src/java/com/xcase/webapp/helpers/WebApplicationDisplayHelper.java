/**
 * Copyright 2016 Xcase, Inc. All rights reserved.
 */
package com.xcase.webapp.helpers;

import com.xcase.annotations.XcaseMethodAnnotation;
import com.xcase.annotations.XcaseParameterAnnotation;
import java.lang.annotation.*;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class WebApplicationDisplayHelper {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static String toMixedCase(String s) {
        if (s != null) {
            String upperCaseString = s.toUpperCase();
            String lowerCaseString = s.toLowerCase();
            String mixedCaseString = upperCaseString.substring(0, 1) + lowerCaseString.substring(1);
            return mixedCaseString;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static Method hasMethod(Object o, String s) {
        try {
            if (o != null) {
                Class objectClass = o.getClass();
                return objectClass.getMethod(s, (Class[]) null);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean hasName(Object o) {
        boolean hasName = false;
        try {
            if (o != null) {
                Class objectClass = o.getClass();
                Method nameMethod = objectClass.getMethod("getName", (Class[]) null);
                if (nameMethod != null) {
                    hasName = true;
                } else {

                }
            } else {

            }

            return hasName;
        } catch (Exception e) {
            return hasName;
        }
    }

    public static boolean hasXcaseParameterAnnotations(Method m) {
        boolean hasAnnotations = false;
        Annotation[][] methodParameterAnnotations = m.getParameterAnnotations();
        for (int i = 0; i < methodParameterAnnotations.length; i++) {
            Annotation[] parameterAnnotations = methodParameterAnnotations[i];
            for (int j = 0; j < parameterAnnotations.length; j++) {
                Annotation annotation = parameterAnnotations[j];
                if (annotation instanceof XcaseParameterAnnotation) {
                    hasAnnotations = true;
                }
            }
        }

        return hasAnnotations;
    }

    @SuppressWarnings("unchecked")
    public static String renderMethod(int methodId, Method method) {
        //LOGGER.debug("starting renderMethod()");
        StringBuffer methodStringBuffer = new StringBuffer();
        try {
            String methodName = method.getName();
            Class[] parameterClassArray = method.getParameterTypes();
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();
            if (parameterClassArray.length == 0) {
                methodStringBuffer.append("<li class=\"menuItem\"><a href=\"index.jsp?method=" + methodName + "\">" + methodName + "</a></li>");
            } else if (hasXcaseParameterAnnotations(method)) {
                methodStringBuffer.append("<li class=\"menuItem\"><a href=\"javascript:executeMethod(" + methodId + ");\" >" + methodName + "</a></li>");
            } else {

            }
        } catch (Exception e) {

        }

        return new String(methodStringBuffer);
    }

    @SuppressWarnings("unchecked")
    public static String getMethodGroup(Method method) throws Exception {
//        LOGGER.debug("starting getMethodGroup(Method method)");
        String group = "hide";
        try {
            Class methodAnnotationClass = Class.forName("com.xcase.annotations.XcaseMethodAnnotation");
//            LOGGER.debug("got methodAnnotationClass");
            XcaseMethodAnnotation methodAnnotation = (XcaseMethodAnnotation) method.getAnnotation(methodAnnotationClass);
//            LOGGER.debug("got methodAnnotation");
            group = methodAnnotation.group();
            if (group != null) {
//                LOGGER.debug("group is not null " + group);
                return group;
            } else {
//                LOGGER.debug("group is null");
                return "hide";
            }
        } catch (Exception e) {
            return "hide";
        }
    }

    /**
     *
     * @param sourceHashMap
     * @return a sorted by key TreeMap
     * @throws java.lang.Exception
     */
    @SuppressWarnings("unchecked")
    public static TreeMap sortByKeys(HashMap sourceHashMap) throws Exception {
        LOGGER.debug("starting sortByKeys(HashMap sourceHashMap)");
        TreeMap sortedTreeMap = new TreeMap(sourceHashMap);
        return sortedTreeMap;
    }

    public static boolean containsString(String[] array, String target) {
        //LOGGER.debug("starting containsString()");
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(target)) {
                return true;
            }
        }

        return false;
    }

    private WebApplicationDisplayHelper() {
    }
}
