package com.xcase.renderers;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClassRenderer implements IObjectRenderer {
    private String rendererRoot = "com.xcase.webapp.renderers";
    
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static ClassRenderer instance = null;

    public static ClassRenderer getInstance() {
        if (instance == null) {
            instance = new ClassRenderer();
        }

        return instance;
    }
    
    public static ClassRenderer getInstance(String rendererRoot) {
        if (instance == null) {
            instance = new ClassRenderer();
            instance.setRendererRoot(rendererRoot);
        }

        return instance;
    }
    
    public void setRendererRoot(String rendererRoot) {
    	this.rendererRoot = rendererRoot;
    }

    @Override
    public String renderTableHeader() throws Exception {
        return "<tr><th>Type</th><th>Name</th><th>Actions</th><th>Bulk Actions</th></tr>\n";
    }

    @Override
    public String renderObject(Object object, boolean list) throws Exception {
        if (object == null) {
            LOGGER.debug("object is null");
            return "<p>Nothing to display!</p>";
        }

        Class<?> objectClass = object.getClass();
        LOGGER.debug("objectClass is " + objectClass);
        StringBuffer stringBuffer = new StringBuffer();
        if (objectClass.isArray()) {
            LOGGER.debug("object is Array");
            LOGGER.debug("Array has " + ((ArrayList) object).size() + " objects");
            return renderArray((Object[]) object);
        }

        if (object instanceof List) {
            LOGGER.debug("object is List");
            LOGGER.debug("List has " + ((List) object).size() + " objects");
            return renderList((List) object);
        }

        if (object instanceof Collection) {
            LOGGER.debug("object is Collection");
            LOGGER.debug("Collection has " + ((Collection) object).size() + " objects");
            return renderCollection((Collection) object);
        }

        if (object instanceof HashMap) {
            LOGGER.debug("object is HashMap");
            LOGGER.debug("HashMap has " + ((HashMap) object).size() + " objects");
            return renderHashMap((HashMap) object);
        }

        if (object instanceof Map) {
            LOGGER.debug("object is Map");
            LOGGER.debug("Map has " + ((Map) object).size() + " objects");
            return renderMap((Map) object);
        }

        if (object instanceof Set) {
            LOGGER.debug("object is Set");
            LOGGER.debug("Set has " + ((Set) object).size() + " objects");
            return renderSet((Set) object);
        }

        LOGGER.debug("object is not a list of any sort");
        IObjectRenderer renderer = getRendererFromUnknownHierarchy(object.getClass());
        if (renderer != null) {
            return renderer.renderObject(object, list);
        } else {
            return "Unimplemented object renderer for " + object.getClass().getName();
        }
    }

    @Override
    public String renderObject(Object object) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public IObjectRenderer getRenderer(Class objectClass) throws Exception {
        LOGGER.debug("starting getRenderer()");
        if (Class.forName("java.util.LinkedHashMap").isAssignableFrom(objectClass)) {
            LOGGER.debug("object is assignable from LinkedHashMap");
            return new ClassRenderer();
        } else if (Class.forName("java.util.LinkedList").isAssignableFrom(objectClass)) {
            LOGGER.debug("object is assignable from LinkedList");
            return new ClassRenderer();
        } else if (Boolean.class.isAssignableFrom(objectClass) || Integer.class.isAssignableFrom(objectClass) || String.class.isAssignableFrom(objectClass)) {
            LOGGER.debug("object is assignable from primitive");
            return new PrimitiveRenderer();
        } else {
            return getRendererFromUnknownHierarchy(objectClass);
        }
    }

    public IObjectRenderer getRendererFromUnknownHierarchy(Class objectClass) {
        if (objectClass != null) {
            String className = objectClass.getName();
            LOGGER.debug("className is " + className);
            if (Boolean.class.isAssignableFrom(objectClass) || Integer.class.isAssignableFrom(objectClass) || String.class.isAssignableFrom(objectClass)) {
                LOGGER.debug("object is assignable from primitive");
                return new PrimitiveRenderer();
            }
            
            String rendererName = className + "Renderer";
            LOGGER.debug("rendererName is " + rendererName);
            try {
                Class<?> rendererClass = Class.forName(rendererRoot + "." + rendererName);
                LOGGER.debug("found rendererClass");
                IObjectRenderer renderer = (IObjectRenderer) rendererClass.getDeclaredConstructor().newInstance();
                LOGGER.debug("created rendererClass");
                return renderer;
            } catch (ClassNotFoundException cnfe) {
                LOGGER.debug("exception getting class for name: " + cnfe.getMessage());
                Class<?> superClass = objectClass.getSuperclass();
                return getRendererFromUnknownHierarchy(superClass);
            } catch (NoSuchMethodException nsme) {
                LOGGER.warn("exception getting declared constructor: " + nsme.getMessage());
            } catch (InstantiationException ie) {
                LOGGER.warn("exception instantiating renderer: " + ie.getMessage());
            } catch (Exception e) {
                LOGGER.warn("exception getting " + rendererName + " :" + e.getMessage());
            }

            LOGGER.warn("unrecognized object class");
            return new NotImplementedRenderer();
        } else {
            LOGGER.warn("objectClass is null");
            return new ClassRenderer();
        }
    }

    public String renderArray(Object[] object) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        Object[] objectArray = (Object[]) object;
        LOGGER.debug("is array of length " + objectArray.length);
        if (objectArray.length == 0) {
            return "No results found";
        } else {
            stringBuffer.append("<form action=\"index.jsp\">\n");
            stringBuffer.append("<table class=\"contextListTable\">\n");
            Object resultFirstObject = objectArray[0];
            Class firstObjectClass = resultFirstObject.getClass();
            LOGGER.debug("firstObjectClass is " + firstObjectClass);
            IObjectRenderer renderer = getRenderer(firstObjectClass);
            String tableHeaderString = renderer.renderTableHeader();
            LOGGER.debug("rendered table header");
            stringBuffer.append(tableHeaderString);
            for (int i = 0; i < objectArray.length; i++) {
                Object resultNextObject = objectArray[i];
                stringBuffer.append(renderer.renderObject(resultNextObject, true));
            }

            stringBuffer.append("</table>\n");
            stringBuffer.append("</form>\n");
            return stringBuffer.toString();
        }
    }

    public String renderCollection(Collection object) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator resultIterator = ((Iterable) object).iterator();
        LOGGER.debug("collection has " + ((Collection) object).size() + " objects");
        if (((Collection) object).size() == 0) {
            return "No results found";
        } else {
            LOGGER.debug("starting collection form");
            stringBuffer.append("<form action=\"index.jsp\">\n");
            stringBuffer.append("<table class=\"contextListTable\">\n");
            Object resultFirstObject = resultIterator.next();
            Class firstObjectClass = resultFirstObject.getClass();
            LOGGER.debug("firstObjectClass is " + firstObjectClass);
            IObjectRenderer renderer = getRenderer(firstObjectClass);
            String tableHeaderString = renderer.renderTableHeader();
            LOGGER.debug("rendered table header");
            stringBuffer.append(tableHeaderString);
            stringBuffer.append(renderer.renderObject(resultFirstObject, true));
            while (resultIterator.hasNext()) {
//                LOGGER.debug("next object");
                Object resultNextObject = resultIterator.next();
                renderer = getRenderer(resultNextObject.getClass());
                stringBuffer.append(renderer.renderObject(resultNextObject, true));
            }

            stringBuffer.append("</table>\n");
            stringBuffer.append("</form>\n");
            return stringBuffer.toString();
        }
    }

    public String renderHashMap(HashMap object) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        LOGGER.debug("HashMap has " + ((HashMap) object).size() + " objects");
        if (((Map) object).size() == 0) {
            return "No results found";
        } else {
            Set resultKeySet = ((HashMap) object).keySet();
            Iterator resultKeyIterator = resultKeySet.iterator();
            stringBuffer.append("<form action=\"index.jsp\">");
            stringBuffer.append("<table class=\"contextListTable\">\n");
            Object resultFirstKeyObject = resultKeyIterator.next();
            Object resultFirstObject = ((HashMap) object).get(resultFirstKeyObject);
            Class firstObjectClass = resultFirstObject.getClass();
            LOGGER.debug("firstObjectClass is " + firstObjectClass);
            IObjectRenderer renderer = getRenderer(firstObjectClass);
            String tableHeaderString = renderer.renderTableHeader();
            LOGGER.debug("rendered table header");
            stringBuffer.append(tableHeaderString);
            stringBuffer.append(renderer.renderObject(resultFirstObject, true));
            LOGGER.debug("rendered resultFirstObject");
            while (resultKeyIterator.hasNext()) {
//              LOGGER.debug("next object");
                Object resultNextKeyObject = resultKeyIterator.next();
                Object resultNextObject = ((HashMap) object).get(resultNextKeyObject);
                renderer = getRenderer(resultNextObject.getClass());
                stringBuffer.append(renderer.renderObject(resultNextObject, true));
            }

            stringBuffer.append("</table>\n");
            stringBuffer.append("</form>\n");
            return stringBuffer.toString();
        }
    }

    public String renderList(List object) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator resultIterator = ((List) object).iterator();
        LOGGER.debug("collection has " + ((Collection) object).size() + " objects");
        if (((Collection) object).size() == 0) {
            return"No results found";
        } else {
            stringBuffer.append("<form action=\"index.jsp\">\n");
            stringBuffer.append("<table class=\"contextListTable\">\n");
            Object resultFirstObject = resultIterator.next();
            Class firstObjectClass = resultFirstObject.getClass();
            LOGGER.debug("firstObjectClass is " + firstObjectClass);
            IObjectRenderer renderer = getRenderer(firstObjectClass);
            String tableHeaderString = renderer.renderTableHeader();
            LOGGER.debug("rendered table header");
            stringBuffer.append(tableHeaderString);
            stringBuffer.append(renderer.renderObject(resultFirstObject, true));
            while (resultIterator.hasNext()) {
//                LOGGER.debug("next object");
                Object resultNextObject = resultIterator.next();
                renderer = getRenderer(resultNextObject.getClass());
                stringBuffer.append(renderer.renderObject(resultNextObject, true));
            }

            stringBuffer.append("</table>\n");
            stringBuffer.append("</form>\n");
            return stringBuffer.toString();
        }
    }

    public String renderMap(Map object) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        if (((Map) object).size() == 0) {
            return "No results found";
        } else {
            Set resultKeySet = ((Map) object).keySet();
            Iterator resultKeyIterator = resultKeySet.iterator();
            stringBuffer.append("<form action=\"index.jsp\">\n");
            stringBuffer.append("<table class=\"contextListTable\">\n");
            Object resultFirstKeyObject = resultKeyIterator.next();
            Object resultFirstObject = ((Map) object).get(resultFirstKeyObject);
            Class firstObjectClass = resultFirstObject.getClass();
            //LOGGER.debug("firstObjectClass is " + firstObjectClass);
            LOGGER.debug("firstObjectClass is " + firstObjectClass);
            IObjectRenderer renderer = getRenderer(firstObjectClass);
            String tableHeaderString = renderer.renderTableHeader();
            stringBuffer.append(tableHeaderString);
            stringBuffer.append(renderer.renderObject(resultFirstObject, true));
            while (resultKeyIterator.hasNext()) {
//              LOGGER.debug("next object");
                Object resultNextKeyObject = resultKeyIterator.next();
                Object resultNextObject = ((Map) object).get(resultNextKeyObject);
                renderer = getRenderer(resultNextObject.getClass());
                stringBuffer.append(renderer.renderObject(resultNextObject, true));
            }

            stringBuffer.append("</table>\n");
            stringBuffer.append("</form>\n");
            return stringBuffer.toString();
        }
    }

    public String renderSet(Set object) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        if (((Collection) object).size() == 0) {
            return "No results found";
        } else {
            Iterator resultIterator = ((Iterable) object).iterator();
            stringBuffer.append("<form action=\"index.jsp\">\n");
            stringBuffer.append("<table class=\"contextListTable\">\n");
            Object resultFirstObject = resultIterator.next();
            Class firstObjectClass = resultFirstObject.getClass();
            LOGGER.debug("firstObjectClass is " + firstObjectClass);
            IObjectRenderer renderer = getRenderer(firstObjectClass);
            String tableHeaderString = renderer.renderTableHeader();
            LOGGER.debug("rendered table header");
            stringBuffer.append(tableHeaderString);
            stringBuffer.append(renderer.renderObject(resultFirstObject, true));
            while (resultIterator.hasNext()) {
//              LOGGER.debug("next object");
              Object resultNextObject = resultIterator.next();
              renderer = getRenderer(resultNextObject.getClass());
              stringBuffer.append(renderer.renderObject(resultNextObject, true));
            }

            stringBuffer.append("</table>\n");
            stringBuffer.append("</form>\n");
            return stringBuffer.toString();
        }
    }
}
