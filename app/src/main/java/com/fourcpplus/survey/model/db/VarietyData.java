package com.fourcpplus.survey.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rohit on 27-03-2020.
 */
@Entity(tableName = "Variety")
public class VarietyData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sNo")
    public int sNo;
    @ColumnInfo(name = "pFilno")
    public String pFilno;
    @ColumnInfo(name = "pdivn")
    public String pdivn;
    @ColumnInfo(name = "varname")
    public String varname;
    @ColumnInfo(name = "filno")
    public String filno;
    @ColumnInfo(name = "pval")
    public String pval;
    @ColumnInfo(name = "pcentre")
    public String pcentre;
    @ColumnInfo(name = "varcat")
    public String varcat;
    @ColumnInfo(name = "varcode")
    public String varcode;

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

    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
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

    public String getVarcat() {
        return varcat;
    }

    public void setVarcat(String varcat) {
        this.varcat = varcat;
    }

    public String getVarcode() {
        return varcode;
    }

    public void setVarcode(String varcode) {
        this.varcode = varcode;
    }
}
