/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface SearchRequest extends BoxRequest {

    /**
     *
     * @return query
     */
    public String getQuery();

    /**
     *
     * @param query
     */
    public void setQuery(String query);

    /**
     *
     * @return limit
     */
    public int getLimit();

    /**
     *
     * @param limit
     */
    public void setLimit(int limit);

    /**
     *
     * @return offset
     */
    public int getOffset();

    /**
     *
     * @param offset
     */
    public void setOffset(int offset);
}
