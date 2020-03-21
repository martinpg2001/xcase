package com.xcase.klearnow.objects;

public class Address {
    public String addressLine1;
    public String addressLine2;
    public String city;
    public String province;
    public String country;
    public String zip;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Address address = (Address) obj;
        return address.addressLine1.equals(this.addressLine1) && address.addressLine2.equals(this.addressLine2)
                && address.city.equals(this.city) && address.province.equals(this.province) 
                && address.country.equals(this.country) && address.zip.equals(this.zip);
    }

    @Override
    public int hashCode() {
        return this.addressLine1.hashCode();
    }
}
