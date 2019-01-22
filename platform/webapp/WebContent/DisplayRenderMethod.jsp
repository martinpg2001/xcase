<%@ page import="com.xcase.annotations.*" %>
<%@ page import="com.xcase.webapp.helpers.*" %>
<%@ page import="com.xcase.webapp.renderers.*" %>
<%@ page import="com.xcase.webapp.renderers.java.lang.*" %>
<%@ page import="java.lang.annotation.*" %>
<%@ page import="java.lang.invoke.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.logging.log4j.*" %>

<%
    Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    boolean byName = false;
    Exception exception = null;
    HashMap dynamicSelectionsHashMap = new HashMap();
    int methodIntId = -1;
    Map contextMap = null;
    Method[] sessionMethods = null;
    Object sessionObject = null;
    String methodName = null;
    try {
        sessionObject = (Object) session.getAttribute("sessionObject");
        LOGGER.debug("got session object");
        sessionMethods = (Method[]) session.getAttribute("sessionMethods");
        LOGGER.debug("got session methods");
        Arrays.sort(sessionMethods, new Comparator<Method>() {
            @Override
            public int compare(Method a, Method b) {
                return a.getName().compareTo(b.getName());
            }
        });
        LOGGER.debug("sorted session methods");
        for (Method method : sessionMethods) {
        	LOGGER.debug("method name is " + method.getName());
        }
        
        byName = false;
        LOGGER.debug("about to get context map");
        contextMap = request.getParameterMap();
        LOGGER.debug("got map of parameters of size " + contextMap.size());
        String methodId = request.getParameter("methodID");
        methodName = request.getParameter("methodName");
        if (methodName != null && !methodName.equals("")) {
            byName = true;
        } else {
            try {
                methodIntId = Integer.parseInt(methodId);
            } catch (Exception e) {

            }
        }
    } catch (Exception e) {
        LOGGER.warn("an exception has occurred: " + e.toString());
        exception = e;
    }
%>

