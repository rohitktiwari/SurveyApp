package com.fourcpplus.survey.ui.villageclosing;

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

/**
 * Created by Rohit on 17-05-2020.
 */
public class VillageClosing extends Fragment {

    private final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    SimpleDateFormat sdfTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
    DecimalFormat df = new DecimalFormat("#.###");
    private EditText etClosingDate;
    private EditText etPlotVillage;
    private EditText etPlotVillageName;
    private EditText etStartSerial;
    private EditText etlastSerial;
    private TextView totalPlots;
    private TextView totalArea;
    private TextView ratoonPlots;
    private TextView ratoonArea;
    private TextView plantPlots;
    private TextView plantArea;
    private Button btnShowData;
    private Button btnPrintAndUpdate;
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
    private AppSharedPreferences prefManager;
    private SurveyData surveyData;

    private void updateLabel() {
        try {
            etClosingDate.setText(sdf.format(myCalendar.getTime()));
            etClosingDate.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_village_closing, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        etClosingDate = root.findViewById(R.id.etClosingDate);
        etPlotVillage = root.findViewById(R.id.etPlotVillage);
        etPlotVillageName = root.findViewById(R.id.etPlotVillageName);
        etStartSerial = root.findViewById(R.id.etStartSerial);
        etlastSerial = root.findViewById(R.id.etlastSerial);
        totalPlots = root.findViewById(R.id.totalPlots);
        totalArea = root.findViewById(R.id.totalArea);
        ratoonPlots = root.findViewById(R.id.ratoonPlots);
        ratoonArea = root.findViewById(R.id.ratoonArea);
        plantPlots = root.findViewById(R.id.plantPlots);
        plantArea = root.findViewById(R.id.plantArea);
        btnShowData = root.findViewById(R.id.btnShowData);
        btnPrintAndUpdate = root.findViewById(R.id.btnPrintAndUpdate);

        etClosingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(requireActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etPlotVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                getPlotAddress();
            }
        });


        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (etClosingDate.getText().toString().isEmpty()) {
                        CommonUtil.toast("Please select Closing date.", requireActivity());
                        return;
                    }
                    if (etPlotVillage.getText().toString().isEmpty()) {
                        CommonUtil.toast("Please select Plot Village.", requireActivity());
                        return;
                    }
                    getPlotAddress();
                    Date date = sdf.parse(etClosingDate.getText().toString());
                    List<SurveyData> surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getSurveyData(date, etPlotVillage.getText().toString());
                    if (surveyDataList != null && surveyDataList.size() > 0) {
                        int size = surveyDataList.size();
                        totalPlots.setText(String.valueOf(surveyDataList.size()));
                        etStartSerial.setText(String.valueOf(surveyDataList.get(0).getPLVsno()));
                        etlastSerial.setText(String.valueOf(surveyDataList.get(size - 1).getPLVsno()));
                        calculatePlantDetails(surveyDataList);
                        calculateTotalArea(surveyDataList);
                        calculateRatoonDetails(surveyDataList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnPrintAndUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etPlotVillage.getText().toString().isEmpty()) {
                    getPlotAddress();
                    updateVillTypeInactive();
                    updateSurveyData();
                    printSurveyData();
                }
            }
        });
    }

    private void updateVillTypeInactive() {
        SurveyDatabase.getInstance(requireActivity()).villageDao().updateVillage(etPlotVillage.getText().toString(), "2");
    }

    private void getPlotAddress() {
        try {
            if (!etPlotVillage.getText().toString().isEmpty()) {
                VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etPlotVillage.getText().toString());
                if (villageData != null) {
                    etPlotVillageName.setText(villageData.getVname());
                    etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                } else {

                    etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_red_colour));
                    CommonUtil.toast("Plot Address not found.", requireActivity());
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printSurveyData() {
        PrintHelper.printVillageClosing(etClosingDate.getText().toString(), etPlotVillage.getText().toString(),
                etPlotVillageName.getText().toString(), totalPlots.getText().toString(),
                totalArea.getText().toString(), plantPlots.getText().toString(),
                plantArea.getText().toString(), ratoonPlots.getText().toString(),
                ratoonArea.getText().toString(), etStartSerial.getText().toString(),
                etlastSerial.getText().toString(),
                CommonUtil.getPreferencesString(requireActivity(), AppConstants.USER_ID),
                CommonUtil.getPreferencesString(requireActivity(), AppConstants.FULL_NAME));
    }

    private void updateSurveyData() {
        surveyData = new SurveyData();
        surveyData.setPdivn(CommonUtil.getPreferencesString(requireActivity(), AppConstants.MYDIVISION));
        surveyData.setPdate(etClosingDate.getText().toString());
        surveyData.setPLGastino(totalPlots.getText().toString());
        surveyData.setPlacvill(etPlotVillage.getText().toString());
        surveyData.setPLVsno(Integer.parseInt(etStartSerial.getText().toString()));
        surveyData.setPLVill(etlastSerial.getText().toString());
        surveyData.setPLGrno("0");
        surveyData.setPlacvar("0");
        surveyData.setPldim1(ratoonPlots.getText().toString());
        surveyData.setPldim2(plantPlots.getText().toString());
        surveyData.setPldim3("0");
        surveyData.setPldim4("0");
        surveyData.setPldivsl(ratoonArea.getText().toString());
        surveyData.setPldivdr(plantArea.getText().toString());
        surveyData.setPlpercent("0");
        surveyData.setPlarea(totalArea.getText().toString());
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
        surveyData.setPstype("C");
        surveyData.setPlat1("0");
        surveyData.setPlon1("0");
        surveyData.setPlat2("0");
        surveyData.setPlon2("0");
        surveyData.setPlat3("0");
        surveyData.setPlon3("0");
        surveyData.setPlat4("0");
        surveyData.setPlon4("0");
        surveyData.setPimei(CommonUtil.getDeviceIMEI(requireActivity()));
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
    }

    private void calculateTotalArea(List<SurveyData> surveyDataList) {
        double area = 0;
        for (SurveyData data : surveyDataList) {
            if (!data.getPlarea().isEmpty())
                area = area + Double.parseDouble(data.getPlarea());
        }
        totalArea.setText(df.format(area));
    }

    private void calculateRatoonDetails(List<SurveyData> surveyDataList) {
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
        ratoonArea.setText(df.format(area));
        ratoonPlots.setText(String.valueOf(plots));
    }

    private void calculatePlantDetails(List<SurveyData> surveyDataList) {
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
        plantArea.setText(df.format(area));
        plantPlots.setText(String.valueOf(plots));
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
