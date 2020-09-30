package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.Address;

public class AddressFormData {

    private String country;
    private String zipCode;
    private String city;
    private String street;
    private String number;
    private String coordinate;


    public AddressFormData(Address address) {
        this.country = address.getCountry();
        this.zipCode = address.getZipCode();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.coordinate = address.getCoordinate();
    }

    public AddressFormData() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

}
