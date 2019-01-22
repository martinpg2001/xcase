/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

import java.util.*;

public class PartyData {

    public AddressData[] addresses;
    public AliasData[] aliases;
    public String aventionId;
    public BlackbookEntryData[] blackbookEntries;
    public String bureauVanDijkId;
    public String country;
    public String description;
    public String dunsNumber;
    public externalDataProviderValues externalDataProvider;
    public boolean includeCorporateTree;
    public String name;
    public NoteData[] notes;
    public String other;
    public String partyId;
    public String partyType;
    public String[] searchTerms;
    public WarningData[] warnings;
    public boolean company___risk;
    public String company___region;
    public Date company___foundation;
    public boolean person___employee;
    public String person___region;
    public Date person___dateOfBirth;

    public enum externalDataProviderValues {
        None,
        DunAndBradstreet,
        Hoovers,
        Avention,
        BureauVanDijk,
    }

}
