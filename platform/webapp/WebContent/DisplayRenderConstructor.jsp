<%@ page import="com.xcase.annotations.*" %>
<%@ page import="com.xcase.webapp.helpers.*" %>
<%@ page import="com.xcase.renderers.*" %>
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
    int methodIntId = -1;
    Method[] sessionMethods = null;
    Object sessionObject = null;
    String methodName = null;
    try {
        sessionObject = (Object) session.getAttribute("sessionObject");
        LOGGER.debug("got session object");
    } catch (Exception e) {
    	LOGGER.warn("exception getting session object: " + e.getMessage());
    }
%>

<%!
    String renderConstructor(Constructor method) {
	    Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
        LOGGER.debug("starting renderConstructor()");
        StringBuffer methodStringBuffer = new StringBuffer();
        try {
            String methodName = method.getName();
            LOGGER.debug("methodName is " + methodName);
            Class[] parameterClassArray = method.getParameterTypes();
            LOGGER.debug("parameterClassArray length is " + parameterClassArray.length);
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();
            if (hasXcaseParameterAnnotations(method)) {
                LOGGER.debug("method has parameter annotations");
                methodStringBuffer.append("<form action=index.jsp method=post target=mainWindow onSubmit=\"return processForm(this) && window.close();\" >\n");
                methodStringBuffer.append("<input name=\"constructor\" value=\"constructor\" type=\"hidden\" >\n");
                methodStringBuffer.append("<input name=\"sessionClass\" value=\"" + methodName +  "\" type=\"hidden\" >\n");
                methodStringBuffer.append("<table cellpadding=2 rules=all>\n");
                for (int j = 0; j < parameterClassArray.length; j++) {
                    LOGGER.debug("next parameter");
                    Class  parameterClass = parameterClassArray[j];
                    String parameterClassName = parameterClass.getName();
                    Annotation[] parameterAnnotations  = methodParameterAnnotations[j];
                    for (int k = 0; k < parameterAnnotations.length; k++) {
                        XcaseParameterAnnotation xcaseParameterAnnotation = (XcaseParameterAnnotation) parameterAnnotations[k];
                        String name = xcaseParameterAnnotation.name();
                        LOGGER.debug("name is " + name);
                        String displayType = xcaseParameterAnnotation.displayType();
                        LOGGER.debug("displayType is " + displayType);
                        if (displayType.equals("boolean")) {
                        	//LOGGER.debug("displayType is boolean");
                        	methodStringBuffer.append("<tr><td>" + name + "</td><td><input class=\"boolean\" id=\"" + name + "\" name=\"display" + name + "\" type=\"checkbox\" /></td></tr>\n");
                        } else if (displayType.equals("int")) {
                        	//LOGGER.debug("displayType is int");
                        	methodStringBuffer.append("<tr><td>" + name + "</td><td><input class=\"int\" id=\"" + name + "\" name=\"display" + name + "\" type=\"text\" /></td></tr>\n");
                        } else if (displayType.equals("text")) {
                            //LOGGER.debug("displayType is text");
                            String nameValue = null;
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
                LOGGER.debug("method has no parameter annotations");
            }
        } catch (Exception e) {

        }

        return new String(methodStringBuffer);
    }

    boolean hasXcaseParameterAnnotations(Constructor m) {
    	Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
        boolean hasXcaseAnnotations = false;
        Annotation[][] methodParameterAnnotations = m.getParameterAnnotations();
        LOGGER.debug("got annotations");
        for (int i = 0; i < methodParameterAnnotations.length; i++) {
        	LOGGER.debug("next parameterAnnotations");
            Annotation[] parameterAnnotations =  methodParameterAnnotations[i];
            LOGGER.debug("parameterAnnotations length is " + parameterAnnotations.length);
            for (int j = 0; j < parameterAnnotations.length; j++) {
            	LOGGER.debug("next annotation");
                Annotation annotation = parameterAnnotations[j];
                LOGGER.debug("annotation class is " + annotation.getClass().getName());
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
<title>Enter Constructor Parameters</title>
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
    String renderConstructorString = "";
    try {
    	//String sessionClassString = getServletContext().getInitParameter("SessionObjectClass");
    	String sessionClassString = request.getParameter("sessionClass");
    	LOGGER.debug("sessionClassString is " + sessionClassString);
    	Class sessionClass = Class.forName(sessionClassString);
        Constructor[] constructors = sessionClass.getDeclaredConstructors();
        LOGGER.debug("length of constructors is " + constructors.length);
        renderConstructorString = renderConstructor(constructors[0]);
        LOGGER.debug("rendered constructor");
    } catch (Exception e) {
    	LOGGER.warn("exception rendering constructor: " + e.getMessage());
    	renderConstructorString = "Exception rendering constructor: " + e.getMessage();
    }
%>
<%= renderConstructorString %>
</div>

</center>

</body>
</html>