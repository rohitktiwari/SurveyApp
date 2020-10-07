package com.fourcpplus.survey.ui;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fourcpplus.survey.R;
import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.model.db.ControlData;
import com.fourcpplus.survey.model.db.GrowerData;
import com.fourcpplus.survey.model.db.MDPIData;
import com.fourcpplus.survey.model.db.VarietyData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataDownLoadFrag extends Fragment implements View.OnClickListener {
    private static final String TAG = "DataDownloading";
    DBHelper dbHelper;
    List<VillageData> villageDataList = new ArrayList<>();
    List<ControlData> controlDataList = new ArrayList<>();
    List<GrowerData> growerDataList = new ArrayList<>();
    List<VarietyData> varietyDataList = new ArrayList<>();
    List<MDPIData> mdpiDataList = new ArrayList<>();
    private LinearLayout indentLL, villageLL, growerLL, trucksLL, allMasterLL, varietyLL, controlL;
    private ProgressDialog progressDialog;
    private RequestQueue dataDownloadReq;
    private String division, pCenter, user_id, user_password;
    private Context mContext;
    private Button downloadAllDATA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_data_down_load, container, false);


        mContext = container.getContext();
        progressDialog = new ProgressDialog(mContext);
        //setTitle("Download Data.");

        dataDownloadReq = Volley.newRequestQueue(mContext);

        indentLL = root.findViewById(R.id.indent);
        villageLL = root.findViewById(R.id.village);
        growerLL = root.findViewById(R.id.grower);
        trucksLL = root.findViewById(R.id.truck);
        allMasterLL = root.findViewById(R.id.all_master);
        varietyLL = root.findViewById(R.id.variety);
        controlL = root.findViewById(R.id.control);
        downloadAllDATA = root.findViewById(R.id.downloadAllData);
        dbHelper = new DBHelper(mContext);

        //  SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        pCenter = CommonUtil.getPreferencesString(mContext, AppConstants.MYCENTER);


        division = CommonUtil.getPreferencesString(mContext, AppConstants.MYDIVISION);

        user_id = CommonUtil.getPreferencesString(mContext, AppConstants.USER_ID);

        user_password = CommonUtil.getPreferencesString(mContext, AppConstants.USER_PASSWORD);


        Log.i(TAG, "onCreate: " + pCenter + division + user_id + user_password);


        indentLL.setOnClickListener(this);
        villageLL.setOnClickListener(this);
        growerLL.setOnClickListener(this);
        trucksLL.setOnClickListener(this);
        allMasterLL.setOnClickListener(this);
        varietyLL.setOnClickListener(this);

        controlL.setOnClickListener(this);

        downloadAllDATA.setOnClickListener(this);

        int nbThreads = Thread.getAllStackTraces().keySet().size();

        Log.i("nbThreads", "onCreateView: nbThreads=" + nbThreads);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.indent://survey data
                //  downloadData("14", "0");
                //new DownloadData().execute("14", "0");
                break;
            case R.id.village:
                //  downloadData("7", "0");
                new DownloadData().execute("7", "0");
                break;
            case R.id.grower:
                new TimeTakingDownloadData().execute("8", "0");
                //timeTakingDataDownload("8", "0");
                break;
            case R.id.truck://truck means mdpi 34
//                timeTakingDataDownload("13", "0");
                new TimeTakingDownloadData().execute("34", "0");
                break;
            case R.id.all_master:
//                downloadData("0", "0");
                // new DownloadData().execute("0", "0");
                break;
            case R.id.variety:
//                downloadData("9", "0");
                new DownloadData().execute("9", "0");
                break;
            case R.id.control:
