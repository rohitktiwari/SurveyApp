package com.fourcpplus.survey.ui.summary;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fourcpplus.survey.R;
import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.model.db.ControlData;
import com.fourcpplus.survey.model.db.SurveyData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.AppSharedPreferences;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.PrintHelper;
import com.fourcpplus.survey.utils.SurveyUtility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SummaryFragment extends Fragment {

    private final Calendar myCalendar = Calendar.getInstance();
    private TextView tv_Total_Plots;
    private TextView tv_Total_Area;
    private TextView tv_Ratoon_Area;
    private TextView tv_Ratoon_Plots;
    private TextView tv_Autumn_Area;
    private TextView tv_Autumn_Plots;
    private TextView tv_Plant_Area;
    private TextView tv_Plant_Plots;
    private EditText etDate;
    private DecimalFormat df = new DecimalFormat("#.###");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private SimpleDateFormat sdfTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
    private List<SurveyData> surveyDataList;
    private TextView tv_lastVillCode;
    private TextView tv_startSerial;
    private TextView tv_lastSerial;
    private AppSharedPreferences prefManager;
    private SurveyData surveyData;
    private String lastVillName;
    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private ControlData controlData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_summary, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        tv_Total_Area = root.findViewById(R.id.tv_totalarea);
        tv_Total_Plots = root.findViewById(R.id.tv_totalPlots);
        tv_Ratoon_Plots = root.findViewById(R.id.ratoonPlots);
        tv_Ratoon_Area = root.findViewById(R.id.ratoonArea);
        tv_Autumn_Plots = root.findViewById(R.id.autumnPlots);
        tv_Autumn_Area = root.findViewById(R.id.autumnArea);
        tv_Plant_Area = root.findViewById(R.id.plantArea);
        tv_Plant_Plots = root.findViewById(R.id.plantPlots);
        tv_startSerial = root.findViewById(R.id.tv_startSerial);
        tv_lastSerial = root.findViewById(R.id.tv_lastSerial);
        tv_lastVillCode = root.findViewById(R.id.tv_lastVillCode);
        if (controlData.getGmlen().equalsIgnoreCase("5") || controlData.getGmlen().equalsIgnoreCase("6")) {
            root.findViewById(R.id.ll_autumn).setVisibility(View.GONE);
            root.findViewById(R.id.viewAutumn).setVisibility(View.GONE);
        }
        etDate = root.findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(requireActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if (etDate.getText().toString().isEmpty()) {
            String date = sdf.format(new Date().getTime());
            updateFields(date);
            etDate.setText(date);
        }

        Button btnPrint = root.findViewById(R.id.btnprint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSurveyData();
                PrintHelper.printSummary(tv_Total_Plots.getText().toString(), tv_Total_Area.getText().toString(),
                        tv_Ratoon_Area.getText().toString(), tv_Ratoon_Plots.getText().toString(),
                        tv_Autumn_Area.getText().toString(), tv_Autumn_Plots.getText().toString(),
                        tv_Plant_Area.getText().toString(), tv_Plant_Plots.getText().toString(),
                        tv_lastSerial.getText().toString(), tv_lastVillCode.getText().toString(), lastVillName,
                        CommonUtil.getPreferencesString(requireActivity(), AppConstants.USER_ID),
                        CommonUtil.getPreferencesString(requireActivity(), AppConstants.FULL_NAME),controlData.getGmlen(), etDate.getText().toString());
            }
        });
    }

    private void updateFields(String date) {
        try {
            surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getSurveyData(sdf.parse(date));
            if (surveyDataList != null && surveyDataList.size() > 0) {
                int size = surveyDataList.size();
                tv_startSerial.setText(String.valueOf(surveyDataList.get(0).getPLVsno()));
                tv_lastSerial.setText(String.valueOf(surveyDataList.get(size - 1).getPLVsno()));
                tv_lastVillCode.setText(String.valueOf(surveyDataList.get(size - 1).getPLGastino()));
                VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(String.valueOf(surveyDataList.get(size - 1).getPLVill()));
                if (villageData != null) {
                    lastVillName = villageData.getVname();
                }
                tv_Total_Plots.setText(String.valueOf(surveyDataList.size()));
                calculateTotalArea();
                calculateAutumnDetails();
                calculatePlantDetails();
                calculateRatoonDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLabel() {
        try {
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            etDate.setText(sdf.format(myCalendar.getTime()));
            etDate.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
            updateFields(etDate.getText().toString());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calculateTotalArea() {
        double area = 0;
        for (SurveyData data : surveyDataList) {
            if (!data.getPlarea().isEmpty())
                area = area + Double.parseDouble(data.getPlarea());
        }
        tv_Total_Area.setText(df.format(area));
    }

    private void calculateAutumnDetails() {
        int plots = 0;
        double area = 0;
        for (SurveyData data : surveyDataList) {
            if (data.getPCRPC() != null) {
                if (data.getPCRPC().equalsIgnoreCase("1")) {
                    plots = plots + 1;
                    if (!data.getPlarea().isEmpty()) {
                        area = area + Double.parseDouble(data.getPlarea());
                    }
                }
            }
        }
        tv_Autumn_Area.setText(df.format(area));
        tv_Autumn_Plots.setText(String.valueOf(plots));
    }

    private void calculateRatoonDetails() {
        int plots = 0;
        double area = 0;
        for (SurveyData data : surveyDataList) {
            if (data.getPCRPC() != null) {
                if (data.getPCRPC().equalsIgnoreCase("2") || data.getPCRPC().equalsIgnoreCase("3")) {
                    plots = plots + 1;
                    if (!data.getPlarea().isEmpty()) {
                        area = area + Double.parseDouble(data.getPlarea());
                    }
                }
            }
        }
        tv_Ratoon_Area.setText(df.format(area));
        tv_Ratoon_Plots.setText(String.valueOf(plots));
    }

    private void calculatePlantDetails() {
        int plots = 0;
        double area = 0;
        for (SurveyData data : surveyDataList) {
            if (data.getPCRPC() != null) {
                if (data.getPCRPC().equalsIgnoreCase("0")) {
                    plots = plots + 1;
                    if (!data.getPlarea().isEmpty()) {
                        area = area + Double.parseDouble(data.getPlarea());
                    }
                }
            }
        }
        tv_Plant_Area.setText(df.format(area));
        tv_Plant_Plots.setText(String.valueOf(plots));
    }


    private void updateSurveyData() {
        surveyData = new SurveyData();
        surveyData.setPdivn(CommonUtil.getPreferencesString(requireActivity(), AppConstants.MYDIVISION));
        surveyData.setPdate(etDate.getText().toString());
        surveyData.setPLGastino(tv_Total_Plots.getText().toString());
        surveyData.setPlacvill(tv_lastVillCode.getText().toString());
        surveyData.setPLVsno(Integer.parseInt(tv_lastSerial.getText().toString()));
        surveyData.setPLVill(tv_startSerial.getText().toString());
        surveyData.setPLGrno("0");
        surveyData.setPlacvar("0");
        surveyData.setPldim1(tv_Ratoon_Plots.getText().toString());
        surveyData.setPldim2(tv_Plant_Plots.getText().toString());
        surveyData.setPldim3("0");
        surveyData.setPldim4("0");
        surveyData.setPldivsl(tv_Ratoon_Area.getText().toString());
        surveyData.setPldivdr(tv_Plant_Area.getText().toString());
        surveyData.setPlpercent("0");
        surveyData.setPlarea(tv_Total_Area.getText().toString());
        surveyData.setPlclerk(CommonUtil.getPreferencesString(requireActivity(), AppConstants.USER_ID));
        surveyData.setPldate(sdfTime.format(new Date()));
        surveyData.setPlratoon("0");
        surveyData.setPlseedflg("0");
        surveyData.setPMTHD("0");
        surveyData.setPINTC("0");
        surveyData.setPDISE("0");
        surveyData.setPINSE("0");
        surveyData.setPLAND("0");
        surveyData.setPCRPC("0");
        surveyData.setPCCND("0");
        surveyData.setPMIXC("0");
        surveyData.setPIRRG("0");
        surveyData.setPBRDC("0");
        surveyData.setPDEMO("0");
        surveyData.setPSOIL("0");
        surveyData.setPSSRC("0");
        surveyData.setPstatus("1");
        surveyData.setPstype("D");
        surveyData.setPlat1("0");
        surveyData.setPlon1("0");
        surveyData.setPlat2("0");
        surveyData.setPlon2("0");
        surveyData.setPlat3("0");
        surveyData.setPlon3("0");
        surveyData.setPlat4("0");
        surveyData.setPlon4("0");
        surveyData.setPimei(CommonUtil.getDeviceIMEI(requireContext()));
        surveyData.setStatus("P");
        surveyData.setsNo(prefManager.getSerialNo());
        prefManager.setSerialNo(prefManager.getSerialNo() + 1);
        new populateSurveyData().execute();
        SurveyUtility.submitFormDataWithImage(requireActivity(), surveyData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = AppSharedPreferences.getInstance(requireActivity());
        controlData = SurveyDatabase.getInstance(requireActivity()).controlDao().getControlDetails();
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

}