/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class CompanyShareholdersData {

    public String companyId;
    public providerTypeValues providerType;
    public ShareholderData[] shareholders;

    public enum providerTypeValues {
        Custom,
        DunAndBradstreet,
        Hoovers,
        Avention,
        BureauVanDijk,
    }

}
