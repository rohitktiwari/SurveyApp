package com.fourcpplus.survey.ui.uploadSurvey;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fourcpplus.survey.R;
import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.model.db.SurveyData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.SurveyUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rohit on 16-05-2020.
 */
public class SurveyUploadFragment extends Fragment {

    private final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private EditText etFromDate;
    private EditText etTillDate;
    private EditText etPlotVillage;
    private EditText etPlotSerial;
    private EditText etPlotVillageName;
    private EditText etPendingUploads;
    private Button btnUpload;
    private Button btnCSVWrite;
    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatefromLabel();
        }

    };
    private DatePickerDialog.OnDateSetListener tilldate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatetillLabel();
        }

    };
    private SurveyData surveyData;

    private void updatefromLabel() {
        try {

            etFromDate.setText(sdf.format(myCalendar.getTime()));
            etFromDate.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updatetillLabel() {
        try {
            etTillDate.setText(sdf.format(myCalendar.getTime()));
            etTillDate.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload_survey, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        etFromDate = root.findViewById(R.id.etDateFrom);
        etTillDate = root.findViewById(R.id.etDateTill);
        etPlotVillage = root.findViewById(R.id.etPlotVillage);
        etPlotSerial = root.findViewById(R.id.etPlotSerialNo);
        etPlotVillageName = root.findViewById(R.id.etPlotVillageName);
        etPendingUploads = root.findViewById(R.id.etPendingUpload);
        btnUpload = root.findViewById(R.id.btnUpload);
        btnCSVWrite = root.findViewById(R.id.btnCSVWrite);
        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(requireActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etTillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(requireActivity(), tilldate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etPlotVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
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
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDoalog = new ProgressDialog(requireActivity());
                progressDoalog.setMessage("Uploading....");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();
                List<SurveyData> surveyDataList;
                try {
                    String serial = etPlotSerial.getText().toString();
                    String fromDate = etFromDate.getText().toString();
                    String tillDate = etTillDate.getText().toString();
                    String plotVill = etPlotVillage.getText().toString();
                    if (serial.isEmpty() && fromDate.isEmpty() && tillDate.isEmpty() && plotVill.isEmpty()) {
                        surveyDataList = SurveyUtility.getPendingSurveys(requireActivity());
                    } else if (serial.isEmpty() && plotVill.isEmpty()) {
                        surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getPendingSurveyData(sdf.parse(fromDate), sdf.parse(tillDate), "P");
                    } else if (serial.isEmpty()) {
                        surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getPendingSurveyData(sdf.parse(fromDate), sdf.parse(tillDate), plotVill, "P");
                    } else {
                        surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getPendingSurveyData(sdf.parse(fromDate), sdf.parse(tillDate), plotVill, serial, "P");
                    }
                    assert surveyDataList != null;
                    etPendingUploads.setText(String.valueOf(surveyDataList.size()));
                    if (surveyDataList.size() != 0) {
                        for (SurveyData data : surveyDataList) {
                            surveyData = data;
                            new populateSurveyData().execute(data);
                            SurveyUtility.submitFormDataWithImage(requireActivity(), data);
                        }
                    }
                    progressDoalog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDoalog.dismiss();
                }
            }
        });

        btnCSVWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDoalog = new ProgressDialog(requireActivity());
                progressDoalog.setMessage("Writing CSV....");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();
                List<SurveyData> surveyDataList = new ArrayList<>();
                try {
                    String serial = etPlotSerial.getText().toString();
                    String fromDate = etFromDate.getText().toString();
                    String tillDate = etTillDate.getText().toString();
                    String plotVill = etPlotVillage.getText().toString();
                    if (serial.isEmpty() && fromDate.isEmpty() && tillDate.isEmpty() && plotVill.isEmpty()) {
                        surveyDataList = SurveyUtility.getPendingSurveys(requireActivity());
                    } else if (serial.isEmpty() && plotVill.isEmpty()) {
                        surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getPendingSurveyData(sdf.parse(fromDate), sdf.parse(tillDate), "P");
                    } else if (serial.isEmpty()) {
                        surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getPendingSurveyData(sdf.parse(fromDate), sdf.parse(tillDate), plotVill, "P");
                    } else {
                        surveyDataList = SurveyDatabase.getInstance(requireActivity()).surveyDao().getPendingSurveyData(sdf.parse(fromDate), sdf.parse(tillDate), plotVill, serial, "P");
                    }
                    assert surveyDataList != null;
                    etPendingUploads.setText(String.valueOf(surveyDataList.size()));
                    try {
                        for (SurveyData surveyData : surveyDataList) {
                            SurveyUtility.writeCSVRecord(surveyData.getContentValues(), "Survey", true);
                        }
                        CommonUtil.toast("All records updated to CSV.", requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDoalog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDoalog.dismiss();
                }

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    class populateSurveyData extends AsyncTask<SurveyData, Void, Void> {

        @Override
        protected Void doInBackground(SurveyData... surveyData) {
            SurveyDatabase.getInstance(requireActivity()).surveyDao().insertSurvey(surveyData);
            return null;
        }
    }
}
