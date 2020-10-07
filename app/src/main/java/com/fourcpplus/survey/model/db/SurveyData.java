package com.fourcpplus.survey.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Rohit on 25-04-2020.
 */
@Entity(tableName = "Survey")
public class SurveyData {

    @PrimaryKey
    @ColumnInfo(name = "sNo")
    public int sNo;
    @ColumnInfo(name = "PLVsno")
    public int PLVsno;
    @ColumnInfo(name = "pdivn")
    public String pdivn;
    @ColumnInfo(name = "pdate")
    public String pdate;
    @ColumnInfo(name = "PLGastino")
    public String PLGastino;
    @ColumnInfo(name = "placvill")
    public String placvill;
    @ColumnInfo(name = "PLVill")
    public String PLVill;
    @ColumnInfo(name = "PLGrno")
    public String PLGrno;
    @ColumnInfo(name = "placvar")
    public String placvar;
    @ColumnInfo(name = "pldim1")
    public String pldim1;
    @ColumnInfo(name = "pldim2")
    public String pldim2;
    @ColumnInfo(name = "pldim3")
    public String pldim3;
    @ColumnInfo(name = "pldim4")
    public String pldim4;
    @ColumnInfo(name = "pldivsl")
    public String pldivsl;
    @ColumnInfo(name = "pldivdr")
    public String pldivdr;
    @ColumnInfo(name = "plpercent")
    public String plpercent;
    @ColumnInfo(name = "plarea")
    public String plarea;
    @ColumnInfo(name = "plclerk")
    public String plclerk;
    @ColumnInfo(name = "pldate")
    public String pldate;
    @ColumnInfo(name = "plseedflg")
    public String plseedflg;
    @ColumnInfo(name = "PMTHD")
    public String PMTHD;
    @ColumnInfo(name = "PDISE")
    public String PDISE;
    @ColumnInfo(name = "PINSE")
    public String PINSE;
    @ColumnInfo(name = "PINTC")
    public String PINTC;
    @ColumnInfo(name = "PLAND")
    public String PLAND;
    @ColumnInfo(name = "PCRPC")
    public String PCRPC;
    @ColumnInfo(name = "PCCND")
    public String PCCND;
    @ColumnInfo(name = "PMIXC")
    public String PMIXC;
    @ColumnInfo(name = "PIRRG")
    public String PIRRG;
    @ColumnInfo(name = "PBRDC")
    public String PBRDC;
    @ColumnInfo(name = "PDEMO")
    public String PDEMO;
    @ColumnInfo(name = "PSOIL")
    public String PSOIL;
    @ColumnInfo(name = "PSSRC")
    public String PSSRC;
    @ColumnInfo(name = "pstatus")
    public String pstatus;
    @ColumnInfo(name = "pstype")
    public String pstype;
    @ColumnInfo(name = "plat1")
    public String plat1;
    @ColumnInfo(name = "plon1")
    public String plon1;
    @ColumnInfo(name = "plat2")
    public String plat2;
    @ColumnInfo(name = "plon2")
    public String plon2;
    @ColumnInfo(name = "plat3")
    public String plat3;
    @ColumnInfo(name = "plon3")
    public String plon3;
    @ColumnInfo(name = "plat4")
    public String plat4;
    @ColumnInfo(name = "plon4")
    public String plon4;
    @ColumnInfo(name = "pimei")
    public String pimei;
    @ColumnInfo(name = "surveyImage")
    public String surveyImage;
    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "plratoon")
    public String plratoon;
    @ColumnInfo(name = "date")
    public Date date;

    public String getPextra() {
        return pextra;
    }

    public void setPextra(String pextra) {
        this.pextra = pextra;
    }

    @ColumnInfo(name = "pextra")
    public String pextra;

    public HashMap<String, Object> getContentValues() {
        HashMap<String, Object> values = new LinkedHashMap<>();
        try {
            values.put("PLVsno", PLVsno);
            values.put("pdivn", pdivn);
            values.put("pdate", pdate);
            values.put("PLGastino", PLGastino);
            values.put("placvill", placvill);
            values.put("PLVill", PLVill);
            values.put("PLGrno", PLGrno);
            values.put("placvar", placvar);
            values.put("pldim1", pldim1);
            values.put("pldim2", pldim2);
            values.put("pldim3", pldim3);
            values.put("pldim4", pldim4);
            values.put("pldivsl", pldivsl);
            values.put("pldivdr", pldivdr);
            values.put("plpercent", plpercent);
            values.put("plarea", plarea);
            values.put("plclerk", plclerk);
            values.put("pldate", pldate);
            values.put("plseedflg", plseedflg);
            values.put("PMTHD", PMTHD);
            values.put("PDISE", PDISE);
            values.put("PINSE", PINSE);
            values.put("PINTC", PINTC);
            values.put("PLAND", PLAND);
            values.put("PCRPC", PCRPC);
            values.put("PCCND", PCCND);
            values.put("PMIXC", PMIXC);
            values.put("PIRRG", PIRRG);
            values.put("PBRDC", PBRDC);
            values.put("PDEMO", PDEMO);
            values.put("PSOIL", PSOIL);
            values.put("PSSRC", PSSRC);
            values.put("pstatus", pstatus);
            values.put("pstype", pstype);
            values.put("plat1", plat1);
            values.put("plon1", plon1);
            values.put("plat2", plat2);
            values.put("plon2", plon2);
            values.put("plat3", plat3);
            values.put("plon3", plon3);
            values.put("plat4", plat4);
            values.put("plon4", plon4);
            values.put("pimei", pimei);
            values.put("surveyImage", surveyImage);
            values.put("plratoon", plratoon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getPlratoon() {
        return plratoon;
    }

    public void setPlratoon(String plratoon) {
        this.plratoon = plratoon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSurveyImage() {
        return surveyImage;
    }

    public void setSurveyImage(String surveyImage) {
        this.surveyImage = surveyImage;
    }

    public String getPdivn() {
        return pdivn;
    }

    public void setPdivn(String pdivn) {
        this.pdivn = pdivn;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPLGastino() {
        return PLGastino;
    }

    public void setPLGastino(String PLGastino) {
        this.PLGastino = PLGastino;
    }

    public String getPlacvill() {
        return placvill;
    }

    public void setPlacvill(String placvill) {
        this.placvill = placvill;
    }

    public String getPLVill() {
        return PLVill;
    }

    public void setPLVill(String PLVill) {
        this.PLVill = PLVill;
    }

    public int getPLVsno() {
        return PLVsno;
    }

    public void setPLVsno(int PLVsno) {
        this.PLVsno = PLVsno;
    }

    public String getPLGrno() {
        return PLGrno;
    }

    public void setPLGrno(String PLGrno) {
        this.PLGrno = PLGrno;
    }

    public String getPlacvar() {
        return placvar;
    }

    public void setPlacvar(String placvar) {
        this.placvar = placvar;
    }

    public String getPldim1() {
        return pldim1;
    }

    public void setPldim1(String pldim1) {
        this.pldim1 = pldim1;
    }

    public String getPldim2() {
        return pldim2;
    }

    public void setPldim2(String pldim2) {
        this.pldim2 = pldim2;
    }

    public String getPldim3() {
        return pldim3;
    }

    public void setPldim3(String pldim3) {
        this.pldim3 = pldim3;
    }

    public String getPldim4() {
        return pldim4;
    }

    public void setPldim4(String pldim4) {
        this.pldim4 = pldim4;
    }

    public String getPldivsl() {
        return pldivsl;
    }

    public void setPldivsl(String pldivsl) {
        this.pldivsl = pldivsl;
    }

    public String getPldivdr() {
        return pldivdr;
    }

    public void setPldivdr(String pldivdr) {
        this.pldivdr = pldivdr;
    }

    public String getPlpercent() {
        return plpercent;
    }

    public void setPlpercent(String plpercent) {
        this.plpercent = plpercent;
    }

    public String getPlarea() {
        return plarea;
    }

    public void setPlarea(String plarea) {
        this.plarea = plarea;
    }

    public String getPlclerk() {
        return plclerk;
    }

    public void setPlclerk(String plclerk) {
        this.plclerk = plclerk;
    }

    public String getPldate() {
        return pldate;
    }

    public void setPldate(String pldate) {
        this.pldate = pldate;
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

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getPstype() {
        return pstype;
    }

    public void setPstype(String pstype) {
        this.pstype = pstype;
    }

    public String getPlat1() {
        return plat1;
    }

    public void setPlat1(String plat1) {
        this.plat1 = plat1;
    }

    public String getPlon1() {
        return plon1;
    }

    public void setPlon1(String plon1) {
        this.plon1 = plon1;
    }

    public String getPlat2() {
        return plat2;
    }

    public void setPlat2(String plat2) {
        this.plat2 = plat2;
    }

    public String getPlon2() {
        return plon2;
    }

    public void setPlon2(String plon2) {
        this.plon2 = plon2;
    }

    public String getPlat3() {
        return plat3;
    }

    public void setPlat3(String plat3) {
        this.plat3 = plat3;
    }

    public String getPlon3() {
        return plon3;
    }

    public void setPlon3(String plon3) {
        this.plon3 = plon3;
    }

    public String getPlat4() {
        return plat4;
    }

    public void setPlat4(String plat4) {
        this.plat4 = plat4;
    }

    public String getPlon4() {
        return plon4;
    }

    public void setPlon4(String plon4) {
        this.plon4 = plon4;
    }

    public String getPimei() {
        return pimei;
    }

    public void setPimei(String pimei) {
        this.pimei = pimei;
    }
}
