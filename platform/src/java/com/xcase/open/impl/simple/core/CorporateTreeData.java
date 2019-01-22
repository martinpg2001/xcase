/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class CorporateTreeData {

    public String name;
    public String rootCompanyId;
    public providerTypeValues providerType;
    public ExternalCompanyData rootCompany;
    public ExternalCompanyChangeData[] changes;

    public enum providerTypeValues {
        Custom,
        DunAndBradstreet,
        Hoovers,
        Avention,
        BureauVanDijk,
    }

}