//                downloadData("2", "0");
                new DownloadData().execute("35", "0");
                break;

            case R.id.downloadAllData:


               /* //  autoMaticDownloadAllData("1", "0");
                final ProgressDialog progressDialogDownload = new ProgressDialog(mContext);
                progressDialogDownload.setMessage("Please Wait..\nDownloading All Data\nThis may take a while. Please be patient....");
                progressDialogDownload.setCanceledOnTouchOutside(false);
                progressDialogDownload.show();

                Intent intent = new Intent(getContext(), DataDownloadIntentService.class);
                intent.putExtra("pFile", "1");
                intent.putExtra("pVal", "0");
                requireActivity().startService(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ALL_DATA_DOWNLOADED) {
                            if (progressDialogDownload.isShowing()) {
                                progressDialogDownload.dismiss();
                            }
                            CommonUtil.toast("Data Downloaded.", mContext);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (progressDialogDownload.isShowing()) {
                                        progressDialogDownload.dismiss();
                                    }
                                    CommonUtil.toast("Data Downloaded", mContext);
                                }
                            }, 60000);
                        }
                    }
                },60000);
*/


                break;

        }

    }


    /**********************************************************************************************************************************************/
    private void timeTakingDataDownload(final String fileNo, final String pVal) {
        if (CommonUtil.isNetworkAvailable(mContext)) {
            try {

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(mContext);
                        }
                        progressDialog.setMessage("Please Wait..\n  This may take a while. Please be patient....");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                    }
                });

                String UserLoginUrl = AppConstants.BaseUrl + AppConstants.dataDownloadUrl;

                StringRequest bigDataDownload = new StringRequest(Request.Method.POST, UserLoginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Log.i(TAG, "onResponse: Data download Response=" + response);

                            JSONObject responseObj = new JSONObject(response);

                            if (!responseObj.getString("ResponseMessage").isEmpty() || responseObj.getString("ResponseMessage") != null) {
                                //   Toast.makeText(mContext, responseObj.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                                CommonUtil.toast(responseObj.getString("ResponseMessage"), mContext);
                            }

                            Log.i(TAG, "onResponse: Data download Response=" + response);


                            if (responseObj.getString("ResponseCode").equals("1")) {

                                final JSONArray dataArray = responseObj.getJSONArray("dataDownload");

                                switch (fileNo) {

                                    case "8":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
                                                        //String gno, String gtarea, String filno, String pval, String gsoccd, String pcentre, String gname, String gwmtlock, String pFilno, String pdivn, String gvill, String gfather, String gucd
                                                        GrowerData growerData = new GrowerData();
                                                        growerData.setsNo(i+1);
                                                        growerData.setFilno(data.optString("filno"));
                                                        growerData.setGfather(data.optString("gfather"));
                                                        growerData.setGname(data.optString("gname"));
                                                        growerData.setGsoccd(data.optString("gsoccd"));
                                                        growerData.setGtarea(data.optString("gtarea"));
                                                        growerData.setGno(data.optString("gno"));
                                                        growerData.setGucd(data.optString("gucd"));
                                                        growerData.setGvill(data.optString("gvill"));
                                                        growerData.setGwmtlock(data.optString("gwmtlock"));
                                                        growerData.setPcentre(data.optString("pcentre"));
                                                        growerData.setPdivn(data.optString("pdivn"));
                                                        growerData.setPval(data.optString("pval"));
                                                        growerDataList.add(growerData);
                                                  /*      dbHelper.insertGrowerData(data.getString("gno"), data.getString("gtarea"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("gsoccd"), data.getString("pcentre"), data.getString("gname"), data.getString("gwmtlock"), data.getString("pFilno"), data.getString("pdivn"), data.getString("gvill"), data.getString("gfather"), data.getString("gucd"));

*/
                                                    } catch (Exception e) {
                                                        //  Toast.makeText(mContext, "Grower Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Grower Data Not Downloaded", mContext);

                                                        Log.i(TAG, "onResponse: Grower error=" + e.getMessage());
                                                    }
                                                }
                                                new populateGrower().execute();
                                            }
                                        }).start();
                                        break;
                                    case "13":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsTruckEmpty());
                                                dbHelper.deleteTruckTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
                                                        /*  "pFilno": 13,"pdivn": "P1","filno": 13,"pval": 0,"pcentre": 102,"truck": "4214-UP34L-CT25","tpcd": 102,"bk_upsno": 226 */
                                                        dbHelper.insertTruckData(data.getString("pFilno"), data.getString("pdivn"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("pcentre"), data.getString("truck"), data.getString("tpcd"), data.getString("bk_upsno"));

                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: date error=" + e.getMessage());

                                                    }
                                                }
                                            }
                                        }).start();
                                        break;

                                    case "34":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    JSONObject data = null;
                                                    try {
                                                        data = dataArray.getJSONObject(i);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    try {
                                                        MDPIData mdpiData = new MDPIData();
                                                        mdpiData.setsNo(i+1);
                                                        mdpiData.setFilno(data.optString("filno"));
                                                        mdpiData.setPcentre(data.optString("pcentre"));
                                                        mdpiData.setPdivn(data.optString("pdivn"));
                                                        mdpiData.setpFilno(data.optString("pFilno"));
                                                        mdpiData.setPval(data.optString("pval"));
                                                        mdpiData.setMcode(data.optString("mcode"));
                                                        mdpiData.setMdesc(data.optString("mdesc"));
                                                        mdpiData.setMtype(data.optString("mtype"));
                                                        mdpiDataList.add(mdpiData);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                new populateMDPI().execute();
                                            }
                                        }).start();
                                        break;
                                }


                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }

                        } catch (Exception e) {
                            e.getMessage();
                            Log.i(TAG, "onErrorResponse: Error=" + e.getMessage());
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Log.i(TAG, "onErrorResponse:Volley Error=" + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> userparms = new HashMap<>();
                        userparms.put("userid", user_id);
                        userparms.put("password", user_password);
                        userparms.put("pdivn", division);
                        userparms.put("pcentre", user_id);
                        userparms.put("pimei", CommonUtil.getDeviceIMEI(requireActivity()));
                        userparms.put("pval", pVal);
                        userparms.put("pFilno", fileNo);
                        userparms.put("pextra", "S1");

                        Log.i(TAG, "getParams: " + user_id);
                        Log.i(TAG, "getParams: " + user_password);
                        Log.i(TAG, "getParams: " + division);
                        Log.i(TAG, "getParams: " + pCenter);
                        Log.i(TAG, "getParams: " + pVal);
                        Log.i(TAG, "getParams: " + fileNo);

                        return userparms;
                    }

                };

                bigDataDownload.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                dataDownloadReq.add(bigDataDownload);


            } catch (Exception e) {
                Log.i(TAG, "userLogin: Error=" + e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        } else {
            CommonUtil.toast("no internet connection", mContext);
        }

    }

    /**********************************************************************************************************************************************/
    private void downloadData(final String fileNo, final String pVal) {
        if (CommonUtil.isNetworkAvailable(mContext)) {
            try {


                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(mContext);
                        }
                        progressDialog.setMessage("Please Wait..\n  This may take a while. Please be patient....");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                    }
                });


              /*  if (progressDialog == null) {
                    progressDialog = new ProgressDialog(mContext);
                }
                progressDialog.setMessage("Please Wait..\n  This may take a while. Please be patient....");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();*/

                String UserLoginUrl = AppConstants.BaseUrl + AppConstants.dataDownloadUrl;

                StringRequest datadownloadreq = new StringRequest(Request.Method.POST, UserLoginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.i(TAG, "onResponse: Data download Response=" + response);
                            JSONObject responseObj = new JSONObject(response);
                            if (!responseObj.getString("ResponseMessage").isEmpty() || responseObj.getString("ResponseMessage") != null) {
                                //   Toast.makeText(mContext, responseObj.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                                CommonUtil.toast(responseObj.getString("ResponseMessage"), mContext);
                            }
                            Log.i(TAG, "onResponse: Data download Response=" + response);
                            if (responseObj.getString("ResponseCode").equals("1")) {
                                final JSONArray dataArray = responseObj.getJSONArray("dataDownload");
                                switch (fileNo) {
                                    case "14":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dbHelper.deleteIndentData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    JSONObject data = null;
                                                    try {
                                                        data = dataArray.getJSONObject(i);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.i(TAG, "onResponse: data=" + data);
//                                     String isvar, String issoc, String filno, String pval, String isvill, String pcentre, String isstatus, String isdeldt, String pFilno, String pdivn, String ismode, String isno
                                                    try {
                                                        dbHelper.insertIndentData(data.getString("isvar"), data.getString("issoc"), data.getString("isgno"), data.getString("filno")
                                                                , data.getString("pval"), data.getString("isvill"), data.getString("pcentre")
                                                                , data.getString("isstatus"), data.getString("isdeldt"), data.getString("pFilno"), data.getString("pdivn"), data.getString("ismode"), data.getString("isno"));
//
//
                                                    } catch (Exception e) {
                                                        //  Toast.makeText(mContext, "Indent Data Not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Indent Data Not Downloaded", mContext);

                                                        Log.i(TAG, "onResponse: Insert Error=" + e.getMessage());
                                                    }
                                                }
                                                //  Toast.makeText(mContext,"Indent Data Downloaded",Toast.LENGTH_SHORT).show();


                                                //   Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsIndentTableEmpty());
                                            }
                                        }).start();
                                        break;
                                    case "7":

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsVillageEmpty());

