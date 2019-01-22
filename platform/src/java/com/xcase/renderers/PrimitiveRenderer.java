package com.xcase.renderers;

public class PrimitiveRenderer extends ClassRenderer implements IObjectRenderer {

    public PrimitiveRenderer() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String renderTableHeader() throws Exception {
        return "<tr><th>Value</th></tr>\n";
    }

    @Override
    public String renderObject(Object object, boolean list) throws Exception {
        LOGGER.debug("starting renderObject()");
        if (list) {
            return "<tr><td>" + object.toString() + "</td><tr>\n";
        } else {
            return object.toString();

        }
    }

    @Override
    public String renderObject(Object object) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
