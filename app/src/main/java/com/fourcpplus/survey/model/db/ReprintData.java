package com.fourcpplus.survey.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rohit on 25-04-2020.
 */
@Entity(tableName = "Reprint")
public class ReprintData {

    @PrimaryKey
    @ColumnInfo(name = "sNo")
    public int sNo;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "plSerial")
    private int plSerial;
    @ColumnInfo(name = "plArea")
    private String plArea;
    @ColumnInfo(name = "growerCode")
    private String growerCode;
    @ColumnInfo(name = "village")
    private String village;
    @ColumnInfo(name = "growerName")
    private String growerName;
    @ColumnInfo(name = "grFather")
    private String grFather;
    @ColumnInfo(name = "variety")
    private String variety;
    @ColumnInfo(name = "sharedArea")
    private String sharedArea;
    @ColumnInfo(name = "percent")
    private String percent;
    @ColumnInfo(name = "unicode")
    private String unicode;
    @ColumnInfo(name = "dims1")
    private double dims1;
    @ColumnInfo(name = "dims2")
    private double dims2;
    @ColumnInfo(name = "dims3")
    private double dims3;
    @ColumnInfo(name = "dims4")
    private double dims4;
    @ColumnInfo(name = "plseedflg")
    private String plseedflg;
    @ColumnInfo(name = "PMTHD")
    private String PMTHD;
    @ColumnInfo(name = "PDISE")
    private String PDISE;
    @ColumnInfo(name = "PINSE")
    private String PINSE;
    @ColumnInfo(name = "PINTC")
    private String PINTC;
    @ColumnInfo(name = "PLAND")
    private String PLAND;
    @ColumnInfo(name = "PCRPC")
    private String PCRPC;
    @ColumnInfo(name = "PCCND")
    private String PCCND;
    @ColumnInfo(name = "PMIXC")
    private String PMIXC;
    @ColumnInfo(name = "PIRRG")
    private String PIRRG;
    @ColumnInfo(name = "PBRDC")
    private String PBRDC;
    @ColumnInfo(name = "PDEMO")
    private String PDEMO;
    @ColumnInfo(name = "PSOIL")
    private String PSOIL;
    @ColumnInfo(name = "PSSRC")
    private String PSSRC;
    @ColumnInfo(name = "clerkCode")
    private String clerkCode;
    @ColumnInfo(name = "season")
    private String season;
    @ColumnInfo(name = "pdate")
    private String pdate;
    @ColumnInfo(name = "plotVill")
    private String plotVill;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlseedflg() {
        return plseedflg;
    }

    public void setPlseedflg(String plseedflg) {
        this.plseedflg = plseedflg;
    }

    public String getPMTHD() {
        return PMTHD;
    }

    public void setPMTHD(String PMTHD) {
        this.PMTHD = PMTHD;
    }

    public String getPDISE() {
        return PDISE;
    }

    public void setPDISE(String PDISE) {
        this.PDISE = PDISE;
    }

    public String getPINSE() {
        return PINSE;
    }

    public void setPINSE(String PINSE) {
        this.PINSE = PINSE;
    }

    public String getPINTC() {
        return PINTC;
    }

    public void setPINTC(String PINTC) {
        this.PINTC = PINTC;
    }

    public String getPLAND() {
        return PLAND;
    }

    public void setPLAND(String PLAND) {
        this.PLAND = PLAND;
    }

    public String getPCRPC() {
        return PCRPC;
    }

    public void setPCRPC(String PCRPC) {
        this.PCRPC = PCRPC;
    }

    public String getPCCND() {
        return PCCND;
    }

    public void setPCCND(String PCCND) {
        this.PCCND = PCCND;
    }

    public String getPMIXC() {
        return PMIXC;
    }

    public void setPMIXC(String PMIXC) {
        this.PMIXC = PMIXC;
    }

    public String getPIRRG() {
        return PIRRG;
    }

    public void setPIRRG(String PIRRG) {
        this.PIRRG = PIRRG;
    }

    public String getPBRDC() {
        return PBRDC;
    }

    public void setPBRDC(String PBRDC) {
        this.PBRDC = PBRDC;
    }

    public String getPDEMO() {
        return PDEMO;
    }

    public void setPDEMO(String PDEMO) {
        this.PDEMO = PDEMO;
    }

    public String getPSOIL() {
        return PSOIL;
    }

    public void setPSOIL(String PSOIL) {
        this.PSOIL = PSOIL;
    }

    public String getPSSRC() {
        return PSSRC;
    }

    public void setPSSRC(String PSSRC) {
        this.PSSRC = PSSRC;
    }

    public int getPlSerial() {
        return plSerial;
    }

    public void setPlSerial(int plSerial) {
        this.plSerial = plSerial;
    }

    public String getClerkCode() {
        return clerkCode;
    }

    public void setClerkCode(String clerkCode) {
        this.clerkCode = clerkCode;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPlotVill() {
        return plotVill;
    }

    public void setPlotVill(String plotVill) {
        this.plotVill = plotVill;
    }

    public String getPlArea() {
        return plArea;
    }

    public void setPlArea(String plArea) {
        this.plArea = plArea;
    }

    public String getGrowerCode() {
        return growerCode;
    }

    public void setGrowerCode(String growerCode) {
        this.growerCode = growerCode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getGrowerName() {
        return growerName;
    }

    public void setGrowerName(String growerName) {
        this.growerName = growerName;
    }

    public String getGrFather() {
        return grFather;
    }

    public void setGrFather(String grFather) {
        this.grFather = grFather;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getSharedArea() {
        return sharedArea;
    }

    public void setSharedArea(String sharedArea) {
        this.sharedArea = sharedArea;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public double getDims1() {
        return dims1;
    }

    public void setDims1(double dims1) {
        this.dims1 = dims1;
    }

    public double getDims2() {
        return dims2;
    }

    public void setDims2(double dims2) {
        this.dims2 = dims2;
    }

    public double getDims3() {
        return dims3;
    }

    public void setDims3(double dims3) {
        this.dims3 = dims3;
    }

    public double getDims4() {
        return dims4;
    }

    public void setDims4(double dims4) {
        this.dims4 = dims4;
    }
}
