<%@ page import="com.xcase.webapp.helpers.*" %>
<%@ page import="com.xcase.renderers.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.Writer" %>
<%@ page import="java.lang.annotation.*" %>
<%@ page import="java.lang.invoke.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.logging.log4j.*" %>

<%
	System.setProperty("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID", "1.0");
    Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    String rendererRoot = getServletContext().getInitParameter("RendererRoot");
    LOGGER.debug("rendererRoot is " + rendererRoot);
    ClassRenderer renderer = ClassRenderer.getInstance(rendererRoot);
    Object sessionObject = session.getAttribute("sessionObject");
    TreeMap sortedGroupsTreeMap = null;
    Exception exception = (Exception) request.getAttribute("exception");
    Object resultObject = null;
    if (sessionObject != null) {
    	LOGGER.debug("got session object " + sessionObject);
        Method[] sessionMethods = sessionObject.getClass().getMethods();
        LOGGER.debug("got " + sessionMethods.length + " methods");
        Arrays.sort(sessionMethods, new Comparator<Method>() {
            @Override
            public int compare(Method a, Method b) {
                return a.getName().compareTo(b.getName());
            }
        });
        LOGGER.debug("sorted methods");
        session.setAttribute("sessionMethods", sessionMethods);
        resultObject = request.getAttribute("resultObject");
        LOGGER.debug("got result object " + resultObject);
        sortedGroupsTreeMap = (TreeMap) session.getAttribute("sortedGroupsTreeMap");
        if (sortedGroupsTreeMap == null) {
        	LOGGER.debug("sortedGroupsTreeMap is null");
    	    sortedGroupsTreeMap = createGroupsTreeMapFromSessionObject(session, sessionObject);
    	    session.setAttribute("sortedGroupsTreeMap", sortedGroupsTreeMap);
        } else {
        	LOGGER.debug("sortedGroupsTreeMap is not null");
    	    sortedGroupsTreeMap = createGroupsTreeMapFromSessionObject(session, sessionObject);
    	    session.setAttribute("sortedGroupsTreeMap", sortedGroupsTreeMap);
        }
    } else {
    	LOGGER.debug("session object is null");
    }
%>

<%!
    TreeMap createGroupsTreeMapFromSessionObject(HttpSession session, Object sessionObject) throws Exception {
	    Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	    TreeMap sortedGroupsTreeMap = new TreeMap<String, String>();
	    if (sessionObject != null) {
	    	LOGGER.debug("sessionObject is not null");
	        Class sessionClass = sessionObject.getClass();
	        Method[] sessionMethods = (Method[]) session.getAttribute("sessionMethods");
	        LOGGER.debug("got " + sessionMethods.length + " methods");
	        HashMap<String, StringBuffer> groupsStringBufferHashMap = new HashMap<String, StringBuffer>();
	        for (int i = 0; i < sessionMethods.length; i++) {
	            Method method = sessionMethods[i];
	            LOGGER.debug("method name is " + method.getName());
	            String renderMethodString = WebApplicationDisplayHelper.renderMethod(i, method);
	            LOGGER.debug("renderMethodString is " + renderMethodString);
	            String group = WebApplicationDisplayHelper.getMethodGroup(method);
	            LOGGER.debug("group is " + group);
	            if (group != null && !group.equals("hide")) {
	                if (groupsStringBufferHashMap.get(group) == null) {
	                	groupsStringBufferHashMap.put(group, new StringBuffer());
	                }

	                StringBuffer groupStringBuffer = (StringBuffer) groupsStringBufferHashMap.get(group);
	                groupStringBuffer.append(renderMethodString);
	            }
	        }
	        
            Set<String> groupsKeySet = groupsStringBufferHashMap.keySet();
            LOGGER.debug("got key set of size " + groupsKeySet.size());
            HashMap<String, String> groupsHashMap = new HashMap<String, String>();
            Iterator<String> groupsIterator = groupsKeySet.iterator();
            while (groupsIterator.hasNext()) {
                String groupKeyString = (String) groupsIterator.next();
                //LOGGER.debug("groupKeyString is " + groupKeyString);
                String groupMenuString = ((StringBuffer) groupsStringBufferHashMap.get(groupKeyString)).toString();
                groupsHashMap.put(groupKeyString, groupMenuString);
                //LOGGER.debug("added " + groupKeyString + " to " + groupMenuString);
                LOGGER.debug("populated groups HashMap");
	        }
            
            sortedGroupsTreeMap = WebApplicationDisplayHelper.sortByKeys(groupsHashMap);
	    }
	    
	    return sortedGroupsTreeMap;
    }
