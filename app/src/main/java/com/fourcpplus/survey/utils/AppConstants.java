package com.fourcpplus.survey.utils;

public class AppConstants {

    //Client MJ API for Prod
    public static String IP_Address = "182.73.83.26:8085";
    public static String BaseUrl = "http://" + IP_Address + "/sgmlapp/api/";
    //FourC API for Testing
    //public static String IP_Address = "199.217.112.145:8089";
    //public static String BaseUrl = "http://" + IP_Address + "/sgmlapp/api/";
    public static final String Role = "Role";
    public static final String IP = "IP";
    public static final String USER_ADMIN = "USER_ADMIN";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_ID = "USER_ID";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String MYDIVISION = "MYDIVISION";
    public static final String MYCENTER = "MYCENTER";
    public static final String MYCENTER_ADMIN = "MYCENTER_ADMIN";
    public static final String MYDIVISION__ADMIN = "MYDIVISION__ADMIN";
    public static final String EMAIL = "EMAIL";
    public static final String photo = "photo";
    public static final String TOKEN = "TOKEN";
    public static final String IS_USER_LOGIN = "IS_USER_LOGIN";
    public static final String ONLY_ONCE = "ONLY_ONCE";
    public static final String GENRATE_RANDOM = "GENRATE_RANDOM";
    public static final String GENRATE_RANDOM_CHLAN = "GENRATE_RANDOM_CHLAN";
    public static final String GENRATE_RANDOM_GROWER = "GENRATE_RANDOM_GROWER";
    public static final String GENRATE_RANDOM_TERA = "GENRATE_RANDOM_TERA";
    public static final String UserLoginUrl = "auth/authentication";
    public static final String surveyDataUploadURL = "data/SurveyDataUpload";
    public static final String dataDownloadUrl = "data/DataDownload";
    public static final String checkOverlap = "PlotOverlap";
    public static final String checkDamru = "DamruPlotCheck";
    public static final String plotRecheck = "PlotRecheck";

    public static boolean ALL_DATA_DOWNLOADED = false;
    public static String TareSlipDuplicate_SINGLE = "";
    public static String TareSlipDuplicate_INBOTH = "";
    public static String GROSSTareSlipDuplicate_SINGLE = "";
    public static String GROSSTareSlipDuplicate_INBOTH = "";
}
