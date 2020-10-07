package com.fourcpplus.survey;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.SurveyUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fourcpplus.survey.utils.AppConstants.BaseUrl;
import static com.fourcpplus.survey.utils.AppConstants.IP_Address;
import static com.fourcpplus.survey.utils.AppConstants.UserLoginUrl;

public class UserLoginActivity extends AppCompatActivity {
    private static final String TAG = "UserLoginActivity";
    RequestQueue userLoginRequestLogin;
    ProgressDialog progressDialog;
    EditText userID_EDT, password_EDT, etDivtxt;
    TextView tvAdminName;
    TextView tvAppVersion;
    String optionSelected, userLoginId, userPassword;
    String division = null;
    String pCenter = null;
    Button loginButton;
    String user_id, user_password;
    EditText et_IP;
    private String deviceIMEIno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserLogin();
        setContentView(R.layout.activity_user_login);
        checkPermission(Manifest.permission.READ_PHONE_STATE);

        if (CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.ONLY_ONCE).equals("")) {
            CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.GENRATE_RANDOM, "1");
            CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.GENRATE_RANDOM_CHLAN, "1");
            CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.GENRATE_RANDOM_GROWER, "1");
            CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.GENRATE_RANDOM_TERA, "1");
            CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.ONLY_ONCE, "true");
        }
        Log.i("pawan hr yy", "onCreateView: " + CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.ONLY_ONCE));
        Log.i("pawan hr tt", "onCreateView: " + CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.GENRATE_RANDOM));

        userLoginRequestLogin = Volley.newRequestQueue(this);

        userID_EDT = findViewById(R.id.userid_edit_text);
        password_EDT = findViewById(R.id.password_edit_text);
        etDivtxt = findViewById(R.id.etDivtxt);
        loginButton = findViewById(R.id.login_btn);
        tvAppVersion = findViewById(R.id.tv_appVersion);
        String pdivn = CommonUtil.getPreferencesString(UserLoginActivity.this, AppConstants.MYDIVISION);
        if (pdivn != null && !pdivn.isEmpty()) {
            etDivtxt.setText(pdivn);
        }
        tvAppVersion.setText(SurveyUtility.getVersionName(UserLoginActivity.this));
        et_IP = findViewById(R.id.etIP);
        et_IP.setText(IP_Address);
        et_IP.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_IP.setFocusableInTouchMode(true);
                et_IP.setFocusable(true);
                et_IP.setTextColor(getResources().getColor(R.color.black));
                et_IP.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et_IP.requestFocus();
                SurveyUtility.showKeyboard(UserLoginActivity.this, et_IP);
                et_IP.setBackground(getResources().getDrawable(R.drawable.edittextshape));
                return true;
            }
        });

        if (!CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.Role).equals("")) {
            tvAdminName = findViewById(R.id.tvAdminName);
            tvAdminName.setVisibility(View.VISIBLE);
            tvAdminName.setText(CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.Role));

        }

        progressDialog = new ProgressDialog(this);

       /* if (!CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.Role).isEmpty())
        {
            divisionOption.setText(CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.Role));
        }*/

        userID_EDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("12345")) {
                    et_IP.setVisibility(View.VISIBLE);
                } else
                    et_IP.setVisibility(View.GONE);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                deviceIMEIno = getDeviceIMEI();
                userLoginId = userID_EDT.getText().toString();
                userPassword = password_EDT.getText().toString();
                division = etDivtxt.getText().toString();
                if (division.trim().equalsIgnoreCase("")) {
                    etDivtxt.setError("Divison required");
                    return;
                }

                if (userPassword != null && userLoginId != null) {
                    if (CommonUtil.isNetworkAvailable(getApplicationContext())) {

                        userLogin(userLoginId, userPassword, deviceIMEIno);
                    } else {
                        CommonUtil.toast("no internet connection", getApplicationContext());
                    }
                }
            }
        });
    }

    private void checkPermission(String readPhoneState) {
        ActivityCompat.requestPermissions(this,
                new String[]{readPhoneState},
                1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
            try {
                deviceUniqueIdentifier = tm.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

    private void userLogin(final String user_id, final String user_password, final String deviceIMEIno) {
        try {
            Log.i(TAG, "userLogin: deviceIMEIno=" + deviceIMEIno);
            /*if (option_selected.equals("Paliya")) {
                division = "P1";
            } else if (option_selected.equals("Gola")) {
                division = "G1";
            } else if (option_selected.equals("Khmarakhera")) {
                division = "K1";
            }*/
            if (user_id.equals("12345")) {
                division = "P1";
                pCenter = "S";
                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.USER_ADMIN, "admin");
            } else {
                division = CommonUtil.getPreferencesString(getApplicationContext(), AppConstants.MYDIVISION);
                pCenter = "S";
                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.USER_ADMIN, "user");
            }

            progressDialog.setMessage("Please Wait..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            if (et_IP.getVisibility() == View.VISIBLE) {
                IP_Address = et_IP.getText().toString();
                BaseUrl = "http://" + IP_Address + "/sgmlapp/api/";
            }
            StringRequest userLoginRequest = new StringRequest(Request.Method.POST, BaseUrl + UserLoginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.i(TAG, "onResponse: Login Response=" + response);
                        JSONObject responseObj = new JSONObject(response);
                        if (responseObj.getString("ResponseCode").equals("1")) {
                            JSONObject userDetailsObj = responseObj.getJSONObject("User");
                            if (userDetailsObj != null) {
                                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.USER_ID, userDetailsObj.getString("userId"));
                                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.FULL_NAME, userDetailsObj.getString("name"));
                                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.USER_PASSWORD, userDetailsObj.getString("password"));
                                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.MYDIVISION, userDetailsObj.getString("division"));
                                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.MYCENTER, userDetailsObj.getString("centre"));
                                CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.IP, et_IP.getText().toString());
                                IP_Address = et_IP.getText().toString();
                                if (user_id.equals("12345")) {
                                    //  CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.MYDIVISION, userDetailsObj.getString("division"));
                                    // CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.MYCENTER, userDetailsObj.getString("centre"));
                                    CommonUtil.saveStringPreferences(getApplicationContext(), AppConstants.IS_USER_LOGIN, "true");

                                }
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                //  Toast.makeText(getApplicationContext(), responseObj.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                                CommonUtil.toast(responseObj.getString("ResponseMessage"), getApplicationContext());
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        } else {
                            //  Toast.makeText(getApplicationContext(), responseObj.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                            CommonUtil.toast(responseObj.getString("ResponseMessage"), getApplicationContext());
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
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
                    Log.i(TAG, "onErrorResponse: Error=" + error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> userparms = new HashMap<>();
                    userparms.put("userid", user_id);
                    userparms.put("password", user_password);
                    userparms.put("division", etDivtxt.getText().toString());
                    userparms.put("pcentre", pCenter);
                    userparms.put("deviceid", deviceIMEIno);
                    return userparms;
                }
            };

            userLoginRequestLogin.add(userLoginRequest);

        } catch (Exception e) {
            Log.i(TAG, "userLogin: Error=" + e.getMessage());
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void checkUserLogin() {
        //SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        user_id = CommonUtil.getPreferencesString(this, AppConstants.USER_ID);
        user_password = CommonUtil.getPreferencesString(this, AppConstants.USER_PASSWORD);
        Log.i("TAG", "onCreate: " + user_id + " " + user_password);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!user_id.equals("") && !user_password.equals("")) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }, 20);


    }


}
