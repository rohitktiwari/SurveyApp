package com.fourcpplus.survey.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String database_Name = "surveyapp.db";
    private static final int database_Version = 1;
    private static final String TAG = "DATABASE OPERATIONS";


    private static final String PurchyFormDataTable = "PurchyFormData";

    private static final String Purchy_TB_indentNO = "indentNO";
    private static final String Purchy_TB_variety = "variety";
    private static final String Purchy_TB_delDate = "delDate";
    private static final String Purchy_TB_villageCode = "villageCode";
    private static final String Purchy_TB_villageName = "villageName";
    private static final String Purchy_TB_growerCode = "growerCode";
    private static final String Purchy_TB_growerName = "growerName";
    private static final String Purchy_TB_modeCategory = "modeCategory";
    private static final String Purchy_TB_mode = "mode";
    private static final String Purchy_TB_acVarietyCode = "acVarietyCode";
    private static final String Purchy_TB_acVarityValue = "acVarityValue";
    private static final String Purchy_TB_spineerValue = "spineerValue";
    private static final String Purchy_TB_grossWeight = "grossWeight";
    private static final String Purchy_TB_tareWeight = "tareWeight";
    private static final String Purchy_TB_rate = "rate";
    private static final String Purchy_TB_bmWeight = "bmWeight";
    private static final String Purchy_TB_netWeight = "netWeight";
    private static final String Purchy_TB_amount = "amount";
    private static final String Purchy_TB_tareSlip = "tareSlip";


    private static final String IndentTable = "Indent";
    /* "isvar": 13"issoc": 0,"isgno": 348,"filno": 14,"pval": 0,"isvill": 10815,"pcentre": 102,"isstatus": 0,
   "isdeldt": "2019-10-06 00:00:00","pFilno": 14,"pdivn": "P1","ismode": 1,"isno": 1103100417*/
    private static final String indent_TB_isvar = "isvar";
    private static final String indent_TB_issoc = "issoc";
    private static final String indent_TB_isgno = "isgno";
    private static final String indent_TB_filno = "filno";
    private static final String indent_TB_pval = "pval";
    private static final String indent_TB_isvill = "isvill";
    private static final String indent_TB_pcentre = "pcentre";
    private static final String indent_TB_isstatus = "isstatus";
    private static final String indent_TB_isdeldt = "isdeldt";
    private static final String indent_TB_pFilno = "pFilno";
    private static final String indent_TB_pdivn = "pdivn";
    private static final String indent_TB_ismode = "ismode";
    private static final String indent_TB_isno = "isno";


    private static final String VillageTable = "Village";
    /* "pFilno": 7,"vname": "BHOGARI","pdivn": "P1","filno": 7,"pval": 0,"pcentre": 102,"vcode": 208*/
    private static final String Village_TB_pFilno = "pFilno";
    private static final String Village_TB_vname = "vname";
    private static final String Village_TB_pdivn = "pdivn";
    private static final String Village_TB_filno = "filno";
    private static final String Village_TB_pval = "pval";
    private static final String Village_TB_pcentre = "pcentre";
    private static final String Village_TB_vcode = "vcode";

    private static final String GrowerTable = "Grower";
    /*  "filno": 8,"pval": 0,"pcentre": 102,"gno": 95,"gtarea": 0.0,"gsoccd": 1,"gname": "CHUNNA LAL",
    "pFilno": 8,"pdivn": "P1","gwmtlock": 0,"gvill": 10815,"gfather": "RAM AUTAR","gucd": 0*/
    private static final String Grower_TB_pFilno = "pFilno";
    private static final String Grower_TB_gvill = "gvill";
    private static final String Grower_TB_pdivn = "pdivn";
    private static final String Grower_TB_filno = "filno";
    private static final String Grower_TB_pval = "pval";
    private static final String Grower_TB_pcentre = "pcentre";
    private static final String Grower_TB_gno = "gno";
    private static final String Grower_TB_gtarea = "gtarea";
    private static final String Grower_TB_gsoccd = "gsoccd";
    private static final String Grower_TB_gname = "gname";
    private static final String Grower_TB_gwmtlock = "gwmtlock";
    private static final String Grower_TB_gfather = "gfather";
    private static final String Grower_TB_gucd = "gucd";


    private static final String ModeTable = "Mode";
    /*  "pFilno": 10,"mdcat": 1,"mdmaxwt": 32.9,"md_tare_minwt": 10.0,"pdivn": "P1","filno": 10,
    "mdcode": 1,"pval": 0,"mdminwt": 15.0,"pcentre": 102,"md_tare_maxwt": 15.0*/
    private static final String Mode_TB_pFilno = "pFilno";
    private static final String Mode_TB_mdcode = "mdcode";
    private static final String Mode_TB_pdivn = "pdivn";
    private static final String Mode_TB_filno = "filno";
    private static final String Mode_TB_pval = "pval";
    private static final String Mode_TB_pcentre = "pcentre";
    private static final String Mode_TB_mdcat = "mdcat";
    private static final String Mode_TB_mdmaxwt = "mdmaxwt";
    private static final String Mode_TB_md_tare_minwt = "md_tare_minwt";
    private static final String Mode_TB_mdminwt = "mdminwt";
    private static final String Mode_TB_md_tare_maxwt = "md_tare_maxwt";

    private static final String VarietyTable = "Variety";
    /* "pFilno": 9,"pdivn": "P1","filno": 9,"pval": 0,"pcentre": 102,"varcat": 10,"varname": "CO.S. 08272","varcode": 14 */
    private static final String Variety_TB_pFilno = "pFilno";
    private static final String Variety_TB_varcat = "varcat";
    private static final String Variety_TB_pdivn = "pdivn";
    private static final String Variety_TB_filno = "filno";
    private static final String Variety_TB_pval = "pval";
    private static final String Variety_TB_pcentre = "pcentre";
    private static final String Variety_TB_varname = "varname";
    private static final String Variety_TB_varcode = "varcode";

    private static final String RateTable = "Rate";
    /* "filno": 5,"smp_gen": 315.0,"prm_erl": 0.0,"pval": 0,"inct_rej": 0.0,"pcentre": 102,"smp_rej": 310.0,"sap_erl": 0.0,"inct_gen": 0.0,
            "pFilno": 5,"sap_rej": 0.0,"pdivn": "P1","smp_erl": 325.0,"sap_gen": 0.0,"inct_erl": 0.0*/
    private static final String Rate_TB_pFilno = "pFilno";
    private static final String Rate_TB_smp_gen = "smp_gen";
    private static final String Rate_TB_pdivn = "pdivn";
    private static final String Rate_TB_filno = "filno";
    private static final String Rate_TB_pval = "pval";
    private static final String Rate_TB_pcentre = "pcentre";
    private static final String Rate_TB_prm_erl = "prm_erl";
    private static final String Rate_TB_inct_rej = "inct_rej";
    private static final String Rate_TB_smp_rej = "smp_rej";
    private static final String Rate_TB_sap_erl = "sap_erl";
    private static final String Rate_TB_inct_gen = "inct_gen";
    private static final String Rate_TB_sap_rej = "sap_rej";
    private static final String Rate_TB_smp_erl = "smp_erl";
    private static final String Rate_TB_sap_gen = "sap_gen";
    private static final String Rate_TB_inct_erl = "inct_erl";

    private static final String TeraSlipTable = "HHC_Centre";
    /*"cngen": 0,"filno": 4,"pval": 0,"cntrl": 0,"trl_gen": 3426666,"pcentre": 102,"cncart": 0,"cart_erl": 3353627,"cnrej": 0,"pFilno": 4,
            "cart_rej": 3395518,"cnerl": 0,"pdivn": "P1","trl_erl": 3424522,"cart_gen": 3361183,"trl_rej": 3429414 */
   /* private static final String HHC_Centre_TB_pFilno = "pFilno";
    private static final String HHC_Centre_TB_cngen = "cngen";
    private static final String HHC_Centre_TB_pdivn = "pdivn";
    private static final String HHC_Centre_TB_filno = "filno";
    private static final String HHC_Centre_TB_pval = "pval";
    private static final String HHC_Centre_TB_pcentre = "pcentre";
    private static final String HHC_Centre_TB_cntrl = "cntrl";
    private static final String HHC_Centre_TB_trl_gen = "trl_gen";
    private static final String HHC_Centre_TB_cncart = "cncart";
    private static final String HHC_Centre_TB_cart_erl = "cart_erl";
    private static final String HHC_Centre_TB_cnrej = "cnrej";
    private static final String HHC_Centre_TB_cart_rej = "cart_rej";
    private static final String HHC_Centre_TB_cnerl = "cnerl";
    private static final String HHC_Centre_TB_trl_erl = "trl_erl";
    private static final String HHC_Centre_TB_cart_gen = "cart_gen";
    private static final String HHC_Centre_TB_trl_rej = "trl_rej";*/

    //Note:Update Variables for HHC Center:
    /*"pFilno": 4,"slipcat": 1,"pdivn": "P1","filno": 4,"pval": 0,"pcentre": 102,"cnslipno": 102100000*/

    private static final String HHC_Centre_TB_pFilno = "pFilno";
    private static final String HHC_Centre_TB_slipcat = "slipcat";
    private static final String HHC_Centre_TB_pdivn = "pdivn";
    private static final String HHC_Centre_TB_filno = "filno";
    private static final String HHC_Centre_TB_pval = "pval";
    private static final String HHC_Centre_TB_pcentre = "pcentre";
    private static final String HHC_Centre_TB_cnslipno = "cnslipno";


    private static final String HHC_MSTTable = "HHC_MST";
    /* "filno": 3,"pval": 0,"valid_fr": "07102019","hada": 0,"pcentre": 102,"cnname": "NAUGAVAN-A CIRCLE 2 RES","hr_start": 5,"challan_no": "0","hr_end": 22,
            "hstatus": 0,"pFilno": 3,"pdivn": "P1","extra_valid_days": 0,"cnfree": 0,"wbnos": 0,"wmt_option": 0*/
    private static final String HHC_MST_TB_pFilno = "pFilno";
    private static final String HHC_MST_TB_valid_fr = "valid_fr";
    private static final String HHC_MST_TB_pdivn = "pdivn";
    private static final String HHC_MST_TB_filno = "filno";
    private static final String HHC_MST_TB_pval = "pval";
    private static final String HHC_MST_TB_pcentre = "pcentre";
    private static final String HHC_MST_TB_hada = "hada";
    private static final String HHC_MST_TB_cnname = "cnname";
    private static final String HHC_MST_TB_hr_start = "hr_start";
    private static final String HHC_MST_TB_challan_no = "challan_no";
    private static final String HHC_MST_TB_hr_end = "hr_end";
    private static final String HHC_MST_TB_hstatus = "hstatus";
    private static final String HHC_MST_TB_extra_valid_days = "extra_valid_days";
    private static final String HHC_MST_TB_cnfree = "cnfree";
    private static final String HHC_MST_TB_wbnos = "wbnos";
    private static final String HHC_MST_TB_wmt_option = "wmt_option";
    private static final String HHC_MST_TB_WTZERO = "WTZERO";
    private static final String HHC_MST_TB_TAREDAYS = "TAREDAYS";

    private static final String HHC_MST_TB_AVGWT= "AVGWT";

    private static final String ControlTable = "Control";
    /* "tare_slips": 1,"purchy_seq": 3,"validation_flg": 3,"blank_role": 0,"filno": 2,"pval": 0,"reprint": 0,"pcentre": 102,"pur_valid_adv": 1,"pur_valid_days": 30,
            "challans": 0,"pFilno": 2,"pdivn": "P1","blank_role_challan": 0,"season": "2019-20","joona_per": 1*/
    private static final String Control_TB_pFilno = "pFilno";
    private static final String Control_TB_pdivn = "pdivn";
    private static final String Control_TB_filno = "filno";
    private static final String Control_TB_pval = "pval";
    private static final String Control_TB_pcentre = "pcentre";
    private static final String Control_TB_tare_slips = "tare_slips";
    private static final String Control_TB_purchy_seq = "purchy_seq";
    private static final String Control_TB_validation_flg = "validation_flg";
    private static final String Control_TB_blank_role = "blank_role";
    private static final String Control_TB_reprint = "reprint";
    private static final String Control_TB_pur_valid_adv = "pur_valid_adv";
    private static final String Control_TB_pur_valid_days = "pur_valid_days";
    private static final String Control_TB_challans = "challans";
    private static final String Control_TB_blank_role_challan = "blank_role_challan";
    private static final String Control_TB_season = "season";
    private static final String Control_TB_joona_per = "joona_per";
    private static final String Control_TB_modcatflg = "modcatflg";

    private static final String Variety_RulesTable = "Variety_Rules";
    /* "filno": 11,"pval": 0,"vareg": 0,"pcentre": 102,"varge": 0,"varee": 1,"vargg": 1,"varrr": 1,"pFilno": 11,"varrg": 0,"pdivn": "P1",
            "varre": 0,"varer": 0,"vargr": 1*/
    private static final String Variety_Rules_TB_pFilno = "pFilno";
    private static final String Variety_Rules_TB_pdivn = "pdivn";
    private static final String Variety_Rules_TB_filno = "filno";
    private static final String Variety_Rules_TB_pval = "pval";
    private static final String Variety_Rules_TB_pcentre = "pcentre";
    private static final String Variety_Rules_TB_vareg = "vareg";
    private static final String Variety_Rules_TB_varge = "varge";
    private static final String Variety_Rules_TB_varee = "varee";
    private static final String Variety_Rules_TB_vargg = "vargg";
    private static final String Variety_Rules_TB_varrr = "varrr";
    private static final String Variety_Rules_TB_varrg = "varrg";
    private static final String Variety_Rules_TB_varre = "varre";
    private static final String Variety_Rules_TB_varer = "varer";
    private static final String Variety_Rules_TB_vargr = "vargr";

    private static final String ContractorsTable = "Contractors";
    /* "pFilno": 12,"concode": 101,"pdivn": "P1","filno": 12,"pval": 0,"pcentre": 102,"conname": "NEW LAXMI TRANSPORT CO."*/
    private static final String Contractors_TB_pFilno = "pFilno";
    private static final String Contractors_TB_pdivn = "pdivn";
    private static final String Contractors_TB_filno = "filno";
    private static final String Contractors_TB_pval = "pval";
    private static final String Contractors_TB_pcentre = "pcentre";
    private static final String Contractors_TB_concode = "concode";
    private static final String Contractors_TB_conname = "conname";

    private static final String DateTable = "Date";
    /* "pFilno": 21,"pdivn": "P1","filno": 21,"pval": 0,"pcentre": 102,"currdate": "07-10-2019 13:03:01"*/
    private static final String Date_TB_pFilno = "pFilno";
    private static final String Date_TB_pdivn = "pdivn";
    private static final String Date_TB_filno = "filno";
    private static final String Date_TB_pval = "pval";
    private static final String Date_TB_pcentre = "pcentre";
    private static final String Date_TB_currdate = "currdate";

    private static final String TruckTable = "Truck";
    /*  "pFilno": 13,"pdivn": "P1","filno": 13,"pval": 0,"pcentre": 102,"truck": "4214-UP34L-CT25","tpcd": 102,"bk_upsno": 226 */
    private static final String Truck_TB_pFilno = "pFilno";
    private static final String Truck_TB_pdivn = "pdivn";
    private static final String Truck_TB_filno = "filno";
    private static final String Truck_TB_pval = "pval";
    private static final String Truck_TB_pcentre = "pcentre";
    private static final String Truck_TB_truck = "truck";
    private static final String Truck_TB_tpcd = "tpcd";
    private static final String Truck_TB_bk_upsno = "bk_upsno";

