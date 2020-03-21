package com.xcase.klearnow.objects;

public class Shipment {
    public String shipmentId;
    public String supplierEmail;
    public String description;
    public String referenceNumber;
    public String masterBolNumber;
    public String houseBolNumber;
    public String htsCode;
    public String vesselName;
    public String departureDate;
    public String arrivalDate;
    public ModeOfTransport modeOfTransport;
    public String originCountry;
    public String originCity;
    public String destinationCountry;
    public String destinationState;
    public String destinationCity;
    public String portOfLadingCode;
    public String portOfUnladingCode;
    public String portOfEntryCode;
    public String currentLocationFirm;
    public String examSiteFirm;
    public String goNumber;
    public String paymentTypeCode;
    public String examPortCode;
    public String supplierActorId;
    public String consigneeActorId;
    public String sellerActorId;
    public String buyerActorId;
    public String manufacturerActorId;
    public String shipperActorId;
    public String stufferActorId;
    public String consolidatorActorId;
    public String notifyPartyActorId;
    
    public enum ModeOfTransport {
    	AIR, OCEAN, RAIL, TRUCK;
    }
}