//                                                dbHelper.deleteVillageTableData();

                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    JSONObject data = null;
                                                    try {
                                                        data = dataArray.getJSONObject(i);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    try {
                                                        VillageData villageData = new VillageData();
                                                        villageData.setsNo(i+1);
                                                        villageData.setFilno(data.optString("filno"));
                                                        villageData.setPcentre(data.optString("pcentre"));
                                                        villageData.setPdivn(data.optString("pdivn"));
                                                        villageData.setpFilno(data.optString("pFilno"));
                                                        villageData.setPval(data.optString("pval"));
                                                        villageData.setVname(data.optString("vname"));
                                                        villageData.setVcode(data.optString("vcode"));
                                                        villageData.setPlsrno(data.optString("plsrno"));
                                                        villageData.setVilltype(data.optString("villtype"));
                                                        villageDataList.add(villageData);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                new populateVillage().execute();
                                            }
  /*                                                  try {
                                                        //String pFilno, String vname, String pdivn, String filno, String pval, String pcentre, String vcode
                                                        Log.i(TAG, "onResponse: ");
                                                        dbHelper.insertVillageData(data.getString("pFilno"), data.getString("vname"), data.getString("pdivn"), data.getString("filno")
                                                                , data.getString("pval"), data.getString("pcentre"), data.getString("vcode"));
                                                    } catch (Exception e) {
                                                        //   Toast.makeText(mContext, "Village Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Village Data Not Downloaded", mContext);
                                                        Log.i(TAG, "onResponse: village error=" + e.getMessage());
                                                    }
                                                }
                                                //    Toast.makeText(mContext,"Village Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
*/
                                        }).start();


                                        break;
                                    case "8":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {


                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsGrowerEmpty());
                                                //dbHelper.deleteGrowerData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
                                                        //String gno, String gtarea, String filno, String pval, String gsoccd, String pcentre, String gname, String gwmtlock, String pFilno, String pdivn, String gvill, String gfather, String gucd
                                                        GrowerData growerData = new GrowerData();
                                                        growerData.setFilno(data.optString("filno"));
                                                        growerData.setGfather(data.optString("gfather"));
                                                        growerData.setGname(data.optString("gname"));
                                                        growerData.setGsoccd(data.optString("gsoccd"));
                                                        growerData.setGtarea(data.optString("gtarea"));
                                                        growerData.setGno(data.optString("gno"));
                                                        growerData.setGucd(data.optString("gucd"));
                                                        growerData.setGvill(data.optString("gvill"));
                                                        growerData.setGwmtlock(data.optString("gwmtlock"));
                                                        growerData.setPcentre(data.optString("pcentre"));
                                                        growerData.setPdivn(data.optString("pdivn"));
                                                        growerData.setPval(data.optString("pval"));
                                                        growerDataList.add(growerData);
                                                  /*      dbHelper.insertGrowerData(data.getString("gno"), data.getString("gtarea"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("gsoccd"), data.getString("pcentre"), data.getString("gname"), data.getString("gwmtlock"), data.getString("pFilno"), data.getString("pdivn"), data.getString("gvill"), data.getString("gfather"), data.getString("gucd"));

*/
                                                    } catch (Exception e) {
                                                        //  Toast.makeText(mContext, "Grower Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Grower Data Not Downloaded", mContext);

                                                        Log.i(TAG, "onResponse: Grower error=" + e.getMessage());
                                                    }
                                                }
                                                new populateGrower().execute();
                                                //     Toast.makeText(mContext,"Grower Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "10":

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {


                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsModeEmpty());
                                                dbHelper.deleteModeTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
