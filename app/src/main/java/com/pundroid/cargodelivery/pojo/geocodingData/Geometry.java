package com.pundroid.cargodelivery.pojo.geocodingData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pumba30 on 30.09.2015.
 */
public class Geometry {
    @SerializedName("location")
    @Expose
    private com.pundroid.cargodelivery.pojo.geocodingData.Location location;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    /**
     * @return The location
     */
    public com.pundroid.cargodelivery.pojo.geocodingData.Location getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(com.pundroid.cargodelivery.pojo.geocodingData.Location location) {
        this.location = location;
    }

    /**
     * @return The locationType
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * @param locationType The location_type
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * @return The viewport
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * @param viewport The viewport
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

}
