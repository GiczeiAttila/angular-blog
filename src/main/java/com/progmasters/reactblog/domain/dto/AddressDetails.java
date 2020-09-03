package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.Address;

public class AddressDetails {

    private String country;
    private String zipCode;
    private String city;
    private String street;
    private String number;
    private String coordinate;

    public AddressDetails(Address address) {
        this.country = address.getCountry();
        this.zipCode = address.getZipCode();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.coordinate = address.getCoordinate();
    }


    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCoordinate() {
        return coordinate;
    }
}
