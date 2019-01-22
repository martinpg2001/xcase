<%@ page import="com.xcase.annotations.*"%>
<%@ page import="com.xcase.renderers.*"%>
<%@ page import="com.xcase.webapp.example.*"%>
<%@ page import="com.xcase.webapp.helpers.*"%>
<%@ page import="java.lang.annotation.*"%>
<%@ page import="java.lang.invoke.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.logging.log4j.*"%>

<%
    System.setProperty("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID", "1.0");
    Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    try {
        /* We start by assuming that both the session object and the result object are null. If the inbound request 
           is a constructor request, then use the parameters to create the session object; otherwise, the session object must be not null,
           and we use the request to invoke a method on the session object */
        Object sessionObject = null;
        String sessionClassString = request.getParameter("sessionClass");//getServletContext().getInitParameter("SessionObjectClass");
        LOGGER.debug("sessionClassString is " + sessionClassString);
        Class sessionClass = null;
        if (sessionClassString != null) {
        	sessionClass = Class.forName(sessionClassString);
        } else {
        	LOGGER.debug("sessionClassString is null");
        }
        
        Object resultObject = null;
        boolean isConstructor = false;
        if (request.getParameter("constructor") != null) {
            isConstructor = true;
        }

        HashMap requestParameterHashMap = processParameters(request);
        if (isConstructor) {
            LOGGER.debug("isConstructor is true");
            Constructor[] constructors = sessionClass.getDeclaredConstructors();
            LOGGER.debug("length of constructors is " + constructors.length);
            Constructor constructor = constructors[0];
            HashMap<String, String> constructorParameterHashMap = new HashMap<String, String>();
            Class[] parameterClasses = constructor.getParameterTypes();
            Parameter[] parameterArray = constructor.getParameters();
            String[] parameterNameArray = new String[parameterArray.length];
            int i = 0;
            for (Parameter parameter : parameterArray) {
                LOGGER.debug("parameter name is " + parameter.getName());
                LOGGER.debug("parameter type is " + parameter.getType().getName());
                Annotation[] annotationArray = parameter.getAnnotations();
                LOGGER.debug("parameter annotations is " + annotationArray);
                String parameterName = null;
                for (Annotation annotation : annotationArray) {
                    parameterName = ((XcaseParameterAnnotation) annotation).name();
                    constructorParameterHashMap.put(parameterName, parameter.getType().getName());
                }

                parameterNameArray[i] = parameterName;
                i++;
            }

            /* At this point, we have the constructorParameterHashMap and the requestParameterHashMap. We need to pair these up, to construct
            the array of parameter objects for the constructor */
            Object[] parameterObjectArray = createParameterObjectArray(parameterNameArray,
                constructorParameterHashMap, requestParameterHashMap);
            LOGGER.debug("about to invoke constructor");
            sessionObject = constructor.newInstance(parameterObjectArray);
            LOGGER.debug("created sessionObject of type " + sessionObject.getClass().getCanonicalName());
            session.setAttribute("sessionObject", sessionObject);
            LOGGER.debug("added sessionObject to session");
        } else {
            LOGGER.debug("isConstructor is false");
            String methodString = request.getParameter("method");
            LOGGER.debug("methodString is " + methodString);
            sessionObject = session.getAttribute("sessionObject");
            /* If the session object is null, then do not attempt to invoke a method */
            if (sessionObject == null) {
                LOGGER.debug("sessionObject is null");
                //throw new Exception("Session object is null");
            } else {
                Class testClass = sessionObject.getClass();
                Method executeMethod = WebApplicationControlHelper.matchMethodToParameters(testClass,
                        methodString, requestParameterHashMap);
                Class[] parameterClassArray = executeMethod.getParameterTypes();
                Object[] parameterObjects = new Object[parameterClassArray.length];
                String[] methodParameterNames = populateMethodParameterNames(parameterClassArray,
                        executeMethod);
                for (int i = 0; i < methodParameterNames.length; i++) {
                    String methodParameterName = methodParameterNames[i];
                    if (requestParameterHashMap.get(methodParameterName) != null) {
                        Object parameterValue = requestParameterHashMap.get(methodParameterName);
                        //                         LOGGER.debug("parameter value for " + methodParameterName + " is " + parameterValue);
                        parameterObjects[i] = parameterValue;
                    }
                }

                LOGGER.debug("matched method and parameters");
                if (executeMethod != null) {
                    LOGGER.debug("executeMethod is not null");
                    Class[] parameterClasses = executeMethod.getParameterTypes();
                    LOGGER.debug("got parameter classes");
                    resultObject = null;
                    if (parameterClasses.length > 0) {
                        LOGGER.debug("about to adjust parameter types");
                        Object[] adjustedParameterObjectArray = new Object[parameterClasses.length];
                        for (int i = 0; i < parameterClasses.length; i++) {
                            Object parameterObject = parameterObjects[i];
                            Object adjustedParameterObject = null;
                            LOGGER.debug("parameterObject is " + parameterObject);
                            Class parameterClass = parameterClasses[i];
                            String className = parameterClass.getName();
                            LOGGER.debug("className is " + className);
                            adjustedParameterObject = createParameterObject(className,
                                    (String) parameterObject);
                            adjustedParameterObjectArray[i] = adjustedParameterObject;
                        }

                        LOGGER.debug("about to invoke method");
                        resultObject = executeMethod.invoke(sessionObject, adjustedParameterObjectArray);
                        LOGGER.debug("invoked method with parameters");
                    } else {
                        LOGGER.debug("no need to get parameter values");
                        resultObject = executeMethod.invoke(sessionObject);
                        LOGGER.debug("invoked method without parameters");
                    }

                    LOGGER.debug("resultObject is " + resultObject);
                    request.setAttribute("resultObject", resultObject);
                } else {
                    LOGGER.debug("executeMethod is null");
                    throw new Exception("Matching method not found!");
                }
            }
        }
    } catch (Exception e) {
        LOGGER.warn("exception invoking method: " + e.toString());
        e.printStackTrace();
        request.setAttribute("exception", e);
        LOGGER.debug("set exception attribute");
    }