%>

<html>
<head>
<link href="css/TestHarness.css" rel="stylesheet" type="text/css">
<script language="javascript">
    function executeSessionConstructor(sessionClass)
    {
        window.name = 'mainWindow';
        formWindow = window.open('DisplayRenderConstructor.jsp?sessionClass='+sessionClass,'','width=450,height=350,location=no,menubar=no,status=no,toolbar=no,screenX=400,screenY=400,top=400,left=400,titlebar=no');
    }

    function executeMethodByName(methodName, contextString)
    {
        window.name = 'mainWindow';
        formWindow = window.open('DisplayRenderMethod.jsp?methodName='+methodName+'&'+contextString,'','width=450,height=350,location=no,menubar=no,status=no,toolbar=no,screenX=400,screenY=400,top=400,left=400,titlebar=no');
    }

    function executeMethod(methodID)
    {
        window.name = 'mainWindow';
        formWindow = window.open('DisplayRenderMethod.jsp?methodID='+methodID,'','width=450,height=350,location=no,menubar=no,status=no,toolbar=no,screenX=400,screenY=400,top=400,left=400,titlebar=no');
    }

    function showDetails()
    {
        document.getElementById('stacktrace').style.visibility="visible";
    }
</script>
<title>Xcase Platform</title>
</head>

<body>
<div>
<form action=index.jsp method="post" target="mainWindow" onSubmit="return processForm(this) && window.close();" >
  <select id="sessionClass" name="sessionClass">
<%
    String sessionClasses = getServletContext().getInitParameter("SessionObjectClasses");
    String[] sessionClassArray = sessionClasses.split(",");
    for (String sessionClass : sessionClassArray) {
%>
    <option value="<%= sessionClass %>"><%= sessionClass %></option>
<%
    }
%>
  </select>
  <input name="submit" onClick="executeSessionConstructor(document.getElementById('sessionClass').value)" type="submit" value="createSession" >
</form>
</div>
<hr/>
<div class="action">
<ul id="menuBar">
<%
    LOGGER.debug("starting menu bar");
    if (sortedGroupsTreeMap != null) {
    	Set<String> menuGroupsKeySet = sortedGroupsTreeMap.keySet();
        LOGGER.debug("key set size is " + menuGroupsKeySet.size());
        Iterator<String> menuGroupsIterator = menuGroupsKeySet.iterator();
        while (menuGroupsIterator.hasNext()) {
            String groupKeyString = (String) menuGroupsIterator.next();
            String groupMenuString = (String) sortedGroupsTreeMap.get(groupKeyString);
%>
    <li class="menuLabel"><%= WebApplicationDisplayHelper.toMixedCase(groupKeyString) %>
      <ul class="menuContainer">
        <%= groupMenuString %>
      </ul>
    </li>
<%
        }
    }

    LOGGER.debug("finishing menu bar");
%>
</ul>
</div>

<div class="result">
<center>
<%
    String resultString = "";
    if (exception != null) {
%>
<p>Exception: <%= exception.toString() %>: <a href="javascript:showDetails()" />Details</></a></p>
<div id="stacktrace" style="text-align: left; visibility: hidden;" >
<%
    Writer stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);
    String stackTraceString = stringWriter.toString();
    stackTraceString = stackTraceString.replaceAll("\n", "<br/>").replaceAll("Caused by:", "<font color=\"red\">Caused by:</font>");
%>
<%= stackTraceString %>
</div>
<%
    } else {
        try {
            StringBuffer resultStringBuffer = new StringBuffer();
            resultStringBuffer.append(renderer.renderObject(resultObject, false));
            LOGGER.debug("rendered result object");
            resultString = resultStringBuffer.toString();
            LOGGER.debug("resultString is " + resultString);
        } catch (Exception e) {
            resultString = e.toString();
            LOGGER.warn("exception rendering result object");
        }
%>

<div class="header">
<%= resultString %>
</div>
<%
    }
%>
</div>

</center>

</body>
</html>