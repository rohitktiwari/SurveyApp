package com.fourcpplus.survey.model.network;

/**
 * Created by Rohit on 26-03-2020.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    @SerializedName("imagePath")
    @Expose
    private String imagePath;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

