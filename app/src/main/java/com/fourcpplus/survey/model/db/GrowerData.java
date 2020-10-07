package com.fourcpplus.survey.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rohit on 27-03-2020.
 */
@Entity(tableName = "Grower")
public class GrowerData {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sNo")
    public int sNo;

    @ColumnInfo(name = "gno")
    public String gno;

    @ColumnInfo(name = "pdivn")
    public String pdivn;

    @ColumnInfo(name = "gtarea")
    public String gtarea;

    @ColumnInfo(name = "filno")
    public String filno;

    @ColumnInfo(name = "pval")
    public String pval;

    @ColumnInfo(name = "pcentre")
    public String pcentre;

    @ColumnInfo(name = "gsoccd")
    public String gsoccd;

    @ColumnInfo(name = "gname")
    public String gname;

    @ColumnInfo(name = "pFilno")
    public String pFilno;

    @ColumnInfo(name = "gwmtlock")
    public String gwmtlock;

    @ColumnInfo(name = "gvill")
    public String gvill;

    @ColumnInfo(name = "gfather")
    public String gfather;

    @ColumnInfo(name = "gucd")
    public String gucd;

    public String getGno() {
        return gno;
    }

    public void setGno(String gno) {
        this.gno = gno;
    }

    public String getPdivn() {
        return pdivn;
    }

    public void setPdivn(String pdivn) {
        this.pdivn = pdivn;
    }

    public String getGtarea() {
        return gtarea;
    }

    public void setGtarea(String gtarea) {
        this.gtarea = gtarea;
    }

    public String getFilno() {
        return filno;
    }

    public void setFilno(String filno) {
        this.filno = filno;
    }

    public String getPval() {
        return pval;
    }

    public void setPval(String pval) {
        this.pval = pval;
    }

    public String getPcentre() {
        return pcentre;
    }

    public void setPcentre(String pcentre) {
        this.pcentre = pcentre;
    }

    public String getGsoccd() {
        return gsoccd;
    }

    public void setGsoccd(String gsoccd) {
        this.gsoccd = gsoccd;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getpFilno() {
        return pFilno;
    }

    public void setpFilno(String pFilno) {
        this.pFilno = pFilno;
    }

    public String getGwmtlock() {
        return gwmtlock;
    }

    public void setGwmtlock(String gwmtlock) {
        this.gwmtlock = gwmtlock;
    }

    public String getGvill() {
        return gvill;
    }

    public void setGvill(String gvill) {
        this.gvill = gvill;
    }

    public String getGfather() {
        return gfather;
    }

    public void setGfather(String gfather) {
        this.gfather = gfather;
    }

    public String getGucd() {
        return gucd;
    }

    public void setGucd(String gucd) {
        this.gucd = gucd;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
}