%>

<%!
    Object[] createParameterObjectArray(String[] parameterNameArray,
            HashMap<String, String> constructorParameterHashMap, HashMap<String, String> requestParameterHashMap) {
        Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
        Object[] parameterObjectArray = new Object[parameterNameArray.length];
        for (int i = 0; i < parameterNameArray.length; i++) {
            String key = parameterNameArray[i];
            LOGGER.debug("key is " + key);
            String typeName = constructorParameterHashMap.get(key);
            LOGGER.debug("typeName is " + typeName);
            String parameterValue = requestParameterHashMap.get(key);
            LOGGER.debug("parameterValue is " + parameterValue);
            Object parameterObject = createParameterObject(typeName, parameterValue);
            LOGGER.debug("parameterObject is " + parameterObject);
            parameterObjectArray[i] = parameterObject;
        }

        return parameterObjectArray;
    }

    Object createParameterObject(String className, String parameterValue) {
        Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
        Object adjustedParameterObject = null;
        if (className.equals("java.lang.String")) {
            LOGGER.debug("parameter type is String");
            adjustedParameterObject = parameterValue;
        } else if (className.equals("java.lang.Boolean")) {
            LOGGER.debug("parameter type is Boolean");
            adjustedParameterObject = false;
            if ("1".equals((String) parameterValue)) {
                adjustedParameterObject = true;
            } else {

            }
        } else if (className.equals("boolean")) {
            LOGGER.debug("parameter type is boolean");
            adjustedParameterObject = Boolean.valueOf(false);
            if ("1".equals((String) parameterValue)) {
                adjustedParameterObject = Boolean.valueOf(true);
            } else {

            }
        } else if (className.equals("java.sql.Timestamp")) {
            LOGGER.debug("parameter type is Timestamp");
            adjustedParameterObject = java.sql.Timestamp.valueOf(parameterValue);
        } else if (className.equals("java.util.HashMap")) {
            LOGGER.debug("parameter type is HashMap");
            //do nothing
        } else if (className.equals("java.lang.Integer")) {
            LOGGER.debug("parameter type is Integer");
            adjustedParameterObject = Integer.valueOf(parameterValue);
        } else if (className.equals("int")) {
            LOGGER.debug("parameter type is int");
            adjustedParameterObject = Integer.parseInt(parameterValue);
        } else if (className.equals("java.lang.Long")) {
            LOGGER.debug("parameter type is Integer");
            adjustedParameterObject = Long.valueOf(parameterValue);
        } else if (className.equals("long")) {
            LOGGER.debug("parameter type is int");
            adjustedParameterObject = Long.parseLong(parameterValue);
        } else {
            LOGGER.debug("parameter type is unrecognized");
        }

        return adjustedParameterObject;
    }

    String[] populateMethodParameterNames(Class[] methodParameterNames, Method classMethod) {
        String[] methodParameterNameArray = new String[classMethod.getParameterTypes().length];
        Annotation[][] methodParameterAnnotations = classMethod.getParameterAnnotations();
        //LOGGER.debug("" + classMethod + " got " + methodParameterAnnotations.length + " method annotations");
        for (int j = 0; j < methodParameterNameArray.length; j++) {
            String name = null;
            Annotation[] parameterAnnotations = methodParameterAnnotations[j];
            //LOGGER.debug("got " + parameterAnnotations.length + " annotations");
            for (int k = 0; k < parameterAnnotations.length; k++) {
                XcaseParameterAnnotation xcaseParameterAnnotation = (XcaseParameterAnnotation) parameterAnnotations[k];
                //LOGGER.debug("parameterAnnotations[" + k + "] is " + parameterAnnotations[k]);
                name = xcaseParameterAnnotation.name();
                //LOGGER.debug("parameter name is " + name);
            }

            methodParameterNameArray[j] = name;
        }

        return methodParameterNameArray;
    }

    HashMap processParameters(HttpServletRequest request) {
        Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
        HashMap requestParameterHashMap = new HashMap();
        String[] idStrings = request.getParameterValues("bulkid");
        int[] ids = null;
        if (idStrings != null) {
            ids = new int[idStrings.length];
            for (int i = 0; i < idStrings.length; i++) {
                try {
                    ids[i] = Integer.parseInt(idStrings[i]);
                } catch (Exception e) {
                    LOGGER.debug("invalid bulkid " + idStrings[i]);
                    ids[i] = -1;
                }
            }
        }

        if (ids != null) {
            LOGGER.debug("ids is not null");
            requestParameterHashMap.put("ids", ids);
        }

        HashMap inputParameterHashMap = new HashMap();
        for (Enumeration parameterEnumeration = request.getParameterNames(); parameterEnumeration.hasMoreElements();) {
            String requestParameterName = (String) parameterEnumeration.nextElement();
            LOGGER.debug("requestParameterName is " + requestParameterName);
            if (requestParameterName.startsWith("display")) {
                String parameterName = requestParameterName.substring(7);
                LOGGER.debug("parameterName is " + parameterName);
                String[] parameterStringArray = request.getParameterValues(requestParameterName);
                LOGGER.debug("parameterStringArray is " + parameterStringArray);
                if (parameterStringArray.length > 1) {
                    requestParameterHashMap.put(parameterName, parameterStringArray);
                } else {
                    requestParameterHashMap.put(parameterName, parameterStringArray[0]);
                }
            } else if (requestParameterName.startsWith("input")) {
                String parameterName = requestParameterName.substring(5);
                LOGGER.debug("parameterName is " + parameterName);
                String parameterValue = request.getParameter(requestParameterName);
                LOGGER.debug("parameterValue is " + parameterValue);
                inputParameterHashMap.put(parameterName, parameterValue);
            } else {
                LOGGER.debug("unprocessed parameter " + requestParameterName);
            }
        }

        if (inputParameterHashMap.size() > 0) {
            requestParameterHashMap.put("inputs", inputParameterHashMap);
        }

        return requestParameterHashMap;
    }
%>

<jsp:forward page="indexView.jsp" />