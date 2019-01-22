/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

import java.util.*;

/*
/// Interface for Web Proxy for TermsApi
 */
public interface ITermsApiWebProxy {

    TermData[] GetTerms(String filter__category, String filter__clientId, String filter__matterId, String filter__name, Date filter__effectiveStartDate, Date filter__effectiveEndDate);
}
