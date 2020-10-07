package com.fourcpplus.survey.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Rohit on 27-03-2020.
 */
@Entity(tableName = "Control")
public class ControlData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sNo")
    public int sNo;
    @ColumnInfo(name = "mincord")
    public String mincord;
    @ColumnInfo(name = "clachk")
    public String clachk;
    @ColumnInfo(name = "MIDHRSTART")
    public String MIDHRSTART;
    @ColumnInfo(name = "filno")
    public String filno;
    @ColumnInfo(name = "pval")
    public String pval;
    @ColumnInfo(name = "gmcrop")
    public String gmcrop;
    @ColumnInfo(name = "gseed_source")
    public String gseed_source;
    @ColumnInfo(name = "ggatanum")
    public String ggatanum;
    @ColumnInfo(name = "gicrop")
    public String gicrop;
    @ColumnInfo(name = "HRSTOP")
    public String HRSTOP;
    @ColumnInfo(name = "gdisease")
    public String gdisease;
    @ColumnInfo(name = "MIDHRSTOP")
    public String MIDHRSTOP;
    @ColumnInfo(name = "pFilno")
    public String pFilno;
    @ColumnInfo(name = "gdemo")
    public String gdemo;
    @ColumnInfo(name = "gpsflg")
    public String gpsflg;
    @ColumnInfo(name = "girrigation")
    public String girrigation;
    @ColumnInfo(name = "areainacr")
    public String areainacr;
    @ColumnInfo(name = "maxcord")
    public String maxcord;
    @ColumnInfo(name = "glastyr_refplot")
    public String glastyr_refplot;
    @ColumnInfo(name = "gsame_ccycle")
    public String gsame_ccycle;
    @ColumnInfo(name = "ginsect")
    public String ginsect;
    @ColumnInfo(name = "gsame_var")
    public String gsame_var;
    @ColumnInfo(name = "HRSTART")
    public String HRSTART;
    @ColumnInfo(name = "printslip")
    public String printslip;
    @ColumnInfo(name = "overlapallow")
    public String overlapallow;
    @ColumnInfo(name = "pcentre")
    public String pcentre;
    @ColumnInfo(name = "gstype")
    public String gstype;
    @ColumnInfo(name = "printrep")
    public String printrep;
    @ColumnInfo(name = "gccondition")
    public String gccondition;
    @ColumnInfo(name = "gcrop")
    public String gcrop;
    @ColumnInfo(name = "gmethod")
    public String gmethod;
    @ColumnInfo(name = "gpldate")
    public String gpldate;
    @ColumnInfo(name = "gmlen")
    public String gmlen;
    @ColumnInfo(name = "maxplarea")
    public String maxplarea;
    @ColumnInfo(name = "pdivn")
    public String pdivn;
    @ColumnInfo(name = "gltype")
    public String gltype;
    @ColumnInfo(name = "gbroder_crop")
    public String gbroder_crop;
    @ColumnInfo(name = "overlapvalue")
    public String overlapvalue;
    @ColumnInfo(name = "damruallow")
    public String damruallow;
    @ColumnInfo(name = "suvflag")
    public String suvflag;
    @ColumnInfo(name = "cname")
    public String cname;

    public String getOverlapvalue() {
        return overlapvalue;
    }

    public void setOverlapvalue(String overlapvalue) {
        this.overlapvalue = overlapvalue;
    }

    public String getDamruallow() {
        return damruallow;
    }

    public void setDamruallow(String damruallow) {
        this.damruallow = damruallow;
    }

    public String getSuvflag() {
        return suvflag;
    }

    public void setSuvflag(String suvflag) {
        this.suvflag = suvflag;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getMincord() {
        return mincord;
    }

    public void setMincord(String mincord) {
        this.mincord = mincord;
    }

    public String getFilno() {
        return filno;
    }

    public void setFilno(String filno) {
        this.filno = filno;
    }

    public String getGmcrop() {
        return gmcrop;
    }

    public void setGmcrop(String gmcrop) {
        this.gmcrop = gmcrop;
    }

    public String getGseed_source() {
        return gseed_source;
    }

    public void setGseed_source(String gseed_source) {
        this.gseed_source = gseed_source;
    }

    public String getGicrop() {
        return gicrop;
    }

    public void setGicrop(String gicrop) {
        this.gicrop = gicrop;
    }

    public String getHRSTOP() {
        return HRSTOP;
    }

    public void setHRSTOP(String HRSTOP) {
        this.HRSTOP = HRSTOP;
    }

    public String getMIDHRSTOP() {
        return MIDHRSTOP;
    }

    public void setMIDHRSTOP(String MIDHRSTOP) {
        this.MIDHRSTOP = MIDHRSTOP;
    }

    public String getpFilno() {
        return pFilno;
    }

    public void setpFilno(String pFilno) {
        this.pFilno = pFilno;
    }

    public String getGdemo() {
        return gdemo;
    }

    public void setGdemo(String gdemo) {
        this.gdemo = gdemo;
    }

    public String getGpsflg() {
        return gpsflg;
    }

    public void setGpsflg(String gpsflg) {
        this.gpsflg = gpsflg;
    }

    public String getGinsect() {
        return ginsect;
    }

    public void setGinsect(String ginsect) {
        this.ginsect = ginsect;
    }

    public String getPrintslip() {
        return printslip;
    }

    public void setPrintslip(String printslip) {
        this.printslip = printslip;
    }

    public String getPcentre() {
        return pcentre;
    }

    public void setPcentre(String pcentre) {
        this.pcentre = pcentre;
    }

    public String getGstype() {
        return gstype;
    }

    public void setGstype(String gstype) {
        this.gstype = gstype;
    }

    public String getPrintrep() {
        return printrep;
    }

    public void setPrintrep(String printrep) {
        this.printrep = printrep;
    }

    public String getGccondition() {
        return gccondition;
    }

    public void setGccondition(String gccondition) {
        this.gccondition = gccondition;
    }

    public String getGcrop() {
        return gcrop;
    }

    public void setGcrop(String gcrop) {
        this.gcrop = gcrop;
    }

    public String getGmethod() {
        return gmethod;
    }

    public void setGmethod(String gmethod) {
        this.gmethod = gmethod;
    }

    public String getGmlen() {
        return gmlen;
    }

    public void setGmlen(String gmlen) {
        this.gmlen = gmlen;
    }

    public String getPdivn() {
        return pdivn;
    }

    public void setPdivn(String pdivn) {
        this.pdivn = pdivn;
    }

    public String getGltype() {
        return gltype;
    }

    public void setGltype(String gltype) {
        this.gltype = gltype;
    }

    public String getGbroder_crop() {
        return gbroder_crop;
    }

    public void setGbroder_crop(String gbroder_crop) {
        this.gbroder_crop = gbroder_crop;
    }

    public String getClachk() {
        return clachk;
    }

    public void setClachk(String clachk) {
        this.clachk = clachk;
    }

    public String getMIDHRSTART() {
        return MIDHRSTART;
    }

    public void setMIDHRSTART(String MIDHRSTART) {
        this.MIDHRSTART = MIDHRSTART;
    }

    public String getPval() {
        return pval;
    }

    public void setPval(String pval) {
        this.pval = pval;
    }

    public String getGgatanum() {
        return ggatanum;
    }

    public void setGgatanum(String ggatanum) {
        this.ggatanum = ggatanum;
    }

    public String getGdisease() {
        return gdisease;
    }

    public void setGdisease(String gdisease) {
        this.gdisease = gdisease;
    }

    public String getGirrigation() {
        return girrigation;
    }

    public void setGirrigation(String girrigation) {
        this.girrigation = girrigation;
    }

    public String getAreainacr() {
        return areainacr;
    }

    public void setAreainacr(String areainacr) {
        this.areainacr = areainacr;
    }

    public String getMaxcord() {
        return maxcord;
    }

    public void setMaxcord(String maxcord) {
        this.maxcord = maxcord;
    }

    public String getGlastyr_refplot() {
        return glastyr_refplot;
    }

    public void setGlastyr_refplot(String glastyr_refplot) {
        this.glastyr_refplot = glastyr_refplot;
    }

    public String getGsame_ccycle() {
        return gsame_ccycle;
    }

    public void setGsame_ccycle(String gsame_ccycle) {
        this.gsame_ccycle = gsame_ccycle;
    }

    public String getGsame_var() {
        return gsame_var;
    }

    public void setGsame_var(String gsame_var) {
        this.gsame_var = gsame_var;
    }

    public String getHRSTART() {
        return HRSTART;
    }

    public void setHRSTART(String HRSTART) {
        this.HRSTART = HRSTART;
    }

    public String getOverlapallow() {
        return overlapallow;
    }

    public void setOverlapallow(String overlapallow) {
        this.overlapallow = overlapallow;
    }

    public String getGpldate() {
        return gpldate;
    }

    public void setGpldate(String gpldate) {
        this.gpldate = gpldate;
    }

    public String getMaxplarea() {
        return maxplarea;
    }

    public void setMaxplarea(String maxplarea) {
        this.maxplarea = maxplarea;
    }
}