<%!
    Method getMethodByID(Method[] methods, int id) {
        if (id >= 0 && id < methods.length) {
            return methods[id];
        }

        return null;
    }

    Method getMethodByName(Method[] methods, String methodName) {
        for (int i = 0; i < methods.length; i++) {
            if (methodName.equals(methods[i].getName())) {
                return methods[i];
            }
        }

        return null;
    }

    String renderMethod(Method method, HashMap dynamicSelectionsHashMap, Map contextMap) {
        //LOGGER.debug("starting renderMethod()");
        StringBuffer methodStringBuffer = new StringBuffer();
        //LOGGER.debug("got map of parameters of size " + contextMap.size());
        try {
            String methodName = method.getName();
            //LOGGER.debug("methodName is " + methodName);
            Class[] parameterClassArray = method.getParameterTypes();
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();
            if (hasXcaseParameterAnnotations(method)) {
                //LOGGER.debug("method has parameter annotations");
                methodStringBuffer.append("<form action=index.jsp method=post target=mainWindow onSubmit=\"return processForm(this) && window.close();\" >\n");
                methodStringBuffer.append("<input name=\"method\" value=\"" + methodName + "\" type=\"hidden\" >\n");
                methodStringBuffer.append("<table cellpadding=2 rules=all>\n");
                for (int j = 0; j < parameterClassArray.length; j++) {
                    //LOGGER.debug("next parameter");
                    Class parameterClass = parameterClassArray[j];
                    String parameterClassName = parameterClass.getName();
                    Annotation[] parameterAnnotations  = methodParameterAnnotations[j];
                    for (int k = 0; k < parameterAnnotations.length; k++) {
                        XcaseParameterAnnotation xcaseParameterAnnotation = (XcaseParameterAnnotation) parameterAnnotations[k];
                        String name = xcaseParameterAnnotation.name();
                        //LOGGER.debug("name is " + name);
                        String displayType = xcaseParameterAnnotation.displayType();
                        //LOGGER.debug("displayType is " + displayType);
                        if (displayType.equals("boolean")) {
                        	//LOGGER.debug("displayType is boolean");
                        	methodStringBuffer.append("<tr><td>" + name + "</td><td><input class=\"boolean\" id=\"" + name + "\" name=\"display" + name + "\" type=\"checkbox\" /></td></tr>\n");
                        } else if (displayType.equals("int")) {
                        	//LOGGER.debug("displayType is int");
                        	methodStringBuffer.append("<tr><td>" + name + "</td><td><input class=\"int\" id=\"" + name + "\" name=\"display" + name + "\" type=\"text\" /></td></tr>\n");
                        } else if (displayType.equals("text")) {
                            //LOGGER.debug("displayType is text");
                            String nameValue = null;
                            if (contextMap != null && contextMap.containsKey(name)) {
                                //LOGGER.debug("contextMap contains key for " + name);
                                String[] nameValueArray = (String[]) contextMap.get(name);
                                nameValue = nameValueArray[0];
                                //LOGGER.debug("nameValue is " + nameValue);
                            } else {
                                //LOGGER.debug("contextMap does not contain key for " + name);
                            }

                            //LOGGER.debug("nameValue is " + nameValue);
                            if (nameValue != null) {
                                //LOGGER.debug("nameValue is " + nameValue);
                                methodStringBuffer.append("<tr><td>" + name + "</td><td><input readonly type=text name=display" + name + " value=" + nameValue + " /></td></tr>");
                            } else {
                                //LOGGER.debug("nameValue is null");
                                methodStringBuffer.append("<tr><td>" + name + "</td><td><input class=\"text\" name=\"display" + name + "\" type=\"text\" /></td></tr>\n");
                            }

                            //LOGGER.debug("finished parameter row");
                        } else {
                            //LOGGER.warn("unrecognized display type " + displayType);
                        }
                    }
                }
            
                methodStringBuffer.append("<tr><td><input name=submit value=" + methodName + " type=submit></td>");
                methodStringBuffer.append("<td><input name=cancel value=Cancel type=button onClick=javascript:window.close();></td></tr>\n");
                methodStringBuffer.append("</table>\n");
                methodStringBuffer.append("</form>\n");
            } else {
                //LOGGER.debug("displayTypemethod has no parameter annotations");
            }
        } catch (Exception e) {

        }

        return new String(methodStringBuffer);
    }

    boolean hasXcaseParameterAnnotations(Method m) {
        boolean hasXcaseAnnotations = false;
        Annotation[][] methodParameterAnnotations = m.getParameterAnnotations();
        for (int i = 0; i < methodParameterAnnotations.length; i++) {
            Annotation[] parameterAnnotations =  methodParameterAnnotations[i];
            for (int j = 0; j < parameterAnnotations.length; j++) {
                Annotation annotation = parameterAnnotations[j];
                if (annotation instanceof XcaseParameterAnnotation) {
                	hasXcaseAnnotations = true;
                }
            }
        }
        
        return hasXcaseAnnotations;
    }
%>

<html>
<head>
<title>Enter Parameters</title>
<script>
function processForm(element) {
	/* Check integer fields for values */
    var $formElements = Array.from(element.elements);
    var $intInputs = $formElements.filter(el => { return (el.getAttribute('class') == 'int')});
    var $intInputsLength = $intInputs.length;
    for (var j = 0; j < $intInputsLength; j++) {
        var id = $intInputs[j].id;
        var inputName = document.getElementById(id).name;
    	var inputValue = document.getElementById(id).value;
    	if (inputValue == '' || isNaN(inputValue)) {
    		alert('inputValue  for ' + inputName + ' is not a number');
    		return false;
    	}
    }
    
    /* Make sure all checkboxes are returned */
    var $checkboxes = $formElements.filter(el => { return (el.getAttribute('type') == 'checkbox')});
    var $arrayLength = $checkboxes.length;
    for (var i = 0; i < $arrayLength; i++) {
        var id = $checkboxes[i].id;
        if ($checkboxes[i].checked == true) {
            document.getElementById(id).value = 1;
        } else {
        	document.getElementById(id).value = 0;
        	document.getElementById(id).checked = true;
        }
    }
    
    return true;
}

</script>
</head>
<body class="body">
<center>
<div>
<%
    String renderMethodString;
    if (exception != null) {
        renderMethodString = exception.toString();
    } else {
        Method method = null;
        if (byName) {
            method = getMethodByName(sessionMethods, methodName);
        } else {
            method = getMethodByID(sessionMethods, methodIntId);
        }
        
        renderMethodString = renderMethod(method, dynamicSelectionsHashMap, contextMap);
    }
%>
<%= renderMethodString %>
</div>

</center>

</body>
</html>