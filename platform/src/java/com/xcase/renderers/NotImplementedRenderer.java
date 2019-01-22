package com.xcase.renderers;

public class NotImplementedRenderer extends ClassRenderer implements IObjectRenderer {

    public NotImplementedRenderer() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String renderTableHeader() throws Exception {
        return "<tr><th>Message</th></tr>\n";
    }

    @Override
    public String renderObject(Object object, boolean list) throws Exception {
        LOGGER.debug("starting renderObject()");
        if (list) {
            return "<tr><td>No implemented renderer for " + object.toString() + "</td><tr>\n";
        } else {
            return "No implemented renderer for " + object.toString();

        }
    }

    @Override
    public String renderObject(Object object) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
