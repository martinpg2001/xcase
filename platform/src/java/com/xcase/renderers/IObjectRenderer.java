package com.xcase.renderers;

import java.lang.Class;

public interface IObjectRenderer {
    public String renderTableHeader()throws Exception ;
    public String renderObject(Object object, boolean list) throws Exception;
    public String renderObject(Object object)throws Exception ;
}
