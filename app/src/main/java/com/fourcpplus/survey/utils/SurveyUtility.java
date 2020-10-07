package com.fourcpplus.survey.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.interfaces.ApiCall;
import com.fourcpplus.survey.model.db.SurveyData;
import com.fourcpplus.survey.model.network.ResponseModel;
import com.opencsv.CSVWriter;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rohit on 30-03-2020.
 */
public class SurveyUtility {

    private static DecimalFormat df = new DecimalFormat("#.###");

    public static long calculateDimen(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371e3; // metres
        double l1 = Math.toRadians(lat1);
        double l2 = Math.toRadians(lat2);
        double l3 = Math.toRadians((lat2 - lat1));
        double l4 = Math.toRadians((lng2 - lng1));

        double a = Math.sin(l3 / 2) * Math.sin(l3 / 2) +
                Math.cos(l1) * Math.cos(l2) *
                        Math.sin(l4 / 2) * Math.sin(l4 / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        /*double dims = 6371000 * (acos(cos(Math.toRadians(90 - lat1)) * cos((90 - lat2)) + sin(Math.toRadians(90 -
                lat1)) * sin(Math.toRadians(90 - lat2)) * cos(Math.toRadians(lng1 - lng2))));*/
        return Math.round(R * c);
    }

    public static HashMap<String, String> plantTypeCodes() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("MTHD", "Plant Method");
        hashMap.put("DISE", "Disease");
        hashMap.put("INSE", "Insect");
        hashMap.put("INTC", "Inter Cropping");
        hashMap.put("LAND", "Land Type");
        hashMap.put("CRPC", "Crop Cycle");
        hashMap.put("CCND", "Crop Condition");
        hashMap.put("MIXC", "Mix Crop");
        hashMap.put("IRRG", "Irrigation");
        hashMap.put("BRDC", "Broder Crop");
        hashMap.put("SOIL", "Soil Type");
        hashMap.put("DEMO", "Demo Plot");
        hashMap.put("SSRC", "Seed Source");
        hashMap.put("CRPT", "Crop Type");
        return hashMap;
    }

