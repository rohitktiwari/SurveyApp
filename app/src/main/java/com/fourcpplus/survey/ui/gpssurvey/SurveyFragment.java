package com.fourcpplus.survey.ui.gpssurvey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.fourcpplus.survey.BuildConfig;
import com.fourcpplus.survey.R;
import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.interfaces.ApiCall;
import com.fourcpplus.survey.model.db.ControlData;
import com.fourcpplus.survey.model.db.GrowerData;
import com.fourcpplus.survey.model.db.MDPIData;
import com.fourcpplus.survey.model.db.ReprintData;
import com.fourcpplus.survey.model.db.SurveyData;
import com.fourcpplus.survey.model.db.VarietyData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.model.network.PlotRecheckResponse;
import com.fourcpplus.survey.model.network.SuccessResponse;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.AppSharedPreferences;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.PrintHelper;
import com.fourcpplus.survey.utils.ServiceGenerator;
import com.fourcpplus.survey.utils.SurveyUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyFragment extends Fragment implements View.OnClickListener {
    private static final int CAMERA_IMAGE = 2000;
    private static ControlData controlData;
    private static FragmentActivity context;
    private static boolean overlap;
    private final Calendar myCalendar = Calendar.getInstance();
    DecimalFormat df = new DecimalFormat("#.######");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private boolean eastDir = false, westDir = false, northDir = false, southDir = false;
    private RadioGroup rgDirections;
    private RadioButton east, west, north, south;
    private EditText etLong;
    private EditText etLat;
    private EditText etPlotVillage;
    private EditText etPlotAddress;
    private EditText etVillageSerial;
    private EditText etPercent;
    private EditText etShareArea;
    private EditText etPlotArea;
    private EditText etEastDimen;
    private EditText etWestDimen;
    private EditText etNorthDimen;
    private EditText etSouthDimen;
    private EditText etVillage;
    private EditText etVillageAddress;
    private EditText etCutliVatedArea;
    private EditText etLandTypeCode;
    private EditText etLandType;
    private EditText etCropCycleCode;
    private EditText etCropCycle;
    private EditText etGrower;
    private EditText etACVariety;
    private EditText etACVarietyName;
    private EditText etShowingDate;
    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private EditText etPlantTypeCode;
    private EditText etPlantType;
    private String TAG = "SurveyFragment";
    private EditText etGrowerName;
    private int rgDirectionIndex = 0;
    private double lat1, lat2, lat3, lat4, lng1, lng2, lng3, lng4;
    private double dims1 = 0, dims2 = 0, dims3 = 0, dims4 = 0;
    private double percent = 0;
    private int count = 0;
    private AppSharedPreferences prefManager;
    private RadioGroup rgPlantTypes1;
    private RadioGroup rgPlantTypes2;
    private RadioGroup rgPlantTypes3;
    private RadioGroup rgPlantTypes4;
    // private RadioGroup rgPlantTypes5;
    private RadioButton pmthd, pdise, pinse, pintc, pland, pcrpc, pccnd, pmixc, pirrg, pbrdc, pdemo, psoil, pssrc, pcrpt;
    private String mthd = "", dise = "", inse = "", intc = "", land = "", crpc = "", ccnd = "", mixc = "", irrg = "", brdc = "", demo = "", soil = "", ssrc = "", crpt = "";
    private int plantmethodIndex;
    private String code;
    private boolean selected;
    private double lat;
    private double lng;
    private LocationManager locationManager;
    private int retryAttempt = 0;
    private Location location;
    private File imageFile;
    private String gmlen = "";
    private Button btnGPS;
    private String user;
    private String grFather;
    private String gucd;
    private SurveyData surveyData;
    private ReprintData data;
    private double pldivdr, pldivsl;
    private int plratoon = 0;
    private boolean grower = false;
    private boolean plotVillage = false;
    private boolean village = false;
    private boolean plantType = false;
    private boolean acVariety = false;
    private CheckBox serialCheck;
    private boolean cropCycle, landType;

    private void checkOverlap(Map<String, String> options) {
        ApiCall apiCall = ServiceGenerator.createService(ApiCall.class);
        Call<SuccessResponse> call;

        call = apiCall.checkOverlap(options);
        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                SuccessResponse successResponse = response.body();
                // CommonUtil.toast("Plot is 20% overlap with Other Plot.", context);
                assert successResponse != null;
                if (successResponse.getResponseCode() == 1) {
                    if (controlData.getOverlapallow().equalsIgnoreCase("1")) {
                        if (successResponse.getPlotOverlap().get(0).getAreaRatio() >= Integer.parseInt(controlData.getOverlapvalue())) {
                            plratoon = 1;
                            showAlert("Plot is " + successResponse.getPlotOverlap().get(0).getAreaRatio() + "% overlap with Other Plot. Do you want to continue?");
                            //CommonUtil.toast("Plot is 20% overlap with Other Plot.", context);
                        }
                    } else if (controlData.getOverlapallow().equalsIgnoreCase("2")) {
                        if (successResponse.getPlotOverlap().get(0).getAreaRatio() >= Integer.parseInt(controlData.getOverlapvalue())) {
                            overlap = true;
                            plratoon = 1;
                            showAlert("Plot is " + successResponse.getPlotOverlap().get(0).getAreaRatio() + "% overlap with Other Plot. Do you want to continue?");
                        } else {
                            overlap = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: ");
            }
        });
    }

    private void checkDamru(Map<String, String> options) {
        ApiCall apiCall = ServiceGenerator.createService(ApiCall.class);
        Call<SuccessResponse> call;
        call = apiCall.checkDamru(options);
        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                SuccessResponse successResponse = response.body();
                assert successResponse != null;
                if (successResponse.getStatus() == 1) {
                    overlap = true;
                    showOKAlert("Not Valid Survey Due Damru Shape");
                    //CommonUtil.toast("Not Valid Survey Due Damru Shape", context);
                } else {
                    overlap = false;
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        try {
            MenuItem item;
            item = menu.findItem(R.id.plot_recheck);
            item.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MenuItem item;
            item = menu.findItem(R.id.last_year_plot);
            item.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.plot_recheck) {
            plotRecheck("plotRecheck");
        } else if (item.getItemId() == R.id.last_year_plot) {
            plotRecheck("lastYear");
        } else {
            super.onContextItemSelected(item);
            return super.onContextItemSelected(item);
        }
        return false;
    }

    private void plotRecheck(final String where) {
        if (!plotVillage) {
            CommonUtil.toast("Please input valid plot village", context);
            return;
        }
        Map<String, String> options = new HashMap<>();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            options.put("lat", df.format(location.getLatitude()));
            options.put("lng", df.format(location.getLongitude()));
        }
        if (where.equalsIgnoreCase("lastYear")) {
            options.put("pcentre", "1");
        } else {
            options.put("pcentre", "0");
        }
        options.put("pdivn", CommonUtil.getPreferencesString(context, AppConstants.MYDIVISION));
        options.put("pFilno", "36");
        options.put("pval", etPlotVillage.getText().toString());
        options.put("pimei", CommonUtil.getDeviceIMEI(context));
        if (where.equalsIgnoreCase("lastYear") || where.equalsIgnoreCase("plotRecheck")) {
            options.put("frminpt", "0");
            options.put("lat1", "0");
            options.put("lat2", "0");
            options.put("lat3", "0");
            options.put("lat4", "0");
            options.put("lng1", "0");
            options.put("lng2", "0");
            options.put("lng3", "0");
            options.put("lng4", "0");
        } else if (where.equalsIgnoreCase("photoRecheck")) {
            options.put("frminpt", "1");
            options.put("lat1", String.valueOf(lat1));
            options.put("lat2", String.valueOf(lat2));
            options.put("lat3", String.valueOf(lat3));
            options.put("lat4", String.valueOf(lat4));
            options.put("lng1", String.valueOf(lng1));
            options.put("lng2", String.valueOf(lng2));
            options.put("lng3", String.valueOf(lng3));
            options.put("lng4", String.valueOf(lng4));
        }

        ApiCall apiCall = ServiceGenerator.createService(ApiCall.class);
        Call<PlotRecheckResponse> call;
        call = apiCall.plotRecheck(options);
        call.enqueue(new Callback<PlotRecheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlotRecheckResponse> call, @NonNull Response<PlotRecheckResponse> response) {
                PlotRecheckResponse plotRecheckResponse = response.body();
                assert plotRecheckResponse != null;
                if (plotRecheckResponse.getResponseCode() == -4) {
                    if (where.equalsIgnoreCase("photoRecheck")) {
                        CommonUtil.toast("Location of Photo is Not Found", context);
                        plratoon = plratoon + 3;
                        updateDB(surveyData);
                    } else {
                        SurveyUtility.showOKAlert(context, plotRecheckResponse.getResponseMessage());
                    }
                    return;
                }
                if (plotRecheckResponse.getResponseCode() == -9) {
                    SurveyUtility.showOKAlert(context, plotRecheckResponse.getResponseMessage());
                    return;
                }
                if (where.equalsIgnoreCase("photoRecheck")) {
                    updateDB(surveyData);
                }

                if (plotRecheckResponse.getResponseCode() == 1) {
                    if (plotRecheckResponse.getPlotData().size() > 0) {
                        fillFormData(plotRecheckResponse);
                    }

                }
            }

            @Override
            public void onFailure(Call<PlotRecheckResponse> call, Throwable t) {
            }
        });
    }

    private void fillFormData(PlotRecheckResponse plotData) {
        etPlotVillage.setText(plotData.getMatchPloat().get(0).getVcode());
        etPlotAddress.setText(plotData.getMatchPloat().get(0).getVname());
        etVillageSerial.setText(plotData.getMatchPloat().get(0).getGPSVSNO());
        etEastDimen.setText(plotData.getPlotData().get(0).getRdim1());
        etWestDimen.setText(plotData.getPlotData().get(0).getRdim3());
        etNorthDimen.setText(plotData.getPlotData().get(0).getRdim2());
        etSouthDimen.setText(plotData.getPlotData().get(0).getRdim4());
        etPercent.setText(String.valueOf(plotData.getPlotData().get(0).getRpercent()));
        etShareArea.setText(String.valueOf(plotData.getPlotData().get(0).getRplarea()));
        etPlotArea.setText(String.valueOf(plotData.getPlotData().get(0).getRplarea()));
        etVillage.setText(plotData.getPlotData().get(0).getRvill());
        etVillageAddress.setText(plotData.getMatchPloat().get(0).getVname());
        //etCutliVatedArea.setText(plotData.getMatchPloat().get(0).getVcode());
        etGrower.setText(plotData.getPlotData().get(0).getRgrno());
        //etGrowerName.setText(plotData.getMatchPloat().get(0).getVcode());
        etACVariety.setText(plotData.getPlotData().get(0).getRplvar());
        //etACVarietyName.setText(plotData.getMatchPloat().get(0).getVcode());
        etShowingDate.setText(plotData.getPlotData().get(0).getRplantdt());
        etPlantType.setText(plotData.getPlotData().get(0).getTempvar1());
        land = plotData.getPlotData().get(0).getRland();
        mixc = plotData.getPlotData().get(0).getRmixc();
        ccnd = plotData.getPlotData().get(0).getRccnd();
        crpt = plotData.getPlotData().get(0).getRctype();
        crpc = plotData.getPlotData().get(0).getRcrpc();
        irrg = plotData.getPlotData().get(0).getRirrg();
        brdc = plotData.getPlotData().get(0).getRbrdc();
        soil = plotData.getPlotData().get(0).getRsoil();
        intc = plotData.getPlotData().get(0).getRintercrop();
        dise = plotData.getPlotData().get(0).getRdisease();
        ssrc = plotData.getPlotData().get(0).getRssrc();
        inse = plotData.getPlotData().get(0).getRpest();
        demo = plotData.getPlotData().get(0).getRdemo();
        mthd = plotData.getPlotData().get(0).getRmethod();
        lat1 = plotData.getMatchPloat().get(0).getLAT1();
        lng1 = plotData.getMatchPloat().get(0).getLON1();
        lat2 = plotData.getMatchPloat().get(0).getLAT2();
        lng2 = plotData.getMatchPloat().get(0).getLON2();
        lat3 = plotData.getMatchPloat().get(0).getLAT3();
        lng3 = plotData.getMatchPloat().get(0).getLON3();
        lat4 = plotData.getMatchPloat().get(0).getLAT4();
        lng4 = plotData.getMatchPloat().get(0).getLON4();
    }

    private void showAlert(String message) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
        confirmBuilder
                .setTitle("Alert")
                .setMessage(message).setCancelable(false)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog confirmAlert = confirmBuilder.create();
        confirmAlert.show();
    }

    private void showOKAlert(String message) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
        confirmBuilder
                .setTitle("Alert")
                .setMessage(message).setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            requireActivity().getSupportFragmentManager().popBackStack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        AlertDialog confirmAlert = confirmBuilder.create();
        confirmAlert.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = requireActivity();
        prefManager = AppSharedPreferences.getInstance(requireActivity());
        controlData = SurveyDatabase.getInstance(requireActivity()).controlDao().getControlDetails();
        user = CommonUtil.getPreferencesString(context, AppConstants.USER_ID);
        if (controlData != null) {
            gmlen = controlData.getGmlen();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_survey, container, false);
        initView(root);
        //SurveyDatabase.getInstance(context).villageDao().updateVillageSerial("112","10801");
        return root;
    }

    private void initView(View root) {
        serialCheck = root.findViewById(R.id.serialCheck);
        btnGPS = root.findViewById(R.id.btnGps);
        etLat = root.findViewById(R.id.etLat);
        etLong = root.findViewById(R.id.etLong);
        btnGPS.setOnClickListener(this);
        east = root.findViewById(R.id.rdEast);
        west = root.findViewById(R.id.rdwest);
        north = root.findViewById(R.id.rdNorth);
        south = root.findViewById(R.id.rdSouth);
        rgDirections = root.findViewById(R.id.rgDirections);
        rgPlantTypes1 = root.findViewById(R.id.plantTypes1);
        rgPlantTypes2 = root.findViewById(R.id.plantTypes2);
        rgPlantTypes3 = root.findViewById(R.id.plantTypes3);
        rgPlantTypes4 = root.findViewById(R.id.plantTypes4);
        // rgPlantTypes5 = root.findViewById(R.id.plantTypes5);
        pmthd = root.findViewById(R.id.pmthd);
        pdise = root.findViewById(R.id.pdise);
        pinse = root.findViewById(R.id.pinse);
        pintc = root.findViewById(R.id.pintc);
        pland = root.findViewById(R.id.pland);
        pcrpc = root.findViewById(R.id.pcrpc);
        pccnd = root.findViewById(R.id.pccnd);
        pmixc = root.findViewById(R.id.pmixc);
        pirrg = root.findViewById(R.id.pirrg);
        pbrdc = root.findViewById(R.id.pbrdc);
        pdemo = root.findViewById(R.id.pdemo);
        psoil = root.findViewById(R.id.psoil);
        pssrc = root.findViewById(R.id.pssrc);
        pcrpt = root.findViewById(R.id.pcrpt);
        Button submitBtn = root.findViewById(R.id.btnSubmit);
        etPlotVillage = root.findViewById(R.id.etPlotVillage);
        etPlotAddress = root.findViewById(R.id.etPlotAddress);
        etVillageSerial = root.findViewById(R.id.etVillageSerial);
        etVillageSerial.setFocusable(false);
        etPercent = root.findViewById(R.id.etPercent);
        etShareArea = root.findViewById(R.id.etShareArea);
        etPlotArea = root.findViewById(R.id.etPlotArea);
        etEastDimen = root.findViewById(R.id.etEastDimen);
        etWestDimen = root.findViewById(R.id.etWestDimen);
        etNorthDimen = root.findViewById(R.id.etNorthDimen);
        etSouthDimen = root.findViewById(R.id.etSouthDimen);
        etVillage = root.findViewById(R.id.etVillage);
        etVillageAddress = root.findViewById(R.id.etVillageAddress);
        etCutliVatedArea = root.findViewById(R.id.etCutliVatedArea);
        etGrower = root.findViewById(R.id.etGrower);
        etGrowerName = root.findViewById(R.id.etGrowerName);
        etACVariety = root.findViewById(R.id.etACVariety);
        etACVarietyName = root.findViewById(R.id.etACVarietyName);
        etShowingDate = root.findViewById(R.id.etShowingDate);
        etPlantTypeCode = root.findViewById(R.id.etPlantTypeCode);
        etPlantType = root.findViewById(R.id.etPlantType);
        etLandType = root.findViewById(R.id.etLandType);
        etLandTypeCode = root.findViewById(R.id.etLandTypeCode);
        etCropCycle = root.findViewById(R.id.etCropCycle);
        etCropCycleCode = root.findViewById(R.id.etCropCycleCode);

        if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
            rgDirections.setVisibility(View.GONE);

            btnGPS.setText("Get Coordinates Corner 1");
        }
        try {
            showRadioButtons();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rgDirections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View view = rgDirections.findViewById(i);
                    if (rgDirectionIndex != rgDirections.indexOfChild(view)) {
                        rgDirectionIndex = rgDirections.indexOfChild(view);
                        //count = 0;
                        etLat.setText("0");
                        etLong.setText("0");
                    }
                    showDirectionButtons();
                    Log.i(TAG, "onCheckedChanged: " + rgDirectionIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        rgPlantTypes1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View view = rgPlantTypes1.findViewById(i);
                    switch (rgPlantTypes1.indexOfChild(view)) {
                        case 0:
                            if (!selected)
                                plantmethodIndex = 1;
                            selectOneradioButton();
                            break;
                        case 1:
                            if (!selected)
                                plantmethodIndex = 2;
                            selectOneradioButton();
                            break;
                        case 2:
                            if (!selected)
                                plantmethodIndex = 3;
                            selectOneradioButton();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selected = false;
            }
        });

        rgPlantTypes2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View view = rgPlantTypes2.findViewById(i);
                    switch (rgPlantTypes2.indexOfChild(view)) {
                        case 0:
                            if (!selected)
                                plantmethodIndex = 4;
                            selectOneradioButton();
                            break;
                        case 1:
                            if (!selected)
                                plantmethodIndex = 5;
                            selectOneradioButton();
                            break;
                        case 2:
                            if (!selected)
                                plantmethodIndex = 6;
                            selectOneradioButton();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selected = false;
            }
        });

        rgPlantTypes3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View view = rgPlantTypes3.findViewById(i);
                    switch (rgPlantTypes3.indexOfChild(view)) {
                        case 0:
                            if (!selected)
                                plantmethodIndex = 7;
                            selectOneradioButton();
                            break;
                        case 1:
                            if (!selected)
                                plantmethodIndex = 8;
                            selectOneradioButton();
                            break;
                        case 2:
                            if (!selected)
                                plantmethodIndex = 9;
                            selectOneradioButton();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selected = false;
            }
        });

        rgPlantTypes4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View view = rgPlantTypes4.findViewById(i);
                    switch (rgPlantTypes4.indexOfChild(view)) {
                        case 0:
                            if (!selected)
                                plantmethodIndex = 10;
                            selectOneradioButton();
                            break;
                        case 1:
                            if (!selected)
                                plantmethodIndex = 11;
                            selectOneradioButton();
                            break;
                        case 2:
                            if (!selected)
                                plantmethodIndex = 12;
                            selectOneradioButton();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selected = false;
            }
        });

        /*rgPlantTypes5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View view = rgPlantTypes5.findViewById(i);
                    switch (rgPlantTypes5.indexOfChild(view)) {
                        case 0:
                            if (!selected)
                                plantmethodIndex = 13;
                            selectOneradioButton();
                            break;
                        case 1:
                            if (!selected)
                                plantmethodIndex = 14;
                            selectOneradioButton();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selected = false;
            }
        });*/

        etPercent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etEastDimen.getText().toString().isEmpty() && !etWestDimen.getText().toString().isEmpty() && !etNorthDimen.getText().toString().isEmpty() && !etSouthDimen.getText().toString().isEmpty()) {

                            if (!etPercent.getText().toString().isEmpty()) {
                                percent = Double.parseDouble(etPercent.getText().toString());
                                etPercent.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            } else {
                                etPercent.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                                CommonUtil.toast("Please enter a value in Percentage field.", requireActivity());
                            }

                            etShareArea.setText(SurveyUtility.calculateShareArea(percent, Double.parseDouble(etPlotArea.getText().toString())));
                        }
                    }
                } catch (NumberFormatException | Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        etPercent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!etEastDimen.getText().toString().isEmpty() && !etWestDimen.getText().toString().isEmpty() && !etNorthDimen.getText().toString().isEmpty() && !etSouthDimen.getText().toString().isEmpty()) {

                        if (!etPercent.getText().toString().isEmpty()) {
                            percent = Double.parseDouble(etPercent.getText().toString());
                            etPercent.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                        } else {
                            etPercent.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Please enter a value in Percentage field.", requireActivity());
                        }

                        etShareArea.setText(SurveyUtility.calculateShareArea(percent, Double.parseDouble(etPlotArea.getText().toString())));
                    }
                } catch (NumberFormatException | Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        serialCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etVillageSerial.setFocusableInTouchMode(true);
                    etVillageSerial.setFocusable(true);
                    etVillageSerial.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etVillageSerial.requestFocus();
                    SurveyUtility.showKeyboard(context, etVillageSerial);
                    etVillageSerial.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                } else {
                    etVillageSerial.setFocusableInTouchMode(false);
                    etVillageSerial.setFocusable(false);
                    etVillageSerial.setBackground(getResources().getDrawable(R.drawable.editdisableshape));
                }
            }
        });

        etVillageSerial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkDuplicateSerial();
            }
        });


        etGrower.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etGrower.getText().toString().isEmpty()) {
                            GrowerData growerData = SurveyDatabase.getInstance(requireActivity()).growerDao().getGrowerDetails(etVillage.getText().toString(), etGrower.getText().toString());
                            if (growerData != null) {
                                grower = true;
                                etGrowerName.setText(growerData.getGname());
                                grFather = growerData.getGfather();
                                gucd = growerData.getGucd();
                                etCutliVatedArea.setText(growerData.getGtarea());
                                etGrower.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            } else {
                                grower = false;
                                etGrower.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                                CommonUtil.toast("Grower not found.", requireActivity());
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        etGrower.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!etGrower.getText().toString().isEmpty()) {
                        GrowerData growerData = SurveyDatabase.getInstance(requireActivity()).growerDao().getGrowerDetails(etVillage.getText().toString(), etGrower.getText().toString());
                        if (growerData != null) {
                            grower = true;
                            etGrowerName.setText(growerData.getGname());
                            grFather = growerData.getGfather();
                            gucd = growerData.getGucd();
                            etCutliVatedArea.setText(growerData.getGtarea());
                            etGrower.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                        } else {
                            grower = false;
                            etGrower.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Grower not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etACVariety.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etACVariety.getText().toString().isEmpty()) {
                            VarietyData varietyData = SurveyDatabase.getInstance(requireActivity()).varietyDao().getVarietyDetails(etACVariety.getText().toString());
                            if (varietyData != null) {
                                acVariety = true;
                                etACVarietyName.setText(varietyData.getVarname());
                                etACVariety.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            } else {
                                acVariety = false;
                                CommonUtil.toast("Variety Name not found.", requireActivity());
                                etACVariety.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        etACVariety.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!etACVariety.getText().toString().isEmpty()) {
                        VarietyData varietyData = SurveyDatabase.getInstance(requireActivity()).varietyDao().getVarietyDetails(etACVariety.getText().toString());
                        if (varietyData != null) {
                            acVariety = true;
                            etACVarietyName.setText(varietyData.getVarname());
                            etACVariety.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                        } else {
                            acVariety = false;
                            etACVariety.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Variety Name not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etPlotVillage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etPlotVillage.getText().toString().isEmpty()) {
                            VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etPlotVillage.getText().toString());
                            if (villageData != null) {
                                if (villageData.getVilltype().equals("2")) {
                                    showOKAlert("Survey not allow for this Plot Village");
                                    return false;
                                }
                                plotVillage = true;

                                //prefManager.setSerialNo(Integer.parseInt(villageData.getPlsrno()));
                                checkSerialNumber();
                                //etVillageSerial.setText(villageData.getPlsrno());
                                etPlotAddress.setText(villageData.getVname());
                                etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            } else {
                                plotVillage = false;
                                CommonUtil.toast("Plot Address not found.", requireActivity());
                                etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        etPlotVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!etPlotVillage.getText().toString().isEmpty()) {
                        VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etPlotVillage.getText().toString());
                        if (villageData != null) {
                            if (villageData.getVilltype().equals("2")) {
                                showOKAlert("Survey not allow for this Plot Village");
                                return;
                                //CommonUtil.toast("Survey not allow for this Plot Village", context);
                            }
                            // if (prefManager.getSerialNo() == 0) {
                            //prefManager.setSerialNo(Integer.parseInt(villageData.getPlsrno()));
                            checkSerialNumber();
                            // etVillageSerial.setText(villageData.getPlsrno());
                            plotVillage = true;

                            etPlotAddress.setText(villageData.getVname());
                            etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                        } else {
                            plotVillage = false;
                            etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Plot Address not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etVillage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etVillage.getText().toString().isEmpty()) {
                            VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etVillage.getText().toString());
                            if (villageData != null) {
                                village = true;
                                etVillageAddress.setText(villageData.getVname());
                                etVillage.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            } else {
                                village = false;
                                CommonUtil.toast("Village not found.", requireActivity());
                                etVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        etVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!etVillage.getText().toString().isEmpty()) {
                        VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etVillage.getText().toString());
                        if (villageData != null) {
                            village = true;
                            etVillageAddress.setText(villageData.getVname());
                            etVillage.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                        } else {
                            village = false;
                            etVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Village not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etPlantTypeCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if (!etPlantTypeCode.getText().toString().isEmpty()) {
                        fillCropDetails();
                        code = "CRPT";
                        MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etPlantTypeCode.getText().toString());
                        if (mdpiData != null) {
                            plantType = true;
                            etPlantType.setText(mdpiData.getMdesc());
                            etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            crpt = etPlantTypeCode.getText().toString();
                        } else {
                            plantType = false;
                            etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Plant Type not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etPlantTypeCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {

                        if (!etPlantTypeCode.getText().toString().isEmpty()) {
                            fillCropDetails();
                            code = "CRPT";
                            MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etPlantTypeCode.getText().toString());
                            if (mdpiData != null) {
                                plantType = true;
                                etPlantType.setText(mdpiData.getMdesc());
                                etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                                crpt = etPlantTypeCode.getText().toString();
                            } else {
                                plantType = false;
                                etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                                CommonUtil.toast("Plant Type not found.", requireActivity());
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        etPlantTypeCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!etPlantTypeCode.getText().toString().isEmpty()) {
                        fillCropDetails();
                        code = "CRPT";
                        MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etPlantTypeCode.getText().toString());
                        if (mdpiData != null) {
                            plantType = true;
                            etPlantType.setText(mdpiData.getMdesc());
                            etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            crpt = etPlantTypeCode.getText().toString();
                        } else {
                            plantType = false;
                            etPlantType.setText("");
                            etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Plant Type not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        etCropCycleCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    code = "CRPC";
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etCropCycleCode.getText().toString().isEmpty()) {

                            if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6")) && crpc.equalsIgnoreCase("0")) {
                                pcrpt.setVisibility(View.VISIBLE);
                                etPlantTypeCode.setVisibility(View.VISIBLE);
                                etPlantType.setVisibility(View.VISIBLE);
                            } else {
                                pcrpt.setVisibility(View.GONE);
                                etPlantTypeCode.setVisibility(View.GONE);
                                etPlantType.setVisibility(View.GONE);
                            }
                            MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etCropCycleCode.getText().toString());
                            if (mdpiData != null) {
                                cropCycle = true;
                                etCropCycle.setText(mdpiData.getMdesc());
                                etCropCycleCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                                crpc = etCropCycleCode.getText().toString();

                            } else {
                                cropCycle = false;
                                etCropCycleCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                                CommonUtil.toast("Crop Cycle not found.", requireActivity());
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        etCropCycleCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    code = "CRPC";
                    if (!etCropCycleCode.getText().toString().isEmpty()) {

                        if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6")) && etCropCycleCode.getText().toString().equalsIgnoreCase("0")) {
                            pcrpt.setVisibility(View.VISIBLE);
                            etPlantType.setVisibility(View.VISIBLE);
                            etPlantTypeCode.setVisibility(View.VISIBLE);
                        } else {
                            pcrpt.setVisibility(View.GONE);
                            etPlantTypeCode.setVisibility(View.GONE);
                            etPlantType.setVisibility(View.GONE);
                        }
                        MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etCropCycleCode.getText().toString());
                        if (mdpiData != null) {
                            cropCycle = true;
                            etCropCycle.setText(mdpiData.getMdesc());
                            etCropCycleCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            crpc = etCropCycleCode.getText().toString();
                        } else {
                            cropCycle = false;
                            etCropCycleCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Crop Cycle not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etCropCycleCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    code = "CRPC";
                    if (!etCropCycleCode.getText().toString().isEmpty()) {

                        if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6")) && etCropCycleCode.getText().toString().equalsIgnoreCase("0")) {
                            pcrpt.setVisibility(View.VISIBLE);
                            etPlantType.setVisibility(View.VISIBLE);
                            etPlantTypeCode.setVisibility(View.VISIBLE);
                        } else {
                            pcrpt.setVisibility(View.GONE);
                            etPlantTypeCode.setVisibility(View.GONE);
                            etPlantType.setVisibility(View.GONE);
                        }
                        MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etCropCycleCode.getText().toString());
                        if (mdpiData != null) {
                            cropCycle = true;
                            etCropCycle.setText(mdpiData.getMdesc());
                            etCropCycleCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            crpc = etCropCycleCode.getText().toString();
                        } else {
                            cropCycle = false;
                            etCropCycle.setText("");
                            etCropCycleCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Crop Cycle not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etLandTypeCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    code = "LAND";
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etLandTypeCode.getText().toString().isEmpty()) {

                            if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6")) && crpc.equalsIgnoreCase("0")) {
                                pcrpt.setVisibility(View.VISIBLE);
                            } else
                                pcrpt.setVisibility(View.GONE);
                            MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etLandTypeCode.getText().toString());
                            if (mdpiData != null) {
                                landType = true;
                                etLandType.setText(mdpiData.getMdesc());
                                etLandTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                                land = etLandTypeCode.getText().toString();
                            } else {
                                landType = false;
                                etLandTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                                CommonUtil.toast("Land Type not found.", requireActivity());
                            }
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        etLandTypeCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    code = "LAND";
                    if (!etLandTypeCode.getText().toString().isEmpty()) {
                        fillCropDetails();
                        MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etLandTypeCode.getText().toString());
                        if (mdpiData != null) {
                            landType = true;
                            etLandType.setText(mdpiData.getMdesc());
                            etLandTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            land = etLandTypeCode.getText().toString();
                        } else {
                            landType = false;
                            etLandType.setText("");
                            etLandTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Land Type not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etLandTypeCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    code = "LAND";
                    if (!etLandTypeCode.getText().toString().isEmpty()) {
                        MDPIData mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails(code, etLandTypeCode.getText().toString());
                        if (mdpiData != null) {
                            landType = true;
                            etLandType.setText(mdpiData.getMdesc());
                            etLandTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            land = etLandTypeCode.getText().toString();
                        } else {
                            landType = false;
                            etLandTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                            CommonUtil.toast("Land Type not found.", requireActivity());
                        }
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        etShowingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyUtility.hideKeyboard(requireActivity());
                new DatePickerDialog(requireActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!overlap) {
                        checkValidation();
                    } else {
                        CommonUtil.toast("Plot is 20% overlap with Other Plot", context);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SurveyUtility.checkPermission(requireActivity());
        String[] plantTypes;
        try {
            Collection<String> plantType = SurveyUtility.plantTypeCodes().values();
            ArrayList<String> list = new ArrayList<>(plantType);
            plantTypes = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                plantTypes[i] = list.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDuplicateSerial() {
        SurveyData surveyData = SurveyDatabase.getInstance(context).surveyDao().getSurveyDatabySerial(etVillageSerial.getText().toString(), etPlotVillage.getText().toString());
        if (surveyData != null) {
            AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
            confirmBuilder
                    .setTitle("Alert")
                    .setMessage("Duplicate Plot Serial Number Found.").setCancelable(false)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            etVillageSerial.setText("");
                        }
                    });
            AlertDialog confirmAlert = confirmBuilder.create();
            confirmAlert.show();
            return true;
        }
        return false;
    }

    private void checkSerialNumber() {
        try {
            VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etPlotVillage.getText().toString());
            String plsrno = "";
            if (villageData != null) {
                plsrno = villageData.getPlsrno();
            }
            String maxSerialNo = SurveyDatabase.getInstance(context).surveyDao().getMaxSerialNo(etPlotVillage.getText().toString());
            if (maxSerialNo == null)
                maxSerialNo = "";
            if (maxSerialNo.isEmpty()) {
                etVillageSerial.setText(plsrno);
                return;
            }
            int maxserial = Integer.parseInt(maxSerialNo);
            if (Integer.parseInt(plsrno) == (maxserial + 1)) {
                etVillageSerial.setText(plsrno);
            } else {
                etVillageSerial.setText(plsrno);
                SurveyUtility.showOKAlert(context, "Plot Serial No Should Be=" + (maxserial + 1));
            }

        } catch (NumberFormatException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void showRadioButtons() {
        if (controlData != null) {
            if (controlData.getGmethod().equalsIgnoreCase("0")) {
                pmthd.setVisibility(View.GONE);
            }
            if (controlData.getGdisease().equalsIgnoreCase("0")) {
                pdise.setVisibility(View.GONE);
            }
            if (controlData.getGinsect().equalsIgnoreCase("0")) {
                pinse.setVisibility(View.GONE);
            }
            if (controlData.getGicrop().equalsIgnoreCase("0")) {
                pintc.setVisibility(View.GONE);
            }
            if (controlData.getGltype().equalsIgnoreCase("0")) {
                pland.setVisibility(View.GONE);
            }
            if (controlData.getGsame_ccycle().equalsIgnoreCase("0")) {
                pcrpc.setVisibility(View.GONE);
            }
            if (controlData.getGccondition().equalsIgnoreCase("0")) {
                pccnd.setVisibility(View.GONE);
            }
            if (controlData.getGmcrop().equalsIgnoreCase("0")) {
                pmixc.setVisibility(View.GONE);
            }
            if (controlData.getGirrigation().equalsIgnoreCase("0")) {
                pirrg.setVisibility(View.GONE);
            }
            if (controlData.getGbroder_crop().equalsIgnoreCase("0")) {
                pbrdc.setVisibility(View.GONE);
            }
            if (controlData.getGdemo().equalsIgnoreCase("0")) {
                pdemo.setVisibility(View.GONE);
            }
            if (controlData.getGstype().equalsIgnoreCase("0")) {
                psoil.setVisibility(View.GONE);
            }
            if (controlData.getGseed_source().equalsIgnoreCase("0")) {
                pssrc.setVisibility(View.GONE);
            }
            if (controlData.getGcrop().equalsIgnoreCase("0")) {
                pcrpt.setVisibility(View.GONE);
            }
        }
    }

    private void selectOneradioButton() {
        selected = true;
        etPlantTypeCode.setText("");
        etPlantType.setText("");
        switch (plantmethodIndex) {
            case 1:
                pmthd.setChecked(true);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 2:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(true);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 3:
                pmthd.setChecked(false);
                pinse.setChecked(true);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 4:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(true);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 5:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(true);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(true);
                break;
            case 6:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(true);
                pcrpt.setChecked(true);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 7:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(true);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 8:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(true);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 9:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(true);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 10:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(true);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 11:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(true);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 12:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(true);
                pssrc.setChecked(false);
                break;
            /*case 13:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(true);
                break;
            case 14:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                //pland.setChecked(false);
                //pcrpc.setChecked(false);
                pcrpt.setChecked(true);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;
            case 15:
                pmthd.setChecked(false);
                pinse.setChecked(false);
                pdise.setChecked(false);
                pintc.setChecked(false);
                pland.setChecked(false);
                pcrpc.setChecked(false);
                pcrpt.setChecked(false);
                pccnd.setChecked(false);
                pmixc.setChecked(false);
                pirrg.setChecked(false);
                pbrdc.setChecked(false);
                pdemo.setChecked(false);
                psoil.setChecked(false);
                pssrc.setChecked(false);
                break;*/

        }
    }

    private void fillCropDetails() {
        switch (plantmethodIndex) {
            case 1:
                code = "MTHD";
                mthd = etPlantTypeCode.getText().toString();
                break;
            case 2:
                code = "DISE";
                dise = etPlantTypeCode.getText().toString();
                break;
            case 3:
                code = "INSE";
                inse = etPlantTypeCode.getText().toString();
                break;
            case 4:
                code = "INTC";
                intc = etPlantTypeCode.getText().toString();
                break;
            case 5:
                code = "SSRC";
                ssrc = etPlantTypeCode.getText().toString();
                break;
            case 6:
                code = "CRPT";
                crpt = etPlantTypeCode.getText().toString();
                break;
            case 7:
                code = "CCND";
                ccnd = etPlantTypeCode.getText().toString();
                break;
            case 8:
                code = "MIXC";
                mixc = etPlantTypeCode.getText().toString();
                break;
            case 9:
                code = "IRRG";
                irrg = etPlantTypeCode.getText().toString();
                break;
            case 10:
                code = "BRDC";
                brdc = etPlantTypeCode.getText().toString();
                break;
            case 11:
                code = "DEMO";
                demo = etPlantTypeCode.getText().toString();
                break;
            case 12:
                code = "SOIL";
                soil = etPlantTypeCode.getText().toString();
                break;
           /* case 13:
                code = "SSRC";
                ssrc = etPlantTypeCode.getText().toString();
                break;
            case 14:
                code = "CRPT";
                crpt = etPlantTypeCode.getText().toString();
                break;*/
        }
    }

    private void showDirectionButtons() {
        try {
            switch (rgDirectionIndex) {
                case 0:     //east
                    west.setVisibility(View.GONE);
                    if (!northDir) north.setVisibility(View.VISIBLE);
                    else north.setVisibility(View.GONE);
                    if (!southDir) south.setVisibility(View.VISIBLE);
                    else south.setVisibility(View.GONE);
                    break;
                case 1:     //west
                    east.setVisibility(View.GONE);
                    if (!northDir) north.setVisibility(View.VISIBLE);
                    else north.setVisibility(View.GONE);
                    if (!southDir) south.setVisibility(View.VISIBLE);
                    else south.setVisibility(View.GONE);
                    break;
                case 2:     //north
                    if (!eastDir) east.setVisibility(View.VISIBLE);
                    else east.setVisibility(View.GONE);
                    if (!westDir) west.setVisibility(View.VISIBLE);
                    else west.setVisibility(View.GONE);
                    south.setVisibility(View.GONE);
                    break;
                case 3:     //south
                    if (!eastDir) east.setVisibility(View.VISIBLE);
                    else east.setVisibility(View.GONE);
                    if (!westDir) west.setVisibility(View.VISIBLE);
                    else west.setVisibility(View.GONE);
                    north.setVisibility(View.GONE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateLatLong() {
        try {
            // if (//count == 0) {
            if (lat1 == 0) {
                //count++;
                lat1 = Double.parseDouble(etLat.getText().toString());
                lng1 = Double.parseDouble(etLong.getText().toString());
                if (lat1 != 0) {
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 2");
                    }
                    //count = 0;
                }
            } else if (lat2 == 0) {
                //count++;
                lat2 = Double.parseDouble(etLat.getText().toString());
                lng2 = Double.parseDouble(etLong.getText().toString());
                if (lat2 != 0) {
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 3");
                    }
                    //count = 0;
                }
            } else if (lat3 == 0) {
                //count++;
                lat3 = Double.parseDouble(etLat.getText().toString());
                lng3 = Double.parseDouble(etLong.getText().toString());
                if (lat3 != 0) {
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 4");
                    }
                    //count = 0;
                }
            } else if (lat4 == 0) {
                //count++;
                lat4 = Double.parseDouble(etLat.getText().toString());
                lng4 = Double.parseDouble(etLong.getText().toString());
                if (lat4 != 0) {
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("All Corners complete");
                        etPercent.setEnabled(false);
                        etPercent.setBackground(getResources().getDrawable(R.drawable.editdisableshape));
                    }
                    //count = 0;
                    etPercent.setText("100");
                    Map<String, String> options = new HashMap<>();
                    options.put("lat1", String.valueOf(lat1));
                    options.put("lat2", String.valueOf(lat2));
                    options.put("lat3", String.valueOf(lat3));
                    options.put("lat4", String.valueOf(lat4));
                    options.put("lng1", String.valueOf(lng1));
                    options.put("lng2", String.valueOf(lng2));
                    options.put("lng3", String.valueOf(lng3));
                    options.put("lng4", String.valueOf(lng4));
                    try {
                        if (!controlData.getDamruallow().equalsIgnoreCase("0")) {
                            if (SurveyUtility.isNetworkAvailable(requireActivity())) {
                                checkDamru(options);
                            } else
                                CommonUtil.toast("Please check your Internet Connection.", requireActivity());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    options.put("pcentre", CommonUtil.getPreferencesString(context, AppConstants.USER_ID));
                    options.put("pdivn", CommonUtil.getPreferencesString(context, AppConstants.MYDIVISION));
                    options.put("pFilno", "36");
                    options.put("pval", etPlotVillage.getText().toString());
                    options.put("pimei", CommonUtil.getDeviceIMEI(requireActivity()));
                    if (!controlData.getOverlapallow().equalsIgnoreCase("0")) {
                        if (SurveyUtility.isNetworkAvailable(requireActivity())) {
                            checkOverlap(options);
                        } else {
                            CommonUtil.toast("Please check your Internet Connection.", requireActivity());
                            plratoon = 2;
                        }
                    }
                }
            }
            // }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            switch (rgDirectionIndex) {
                case 0:
                    eastDir = true;
                    break;
                case 1:
                    westDir = true;
                    break;
                case 2:
                    northDir = true;
                    break;
                case 3:
                    southDir = true;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLabel() {
        try {
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            etShowingDate.setText(sdf.format(myCalendar.getTime()));
            etShowingDate.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void checkValidation() {
        if (checkDuplicateSerial()) {
            return;
        }
        surveyData = new SurveyData();
        try {
            surveyData.setPdivn(CommonUtil.getPreferencesString(context, AppConstants.MYDIVISION));
            surveyData.setPdate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
            surveyData.setDate(sdf.parse(surveyData.getPdate().split(" ")[0]));
            if (plotVillage) {
                if (!etPlotVillage.getText().toString().isEmpty())
                    surveyData.setPLGastino(etPlotVillage.getText().toString());
                else {
                    etPlotVillage.requestFocus();
                    etPlotVillage.setError("Plot Village is required.");
                    return;
                }
            } else {
                etPlotVillage.requestFocus();
                etPlotVillage.setError("Plot Village is required.");
                return;
            }
            surveyData.setPlacvill(etPlotVillage.getText().toString());
            if (lat1 == 0 || lat2 == 0 || lat3 == 0 || lat4 == 0) {
                CommonUtil.toast("Coordinates Missing. Please validate", requireActivity());
                return;
            }
            surveyData.setPlat1(String.valueOf(lat1));
            surveyData.setPlon1(String.valueOf(lng1));
            surveyData.setPlat2(String.valueOf(lat2));
            surveyData.setPlon2(String.valueOf(lng2));
            surveyData.setPlat3(String.valueOf(lat3));
            surveyData.setPlon3(String.valueOf(lng3));
            surveyData.setPlat4(String.valueOf(lat4));
            surveyData.setPlon4(String.valueOf(lng4));
            surveyData.setPldim1(etEastDimen.getText().toString());
            surveyData.setPldim2(etWestDimen.getText().toString());
            surveyData.setPldim3(etNorthDimen.getText().toString());
            surveyData.setPldim4(etSouthDimen.getText().toString());
            surveyData.setPextra(SurveyUtility.getVersionName(requireActivity()));

            if (village) {
                if (!etVillage.getText().toString().isEmpty())
                    surveyData.setPLVill(etVillage.getText().toString());
                else {
                    etVillage.requestFocus();
                    etVillage.setError("Village code is required.");
                    return;
                }
            } else {
                etVillage.requestFocus();
                etVillage.setError("Village code is required.");
                return;
            }
            surveyData.setPLVsno(Integer.parseInt(etVillageSerial.getText().toString()));
            if (grower) {
                if (!etGrower.getText().toString().isEmpty())
                    surveyData.setPLGrno(etGrower.getText().toString());
                else {
                    etGrower.requestFocus();
                    etGrower.setError("Grower is required.");
                    return;
                }
            } else {
                etGrower.requestFocus();
                etGrower.setError("Grower is required.");
                return;
            }
            if (acVariety) {
                if (!etACVariety.getText().toString().isEmpty())
                    surveyData.setPlacvar(etACVariety.getText().toString());
                else {
                    etACVariety.requestFocus();
                    etACVariety.setError("Account Variety is required.");
                    return;
                }
            } else {
                etACVariety.requestFocus();
                etACVariety.setError("Account Variety is required.");
                return;
            }

            surveyData.setPldivsl(df.format(pldivsl));
            surveyData.setPldivdr(df.format(pldivdr));
            surveyData.setPlpercent(etPercent.getText().toString());
            surveyData.setPlarea(etShareArea.getText().toString());
            surveyData.setPlclerk(CommonUtil.getPreferencesString(context, AppConstants.USER_ID));
            if (!etShowingDate.getText().toString().isEmpty()) {
                surveyData.setPldate(etShowingDate.getText().toString());
            } else {
                etShowingDate.setError("Showing Date is Required");
                return;
            }

            if (pcrpc.getVisibility() == View.VISIBLE) {
                if (cropCycle) {
                    if (!crpc.isEmpty())
                        surveyData.setPCRPC(crpc);
                    else {
                        etCropCycleCode.requestFocus();
                        etCropCycleCode.setError("Crop Cycle Data Missing");
                        //CommonUtil.toast("Crop Cycle Data Missing", requireActivity());
                        pcrpc.setChecked(true);
                        return;
                    }
                } else {
                    etCropCycleCode.requestFocus();
                    etCropCycleCode.setError("Enter valid Crop Cycle Data!");
                    pcrpc.setChecked(true);
                    return;
                }
            }

            if (pmthd.getVisibility() == View.VISIBLE) {
                if (!mthd.isEmpty())
                    surveyData.setPMTHD(mthd);
                else {
                    CommonUtil.toast("Plant Method Data Missing", requireActivity());
                    pmthd.setChecked(true);
                    return;
                }
            }

            if (pdise.getVisibility() == View.VISIBLE) {
                if (!dise.isEmpty())
                    surveyData.setPDISE(dise);
                else {
                    CommonUtil.toast("Disease Data Missing", requireActivity());
                    pdise.setChecked(true);
                    return;
                }
            }

            if (pinse.getVisibility() == View.VISIBLE) {
                if (!inse.isEmpty())
                    surveyData.setPINSE(inse);
                else {
                    CommonUtil.toast("Insect Data Missing", requireActivity());
                    pinse.setChecked(true);
                    return;
                }
            }

            if (pintc.getVisibility() == View.VISIBLE) {
                if (!intc.isEmpty())
                    surveyData.setPINTC(intc);
                else {
                    CommonUtil.toast("Inter Cropping Data Missing", requireActivity());
                    pintc.setChecked(true);
                    return;
                }
            }

            if (pland.getVisibility() == View.VISIBLE) {
                if (landType) {
                    if (!land.isEmpty())
                        surveyData.setPLAND(land);
                    else {
                        etLandTypeCode.requestFocus();
                        etLandTypeCode.setError("Land Type Data Missing");
                        pland.setChecked(true);
                        return;
                    }
                } else {
                    etLandTypeCode.requestFocus();
                    etLandTypeCode.setError("Enter valid Land Type Code!");
                    return;
                }
            }

            if (pcrpt.getVisibility() == View.VISIBLE) {
                if (plantType) {
                    if (!crpt.isEmpty())
                        surveyData.setPlseedflg(crpt);
                    else {
                        etPlantTypeCode.requestFocus();
                        etPlantTypeCode.setError("Crop Type Data Missing");
                        //CommonUtil.toast("Crop Type Data Missing", requireActivity());
                        pcrpt.setChecked(true);
                        return;
                    }
                } else {
                    etPlantTypeCode.requestFocus();
                    etPlantTypeCode.setError("Enter valid Crop Type data!");
                    return;
                }
            }
            if (pccnd.getVisibility() == View.VISIBLE) {
                if (!ccnd.isEmpty())
                    surveyData.setPCCND(ccnd);
                else {
                    CommonUtil.toast("Crop Condition Data Missing", requireActivity());
                    pccnd.setChecked(true);
                    return;
                }
            }

            if (pmixc.getVisibility() == View.VISIBLE) {
                if (!mixc.isEmpty())
                    surveyData.setPMIXC(mixc);
                else {
                    CommonUtil.toast("Mix Crop Data Missing", requireActivity());
                    pmixc.setChecked(true);
                    return;
                }
            }

            if (pirrg.getVisibility() == View.VISIBLE) {
                if (!irrg.isEmpty())
                    surveyData.setPIRRG(irrg);
                else {
                    CommonUtil.toast("Irrigation Data Missing", requireActivity());
                    pirrg.setChecked(true);
                    return;
                }
            }

            if (pbrdc.getVisibility() == View.VISIBLE) {
                if (!brdc.isEmpty())
                    surveyData.setPBRDC(brdc);
                else {
                    CommonUtil.toast("Broder Crop Data Missing", requireActivity());
                    pbrdc.setChecked(true);
                    return;
                }
            }

            if (pdemo.getVisibility() == View.VISIBLE) {
                if (!demo.isEmpty())
                    surveyData.setPDEMO(demo);
                else {
                    CommonUtil.toast("Demo Plot Data Missing", requireActivity());
                    pdemo.setChecked(true);
                    return;
                }
            }

            if (psoil.getVisibility() == View.VISIBLE) {
                if (!soil.isEmpty())
                    surveyData.setPSOIL(soil);
                else {
                    CommonUtil.toast("Soil Type Data Missing", requireActivity());
                    psoil.setChecked(true);
                    return;
                }
            }

            if (pssrc.getVisibility() == View.VISIBLE) {
                if (!ssrc.isEmpty())
                    surveyData.setPSSRC(ssrc);
                else {
                    CommonUtil.toast("Seed Source Data Missing", requireActivity());
                    pssrc.setChecked(true);
                    return;
                }
            }

            surveyData.setPstatus("1");
            surveyData.setPstype("S");
            surveyData.setPimei(CommonUtil.getDeviceIMEI(requireActivity()));
            if (gmlen.equalsIgnoreCase("6")) {
                if (imageFile == null || !imageFile.exists()) {
                    cameraImageIntent();
                } else
                    updateDB(surveyData);
            } else {
                updateDB(surveyData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDB(final SurveyData surveyData) {

        try {
            surveyData.setPlratoon(String.valueOf(plratoon));
            surveyData.setSurveyImage(imageFile.getAbsolutePath());
            surveyData.setStatus("P");
            surveyData.setsNo(prefManager.getSerialNo());
            new populateSurveyData().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SurveyUtility.writeCSVRecord(surveyData.getContentValues(), "Survey", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        SurveyDatabase.getInstance(context).villageDao().updateVillageSerial(String.valueOf(Integer.parseInt(etVillageSerial.getText().toString()) + 1), etPlotVillage.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (controlData.getPrintslip().equalsIgnoreCase("1")) {
                            updateDBforPrint();
                            PrintHelper.printTokenIssuanceSlip(data, controlData.getGmlen(), controlData.getCname(), CommonUtil.getPreferencesString(context, AppConstants.FULL_NAME));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        CommonUtil.toast("Unable to Print. Please try again.",requireActivity());
                        return;
                    }
                    try {
                        if (SurveyUtility.isNetworkAvailable(requireActivity())) {
                            SurveyUtility.submitFormDataWithImage(context, surveyData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        CommonUtil.toast("Unable to save data. Please check your internet connection.", requireActivity());
                    }
                    prefManager.setSerialNo(prefManager.getSerialNo() + 1);
                    clearALL();
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDBforPrint() {
        MDPIData mdpiData;
        data = new ReprintData();
        data.setsNo(surveyData.getsNo());
        data.setClerkCode(user);
        data.setSeason("2020-21");
        data.setPdate(surveyData.getPdate());
        data.setDate(surveyData.getPdate().split(" ")[0]);
        if (!crpc.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("CRPC", crpc);
            data.setPCRPC(crpc + " " + mdpiData.getMdesc());
        }
        if (!mthd.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("MTHD", mthd);
            data.setPMTHD(mthd + " " + mdpiData.getMdesc());
        }
        if (!dise.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("DISE", dise);
            data.setPDISE(dise + " " + mdpiData.getMdesc());
        }
        if (!intc.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("INTC", intc);
            data.setPINTC(intc + " " + mdpiData.getMdesc());
        }
        if (!land.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("LAND", land);
            data.setPLAND(land + " " + mdpiData.getMdesc());
        }
        if (!crpt.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("CRPT", crpt);
            data.setPlseedflg(crpt + " " + mdpiData.getMdesc());
        }
        if (!ssrc.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("SSRC", ssrc);
            data.setPSSRC(ssrc + " " + mdpiData.getMdesc());
        }
        if (!ccnd.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("CCND", ccnd);
            data.setPCCND(ccnd + " " + mdpiData.getMdesc());
        }
        if (!mixc.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("MIXC", mixc);
            data.setPMIXC(mixc + " " + mdpiData.getMdesc());
        }
        if (!irrg.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("IRRG", irrg);
            data.setPIRRG(irrg + " " + mdpiData.getMdesc());
        }
        if (!brdc.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("BRDC", brdc);
            data.setPBRDC(brdc + " " + mdpiData.getMdesc());
        }
        if (!demo.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("DEMO", demo);
            data.setPDEMO(demo + " " + mdpiData.getMdesc());
        }
        if (!soil.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("SOIL", soil);
            data.setPSOIL(soil + " " + mdpiData.getMdesc());
        }
        if (!inse.isEmpty()) {
            mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("INSE", inse);
            data.setPINSE(inse + " " + mdpiData.getMdesc());
        }
        //data.setPlantType(etPlantTypeCode.getText().toString() + " " + etPlantType.getText().toString());
        data.setPlotVill(etPlotVillage.getText().toString());
        data.setPlSerial(Integer.parseInt(etVillageSerial.getText().toString()));
        data.setPlArea(etPlotArea.getText().toString());
        data.setGrowerCode(etVillage.getText().toString() + "/" + etGrower.getText().toString());
        data.setVillage(etVillageAddress.getText().toString());
        data.setGrowerName(etGrowerName.getText().toString());
        data.setGrFather(grFather);
        data.setVariety(etACVariety.getText().toString() + " " + etACVarietyName.getText().toString());
        data.setSharedArea(etShareArea.getText().toString());
        data.setPercent(etPercent.getText().toString());
        data.setUnicode(gucd);
        data.setDims1(dims1);
        data.setDims2(dims2);
        data.setDims3(dims3);
        data.setDims4(dims4);
        new populateReprintData().execute(data);
    }

    private void clearALL() {
        data = null;
        plratoon = 0;
        overlap = false;
        serialCheck.setChecked(false);
        etLat.setText("0");
        etCropCycleCode.setText("");
        etCropCycle.setText("");
        etLandTypeCode.setText("");
        etLandType.setText("");
        pcrpt.setVisibility(View.GONE);
        etLong.setText("0");
        //etPlotVillage.setText("");
        //etPlotAddress.setText("");
        etVillageSerial.setText("");
        etPercent.setText("");
        etShareArea.setText("");
        etPlotArea.setText("");
        etEastDimen.setText("");
        etWestDimen.setText("");
        etNorthDimen.setText("");
        etSouthDimen.setText("");
        etVillage.setText("");
        etVillageAddress.setText("");
        etCutliVatedArea.setText("");
        etGrower.setText("");
        etGrowerName.setText("");
        etACVariety.setText("");
        etACVarietyName.setText("");
        etShowingDate.setText("");
        etPlantTypeCode.setText("");
        etPlantType.setText("");
        etPlantTypeCode.setVisibility(View.GONE);
        etPlantType.setVisibility(View.GONE);
        etPlotVillage.requestFocus();
        etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        etVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        etGrower.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        etACVariety.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        etPercent.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        etShowingDate.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        etPlantTypeCode.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
        lat1 = 0;
        lat2 = 0;
        lat3 = 0;
        lat4 = 0;
        percent = 0;
        dims1 = 0;
        dims2 = 0;
        dims3 = 0;
        dims4 = 0;
        lng1 = 0;
        lng2 = 0;
        lng3 = 0;
        lng4 = 0;
        // count = 0;
        rgDirectionIndex = 0;
        mthd = "";
        dise = "";
        inse = "";
        intc = "";
        land = "";
        crpc = "";
        ccnd = "";
        mixc = "";
        irrg = "";
        brdc = "";
        demo = "";
        soil = "";
        ssrc = "";
        crpt = "";
        selected = false;
        lat = 0;
        lng = 0;
        code = "";
        plantmethodIndex = 15;
        retryAttempt = 0;
        northDir = false;
        eastDir = false;
        westDir = false;
        southDir = false;
        if (!(gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
            rgDirections.setVisibility(View.VISIBLE);
            rgDirections.clearCheck();
            east.setVisibility(View.VISIBLE);
            west.setVisibility(View.VISIBLE);
            north.setVisibility(View.VISIBLE);
            south.setVisibility(View.VISIBLE);
        } else {
            btnGPS.setText("Get Coordinates Corner 1");
        }
        imageFile = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGps) {
            if (SurveyUtility.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION, 0)) {
                try {
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        if (!btnGPS.getText().toString().contains("All Corners complete")) {
                            getLocationUpdates();
                        } else
                            CommonUtil.toast("Corners completed! Can't be more than 4.", requireActivity());
                    } else {
                        getLocationUpdates();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void calculateDimensions() {
        int minCord = Integer.parseInt(controlData.getMincord());
        int maxCord = Integer.parseInt(controlData.getMaxcord());

        try {
            if (lat1 != 0 && lat2 != 0) {
                etEastDimen.setText(String.valueOf(SurveyUtility.calculateDimen(lat1, lng1, lat2, lng2)));
                dims1 = Double.parseDouble(etEastDimen.getText().toString());
                if (dims1 < minCord) {
                    lat2 = 0;
                    lng2 = 0;
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 2");
                    }
                    CommonUtil.toast("Dimension cannot be less than " + minCord, context);
                }
                if (dims1 > maxCord) {
                    lat2 = 0;
                    lng2 = 0;
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 2");
                    }
                    CommonUtil.toast("Dimension cannot be greater than " + maxCord, context);
                }

            }
            if (lat2 != 0 && lat3 != 0) {
                etWestDimen.setText(String.valueOf(SurveyUtility.calculateDimen(lat2, lng2, lat3, lng3)));
                dims2 = Double.parseDouble(etWestDimen.getText().toString());
                if (dims2 < minCord) {
                    lat3 = 0;
                    lng3 = 0;
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 3");
                    }
                    CommonUtil.toast("Dimension cannot be less than " + minCord, context);
                }
                if (dims2 > maxCord) {
                    lat3 = 0;
                    lng3 = 0;
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 3");
                    }
                    CommonUtil.toast("Dimension cannot be greater than " + maxCord, context);
                }
            }
            if (lat3 != 0 && lat4 != 0) {
                etNorthDimen.setText(String.valueOf(SurveyUtility.calculateDimen(lat3, lng3, lat4, lng4)));
                dims3 = Double.parseDouble(etNorthDimen.getText().toString());
                if (dims3 < minCord) {
                    lat4 = 0;
                    lng4 = 0;
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 4");
                    }
                    CommonUtil.toast("Dimension cannot be less than " + minCord, context);
                }
                if (dims3 > maxCord) {
                    lat4 = 0;
                    lng4 = 0;
                    if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                        btnGPS.setText("Get Coordinates Corner 4");
                    }
                    CommonUtil.toast("Dimension cannot be greater than " + maxCord, context);
                }
            }
            if (lat1 != 0 && lat4 != 0) {
                etSouthDimen.setText(String.valueOf(SurveyUtility.calculateDimen(lat4, lng4, lat1, lng1)));
                dims4 = Double.parseDouble(etSouthDimen.getText().toString());
            }
            if (lat1 != 0 && lat2 != 0 && lat3 != 0 && lat4 != 0) {
                east.setVisibility(View.VISIBLE);
                west.setVisibility(View.VISIBLE);
                north.setVisibility(View.VISIBLE);
                south.setVisibility(View.VISIBLE);

                etPlotArea.setText(SurveyUtility.calculateArea(dims1, dims2, dims3, dims4, controlData.getAreainacr()));
                if ((gmlen.equalsIgnoreCase("5") || gmlen.equalsIgnoreCase("6"))) {
                    etShareArea.setText(SurveyUtility.calculateShareArea(100, Double.parseDouble(etPlotArea.getText().toString())));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void getLocationUpdates() {
        try {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                CommonUtil.toast("Unable to get your location. Trying again", requireActivity());
                if (retryAttempt < 5) {
                    retryAttempt++;
                    getLocationUpdates();
                    return;
                }
            }
            // CommonUtil.toast("Accuracy : " + location.getAccuracy() + "Latitude : " + location.getLatitude() + "Longitude : " + location.getLongitude(), requireActivity());
            // if (count < 3) {
            if (location.hasAccuracy()) {
                if (location.getAccuracy() < 20) {
                    if (lat == location.getLatitude() && lng == location.getLongitude()) {
                        if (retryAttempt < 5) {
                            retryAttempt++;
                            getLocationUpdates();
                            return;
                        }
                    }
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    etLat.setText(df.format(location.getLatitude()));
                    etLong.setText(df.format(location.getLongitude()));
                    populateLatLong();
                    calculateDimensions();
                } else {
                    if (retryAttempt < 5) {
                        retryAttempt++;
                        getLocationUpdates();
                        return;
                    }
                }
            }
            //}
            Log.i(TAG, "getLocationUpdates: " + location.getLatitude() + location.getLongitude());
            retryAttempt = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controlData = SurveyDatabase.getInstance(requireActivity()).controlDao().getControlDetails();
        user = CommonUtil.getPreferencesString(context, AppConstants.USER_ID);
        if (controlData != null) {
            gmlen = controlData.getGmlen();
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, new MyLocationListener());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void cameraImageIntent() {
        try {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile = getImageName();
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_IMAGE);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getImageName() {
        File wallpaperDirectory = new File(android.os.Environment.getExternalStorageDirectory(), "/SurveyApp");
        if (!wallpaperDirectory.exists()) wallpaperDirectory.mkdirs();
        String name = etPlotVillage.getText().toString() + "_" + etVillageSerial.getText().toString() + "_" + etVillage.getText().toString() + "_" + etGrower.getText().toString() + "_" + "2020";
        return new File(wallpaperDirectory, name + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_IMAGE) {
            try {
                if (!imageFile.exists()) {
                    return;
                }
                String filePath = imageFile.getAbsolutePath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                File file = getImageName();
                OutputStream fOut;
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut);
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    pldivsl = location.getLatitude();
                    pldivdr = location.getLongitude();
                }
                surveyData.setPldivsl(String.valueOf(pldivsl));
                surveyData.setPldivdr(String.valueOf(pldivdr));
                if (SurveyUtility.isNetworkAvailable(context)) {
                    plotRecheck("photoRecheck");
                } else {
                    CommonUtil.toast("Please check your Intenet Connection.", context);
                    plratoon = plratoon + 6;
                    updateDB(surveyData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location1) {
            location = location1;
            Log.i(TAG, "onLocationChanged: " + location.getLatitude() + location.getAccuracy() + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateSurveyData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // SurveyDatabase.getInstance(requireActivity()).surveyDao().deleteAll();
            SurveyDatabase.getInstance(requireActivity()).surveyDao().insertSurvey(surveyData);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class populateReprintData extends AsyncTask<ReprintData, Void, Void> {
        @Override
        protected Void doInBackground(ReprintData... data) {
            // SurveyDatabase.getInstance(requireActivity()).reprintDao().deleteAll();
            SurveyDatabase.getInstance(requireActivity()).reprintDao().insertReprint(data);
            return null;
        }
    }
}