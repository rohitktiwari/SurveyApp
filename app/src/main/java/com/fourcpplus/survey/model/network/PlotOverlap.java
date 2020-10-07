package com.fourcpplus.survey.model.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rohit on 24-03-2020.
 */import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlotOverlap {

    @SerializedName("filno")
    @Expose
    private Integer filno;
    @SerializedName("LON2")
    @Expose
    private Double lON2;
    @SerializedName("pval")
    @Expose
    private Integer pval;
    @SerializedName("LON1")
    @Expose
    private Double lON1;
    @SerializedName("pcentre")
    @Expose
    private Integer pcentre;
    @SerializedName("LON4")
    @Expose
    private Double lON4;
    @SerializedName("LON3")
    @Expose
    private Double lON3;
    @SerializedName("vcode")
    @Expose
    private Integer vcode;
    @SerializedName("pFilno")
    @Expose
    private Integer pFilno;
    @SerializedName("GPSVSNO")
    @Expose
    private Integer gPSVSNO;
    @SerializedName("LAT2")
    @Expose
    private Double lAT2;
    @SerializedName("LAT1")
    @Expose
    private Double lAT1;
    @SerializedName("vname")
    @Expose
    private String vname;
    @SerializedName("pdivn")
    @Expose
    private String pdivn;
    @SerializedName("LAT4")
    @Expose
    private Double lAT4;
    @SerializedName("LAT3")
    @Expose
    private Double lAT3;
    @SerializedName("areaRatio")
    @Expose
    private Integer areaRatio;

    public Integer getFilno() {
        return filno;
    }

    public void setFilno(Integer filno) {
        this.filno = filno;
    }

    public Double getLON2() {
        return lON2;
    }

    public void setLON2(Double lON2) {
        this.lON2 = lON2;
    }

    public Integer getPval() {
        return pval;
    }

    public void setPval(Integer pval) {
        this.pval = pval;
    }

    public Double getLON1() {
        return lON1;
    }

    public void setLON1(Double lON1) {
        this.lON1 = lON1;
    }

    public Integer getPcentre() {
        return pcentre;
    }

    public void setPcentre(Integer pcentre) {
        this.pcentre = pcentre;
    }

    public Double getLON4() {
        return lON4;
    }

    public void setLON4(Double lON4) {
        this.lON4 = lON4;
    }

    public Double getLON3() {
        return lON3;
    }

    public void setLON3(Double lON3) {
        this.lON3 = lON3;
    }

    public Integer getVcode() {
        return vcode;
    }

    public void setVcode(Integer vcode) {
        this.vcode = vcode;
    }

    public Integer getPFilno() {
        return pFilno;
    }

    public void setPFilno(Integer pFilno) {
        this.pFilno = pFilno;
    }

    public Integer getGPSVSNO() {
        return gPSVSNO;
    }

    public void setGPSVSNO(Integer gPSVSNO) {
        this.gPSVSNO = gPSVSNO;
    }

    public Double getLAT2() {
        return lAT2;
    }

    public void setLAT2(Double lAT2) {
        this.lAT2 = lAT2;
    }

    public Double getLAT1() {
        return lAT1;
    }

    public void setLAT1(Double lAT1) {
        this.lAT1 = lAT1;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getPdivn() {
        return pdivn;
    }

    public void setPdivn(String pdivn) {
        this.pdivn = pdivn;
    }

    public Double getLAT4() {
        return lAT4;
    }

    public void setLAT4(Double lAT4) {
        this.lAT4 = lAT4;
    }

    public Double getLAT3() {
        return lAT3;
    }

    public void setLAT3(Double lAT3) {
        this.lAT3 = lAT3;
    }

    public Integer getAreaRatio() {
        return areaRatio;
    }

    public void setAreaRatio(Integer areaRatio) {
        this.areaRatio = areaRatio;
    }

}