//                                        String mdcat, String mdmaxwt, String filno, String pval, String md_tare_minwt, String pcentre, String mdcode, String mdminwt, String pFilno, String pdivn, String md_tare_maxwt
                                                        dbHelper.insertModeData(data.getString("mdcat"), data.getString("mdmaxwt"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("md_tare_minwt"), data.getString("pcentre"), data.getString("mdcode"), data.getString("mdminwt"), data.getString("pFilno"), data.getString("pdivn"), data.getString("md_tare_maxwt"));


                                                    } catch (Exception e) {
                                                        //  Toast.makeText(mContext, "Mode Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Mode Data Not Downloaded", mContext);

                                                        Log.i(TAG, "onResponse: Mode error=" + e.getMessage());
                                                    }
                                                }
                                                //       Toast.makeText(mContext,"Mode Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "9":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsVarietyTableEmpty());
                                                //dbHelper.deleteVarietyTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);

                                                        VarietyData varietyData = new VarietyData();
                                                        varietyData.setsNo(i+1);
                                                        varietyData.setFilno(data.optString("filno"));
                                                        varietyData.setPcentre(data.optString("pcentre"));
                                                        varietyData.setPdivn(data.optString("pdivn"));
                                                        varietyData.setpFilno(data.optString("pFilno"));
                                                        varietyData.setPval(data.optString("pval"));
                                                        varietyData.setVarcat(data.optString("varcat"));
                                                        varietyData.setVarname(data.optString("varname"));
                                                        varietyData.setVarcode(data.optString("varcode"));
                                                        varietyDataList.add(varietyData);
                                                  /*
//                                        String varcat, String varname, String filno, String pval, String varcode, String pcentre, String pFilno, String pdivn
                                                        dbHelper.insertVarietyData(data.getString("varcat"), data.getString("varname"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("varcode"), data.getString("pcentre"), data.getString("pFilno"), data.getString("pdivn"));
*/

                                                    } catch (Exception e) {
                                                        // Toast.makeText(mContext, "Variety Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Variety Data Not Downloaded", mContext);

                                                        Log.i(TAG, "onResponse: Variety error=" + e.getMessage());
                                                    }
                                                }
                                                new populateVariety().execute();
                                                ///            Toast.makeText(mContext,"Variety Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "5":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsRateEmpty());
                                                dbHelper.deleteRateTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
                                                        Log.i(TAG, "onResponse: data=" + data);