//    private static final String create_Table="CREATE TABLE "+table_Name+"("+column1+" TEXT,"+column2+" TEXT);";

    private static final String create_Indent_Table = "CREATE TABLE " + IndentTable + "(" + indent_TB_filno + " TEXT," + indent_TB_isdeldt + " TEXT," + indent_TB_isgno + " TEXT" +
            "," + indent_TB_ismode + " TEXT," + indent_TB_isno + " TEXT," + indent_TB_issoc + " TEXT," + indent_TB_isstatus + " TEXT," + indent_TB_isvar + " TEXT" +
            "," + indent_TB_isvill + " TEXT," + indent_TB_pcentre + " TEXT," + indent_TB_pdivn + " TEXT," + indent_TB_pFilno + " TEXT," + indent_TB_pval + " TEXT);";

    private static final String create_Village_Table = "CREATE TABLE " + VillageTable + "(" + Village_TB_filno + " TEXT," + Village_TB_pcentre + " TEXT," + Village_TB_pdivn + " TEXT" +
            "," + Village_TB_pFilno + " TEXT," + Village_TB_pval + " TEXT," + Village_TB_vcode + " TEXT," + Village_TB_vname + " TEXT);";

    private static final String create_Grower_Table = "CREATE TABLE " + GrowerTable + "(" + Grower_TB_filno + " TEXT," + Grower_TB_gfather + " TEXT," + Grower_TB_gname + " TEXT" +
            "," + Grower_TB_gno + " TEXT," + Grower_TB_gsoccd + " TEXT," + Grower_TB_gtarea + " TEXT," + Grower_TB_gvill + " TEXT," + Grower_TB_gwmtlock + " TEXT," + Grower_TB_pcentre + " TEXT" +
            "," + Grower_TB_pdivn + " TEXT," + Grower_TB_pFilno + " TEXT," + Grower_TB_pval + " TEXT," + Grower_TB_gucd + " TEXT);";

    private static final String create_Mode_Table = "CREATE TABLE " + ModeTable + "(" + Mode_TB_filno + " TEXT," + Mode_TB_md_tare_maxwt + " TEXT," + Mode_TB_md_tare_minwt + " TEXT" +
            "," + Mode_TB_mdcat + " TEXT," + Mode_TB_mdcode + " TEXT," + Mode_TB_mdmaxwt + " TEXT," + Mode_TB_mdminwt + " TEXT," + Mode_TB_pcentre + " TEXT," + Mode_TB_pdivn + " TEXT" +
            "," + Mode_TB_pval + " TEXT," + Mode_TB_pFilno + " TEXT);";

    private static final String create_VarietyRules_Table = "CREATE TABLE " + Variety_RulesTable + "(" + Variety_Rules_TB_filno + " TEXT," + Variety_Rules_TB_pcentre + " TEXT," + Variety_Rules_TB_pdivn + " TEXT" +
            "," + Variety_Rules_TB_pFilno + " TEXT," + Variety_Rules_TB_pval + " TEXT," + Variety_Rules_TB_varee + " TEXT," + Variety_Rules_TB_vareg + " TEXT," + Variety_Rules_TB_varge + " TEXT" +
            "," + Variety_Rules_TB_varrg + " TEXT," + Variety_Rules_TB_varrr + " TEXT," + Variety_Rules_TB_vargg + " TEXT," + Variety_Rules_TB_varer + " TEXT," + Variety_Rules_TB_varre + " TEXT," + Variety_Rules_TB_vargr + " TEXT);";

    /*private static final String create_HHC_Centre_Table = "CREATE TABLE " + TeraSlipTable + "(" + HHC_Centre_TB_cart_erl + " TEXT," + HHC_Centre_TB_cart_gen + " TEXT," + HHC_Centre_TB_cart_rej + " TEXT" +
            "," + HHC_Centre_TB_cncart + " TEXT," + HHC_Centre_TB_cnerl + " TEXT," + HHC_Centre_TB_cngen + " TEXT," + HHC_Centre_TB_cnrej + " TEXT," + HHC_Centre_TB_cntrl + " TEXT," + HHC_Centre_TB_filno + " TEXT" +
            "," + HHC_Centre_TB_pcentre + " TEXT," + HHC_Centre_TB_pdivn + " TEXT," + HHC_Centre_TB_pFilno + " TEXT," + HHC_Centre_TB_pval + " TEXT," + HHC_Centre_TB_trl_erl + " TEXT," + HHC_Centre_TB_trl_gen + " TEXT," + HHC_Centre_TB_trl_rej + " TEXT);";*/

    private static final String create_HHC_Centre_Table = "CREATE TABLE " + TeraSlipTable + "(" + HHC_Centre_TB_slipcat + " TEXT," + HHC_Centre_TB_filno + " TEXT" +
            "," + HHC_Centre_TB_pcentre + " TEXT," + HHC_Centre_TB_pdivn + " TEXT," + HHC_Centre_TB_pFilno + " TEXT," + HHC_Centre_TB_pval + " TEXT," + HHC_Centre_TB_cnslipno + " TEXT);";


    private static final String create_HHC_MST_Table = "CREATE TABLE " + HHC_MSTTable + "(" + HHC_MST_TB_filno + " TEXT," + HHC_MST_TB_challan_no + " TEXT," + HHC_MST_TB_cnfree + " TEXT" +
            "," + HHC_MST_TB_cnname + " TEXT," + HHC_MST_TB_extra_valid_days + " TEXT," + HHC_MST_TB_hada + " TEXT," + HHC_MST_TB_hr_end + " TEXT," + HHC_MST_TB_hr_start + " TEXT" +
            "," + HHC_MST_TB_hstatus + " TEXT," + HHC_MST_TB_pcentre + " TEXT," + HHC_MST_TB_pdivn + " TEXT," + HHC_MST_TB_pFilno + " TEXT," + HHC_MST_TB_pval + " TEXT," + HHC_MST_TB_valid_fr + " TEXT," + HHC_MST_TB_wbnos + " TEXT,"  + HHC_MST_TB_wmt_option + " TEXT,"  + HHC_MST_TB_AVGWT + " TEXT,"  + HHC_MST_TB_TAREDAYS + " TEXT," +  HHC_MST_TB_WTZERO + " TEXT );";

    private static final String create_Control_Table = "CREATE TABLE " + ControlTable + "(" + Control_TB_blank_role + " TEXT," + Control_TB_blank_role_challan + " TEXT," + Control_TB_challans + " TEXT" +
            "," + Control_TB_filno + " TEXT," + Control_TB_joona_per + " TEXT," + Control_TB_pcentre + " TEXT," + Control_TB_pdivn + " TEXT," + Control_TB_pFilno + " TEXT," + Control_TB_pur_valid_adv + " TEXT" +
            "," + Control_TB_pur_valid_days + " TEXT," + Control_TB_purchy_seq + " TEXT," + Control_TB_pval + " TEXT," + Control_TB_reprint + " TEXT," + Control_TB_season + " TEXT," + Control_TB_tare_slips + " TEXT," + Control_TB_modcatflg + " TEXT," + Control_TB_validation_flg + " TEXT);";

    private static final String create_Variety_Table = "CREATE TABLE " + VarietyTable + "(" + Variety_TB_filno + " TEXT," + Variety_TB_pcentre + " TEXT," + Variety_TB_pdivn + " TEXT" +
            "," + Variety_TB_pFilno + " TEXT," + Variety_TB_pval + " TEXT," + Variety_TB_varcat + " TEXT," + Variety_TB_varcode + " TEXT," + Variety_TB_varname + " TEXT);";

    private static final String create_Rate_Table = "CREATE TABLE " + RateTable + "(" + Rate_TB_smp_gen + " TEXT," + Rate_TB_prm_erl + " TEXT," + Rate_TB_filno + " TEXT" +
            "," + Rate_TB_pval + " TEXT," + Rate_TB_inct_rej + " TEXT," + Rate_TB_pcentre + " TEXT," + Rate_TB_pdivn + " TEXT," + Rate_TB_pFilno + " TEXT," + Rate_TB_smp_rej + " TEXT," + Rate_TB_sap_erl + " TEXT," + Rate_TB_inct_gen +
            " TEXT," + Rate_TB_sap_rej + " TEXT," + Rate_TB_smp_erl + " TEXT," + Rate_TB_sap_gen + " TEXT," + Rate_TB_inct_erl + " TEXT);";

    private static final String create_Contractors_Table = "CREATE TABLE " + ContractorsTable + "(" + Contractors_TB_concode + " TEXT," + Contractors_TB_conname + " TEXT," + Contractors_TB_filno + " TEXT" +
            "," + Contractors_TB_pcentre + " TEXT," + Contractors_TB_pdivn + " TEXT," + Contractors_TB_pFilno + " TEXT," + Contractors_TB_pval + " TEXT);";

    private static final String create_Date_Table = "CREATE TABLE " + DateTable + "(" + Date_TB_filno + " TEXT," + Date_TB_currdate + " TEXT," + Date_TB_pcentre + " TEXT" +
            "," + Date_TB_pdivn + " TEXT," + Date_TB_pFilno + " TEXT," + Date_TB_pval + " TEXT);";


    private static final String create_Truck_Table = "CREATE TABLE " + TruckTable + "(" + Truck_TB_filno + " TEXT," + Truck_TB_pcentre + " TEXT" +
            "," + Truck_TB_pdivn + " TEXT," + Truck_TB_pFilno + " TEXT," + Truck_TB_pval + " TEXT," + Truck_TB_truck + " TEXT," + Truck_TB_tpcd + " TEXT," + Truck_TB_bk_upsno + " TEXT);";


    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_Name, null, database_Version);
        Log.i(TAG, "Database Created / Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(create_Indent_Table);
        db.execSQL(create_Village_Table);
        db.execSQL(create_Grower_Table);
        db.execSQL(create_Mode_Table);
        db.execSQL(create_Variety_Table);
        //db.execSQL(create_Rate_Table);
       // db.execSQL(create_HHC_Centre_Table);
        //db.execSQL(create_HHC_MST_Table);
        db.execSQL(create_Control_Table);
        //db.execSQL(create_VarietyRules_Table);
        //db.execSQL(create_Truck_Table);
        //db.execSQL(create_Date_Table);
        //db.execSQL(create_Contractors_Table);
        //db.execSQL(create_PurchyData_Table);
        Log.i(TAG, "Table Created");
    }

    private static final String create_PurchyData_Table = "CREATE TABLE " + PurchyFormDataTable + "(" + Purchy_TB_indentNO + " TEXT," + Purchy_TB_variety + " TEXT," + Purchy_TB_delDate + " TEXT," + Purchy_TB_villageCode + " TEXT" +
            "," + Purchy_TB_villageName + " TEXT," + Purchy_TB_growerCode + " TEXT," + Purchy_TB_growerName + " TEXT," + Purchy_TB_modeCategory + " TEXT," + Purchy_TB_mode + " TEXT" +
            "," + Purchy_TB_acVarietyCode + " TEXT," + Purchy_TB_acVarityValue + " TEXT," + Purchy_TB_spineerValue + " TEXT," + Purchy_TB_grossWeight + " TEXT," + Purchy_TB_tareWeight + " TEXT," + Purchy_TB_rate + " TEXT,"
            + Purchy_TB_bmWeight + " TEXT," + Purchy_TB_netWeight + " TEXT," + Purchy_TB_amount + " TEXT," + Purchy_TB_tareSlip + " TEXT);";

    /* "isvar": 13"issoc": 0,"isgno": 348,"filno": 14,"pval": 0,"isvill": 10815,"pcentre": 102,"isstatus": 0,
"isdeldt": "2019-10-06 00:00:00","pFilno": 14,"pdivn": "P1","ismode": 1,"isno": 1103100417*/
    public void insertIndentData(String isvar, String issoc, String isgno, String filno, String pval, String isvill, String pcentre, String isstatus, String isdeldt, String pFilno, String pdivn, String ismode, String isno) {
        db = this.getWritableDatabase();
        Log.i(TAG, "insertIndentData: called");
        ContentValues contentValues = new ContentValues();
        contentValues.put(indent_TB_isvar, isvar);
        contentValues.put(indent_TB_isgno, isgno);
        contentValues.put(indent_TB_issoc, issoc);
        contentValues.put(indent_TB_filno, filno);
        contentValues.put(indent_TB_pFilno, pFilno);
        contentValues.put(indent_TB_pval, pval);
        contentValues.put(indent_TB_isvill, isvill);
        contentValues.put(indent_TB_pcentre, pcentre);
        contentValues.put(indent_TB_isstatus, isstatus);
        contentValues.put(indent_TB_isdeldt, isdeldt);
        contentValues.put(indent_TB_pdivn, pdivn);
        contentValues.put(indent_TB_ismode, ismode);
        contentValues.put(indent_TB_isno, isno);
        long result = db.insert(IndentTable, null, contentValues);
        Log.i(TAG, "insertIndentData:insert Indent result=" + result);
    }

    /* "pFilno": 7,"vname": "BHOGARI","pdivn": "P1","filno": 7,"pval": 0,"pcentre": 102,"vcode": 208*/
    public void insertVillageData(String pFilno, String vname, String pdivn, String filno, String pval, String pcentre, String vcode) {
        Log.i(TAG, "insertVillageData:insert village");
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Village_TB_vname, vname);
        contentValues.put(Village_TB_vcode, vcode);
        contentValues.put(Village_TB_filno, filno);
        contentValues.put(Village_TB_pFilno, pFilno);
        contentValues.put(Village_TB_pval, pval);
        contentValues.put(Village_TB_pcentre, pcentre);
        contentValues.put(Village_TB_pdivn, pdivn);

        long result = db.insert(VillageTable, null, contentValues);
        Log.i(TAG, "insertVillageData:insert village result=" + result);
        db.close();
    }

    /*  "filno": 8,"pval": 0,"pcentre": 102,"gno": 95,"gtarea": 0.0,"gsoccd": 1,"gname": "CHUNNA LAL",
          "pFilno": 8,"pdivn": "P1","gwmtlock": 0,"gvill": 10815,"gfather": "RAM AUTAR","gucd": 0*/
    public void insertGrowerData(String gno, String gtarea, String filno, String pval, String gsoccd, String pcentre, String gname, String gwmtlock, String pFilno, String pdivn, String gvill, String gfather, String gucd) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Grower_TB_gno, gno);
        contentValues.put(Grower_TB_gtarea, gtarea);
        contentValues.put(Grower_TB_filno, filno);
        contentValues.put(Grower_TB_pFilno, pFilno);
        contentValues.put(Grower_TB_pval, pval);
        contentValues.put(Grower_TB_gsoccd, gsoccd);
        contentValues.put(Grower_TB_pcentre, pcentre);
        contentValues.put(Grower_TB_gname, gname);
        contentValues.put(Grower_TB_gwmtlock, gwmtlock);
        contentValues.put(Grower_TB_pdivn, pdivn);
        contentValues.put(Grower_TB_gvill, gvill);
        contentValues.put(Grower_TB_gfather, gfather);
        contentValues.put(Grower_TB_gucd, gucd);

        long result = db.insert(GrowerTable, null, contentValues);
        Log.i(TAG, "insertGrowerData: result=" + result);
        db.close();
    }

    /*  "pFilno": 10,"mdcat": 1,"mdmaxwt": 32.9,"md_tare_minwt": 10.0,"pdivn": "P1","filno": 10,
        "mdcode": 1,"pval": 0,"mdminwt": 15.0,"pcentre": 102,"md_tare_maxwt": 15.0*/
    public void insertModeData(String mdcat, String mdmaxwt, String filno, String pval, String md_tare_minwt, String pcentre, String mdcode, String mdminwt, String pFilno, String pdivn, String md_tare_maxwt) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Mode_TB_mdcat, mdcat);
        contentValues.put(Mode_TB_mdmaxwt, mdmaxwt);
        contentValues.put(Mode_TB_filno, filno);
        contentValues.put(Mode_TB_pFilno, pFilno);
        contentValues.put(Mode_TB_pval, pval);
        contentValues.put(Mode_TB_md_tare_minwt, md_tare_minwt);
        contentValues.put(Mode_TB_pcentre, pcentre);
        contentValues.put(Mode_TB_mdcode, mdcode);
        contentValues.put(Mode_TB_mdminwt, mdminwt);
        contentValues.put(Mode_TB_pdivn, pdivn);
        contentValues.put(Mode_TB_md_tare_maxwt, md_tare_maxwt);

        long result = db.insert(ModeTable, null, contentValues);
        Log.i(TAG, "insertModeData: result=" + result);
        db.close();
    }

    /* "pFilno": 9,"pdivn": "P1","filno": 9,"pval": 0,"pcentre": 102,"varcat": 10,"varname": "CO.S. 08272","varcode": 14 */
    public void insertVarietyData(String varcat, String varname, String filno, String pval, String varcode, String pcentre, String pFilno, String pdivn) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Variety_TB_varcat, varcat);
        contentValues.put(Variety_TB_varname, varname);
        contentValues.put(Variety_TB_filno, filno);
        contentValues.put(Variety_TB_pFilno, pFilno);
        contentValues.put(Variety_TB_pval, pval);
        contentValues.put(Variety_TB_varcode, varcode);
        contentValues.put(Variety_TB_pcentre, pcentre);
        contentValues.put(Variety_TB_pdivn, pdivn);


        long result = db.insert(VarietyTable, null, contentValues);
        Log.i(TAG, "insertVarietyData: result=" + result);
        db.close();
    }

    /* "filno": 5,"smp_gen": 315.0,"prm_erl": 0.0,"pval": 0,"inct_rej": 0.0,"pcentre": 102,"smp_rej": 310.0,"sap_erl": 0.0,"inct_gen": 0.0,
                "pFilno": 5,"sap_rej": 0.0,"pdivn": "P1","smp_erl": 325.0,"sap_gen": 0.0,"inct_erl": 0.0*/
    public void insertRateData(String smp_gen, String prm_erl, String filno, String pval, String inct_rej
            , String pcentre, String pFilno, String pdivn, String smp_rej, String sap_erl
            , String inct_gen, String sap_rej, String smp_erl, String sap_gen, String inct_erl) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Rate_TB_smp_gen, smp_gen);
        contentValues.put(Rate_TB_prm_erl, prm_erl);
        contentValues.put(Rate_TB_filno, filno);
        contentValues.put(Rate_TB_pFilno, pFilno);
        contentValues.put(Rate_TB_pval, pval);
        contentValues.put(Rate_TB_inct_rej, inct_rej);
        contentValues.put(Rate_TB_pcentre, pcentre);
        contentValues.put(Rate_TB_pdivn, pdivn);
        contentValues.put(Rate_TB_smp_rej, smp_rej);
        contentValues.put(Rate_TB_sap_erl, sap_erl);
        contentValues.put(Rate_TB_inct_gen, inct_gen);
        contentValues.put(Rate_TB_sap_rej, sap_rej);
        contentValues.put(Rate_TB_smp_erl, smp_erl);
        contentValues.put(Rate_TB_sap_gen, sap_gen);
        contentValues.put(Rate_TB_inct_erl, inct_erl);

        long result = db.insert(RateTable, null, contentValues);
        Log.i(TAG, "insertRateData: result=" + result);
        db.close();
    }

    /*"cngen": 0,"filno": 4,"pval": 0,"cntrl": 0,"trl_gen": 3426666,"pcentre": 102,"cncart": 0,"cart_erl": 3353627,"cnrej": 0,"pFilno": 4,
           "cart_rej": 3395518,"cnerl": 0,"pdivn": "P1","trl_erl": 3424522,"cart_gen": 3361183,"trl_rej": 3429414 */
   /* public void insertHHC_CentreData(String cngen, String cntrl, String filno, String pval, String trl_gen, String pcentre, String cncart, String cart_erl, String pFilno, String pdivn,
                                     String cnrej, String cart_rej, String cnerl, String trl_erl, String cart_gen, String trl_rej) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HHC_Centre_TB_cart_erl, cart_erl);
        contentValues.put(HHC_Centre_TB_cngen, cngen);
        contentValues.put(HHC_Centre_TB_filno, filno);
        contentValues.put(HHC_Centre_TB_pFilno, pFilno);
        contentValues.put(HHC_Centre_TB_pval, pval);
        contentValues.put(HHC_Centre_TB_cntrl, cntrl);
        contentValues.put(HHC_Centre_TB_pcentre, pcentre);
        contentValues.put(HHC_Centre_TB_trl_gen, trl_gen);
        contentValues.put(HHC_Centre_TB_cncart, cncart);
        contentValues.put(HHC_Centre_TB_pdivn, pdivn);
        contentValues.put(HHC_Centre_TB_cnrej, cnrej);
        contentValues.put(HHC_Centre_TB_cart_rej, cart_rej);
        contentValues.put(HHC_Centre_TB_cnerl, cnerl);
        contentValues.put(HHC_Centre_TB_trl_erl, trl_erl);
        contentValues.put(HHC_Centre_TB_cart_gen, cart_gen);
        contentValues.put(HHC_Centre_TB_trl_rej, trl_rej);

        long result = db.insert(TeraSlipTable, null, contentValues);
        Log.i(TAG, "insertHHC_CentreData: result=" + result);
        db.close();
    }*/

    //dbHelper.insertTeraSlipUpdatedData(data.getString("slipcat"), data.getString("filno"), data.getString("pval"), data.getString("cnslipno"), data.getString("pcentre"), data.getString("pFilno"), data.getString("pdivn"));


    public void insertTeraSlipUpdatedData(String slipcat, String filno, String pval, String cnslipno, String pcentre, String pFilno, String pdivn) {

        try {
            db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(HHC_Centre_TB_slipcat, slipcat);
            contentValues.put(HHC_Centre_TB_cnslipno, cnslipno);
            contentValues.put(HHC_Centre_TB_filno, filno);
            contentValues.put(HHC_Centre_TB_pFilno, pFilno);
            contentValues.put(HHC_Centre_TB_pval, pval);
            contentValues.put(HHC_Centre_TB_pcentre, pcentre);
            contentValues.put(HHC_Centre_TB_pdivn, pdivn);


            long result = db.insert(TeraSlipTable, null, contentValues);
            Log.i(TAG, "insertHHC_CentreData: result=" + result);
            db.close();
        } catch (Exception e) {
            Log.i(TAG, "insertTeraSlipUpdatedData: Error=" + e.getMessage());
        }
    }

    /* "filno": 3,"pval": 0,"valid_fr": "07102019","hada": 0,"pcentre": 102,"cnname": "NAUGAVAN-A CIRCLE 2 RES","hr_start": 5,"challan_no": "0","hr_end": 22,
           "hstatus": 0,"pFilno": 3,"pdivn": "P1","extra_valid_days": 0,"cnfree": 0,"wbnos": 0,"wmt_option": 0*/
    public void insertHHC_MSTData(String valid_fr, String hada, String filno, String pval, String cnname, String pcentre, String hr_start, String challan_no, String pFilno, String pdivn,
                                  String hr_end, String hstatus, String extra_valid_days, String cnfree, String wbnos, String wmt_option, String weightZero, String avgwt , String tareDays) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HHC_MST_TB_valid_fr, valid_fr);
        contentValues.put(HHC_MST_TB_hada, hada);
        contentValues.put(HHC_MST_TB_filno, filno);
        contentValues.put(HHC_MST_TB_pFilno, pFilno);
        contentValues.put(HHC_MST_TB_pval, pval);
        contentValues.put(HHC_MST_TB_cnname, cnname);
        contentValues.put(HHC_MST_TB_pcentre, pcentre);
        contentValues.put(HHC_MST_TB_hr_start, hr_start);
        contentValues.put(HHC_MST_TB_hr_end, hr_end);
        contentValues.put(HHC_MST_TB_challan_no, challan_no);
        contentValues.put(HHC_MST_TB_pdivn, pdivn);
        contentValues.put(HHC_MST_TB_hstatus, hstatus);
        contentValues.put(HHC_MST_TB_extra_valid_days, extra_valid_days);
        contentValues.put(HHC_MST_TB_cnfree, cnfree);
        contentValues.put(HHC_MST_TB_wbnos, wbnos);
        contentValues.put(HHC_MST_TB_wmt_option, wmt_option);
        contentValues.put(HHC_MST_TB_AVGWT, avgwt);
        contentValues.put(HHC_MST_TB_TAREDAYS, tareDays);

        contentValues.put(HHC_MST_TB_WTZERO, weightZero);

        long result = db.insert(HHC_MSTTable, null, contentValues);
        Log.i(TAG, "insertHHC_MSTData: result=" + result);
        db.close();
    }

    /* "tare_slips": 1,"purchy_seq": 3,"validation_flg": 3,"blank_role": 0,"filno": 2,"pval": 0,"reprint": 0,"pcentre": 102,"pur_valid_adv": 1,"pur_valid_days": 30,
           "challans": 0,"pFilno": 2,"pdivn": "P1","blank_role_challan": 0,"season": "2019-20","joona_per": 1*/
    public void insertControlData(String tare_slips, String purchy_seq, String filno, String pval, String validation_flg, String pcentre, String blank_role, String reprint, String pFilno, String pdivn,
                                  String pur_valid_adv, String pur_valid_days, String challans, String blank_role_challan, String season, String joona_per, String control_TB_modCatflg) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Control_TB_tare_slips, tare_slips);
        contentValues.put(Control_TB_purchy_seq, purchy_seq);
        contentValues.put(Control_TB_filno, filno);
        contentValues.put(Control_TB_pFilno, pFilno);
        contentValues.put(Control_TB_pval, pval);
        contentValues.put(Control_TB_validation_flg, validation_flg);
        contentValues.put(Control_TB_pcentre, pcentre);
        contentValues.put(Control_TB_blank_role, blank_role);
        contentValues.put(Control_TB_reprint, reprint);
        contentValues.put(Control_TB_pur_valid_adv, pur_valid_adv);
        contentValues.put(Control_TB_pdivn, pdivn);
        contentValues.put(Control_TB_pur_valid_days, pur_valid_days);
        contentValues.put(Control_TB_challans, challans);
        contentValues.put(Control_TB_blank_role_challan, blank_role_challan);
        contentValues.put(Control_TB_season, season);
        contentValues.put(Control_TB_modcatflg,  control_TB_modCatflg);
        contentValues.put(Control_TB_joona_per, joona_per);

        long result = db.insert(ControlTable, null, contentValues);
        Log.i(TAG, "insertControlData: result=" + result);
        db.close();
    }

    /* "filno": 11,"pval": 0,"vareg": 0,"pcentre": 102,"varge": 0,"varee": 1,"vargg": 1,"varrr": 1,"pFilno": 11,"varrg": 0,"pdivn": "P1",
            "varre": 0,"varer": 0,"vargr": 1*/
    public void insertVarietyRulesData(String vargg, String vareg, String varge, String filno, String pval, String varee, String pcentre, String varrr, String varrg, String pFilno, String pdivn,
                                       String varre, String varer, String vargr) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Variety_Rules_TB_vareg, vareg);
        contentValues.put(Variety_Rules_TB_varge, varge);
        contentValues.put(Variety_Rules_TB_filno, filno);
        contentValues.put(Variety_Rules_TB_pFilno, pFilno);
        contentValues.put(Variety_Rules_TB_pval, pval);
        contentValues.put(Variety_Rules_TB_pdivn, pdivn);
        contentValues.put(Variety_Rules_TB_varee, varee);
        contentValues.put(Variety_Rules_TB_pcentre, pcentre);
        contentValues.put(Variety_Rules_TB_varrr, varrr);
        contentValues.put(Variety_Rules_TB_varrg, varrg);
        contentValues.put(Variety_Rules_TB_varre, varre);
        contentValues.put(Variety_Rules_TB_varer, varer);
        contentValues.put(Variety_Rules_TB_vargr, vargr);
        contentValues.put(Variety_Rules_TB_vargg, vargg);

        long result = db.insert(Variety_RulesTable, null, contentValues);
        Log.i(TAG, "insertVarietyRulesData: result=" + result);
        db.close();
    }

    /* "pFilno": 12,"concode": 101,"pdivn": "P1","filno": 12,"pval": 0,"pcentre": 102,"conname": "NEW LAXMI TRANSPORT CO."*/
    public void insertContractorsData(String concode, String conname, String filno, String pval, String pcentre, String pFilno, String pdivn) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contractors_TB_filno, filno);
        contentValues.put(Contractors_TB_pFilno, pFilno);
        contentValues.put(Contractors_TB_pval, pval);
        contentValues.put(Contractors_TB_pdivn, pdivn);
        contentValues.put(Contractors_TB_pcentre, pcentre);
        contentValues.put(Contractors_TB_concode, concode);
        contentValues.put(Contractors_TB_conname, conname);


        long result = db.insert(ContractorsTable, null, contentValues);
        Log.i(TAG, "insertContractorsData: result=" + result);
        db.close();
    }

    /* "pFilno": 21,"pdivn": "P1","filno": 21,"pval": 0,"pcentre": 102,"currdate": "07-10-2019 13:03:01"*/
    public void insertDateData(String currdate, String filno, String pval, String pcentre, String pFilno, String pdivn) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Date_TB_filno, filno);
        contentValues.put(Date_TB_pFilno, pFilno);
        contentValues.put(Date_TB_pval, pval);
        contentValues.put(Date_TB_pdivn, pdivn);
        contentValues.put(Date_TB_pcentre, pcentre);
        contentValues.put(Date_TB_currdate, currdate);


        long result = db.insert(DateTable, null, contentValues);
        Log.i(TAG, "insertDateData: result=" + result);
        db.close();
    }

    /*  "pFilno": 13,"pdivn": "P1","filno": 13,"pval": 0,"pcentre": 102,"truck": "4214-UP34L-CT25","tpcd": 102,"bk_upsno": 226 */
    public void insertTruckData(String pFilno, String pdivn, String filno, String pval, String pcentre, String truck, String tpcd, String bk_upsno) {

        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Truck_TB_filno, filno);
        contentValues.put(Truck_TB_pFilno, pFilno);
        contentValues.put(Truck_TB_pval, pval);
        contentValues.put(Truck_TB_pdivn, pdivn);
        contentValues.put(Truck_TB_pcentre, pcentre);
        contentValues.put(Truck_TB_truck, truck);
        contentValues.put(Truck_TB_tpcd, tpcd);
        contentValues.put(Truck_TB_bk_upsno, bk_upsno);


        long result = db.insert(TruckTable, null, contentValues);
        Log.i(TAG, "insertTruckData: result=" + result);
        db.close();
    }

    //Fetch Indent data
    public Map<String, String> getIndentDetail(String indentNO) {
        try {

            final String TABLE_NAME = "name of table";

            String selectQuery = "SELECT  * FROM " + IndentTable + " where " + indent_TB_isno + " LIKE " + indentNO;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("isstatus", cursor.getString(cursor.getColumnIndex(indent_TB_isstatus)));
                    intendData.put("filno", cursor.getString(cursor.getColumnIndex(indent_TB_filno)));
                    intendData.put("isdeldt", cursor.getString(cursor.getColumnIndex(indent_TB_isdeldt)));
                    intendData.put("isgno", cursor.getString(cursor.getColumnIndex(indent_TB_isgno)));
                    intendData.put("ismode", cursor.getString(cursor.getColumnIndex(indent_TB_ismode)));
                    intendData.put("isno", cursor.getString(cursor.getColumnIndex(indent_TB_isno)));
                    intendData.put("issoc", cursor.getString(cursor.getColumnIndex(indent_TB_issoc)));
                    intendData.put("isvar", cursor.getString(cursor.getColumnIndex(indent_TB_isvar)));
                    intendData.put("isvill", cursor.getString(cursor.getColumnIndex(indent_TB_isvill)));

                    intendData.put("pcentre", cursor.getString(cursor.getColumnIndex(indent_TB_pcentre)));
                    intendData.put("pval", cursor.getString(cursor.getColumnIndex(indent_TB_pval)));
                    intendData.put("pFilno", cursor.getString(cursor.getColumnIndex(indent_TB_pFilno)));

                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Map<String, String> getVillageDetail(String villcode) {
        try {

            final String TABLE_NAME = "name of table";

            String selectQuery = "SELECT  * FROM " + VillageTable + " where " + Village_TB_vcode + " LIKE " + villcode;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("vname", cursor.getString(cursor.getColumnIndex(Village_TB_vname)));
//                    intendData.put("filno", cursor.getString(cursor.getColumnIndex(indent_TB_filno)));
//                    intendData.put("isdeldt", cursor.getString(cursor.getColumnIndex(indent_TB_isdeldt)));
//                    intendData.put("isgno", cursor.getString(cursor.getColumnIndex(indent_TB_isgno)));
//                    intendData.put("ismode", cursor.getString(cursor.getColumnIndex(indent_TB_ismode)));
//                    intendData.put("isno", cursor.getString(cursor.getColumnIndex(indent_TB_isno)));
//                    intendData.put("issoc", cursor.getString(cursor.getColumnIndex(indent_TB_issoc)));
//                    intendData.put("isvar", cursor.getString(cursor.getColumnIndex(indent_TB_isvar)));
//                    intendData.put("isvill", cursor.getString(cursor.getColumnIndex(indent_TB_isvill)));

                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Map<String, String> getGrowerDetail(String gno,String gvill) {
        try {
            //String selectQuery = "SELECT  * FROM " + GrowerTable + " where " + Grower_TB_gno + " LIKE " + gno+  " and "+;

            String selectQuery = "SELECT  * FROM " + GrowerTable + " where " + Grower_TB_gno + " LIKE " + gno +  " AND "+ Grower_TB_gvill + " LIKE " + gvill ;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("gname", cursor.getString(cursor.getColumnIndex(Grower_TB_gname)));
                    intendData.put("gfather", cursor.getString(cursor.getColumnIndex(Grower_TB_gfather)));
                    intendData.put("gucd", cursor.getString(cursor.getColumnIndex(Grower_TB_gucd)));
//                    intendData.put("isdeldt", cursor.getString(cursor.getColumnIndex(indent_TB_isdeldt)));
//                    intendData.put("isgno", cursor.getString(cursor.getColumnIndex(indent_TB_isgno)));
//                    intendData.put("ismode", cursor.getString(cursor.getColumnIndex(indent_TB_ismode)));
//                    intendData.put("isno", cursor.getString(cursor.getColumnIndex(indent_TB_isno)));
//                    intendData.put("issoc", cursor.getString(cursor.getColumnIndex(indent_TB_issoc)));
//                    intendData.put("isvar", cursor.getString(cursor.getColumnIndex(indent_TB_isvar)));
//                    intendData.put("isvill", cursor.getString(cursor.getColumnIndex(indent_TB_isvill)));

                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Map<String, String> getAcVarietyDetail(String isvar) {
        try {
            String selectQuery = "SELECT  * FROM " + VarietyTable + " where " + Variety_TB_varcode + " LIKE " + isvar;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("varname", cursor.getString(cursor.getColumnIndex(Variety_TB_varname)));
                    intendData.put("varcat", cursor.getString(cursor.getColumnIndex(Variety_TB_varcat)));
                    intendData.put("varcode", cursor.getString(cursor.getColumnIndex(Variety_TB_varcode)));
//                    intendData.put("gfather", cursor.getString(cursor.getColumnIndex(Grower_TB_gfather)));
//                    intendData.put("isdeldt", cursor.getString(cursor.getColumnIndex(indent_TB_isdeldt)));
//                    intendData.put("isgno", cursor.getString(cursor.getColumnIndex(indent_TB_isgno)));
//                    intendData.put("ismode", cursor.getString(cursor.getColumnIndex(indent_TB_ismode)));
//                    intendData.put("isno", cursor.getString(cursor.getColumnIndex(indent_TB_isno)));
//                    intendData.put("issoc", cursor.getString(cursor.getColumnIndex(indent_TB_issoc)));
//                    intendData.put("isvar", cursor.getString(cursor.getColumnIndex(indent_TB_isvar)));
//                    intendData.put("isvill", cursor.getString(cursor.getColumnIndex(indent_TB_isvill)));

                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Map<String, String> getGrossAndTareWeightDetail(String mdcat) {

        try {
            String selectQuery = "SELECT  * FROM " + ModeTable + " where " + Mode_TB_mdcat + " LIKE " + mdcat;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("tare_maxwt", cursor.getString(cursor.getColumnIndex(Mode_TB_md_tare_maxwt)));
                    intendData.put("md_tare_minwt", cursor.getString(cursor.getColumnIndex(Mode_TB_md_tare_minwt)));
                    intendData.put("mdmaxwt", cursor.getString(cursor.getColumnIndex(Mode_TB_mdmaxwt)));
                    intendData.put("mdminwt", cursor.getString(cursor.getColumnIndex(Mode_TB_mdminwt)));


                    intendData.put("mdcat", cursor.getString(cursor.getColumnIndex(Mode_TB_mdcat)));
                    intendData.put("pdivn", cursor.getString(cursor.getColumnIndex(Mode_TB_pdivn)));
                    intendData.put("mdcode", cursor.getString(cursor.getColumnIndex(Mode_TB_mdcode)));
                    intendData.put("pcentre", cursor.getString(cursor.getColumnIndex(Mode_TB_pcentre)));
//                    intendData.put("ismode", cursor.getString(cursor.getColumnIndex(indent_TB_ismode)));
//                    intendData.put("isno", cursor.getString(cursor.getColumnIndex(indent_TB_isno)));
//                    intendData.put("issoc", cursor.getString(cursor.getColumnIndex(indent_TB_issoc)));
//                    intendData.put("isvar", cursor.getString(cursor.getColumnIndex(indent_TB_isvar)));
//                    intendData.put("isvill", cursor.getString(cursor.getColumnIndex(indent_TB_isvill)));

                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


    public Map<String, String> getRate() {

        try {
            String selectQuery = "SELECT  * FROM " + RateTable;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("smp_erl", cursor.getString(cursor.getColumnIndex(Rate_TB_smp_erl)));
                    intendData.put("sap_erl", cursor.getString(cursor.getColumnIndex(Rate_TB_sap_erl)));
                    intendData.put("prm_erl", cursor.getString(cursor.getColumnIndex(Rate_TB_prm_erl)));
                    intendData.put("inct_erl", cursor.getString(cursor.getColumnIndex(Rate_TB_inct_erl)));

                    intendData.put("sap_gen", cursor.getString(cursor.getColumnIndex(Rate_TB_sap_gen)));
                    intendData.put("inct_gen", cursor.getString(cursor.getColumnIndex(Rate_TB_inct_gen)));
                    intendData.put("smp_gen", cursor.getString(cursor.getColumnIndex(Rate_TB_smp_gen)));

                    intendData.put("sap_rej", cursor.getString(cursor.getColumnIndex(Rate_TB_sap_rej)));
                    intendData.put("smp_rej", cursor.getString(cursor.getColumnIndex(Rate_TB_smp_rej)));
                    intendData.put("inct_rej", cursor.getString(cursor.getColumnIndex(Rate_TB_inct_rej)));


                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

 /*  public Map<String, String> getHHC_Centre() {

        try {
            String selectQuery = "SELECT  * FROM " + TeraSlipTable;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                   /*"cngen": 0,"filno": 4,"pval": 0,"cntrl": 0,"trl_gen": 3426666,"pcentre": 102,"cncart": 0,"cart_erl": 3353627,"cnrej": 0,"pFilno": 4,
            "cart_rej": 3395518,"cnerl": 0,"pdivn": "P1","trl_erl": 3424522,"cart_gen": 3361183,"trl_rej": 3429414 /*

                    intendData.put("cngen", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cngen)));
                    intendData.put("trl_gen", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_trl_gen)));
                    intendData.put("cncart", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cncart)));
                    intendData.put("cart_erl", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cart_erl)));

                    intendData.put("cnrej", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnrej)));
                    intendData.put("cart_rej", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cart_rej)));
                    intendData.put("cnerl", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnerl)));

                    intendData.put("trl_erl", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_trl_erl)));
                    intendData.put("cart_gen", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cart_gen)));
                    intendData.put("trl_rej", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_trl_rej)));


                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    } */

    public Map<String, String> getUpdatedHHC_CentreAll() {

        try {

            String selectQuery = "SELECT  * FROM " + TeraSlipTable;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    /* "pFilno": 4, "slipcat": 1,"pdivn": "P1","filno": 4,"pval": 0,"pcentre": 102,"cnslipno": 102100000*/

                    intendData.put("slipcat", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_slipcat)));
                    intendData.put("pFilno", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_pFilno)));
                    intendData.put("pdivn", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_pdivn)));
                    intendData.put("filno", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_filno)));

                    intendData.put("pval", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_pval)));
                    intendData.put("pcentre", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_pcentre)));
                    intendData.put("cnslipno", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnslipno)));


                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public String getUpdatedHHC_Centre(String slipcat) {

        try {

            Log.i(TAG, "getIndentDetail: data fatched=slipcat" + slipcat);

            String selectQuery = "SELECT  * FROM " + TeraSlipTable + " where " + HHC_Centre_TB_slipcat + " LIKE " + slipcat;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String data = null;
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    /* "pFilno": 4, "slipcat": 1,"pdivn": "P1","filno": 4,"pval": 0,"pcentre": 102,"cnslipno": 102100000*/

                    data = cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnslipno));


                    Log.i(TAG, "getIndentDetail: data fatched=data" + data);

                } while (cursor.moveToNext());
            }
            cursor.close();
            return data;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public void updateHHC_CentreTareSlip(String tareSlip, String updatedID) {

        try {
            db = getWritableDatabase();
//            ("UPDATE DB_TABLE SET YOUR_COLUMN='newValue' WHERE id=6 "

            db.execSQL("UPDATE " + TeraSlipTable + " SET cnslipno = " + "'" + tareSlip + "' " + " where " + HHC_Centre_TB_slipcat + " LIKE " + updatedID);

            db.close();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public Map<String, String> getControl() {
        try {
            String selectQuery = "SELECT  * FROM " + ControlTable;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                      /* "tare_slips": 1,"purchy_seq": 3,"validation_flg": 3,"blank_role": 0,"filno": 2,"pval": 0,"reprint": 0,"pcentre": 102,"pur_valid_adv": 1,"pur_valid_days": 30,
            "challans": 0,"pFilno": 2,"pdivn": "P1","blank_role_challan": 0,"season": "2019-20","joona_per": 1*/

                    intendData.put("purchy_seq", cursor.getString(cursor.getColumnIndex(Control_TB_purchy_seq)));
                    intendData.put("season", cursor.getString(cursor.getColumnIndex(Control_TB_season)));
                    intendData.put("tare_slips", cursor.getString(cursor.getColumnIndex(Control_TB_tare_slips)));
                    intendData.put(Control_TB_blank_role, cursor.getString(cursor.getColumnIndex(Control_TB_blank_role)));
                    intendData.put(Control_TB_blank_role_challan, cursor.getString(cursor.getColumnIndex(Control_TB_blank_role_challan)));
                    intendData.put(Control_TB_challans, cursor.getString(cursor.getColumnIndex(Control_TB_challans)));
                    intendData.put(Control_TB_modcatflg, cursor.getString(cursor.getColumnIndex(Control_TB_modcatflg)));

                    /*intendData.put("trl_gen", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_trl_gen)));
                    intendData.put("cncart", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cncart)));
                    intendData.put("cart_erl", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cart_erl)));

                    intendData.put("cnrej", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnrej)));
                    intendData.put("cart_rej", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cart_rej)));
                    intendData.put("cnerl", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnerl)));

                    intendData.put("trl_erl", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_trl_erl)));
                    intendData.put("cart_gen", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cart_gen)));
                    intendData.put("trl_rej", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_trl_rej)));*/


                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


    public Map<String, String> getVarityRules() {

        try {
            String selectQuery = "SELECT  * FROM " + Variety_RulesTable;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("varee", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_varee)));
                    intendData.put("vareg", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_vareg)));
                    intendData.put("varer", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_varer)));


                    intendData.put("varge", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_varge)));
                    intendData.put("vargg", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_vargg)));
                    intendData.put("vargr", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_vargr)));

                    intendData.put("varre", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_varre)));
                    intendData.put("varrg", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_varrg)));
                    intendData.put("varrr", cursor.getString(cursor.getColumnIndex(Variety_Rules_TB_varrr)));


                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


    public Map<String, String> getHHCMst() {

        try {
            String selectQuery = "SELECT  * FROM " + HHC_MSTTable;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("cnname", cursor.getString(cursor.getColumnIndex(HHC_MST_TB_cnname)));
                    intendData.put("status", cursor.getString(cursor.getColumnIndex(HHC_MST_TB_valid_fr)));
                    intendData.put("challan_no", cursor.getString(cursor.getColumnIndex(HHC_MST_TB_challan_no)));
                    intendData.put(HHC_MST_TB_wmt_option, cursor.getString(cursor.getColumnIndex(HHC_MST_TB_wmt_option)));
                    intendData.put(HHC_MST_TB_WTZERO, cursor.getString(cursor.getColumnIndex(HHC_MST_TB_WTZERO)));
                    intendData.put(HHC_MST_TB_AVGWT, cursor.getString(cursor.getColumnIndex(HHC_MST_TB_AVGWT)));

                    Log.i(TAG, "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Map<String, String> getHHC_TareSlip(String slipcat) {

        try {
            String selectQuery = "SELECT  * FROM " + TeraSlipTable + " where " + HHC_Centre_TB_slipcat + " LIKE " + slipcat;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {
                    /*private static final String HHC_Centre_TB_pFilno = "pFilno";
                    private static final String HHC_Centre_TB_slipcat = "slipcat";
                    private static final String HHC_Centre_TB_pdivn = "pdivn";
                    private static final String HHC_Centre_TB_filno = "filno";
                    private static final String HHC_Centre_TB_pval = "pval";
                    private static final String HHC_Centre_TB_pcentre = "pcentre";
                    private static final String HHC_Centre_TB_cnslipno = "cnslipno";*/

                    intendData.put("slipcat", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_slipcat)));
                    intendData.put("cnslipno", cursor.getString(cursor.getColumnIndex(HHC_Centre_TB_cnslipno)));


                    Log.i("getHHC_TARE", "getIndentDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Cursor getAllHHC_TareSlip() {

        try {
            String selectQuery = "SELECT  * FROM " + TeraSlipTable ;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            return cursor;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public void updateHHCMstChallanNo(String challanNo) {

        try {
            db = getWritableDatabase();

            db.execSQL("UPDATE " + HHC_MSTTable + " SET challan_no = " + "'" + challanNo + "' ");

            db.close();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void updateHHCTareSlipChallanNo(String challanNo, String updatedID) {

        try {
            db = getWritableDatabase();
//            ("UPDATE DB_TABLE SET YOUR_COLUMN='newValue' WHERE id=6 "

            db.execSQL("UPDATE " + TeraSlipTable + " SET cnslipno = " + "'" + challanNo + "' " + " where " + HHC_Centre_TB_slipcat + " LIKE " + updatedID);

            db.close();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public Map<String, String> getTruckDetail(String truckno) {
        try {

            final String TABLE_NAME = "name of table";

            /*  "pFilno": 13,"pdivn": "P1","filno": 13,"pval": 0,"pcentre": 102,"truck": "4214-UP34L-CT25","tpcd": 102,"bk_upsno": 226 */

            String selectQuery = "SELECT  * FROM " + TruckTable + " where " + Truck_TB_bk_upsno + " LIKE " + truckno;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("pFilno", cursor.getString(cursor.getColumnIndex(Truck_TB_pFilno)));
                    intendData.put("pdivn", cursor.getString(cursor.getColumnIndex(Truck_TB_pdivn)));
                    intendData.put("filno", cursor.getString(cursor.getColumnIndex(Truck_TB_filno)));
                    intendData.put("pval", cursor.getString(cursor.getColumnIndex(Truck_TB_pval)));
                    intendData.put("pcentre", cursor.getString(cursor.getColumnIndex(Truck_TB_pcentre)));
                    intendData.put("truck", cursor.getString(cursor.getColumnIndex(Truck_TB_truck)));
                    intendData.put("tpcd", cursor.getString(cursor.getColumnIndex(Truck_TB_tpcd)));
                    intendData.put("bk_upsno", cursor.getString(cursor.getColumnIndex(Truck_TB_bk_upsno)));

                    Log.i(TAG, "getTruckDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Map<String, String> getContractorDetail(String truckCode) {
        try {

            final String TABLE_NAME = "name of table";

            /*  "pFilno": 12,"concode": 106,"pdivn": "P1","filno": 12,"pval": 0,"pcentre": 102,"conname": "PARAMJEET KAUR" */

            String selectQuery = "SELECT  * FROM " + ContractorsTable + " where " + Contractors_TB_concode + " LIKE " + truckCode;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            String[] data = null;
            List<String> datalist = new ArrayList<>();
            Map<String, String> intendData = new HashMap<>();
            Log.i(TAG, "getIndentDetail: Called=");
            if (cursor.moveToFirst()) {
                do {

                    intendData.put("pFilno", cursor.getString(cursor.getColumnIndex(Contractors_TB_pFilno)));
                    intendData.put("pdivn", cursor.getString(cursor.getColumnIndex(Contractors_TB_pdivn)));
                    intendData.put("filno", cursor.getString(cursor.getColumnIndex(Contractors_TB_filno)));
                    intendData.put("pval", cursor.getString(cursor.getColumnIndex(Contractors_TB_pval)));
                    intendData.put("pcentre", cursor.getString(cursor.getColumnIndex(Contractors_TB_pcentre)));
                    intendData.put("concode", cursor.getString(cursor.getColumnIndex(Contractors_TB_concode)));
                    intendData.put("conname", cursor.getString(cursor.getColumnIndex(Contractors_TB_conname)));


                    Log.i(TAG, "getContractorsDetail: data fatched=");

                } while (cursor.moveToNext());
            }
            cursor.close();
            return intendData;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public boolean checkIsIndentTableEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + IndentTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void deleteIndentData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + IndentTable);
    }

    public boolean checkIsGrowerEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + GrowerTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteGrowerData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + GrowerTable);
    }

    public boolean checkIsVillageEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + VillageTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteVillageTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + VillageTable);
    }

    public boolean checkIsModeEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + ModeTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void deleteModeTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ModeTable);
    }

    public boolean checkIsVarietyTableEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + VarietyTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteVarietyTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + VarietyTable);
    }

    public boolean checkIsRateEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + RateTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteRateTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + RateTable);
    }

    public boolean checkIsTeraSlipEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TeraSlipTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void deleteTera_SlipTableData() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TeraSlipTable);
        } catch (Exception e) {
            Log.i(TAG, "deleteTera_SlipTableData: delete Error=" + e.getMessage());
        }
    }


    public boolean checkIsHHC_MSTEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + HHC_MSTTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void deleteHHC_MSTTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + HHC_MSTTable);
    }

    public boolean checkIsControlTableEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + ControlTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void deleteControlTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ControlTable);
    }

    public boolean checkIsVarietyRuleEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + Variety_RulesTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteVariety_RulesTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Variety_RulesTable);
    }

    public boolean checkIsContractorEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + ContractorsTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteContractorsTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ContractorsTable);
    }


    public boolean checkIsDateEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DateTable;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteDateTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DateTable);
    }

    public boolean checkIsTruckEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TruckTable;
        Cursor mcursor = db.rawQuery(count, null);
        int icount = 0;
        if(mcursor!=null) {
            mcursor.moveToFirst();
              icount = mcursor.getInt(0);
        }
        if (icount > 0) {

            return false;
        } else {
            return true;
        }

    }

    public void deleteTruckTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TruckTable);
    }

/*    public ArrayList<String> showHistory(String calcName) {
        db = getReadableDatabase();
        Cursor cursor;
        ArrayList<String> list = new ArrayList<String>();
        String[] selectionArgs = {calcName};
        //cursor=db.query(table_Name,columns,column1+" LIKE ?",selectionArgs,null,null,null);
        cursor = db.rawQuery("select * from " + table_Name + " where " + column1 + " = ?", selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                String expression = cursor.getString(1);
                list.add(expression);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    public void deleteRecords(String calcName) {
        db = getWritableDatabase();
        String value[] = {calcName};
        int i = db.delete(table_Name, column1 + "=?", value);
        db.close();
    }*/


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }


}
