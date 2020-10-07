package com.fourcpplus.survey.ui.recheckPlot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.fourcpplus.survey.R;
import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.interfaces.ApiCall;
import com.fourcpplus.survey.model.db.ControlData;
import com.fourcpplus.survey.model.db.MDPIData;
import com.fourcpplus.survey.model.db.VarietyData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.model.network.PlotRecheckResponse;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.PrintHelper;
import com.fourcpplus.survey.utils.ServiceGenerator;
import com.fourcpplus.survey.utils.SurveyUtility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rohit on 12-05-2020.
 */
public class PlotRecheckFragment extends Fragment {
    private static final String TAG = "PlotRecheckFragment";
    private EditText etLong;
    private EditText etLat;
    private EditText etPlotVillage;
    private EditText etPlotAddress;
    private EditText etVillageSerial;
    private EditText etPercent;
    private EditText etPlotArea;
    private EditText dims1;
    private EditText dims2;
    private EditText dims3;
    private EditText dims4;
    private EditText etDims1Recheck;
    private EditText etDims2Recheck;
    private EditText etDims3Recheck;
    private EditText etDims4Recheck;
    private EditText etPlotAreaRecheck;
    private EditText etVillage;
    private EditText etVillageAddress;
    private EditText etGrower;
    private EditText etGrowerName;
    private EditText etACVariety;
    private EditText etACVarietyName;
    private EditText etShowingDate;
    private Button btnGPS;
    private Button btnPRINT;
    private Location location;
    private LocationManager locationManager;
    private EditText etCropTypeCode;
    private EditText etCropType;
    private EditText etCropCycleCode;
    private EditText etCropCycle;
    private EditText etLandTypeCode;
    private EditText etLandType;
    private Context context;
    private int retryAttempt = 0;
    private DecimalFormat df;
    private double lat, lng;
    private boolean plotVillage;
    private Button btnRecheck;
    private double lat1, lat2, lat3, lat4, lng1, lng2, lng3, lng4;
    private ControlData controlData;
    private EditText etLongRecheck;
    private EditText etLatRecheck;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireActivity();
        df = new DecimalFormat("#.######");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
        try {
            controlData = SurveyDatabase.getInstance(context).controlDao().getControlDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        locationManager = (LocationManager) Objects.requireNonNull(requireActivity()).getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, new MyLocationListener());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_plotrecheck, container, false);
        initView(root);
        return root;

    }

    private void initView(View root) {

        btnGPS = root.findViewById(R.id.btnGps);
        btnPRINT = root.findViewById(R.id.btnprint);
        btnRecheck = root.findViewById(R.id.btnRecheckPlot);
        etLat = root.findViewById(R.id.etLat);
        etLatRecheck = root.findViewById(R.id.etLatRecheck);
        etLong = root.findViewById(R.id.etLong);
        etLongRecheck = root.findViewById(R.id.etLongRecheck);
        etPlotVillage = root.findViewById(R.id.etPlotVillage);
        etPlotAddress = root.findViewById(R.id.etPlotAddress);
        etVillageSerial = root.findViewById(R.id.etVillageSerial);
        etPercent = root.findViewById(R.id.etPercent);
        etPlotArea = root.findViewById(R.id.etPlotArea);
        etPlotAreaRecheck = root.findViewById(R.id.etPlotAreaRecheck);
        etDims1Recheck = root.findViewById(R.id.etDim1);
        etDims2Recheck = root.findViewById(R.id.etDim2);
        etDims3Recheck = root.findViewById(R.id.etDim3);
        etDims4Recheck = root.findViewById(R.id.etDim4);
        dims1 = root.findViewById(R.id.etEastDimen);
        dims2 = root.findViewById(R.id.etWestDimen);
        dims3 = root.findViewById(R.id.etNorthDimen);
        dims4 = root.findViewById(R.id.etSouthDimen);
        etVillage = root.findViewById(R.id.etVillage);
        etVillageAddress = root.findViewById(R.id.etVillageAddress);
        etGrower = root.findViewById(R.id.etGrower);
        etGrowerName = root.findViewById(R.id.etGrowerName);
        etACVariety = root.findViewById(R.id.etACVariety);
        etACVarietyName = root.findViewById(R.id.etACVarietyName);
        etShowingDate = root.findViewById(R.id.etShowingDate);
        etCropCycle = root.findViewById(R.id.etCropCycle);
        etCropCycleCode = root.findViewById(R.id.etCropCycleCode);
        etCropType = root.findViewById(R.id.etCropType);
        etCropTypeCode = root.findViewById(R.id.etCropTypeCode);
        etLandType = root.findViewById(R.id.etLandType);
        etLandTypeCode = root.findViewById(R.id.etLandTypeCode);

        btnRecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationUpdates(true);
            }
        });

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationUpdates(false);
            }
        });

        btnPRINT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printData();
            }
        });

        etPlotVillage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.KEYCODE_NAVIGATE_NEXT) && (i == android.view.KeyEvent.KEYCODE_ENTER)) {
                        if (!etPlotVillage.getText().toString().isEmpty()) {
                            VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etPlotVillage.getText().toString());
                            if (villageData != null) {
                                plotVillage = true;
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
    }


    private void getLocationUpdates(boolean plotRecheck) {
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

                if (retryAttempt < 5) {
                    retryAttempt++;
                    getLocationUpdates(plotRecheck);
                    return;
                } else {
                    CommonUtil.toast("Unable to get your location. Try again", requireActivity());
                }
                return;
            }

            if (location.hasAccuracy()) {
                if (location.getAccuracy() <= 20) {
                    if (lat == location.getLatitude() && lng == location.getLongitude()) {
                        if (retryAttempt < 5) {
                            retryAttempt++;
                            getLocationUpdates(plotRecheck);
                            return;
                        } else {
                            CommonUtil.toast("Unable to get your location. Try again", requireActivity());
                        }
                    }
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    if (plotRecheck) {
                        etLat.setText(df.format(location.getLatitude()));
                        etLong.setText(df.format(location.getLongitude()));
                        plotRecheck(df.format(location.getLatitude()), df.format(location.getLongitude()));
                    } else {
                        etLatRecheck.setText(df.format(location.getLatitude()));
                        etLongRecheck.setText(df.format(location.getLongitude()));
                        populateLatLong();
                    }
                } else {
                    if (retryAttempt < 5) {
                        retryAttempt++;
                        getLocationUpdates(plotRecheck);
                        return;
                    } else {
                        CommonUtil.toast("Unable to get your location. Try again", requireActivity());
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

    private void plotRecheck(String lat, String lng) {
        if (etPlotVillage.getText().toString().isEmpty()) {
            CommonUtil.toast("Please input valid plot village", context);
            return;
        }
        Map<String, String> options = new HashMap<>();

        options.put("lat", lat);
        options.put("lng", lng);
        options.put("pcentre", "0");
        options.put("pdivn", CommonUtil.getPreferencesString(context, AppConstants.MYDIVISION));
        options.put("pFilno", "36");
        options.put("pval", etPlotVillage.getText().toString());
        options.put("pimei", CommonUtil.getDeviceIMEI(context));

        options.put("frminpt", "0");
        options.put("lat1", "0");
        options.put("lat2", "0");
        options.put("lat3", "0");
        options.put("lat4", "0");
        options.put("lng1", "0");
        options.put("lng2", "0");
        options.put("lng3", "0");
        options.put("lng4", "0");


        ApiCall apiCall = ServiceGenerator.createService(ApiCall.class);
        Call<PlotRecheckResponse> call;
        call = apiCall.plotRecheck(options);
        call.enqueue(new Callback<PlotRecheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlotRecheckResponse> call, @NonNull Response<PlotRecheckResponse> response) {
                PlotRecheckResponse plotRecheckResponse = response.body();
                assert plotRecheckResponse != null;
                if (plotRecheckResponse.getResponseCode() == 1) {
                    SurveyUtility.showOKAlert(requireActivity(), "Plot successfully found");
                    if (plotRecheckResponse.getPlotData().size() > 0) {
                        fillFormData(plotRecheckResponse);
                    }
                } else {
                    clearAll();
                    SurveyUtility.showOKAlert(requireActivity(), plotRecheckResponse.getResponseMessage());
                }
            }

            @Override
            public void onFailure(Call<PlotRecheckResponse> call, Throwable t) {
            }
        });
    }

    private void fillFormData(PlotRecheckResponse plotData) {
        MDPIData mdpiData;
        etPlotVillage.setText(String.valueOf(plotData.getMatchPloat().get(0).getVcode()));
        etPlotAddress.setText(plotData.getMatchPloat().get(0).getVname());
        etVillageSerial.setText(String.valueOf(plotData.getMatchPloat().get(0).getGPSVSNO()));
        dims1.setText(plotData.getPlotData().get(0).getRdim1());
        dims2.setText(plotData.getPlotData().get(0).getRdim3());
        dims3.setText(plotData.getPlotData().get(0).getRdim2());
        dims4.setText(plotData.getPlotData().get(0).getRdim4());
        etPercent.setText(String.valueOf(plotData.getPlotData().get(0).getRpercent()));
        etPlotArea.setText(String.valueOf(plotData.getPlotData().get(0).getRplarea()));
        etVillage.setText(plotData.getPlotData().get(0).getRvill());
        etVillageAddress.setText(plotData.getPlotData().get(0).getRvillname());
        etGrower.setText(plotData.getPlotData().get(0).getRgrno());
        etACVariety.setText(plotData.getPlotData().get(0).getRplvar());
        etShowingDate.setText(plotData.getPlotData().get(0).getRplantdt());
        etCropCycleCode.setText(plotData.getPlotData().get(0).getRcrpc());
        etCropTypeCode.setText(plotData.getPlotData().get(0).getRctype());
        etLandTypeCode.setText(plotData.getPlotData().get(0).getRland());
        etGrowerName.setText(plotData.getPlotData().get(0).getRgname());
        VarietyData varietyData = SurveyDatabase.getInstance(context).varietyDao().getVarietyDetails(etACVariety.getText().toString());
        if (varietyData != null) {
            etACVarietyName.setText(varietyData.getVarname());
        }
        mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("CRPT", etCropTypeCode.getText().toString());
        if (mdpiData != null) {
            etCropType.setText(mdpiData.getMdesc());
        }
        mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("CRPC", etCropCycleCode.getText().toString());
        if (mdpiData != null) {
            etCropCycle.setText(mdpiData.getMdesc());
        }
        mdpiData = SurveyDatabase.getInstance(requireActivity()).mdpiDao().getMDPIDetails("LAND", etLandTypeCode.getText().toString());
        if (mdpiData != null) {
            etLandType.setText(mdpiData.getMdesc());
        }
    }

    private void populateLatLong() {
        try {
            if (lat1 == 0) {
                lat1 = Double.parseDouble(etLatRecheck.getText().toString());
                lng1 = Double.parseDouble(etLongRecheck.getText().toString());
                if (lat1 != 0) {
                    btnGPS.setText("Get Coordinates Corner 2");
                }
            } else if (lat2 == 0) {
                lat2 = Double.parseDouble(etLatRecheck.getText().toString());
                lng2 = Double.parseDouble(etLongRecheck.getText().toString());
                etDims1Recheck.setText(String.valueOf(SurveyUtility.calculateDimen(lat1, lng1, lat2, lng2)));
                if (lat2 != 0) {
                    btnGPS.setText("Get Coordinates Corner 3");
                }
            } else if (lat3 == 0) {
                lat3 = Double.parseDouble(etLatRecheck.getText().toString());
                lng3 = Double.parseDouble(etLongRecheck.getText().toString());
                etDims2Recheck.setText(String.valueOf(SurveyUtility.calculateDimen(lat2, lng2, lat3, lng3)));
                if (lat3 != 0) {
                    btnGPS.setText("Get Coordinates Corner 4");
                }
            } else if (lat4 == 0) {
                lat4 = Double.parseDouble(etLatRecheck.getText().toString());
                lng4 = Double.parseDouble(etLongRecheck.getText().toString());
                etDims3Recheck.setText(String.valueOf(SurveyUtility.calculateDimen(lat3, lng3, lat4, lng4)));
                etDims4Recheck.setText(String.valueOf(SurveyUtility.calculateDimen(lat4, lng4, lat1, lng1)));
                double dimens1 = Double.parseDouble(etDims1Recheck.getText().toString());
                double dimens2 = Double.parseDouble(etDims2Recheck.getText().toString());
                double dimens3 = Double.parseDouble(etDims3Recheck.getText().toString());
                double dimens4 = Double.parseDouble(etDims4Recheck.getText().toString());
                etPlotAreaRecheck.setText(SurveyUtility.calculateArea(dimens1, dimens2, dimens3, dimens4, controlData.getAreainacr()));
                if (lat4 != 0) {
                    btnGPS.setText("All Corners complete");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void printData() {
        PrintHelper.printPlotRecheck(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()),
                etCropCycleCode.getText().toString() + " " + etCropCycle.getText().toString(),
                etCropTypeCode.getText().toString() + " " + etCropType.getText().toString(),
                etLandTypeCode.getText().toString() + " " + etLandType.getText().toString(),
                etPlotVillage.getText().toString(),
                etVillageSerial.getText().toString(),
                etPlotArea.getText().toString(),
                dims1.getText().toString(), dims2.getText().toString(),
                dims3.getText().toString(), dims4.getText().toString(),
                etPlotVillage.getText().toString() + "/" + etGrower.getText().toString(),
                etVillage.getText().toString(), etGrowerName.getText().toString(),
                etACVariety.getText().toString() + " " + etACVarietyName.getText().toString(),
                CommonUtil.getPreferencesString(context, AppConstants.USER_ID),
                CommonUtil.getPreferencesString(context, AppConstants.FULL_NAME), etPlotAreaRecheck.getText().toString());
    }

    private void clearAll() {
        etLat.setText("");
        etLong.setText("");
        lat = 0;
        lng = 0;
        lat1 = 0;
        lat2 = 0;
        lat3 = 0;
        lat4 = 0;
        lng1 = 0;
        lng2 = 0;
        lng3 = 0;
        lng4 = 0;
        // etPlotVillage.setText("");
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
}