//                                            String smp_gen, String prm_erl, String filno, String pval, String inct_rej
//                                                    , String pcentre, String pFilno, String pdivn, String smp_rej, String sap_erl
//                                                    , String inct_gen, String sap_rej, String smp_erl, String sap_gen, String inct_erl
                                                        dbHelper.insertRateData(data.getString("smp_gen"), data.getString("prm_erl"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("inct_rej"), data.getString("pcentre"), data.getString("pFilno"), data.getString("pdivn"), data.getString("smp_rej"), data.getString("sap_erl")
                                                                , data.getString("inct_gen"), data.getString("sap_rej"), data.getString("smp_erl"), data.getString("sap_gen"), data.getString("inct_erl"));

                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: Rate error=" + e.getMessage());
                                                        // Toast.makeText(mContext, "Rate Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Rate Data Not Downloaded", mContext);

                                                    }
                                                }
                                                //         Toast.makeText(mContext,"Rate Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "4":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsTeraSlipEmpty());
                                                dbHelper.deleteTera_SlipTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);

                                                        Log.i(TAG, "onResponse: data=" + data);

//                                        String cngen, String cntrl, String filno, String pval, String trl_gen, String pcentre, String cncart, String cart_erl, String pFilno, String pdivn,
//                                                String cnrej, String cart_rej, String cnerl, String trl_erl, String cart_gen, String trl_rej
                                              /*  dbHelper.insertHHC_CentreData(data.getString("cngen"), data.getString("cntrl"), data.getString("filno"), data.getString("pval")
                                                        , data.getString("trl_gen"), data.getString("pcentre"), data.getString("cncart"), data.getString("cart_erl"), data.getString("pFilno"), data.getString("pdivn")
                                                        , data.getString("cnrej"), data.getString("cart_rej"), data.getString("cnerl"), data.getString("trl_erl"), data.getString("cart_gen"), data.getString("trl_rej"));
                                              */

                                                        /* "pFilno": 4, "slipcat": 1,"pdivn": "P1","filno": 4,"pval": 0,"pcentre": 102,"cnslipno": 102100000*/

                                                        dbHelper.insertTeraSlipUpdatedData(data.getString("slipcat"), data.getString("filno"), data.getString("pval"), data.getString("cnslipno"), data.getString("pcentre"), data.getString("pFilno"), data.getString("pdivn"));

                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: HHC_Centre error=" + e.getMessage());
                                                        //Toast.makeText(mContext, "HHC Centre Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("HHC Centre Data Not Downloaded", mContext);

                                                    }
                                                }
                                                //          Toast.makeText(mContext,"HHC Centre Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;
                                    case "3":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsHHC_MSTEmpty());
                                                dbHelper.deleteHHC_MSTTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
