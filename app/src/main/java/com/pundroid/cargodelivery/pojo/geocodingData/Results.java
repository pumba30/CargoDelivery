package com.pundroid.cargodelivery.pojo.geocodingData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pumba30 on 30.09.2015.
 */
public class Results {
    @SerializedName("address_components")
    @Expose
    private List<Object> addressComponents = new ArrayList<Object>();
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("types")
    @Expose
    private List<Object> types = new ArrayList<Object>();

    /**
     * @return The addressComponents
     */
    public List<Object> getAddressComponents() {
        return addressComponents;
    }

    /**
     * @param addressComponents The address_components
     */
    public void setAddressComponents(List<Object> addressComponents) {
        this.addressComponents = addressComponents;
    }

    /**
     * @return The formattedAddress
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * @param formattedAddress The formatted_address
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * @return The geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * @param geometry The geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * @return The placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * @param placeId The place_id
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * @return The types
     */
    public List<Object> getTypes() {
        return types;
    }

    /**
     * @param types The types
     */
    public void setTypes(List<Object> types) {
        this.types = types;
    }

}
