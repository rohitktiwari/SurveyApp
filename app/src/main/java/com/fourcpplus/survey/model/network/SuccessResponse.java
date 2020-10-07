package com.fourcpplus.survey.model.network;

/**
 * Created by Rohit on 24-03-2020.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuccessResponse {

    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("plotOverlap")
    @Expose
    private List<PlotOverlap> plotOverlap = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<PlotOverlap> getPlotOverlap() {
        return plotOverlap;
    }

    public void setPlotOverlap(List<PlotOverlap> plotOverlap) {
        this.plotOverlap = plotOverlap;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}