//                                        String valid_fr, String hada, String filno, String pval, String cnname, String pcentre, String hr_start, String challan_no, String pFilno, String pdivn,
//                                  String hr_end, String hstatus, String extra_valid_days, String cnfree, String wbnos, String wmt_option
                                                        dbHelper.insertHHC_MSTData(data.getString("OFFONLINE"), data.getString("hada"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("cnname"), data.getString("pcentre"), data.getString("hr_start"), data.getString("challan_no"), data.getString("pFilno"), data.getString("pdivn")
                                                                , data.getString("hr_end"), data.getString("hstatus"), data.getString("extra_valid_days"), data.getString("cnfree"), data.getString("wbnos"), data.getString("wmt_option"), data.getString("WTZERO"), data.getString("AVGWT"), data.getString("TAREDAYS"));
                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: HHC_MST error=" + e.getMessage());
                                                        // Toast.makeText(mContext, "HHC MST Data not Downloaded", Toast.LENGTH_SHORT).show();

                                                        CommonUtil.toast("HHC MST Data Not Downloaded", mContext);

                                                    }
                                                }
                                                //         Toast.makeText(mContext,"HHC MST Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;
                                    case "2":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsControlTableEmpty());
                                                dbHelper.deleteControlTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
//                                        String tare_slips, String purchy_seq, String filno, String pval, String validation_flg, String pcentre, String blank_role, String reprint, String pFilno, String pdivn,
//                                                String pur_valid_adv, String pur_valid_days, String challans, String blank_role_challan, String season, String joona_per
                                                        dbHelper.insertControlData(data.getString("tare_slips"), data.getString("purchy_seq"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("validation_flg"), data.getString("pcentre"), data.getString("blank_role"), data.getString("reprint"), data.getString("pFilno"), data.getString("pdivn")
                                                                , data.getString("pur_valid_adv"), data.getString("pur_valid_days"), data.getString("challans"), data.getString("blank_role_challan"), data.getString("season"), data.getString("joona_per"), data.getString("modcatflg"));
                                                    } catch (Exception e) {
                                                        CommonUtil.toast("Control Data Not Downloaded", mContext);

                                                        Log.i(TAG, "onResponse: Control error=" + e.getMessage());
                                                        // Toast.makeText(mContext, "Control Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                //          Toast.makeText(mContext,"Control Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;
                                    case "11":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsVarietyRuleEmpty());
                                                dbHelper.deleteVariety_RulesTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
