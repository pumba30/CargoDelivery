package com.pundroid.cargodelivery.pojo.geocodingData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pumba30 on 30.09.2015.
 */
public class ResultRequestGeocodingApi {

    @SerializedName("results")
    @Expose
    private List<Results> results = new ArrayList<>();
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The results
     */
    public List<Results> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Results> results) {
        this.results = results;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
