
package com.fourcpplus.survey.model.network;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlotRecheckResponse {

    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("matchPloat")
    @Expose
    private List<MatchPloat> matchPloat = null;
    @SerializedName("plotData")
    @Expose
    private List<PlotDatum> plotData = null;

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

    public List<MatchPloat> getMatchPloat() {
        return matchPloat;
    }

    public void setMatchPloat(List<MatchPloat> matchPloat) {
        this.matchPloat = matchPloat;
    }

    public List<PlotDatum> getPlotData() {
        return plotData;
    }

    public void setPlotData(List<PlotDatum> plotData) {
        this.plotData = plotData;
    }
}
