package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.AddressFormData;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String country;
    private String zipCode;
    private String city;
    private String street;
    private String number;
    private String coordinate;

    public Address() {
    }

    public Address(AddressFormData addressFormData) {
        this.country = addressFormData.getCountry();
        this.zipCode = addressFormData.getZipCode();
        this.city = addressFormData.getCity();
        this.street = addressFormData.getStreet();
        this.number = addressFormData.getNumber();
        this.coordinate = addressFormData.getCoordinate();
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
