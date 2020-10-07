package com.fourcpplus.survey.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rohit on 27-03-2020.
 */
@Entity(tableName = "MDPI")
public class MDPIData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sNo")
    public int sNo;
    @ColumnInfo(name = "pFilno")
    public String pFilno;
    @ColumnInfo(name = "pdivn")
    public String pdivn;
    @ColumnInfo(name = "mdesc")
    public String mdesc;
    @ColumnInfo(name = "mcode")
    public String mcode;
    @ColumnInfo(name = "filno")
    public String filno;
    @ColumnInfo(name = "pval")
    public String pval;
    @ColumnInfo(name = "pcentre")
    public String pcentre;
    @ColumnInfo(name = "mtype")
    public String mtype;

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

    public String getMdesc() {
        return mdesc;
    }

    public void setMdesc(String mdesc) {
        this.mdesc = mdesc;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
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

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }
}