//                                       String vareg, String varge, String filno, String pval, String varee, String pcentre, String varrr, String varrg, String pFilno, String pdivn,
//                                       String varre, String varer, String vargr
                                                        dbHelper.insertVarietyRulesData(data.getString("vargg"), data.getString("vareg"), data.getString("varge"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("varee"), data.getString("pcentre"), data.getString("varrr"), data.getString("varrg"), data.getString("pFilno"), data.getString("pdivn")
                                                                , data.getString("varre"), data.getString("varer"), data.getString("vargr"));
                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: Variety Rules error=" + e.getMessage());
                                                        CommonUtil.toast("Variety Data Not Downloaded", mContext);

                                                        //  Toast.makeText(mContext, "Variety Rules Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                //         Toast.makeText(mContext,"Variety Rules Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "12":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsContractorEmpty());
                                                dbHelper.deleteContractorsTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
//                                      String concode, String conname, String filno, String pval, String pcentre, String pFilno, String pdivn
                                                        dbHelper.insertContractorsData(data.getString("concode"), data.getString("conname"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("pcentre"), data.getString("pFilno"), data.getString("pdivn"));


                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: Contractors error=" + e.getMessage());
                                                        //  Toast.makeText(mContext, "Contractors Data not Downloaded", Toast.LENGTH_SHORT).show();
                                                        CommonUtil.toast("Contractors Data Not Downloaded", mContext);

                                                    }
                                                }
                                                //          Toast.makeText(mContext,"Contractors Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;
                                    case "13":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsTruckEmpty());
                                                dbHelper.deleteTruckTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
                                                        /*  "pFilno": 13,"pdivn": "P1","filno": 13,"pval": 0,"pcentre": 102,"truck": "4214-UP34L-CT25","tpcd": 102,"bk_upsno": 226 */
                                                        dbHelper.insertTruckData(data.getString("pFilno"), data.getString("pdivn"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("pcentre"), data.getString("truck"), data.getString("tpcd"), data.getString("bk_upsno"));

                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: date error=" + e.getMessage());
                                                        CommonUtil.toast("Date Data Not Downloaded", mContext);

                                                        // Toast.makeText(mContext, "Date Data not" + " Downloaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                //          Toast.makeText(mContext,"Date Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "21":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "onResponse: Is Table Empty======" + dbHelper.checkIsDateEmpty());
                                                dbHelper.deleteDateTableData();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
//                                       String currdate, String filno, String pval, String pcentre, String pFilno, String pdivn
                                                        dbHelper.insertDateData(data.getString("currdate"), data.getString("filno"), data.getString("pval")
                                                                , data.getString("pcentre"), data.getString("pFilno"), data.getString("pdivn"));

                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: date error=" + e.getMessage());
                                                        CommonUtil.toast("Date Data Not Downloaded", mContext);

                                                        // Toast.makeText(mContext, "Date Data not" + " Downloaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                //          Toast.makeText(mContext,"Date Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;

                                    case "35":
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {

                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    try {
                                                        JSONObject data = dataArray.getJSONObject(i);
                                                        ControlData controlData = new ControlData();
                                                        controlData.setsNo(i+1);
                                                        controlData.setAreainacr(data.optString("areainacr"));
                                                        controlData.setClachk(data.optString("clachk"));
                                                        controlData.setFilno(data.optString("filno"));
                                                        controlData.setGbroder_crop(data.optString("gbroder_crop"));
                                                        controlData.setGccondition(data.optString("gccondition"));
                                                        controlData.setGcrop(data.optString("gcrop"));
                                                        controlData.setGdemo(data.optString("gdemo"));
                                                        controlData.setGdisease(data.optString("gdisease"));
                                                        controlData.setGgatanum(data.optString("ggatanum"));
                                                        controlData.setGicrop(data.optString("gicrop"));
                                                        controlData.setGinsect(data.optString("ginsect"));
                                                        controlData.setGirrigation(data.optString("girrigation"));
                                                        controlData.setGlastyr_refplot(data.optString("glastyr_refplot"));
                                                        controlData.setGltype(data.optString("gltype"));
                                                        controlData.setGmcrop(data.optString("gmcrop"));
                                                        controlData.setGmethod(data.optString("gmethod"));
                                                        controlData.setGmlen(data.optString("gmlen"));
                                                        controlData.setGpldate(data.optString("gpldate"));
                                                        controlData.setGpsflg(data.optString("gpsflg"));
                                                        controlData.setGsame_ccycle(data.optString("gsame_ccycle"));
                                                        controlData.setGsame_var(data.optString("gsame_var"));
                                                        controlData.setGseed_source(data.optString("gseed_source"));
                                                        controlData.setGstype(data.optString("gstype"));
                                                        controlData.setHRSTART(data.optString("HRSTART"));
                                                        controlData.setHRSTOP(data.optString("HRSTOP"));
                                                        controlData.setMaxcord(data.optString("maxcord"));
                                                        controlData.setMaxplarea(data.optString("maxplarea"));
                                                        controlData.setMIDHRSTART(data.optString("MIDHRSTART"));
                                                        controlData.setMIDHRSTOP(data.optString("MIDHRSTOP"));
                                                        controlData.setMincord(data.optString("mincord"));
                                                        controlData.setOverlapallow(data.optString("overlapallow"));
                                                        controlData.setPcentre(data.optString("pcentre"));
                                                        controlData.setPval(data.optString("pval"));
                                                        controlData.setPdivn(data.optString("pdivn"));
                                                        controlData.setpFilno(data.optString("pFilno"));
                                                        controlData.setPrintrep(data.optString("printrep"));
                                                        controlData.setPrintslip(data.optString("printslip"));
                                                        controlData.setOverlapvalue(data.optString("overlapvalue"));
                                                        controlData.setDamruallow(data.optString("damruallow"));
                                                        controlData.setSuvflag(data.optString("suvflag"));
                                                        controlData.setCname(data.optString("cname"));
                                                        controlDataList.add(controlData);
                                                    } catch (Exception e) {
                                                        Log.i(TAG, "onResponse: date error=" + e.getMessage());
                                                        CommonUtil.toast("Control Data Not Downloaded", mContext);
                                                        // Toast.makeText(mContext, "Date Data not" + " Downloaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                new populateControl().execute();
                                                //          Toast.makeText(mContext,"Date Data Downloaded",Toast.LENGTH_SHORT).show();
                                            }
                                        }).start();
                                        break;
                                }

                                Log.i("THREAD_TEST", "onResponse:UI Thread called1");


                                Log.i("THREAD_TEST", "onResponse:UI Thread called2");
                                if (progressDialog.isShowing()) {
                                    Log.i("THREAD_TEST", "onResponse:UI Thread called3");
                                    progressDialog.dismiss();
                                }


                            } else {
                                //  Toast.makeText(mContext, responseObj.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }


                        } catch (Exception e) {
                            e.getMessage();
                            Log.i(TAG, "onErrorResponse: Error=" + e.getMessage());
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Log.i(TAG, "onErrorResponse:Volley Error=" + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> userparms = new HashMap<>();
                        userparms.put("userid", user_id);
                        //userparms.put("password", user_password);
                        userparms.put("pdivn", division);
                        userparms.put("pcentre", user_id);
                        //userparms.put("pcentre1", pCenter);

                        userparms.put("pval", pVal);
                        userparms.put("pFilno", fileNo);
                        userparms.put("pimei", CommonUtil.getDeviceIMEI(getContext()));
                        userparms.put("pextra", "S1");


                        //pdivn=P1&pcentre=286&pFilno=7&pval=0&pimei=1&pextra=S1&userid=12345

                        Log.i(TAG, "getParams: " + user_id);
                        Log.i(TAG, "getParams: " + user_password);
                        Log.i(TAG, "getParams: " + division);
                        Log.i(TAG, "getParams: " + pCenter);
                        Log.i(TAG, "getParams: " + pVal);
                        Log.i(TAG, "getParams: " + fileNo);

                        return userparms;
                    }

                };

                datadownloadreq.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                dataDownloadReq.add(datadownloadreq);

                int nbThreads = Thread.getAllStackTraces().keySet().size();

                Log.i("nbThreads", "onCreateView: nbThreads=" + nbThreads);

            } catch (Exception e) {
                Log.i(TAG, "userLogin: Error=" + e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        } else {
            CommonUtil.toast("no internet connection", mContext);
        }

    }

    private class DownloadData extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog1 = new ProgressDialog(mContext);

            progressDialog1.setMessage("Please Wait..\n  This may take a while. Please be patient....");
            progressDialog1.setCanceledOnTouchOutside(false);
            progressDialog1.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.i("DATAAAA", "doInBackground: " + strings[0] + strings[1]);

            downloadData(strings[0], strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog1.isShowing()) {
                progressDialog1.dismiss();
            }
        }
    }

    private class TimeTakingDownloadData extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog1 = new ProgressDialog(mContext);
            progressDialog1.setMessage("Please Wait..\n  This may take a while. Please be patient....");
            progressDialog1.setCanceledOnTouchOutside(false);
            progressDialog1.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.i("DATAAAA", "doInBackground: " + strings[0] + strings[1]);

            timeTakingDataDownload(strings[0], strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog1.isShowing()) {
                progressDialog1.dismiss();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateVillage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //for (VillageData villageData : villageDataList) {
            SurveyDatabase.getInstance(mContext).villageDao().deleteAll();
            SurveyDatabase.getInstance(mContext).villageDao().insertVillageList(villageDataList);
            //}
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateControl extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //for (VillageData villageData : villageDataList) {
            SurveyDatabase.getInstance(mContext).controlDao().deleteAll();
            SurveyDatabase.getInstance(mContext).controlDao().insertControlList(controlDataList);
            //}
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateGrower extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //for (VillageData villageData : villageDataList) {
            SurveyDatabase.getInstance(mContext).growerDao().deleteAll();
            SurveyDatabase.getInstance(mContext).growerDao().insertGrowerList(growerDataList);
            //}
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateMDPI extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //for (VillageData villageData : villageDataList) {
            SurveyDatabase.getInstance(mContext).mdpiDao().deleteAll();
            SurveyDatabase.getInstance(mContext).mdpiDao().insertMDPIList(mdpiDataList);
            //}
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateVariety extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //for (VillageData villageData : villageDataList) {
            SurveyDatabase.getInstance(mContext).varietyDao().deleteAll();
            SurveyDatabase.getInstance(mContext).varietyDao().insertVarietyList(varietyDataList);
            //}
            return null;
        }
    }
}
