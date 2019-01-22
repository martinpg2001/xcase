/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

public class CompanyBoardMembersData {

    public String companyId;
    public providerTypeValues providerType;
    public BoardMemberData[] boardMembers;

    public enum providerTypeValues {
        Custom,
        DunAndBradstreet,
        Hoovers,
        Avention,
        BureauVanDijk,
    }

}
