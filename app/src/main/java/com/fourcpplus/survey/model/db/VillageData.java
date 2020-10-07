package com.fourcpplus.survey.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rohit on 23-03-2020.
 */

@Entity(tableName = "Village")
public class VillageData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sNo")
    public int sNo;
    @ColumnInfo(name = "pFilno")
    public String pFilno;
    @ColumnInfo(name = "pdivn")
    public String pdivn;
    @ColumnInfo(name = "vname")
    public String vname;
    @ColumnInfo(name = "filno")
    public String filno;
    @ColumnInfo(name = "pval")
    public String pval;
    @ColumnInfo(name = "pcentre")
    public String pcentre;
    @ColumnInfo(name = "vcode")
    public String vcode;
    @ColumnInfo(name = "plsrno")
    public String plsrno;
    @ColumnInfo(name = "villtype")
    public String villtype;

    public String getPlsrno() {
        return plsrno;
    }

    public void setPlsrno(String plsrno) {
        this.plsrno = plsrno;
    }

    public String getVilltype() {
        return villtype;
    }

    public void setVilltype(String villtype) {
        this.villtype = villtype;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getpFilno() {
        return pFilno;
    }

    public void setpFilno(String pFilno) {
        this.pFilno = pFilno;
    }

    public String getPdivn() {
        return pdivn;
    }

    public void setPdivn(String pdivn) {
        this.pdivn = pdivn;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
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

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
