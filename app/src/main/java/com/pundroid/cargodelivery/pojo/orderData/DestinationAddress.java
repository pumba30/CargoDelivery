package com.pundroid.cargodelivery.pojo.orderData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pumba30 on 28.09.2015.
 */
public class DestinationAddress {
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("houseNumber")
    @Expose
    private String houseNumber;

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode The zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return The street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return The houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber The houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