    public static String getVersionName(Context context) {
        String version = "Version: ";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = version + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String calculateArea(double dims1, double dims2, double dims3, double dims4, String areainacr) {
        double area;
        if (areainacr.equalsIgnoreCase("0")) {
            area = ((dims1 + dims3) * (dims2 + dims4)) / 40000;
        } else
            area = ((((dims1 + dims3) / 2) * ((dims2 + dims4) / 2)) / 4000);
        return df.format(area);
    }

    public static String calculateShareArea(double percent, double plotArea) {
        return df.format((plotArea * percent) / 100);
    }

    public static void showKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean hasPermission(Activity activity, String permission, int reqId) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        if (result == PackageManager.PERMISSION_GRANTED) return true;
        else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission}, reqId);
            return false;
        }
    }

    public static boolean checkPermission(Activity activity) {
        int permissionStorage = ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int permissionLocation = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionReadStorage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    private static RequestBody getRequestBody(String value) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, value);
    }

    public static void showOKAlert(Context context,String message) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
        confirmBuilder
                .setTitle("Alert")
                .setMessage(message).setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog confirmAlert = confirmBuilder.create();
        confirmAlert.show();
    }

    @SuppressLint("SimpleDateFormat")
    public static void submitFormDataWithImage(Context activity, SurveyData surveyData) {
        Map<String, RequestBody> multipartOptions = new HashMap<>();
        try {
            multipartOptions.put("pdivn", getRequestBody(CommonUtil.getPreferencesString(activity, AppConstants.MYDIVISION)));
            multipartOptions.put("pdate", getRequestBody(surveyData.getPdate()));
            multipartOptions.put("PLGastino", getRequestBody(surveyData.getPLGastino()));
            multipartOptions.put("placvill", getRequestBody(surveyData.getPlacvill()));
            multipartOptions.put("PLVill", getRequestBody(surveyData.getPLVill()));
            multipartOptions.put("PLVsno", getRequestBody(String.valueOf(surveyData.getPLVsno())));
            multipartOptions.put("PLGrno", getRequestBody(surveyData.getPLGrno()));
            multipartOptions.put("placvar", getRequestBody(surveyData.getPlacvar()));
            multipartOptions.put("pldim1", getRequestBody(surveyData.getPldim1()));
            multipartOptions.put("pldim2", getRequestBody(surveyData.getPldim2()));
            multipartOptions.put("pldim3", getRequestBody(surveyData.getPldim3()));
            multipartOptions.put("pldim4", getRequestBody(surveyData.getPldim4()));
            multipartOptions.put("pldivsl", getRequestBody(surveyData.getPldivsl()));
            multipartOptions.put("pldivdr", getRequestBody(surveyData.getPldivdr()));
            multipartOptions.put("plratoon", getRequestBody(surveyData.getPlratoon()));
            multipartOptions.put("plpercent", getRequestBody(surveyData.getPlpercent()));
            multipartOptions.put("plarea", getRequestBody(surveyData.getPlarea()));
            multipartOptions.put("plclerk", getRequestBody(surveyData.getPlclerk()));
            multipartOptions.put("pldate", getRequestBody(surveyData.getPldate()));
            if (surveyData.getPlseedflg() != null)
                multipartOptions.put("plseedflg", getRequestBody(surveyData.getPlseedflg()));
            if (surveyData.getPMTHD() != null)
                multipartOptions.put("PMTHD", getRequestBody(surveyData.getPMTHD()));
            if (surveyData.getPDISE() != null)
                multipartOptions.put("PDISE", getRequestBody(surveyData.getPDISE()));
            if (surveyData.getPINSE() != null)
                multipartOptions.put("PINSE", getRequestBody(surveyData.getPINSE()));
            if (surveyData.getPINTC() != null)
                multipartOptions.put("PINTC", getRequestBody(surveyData.getPINTC()));
            if (surveyData.getPLAND() != null)
                multipartOptions.put("PLAND", getRequestBody(surveyData.getPLAND()));
            if (surveyData.getPCRPC() != null)
                multipartOptions.put("PCRPC", getRequestBody(surveyData.getPCRPC()));
            if (surveyData.getPCCND() != null)
                multipartOptions.put("PCCND", getRequestBody(surveyData.getPCCND()));
            if (surveyData.getPMIXC() != null)
                multipartOptions.put("PMIXC", getRequestBody(surveyData.getPMIXC()));
            if (surveyData.getPIRRG() != null)
                multipartOptions.put("PIRRG", getRequestBody(surveyData.getPIRRG()));
            if (surveyData.getPBRDC() != null)
                multipartOptions.put("PBRDC", getRequestBody(surveyData.getPBRDC()));
            if (surveyData.getPDEMO() != null)
                multipartOptions.put("PDEMO", getRequestBody(surveyData.getPDEMO()));
            if (surveyData.getPSOIL() != null)
                multipartOptions.put("PSOIL", getRequestBody(surveyData.getPSOIL()));
            if (surveyData.getPSSRC() != null)
                multipartOptions.put("PSSRC", getRequestBody(surveyData.getPSSRC()));
            multipartOptions.put("pstatus", getRequestBody("1"));
            multipartOptions.put("pstype", getRequestBody(surveyData.getPstype()));
            multipartOptions.put("plat1", getRequestBody(String.valueOf(surveyData.getPlat1())));
            multipartOptions.put("plon1", getRequestBody(String.valueOf(surveyData.getPlon1())));
            multipartOptions.put("plat2", getRequestBody(String.valueOf(surveyData.getPlat2())));
            multipartOptions.put("plon2", getRequestBody(String.valueOf(surveyData.getPlon2())));
            multipartOptions.put("plat3", getRequestBody(String.valueOf(surveyData.getPlat3())));
            multipartOptions.put("plon3", getRequestBody(String.valueOf(surveyData.getPlon3())));
            multipartOptions.put("plat4", getRequestBody(String.valueOf(surveyData.getPlat4())));
            multipartOptions.put("plon4", getRequestBody(String.valueOf(surveyData.getPlon4())));
            multipartOptions.put("pimei", getRequestBody(surveyData.getPimei()));
            submitData(multipartOptions, activity, surveyData.getPLVsno(), surveyData.getSurveyImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null)
            return false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo anInfo : info)
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }
        return false;
    }

    private static String getDefaultImage(Context context) {
        String file = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("download.png");
            File wallpaperDirectory = new File(android.os.Environment.getExternalStorageDirectory(), "/SurveyApp");
            if (!wallpaperDirectory.exists()) wallpaperDirectory.mkdirs();
            File out = new File(wallpaperDirectory, "dummy.png");
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(out);
            int read = 0;
            while ((read = is.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.flush();
            fos.close();
            is.close();
            file = out.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void submitData(Map<String, RequestBody> multipartOptions, final Context activity, final int plVsno, String imagePath) {
        try {
            ApiCall apiCall = ServiceGenerator.createService(ApiCall.class);
            MultipartBody.Part body = null;
            try {
                File imageFile;
                if (imagePath == null) {
                    imageFile = new File(getDefaultImage(activity));
                } else {
                    imageFile = new File(imagePath);
                }
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/*"), imageFile);
                body = MultipartBody.Part.createFormData("surveyimage", imageFile.getName(), requestFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Call<ResponseModel> call;
            call = apiCall.surveyDataUpload(multipartOptions, body);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getResults().get(0).get1() != null) {
                            // CommonUtil.toast(response.body().getResults().get(0).get1(), activity);
                            SurveyDatabase.getInstance(activity).surveyDao().updateSurvey("S", plVsno);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                    CommonUtil.toast("Form Update failed.", activity);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<SurveyData> getPendingSurveys(Context context) {
        return SurveyDatabase.getInstance(context).surveyDao().getPendingSurveyData("P");
    }

    private static File getCurrentCSVFile(String table) {
        if (table == null) {
            return null;
        }
        return new File(getCSVBackupFolder(),
                new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Calendar.getInstance().getTime()) + "_TBL_" + table + ".csv");
    }

    private static File getCurrentCSVFileForManualUpload(String table) {
        if (table == null) {
            return null;
        }
        return new File(getCSVBackupFolder(),
                new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Calendar.getInstance().getTime()) + "_TBL_ManualUpload_" + table + ".csv");
    }


    private static String getCSVBackupFolder() {
        String dataFolder = getDataFolder();
        File file = new File(dataFolder , "/CSVBackups");
        if (!file.exists()) {
            file.mkdir();
        }
        return dataFolder + "CSVBackups/";
    }

    private static String getDataFolder() {
        File wallpaperDirectory = new File(android.os.Environment.getExternalStorageDirectory(), "/SurveyApp/");
        if (!wallpaperDirectory.exists()) wallpaperDirectory.mkdirs();
        return wallpaperDirectory.getAbsolutePath();
    }

    public static void writeCSVRecord(HashMap<String, Object> contentValues, String tableName, boolean manualUpload) {
        File csvFile;
        if (manualUpload) {
            csvFile = getCurrentCSVFileForManualUpload(tableName);
        } else {
            csvFile = getCurrentCSVFile(tableName);
        }
        CSVWriter writer;
        boolean columnsFlag = false;

        try {
            if (!csvFile.exists()) {
                columnsFlag = true;
            }

            writer = new CSVWriter(new FileWriter(csvFile, true));
            try {
                List<String[]> entries = new ArrayList<>();
                ArrayList<String> columns = new ArrayList<>();
                ArrayList<String> values = new ArrayList<>();
                for (String key : contentValues.keySet()) {
                    String value = getDisplayString(String.valueOf(contentValues.get(key)));
                    if (columnsFlag) {
                        columns.add(key);
                    }

                    values.add(value);
                }

                if (columnsFlag) {
                    entries.add(columns.toArray(new String[0]));
                }

                entries.add(values.toArray(new String[0]));
                writer.writeAll(entries);

                try {
                    writer.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            } catch (Exception e) {
                Log.e("CSVWriter", Objects.requireNonNull(e.getMessage()));
            }

        } catch (Exception e) {
            Log.e("CSVWriter", Objects.requireNonNull(e.getMessage()));
        }
    }

    private static String getDisplayString(String str) {
        if (str.isEmpty()) {
            return XmlPullParser.NO_NAMESPACE;
        }

        return str;
    }


}
