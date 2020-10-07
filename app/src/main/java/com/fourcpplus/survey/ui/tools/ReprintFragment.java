package com.fourcpplus.survey.ui.tools;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fourcpplus.survey.R;
import com.fourcpplus.survey.db.SurveyDatabase;
import com.fourcpplus.survey.model.db.ControlData;
import com.fourcpplus.survey.model.db.ReprintData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.PrintHelper;
import com.fourcpplus.survey.utils.SurveyUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReprintFragment extends Fragment {

    private final Calendar myCalendar = Calendar.getInstance();

    private EditText etDate;
    private EditText etPlotVillage;
    private EditText etPlotAddress;
    private EditText etSerial;
    private Button btnReprint;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reprint, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        etDate = root.findViewById(R.id.et_surveyDate);
        etPlotVillage = root.findViewById(R.id.et_plotVill);
        etPlotAddress = root.findViewById(R.id.et_plotVillAddr);
        etSerial = root.findViewById(R.id.et_Serial);
        btnReprint = root.findViewById(R.id.btnReprint);
        etPlotVillage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        if (!etPlotVillage.getText().toString().isEmpty()) {
                            VillageData villageData = SurveyDatabase.getInstance(requireActivity()).villageDao().getVillageName(etPlotVillage.getText().toString());
                            if (villageData != null) {
                                etPlotAddress.setText(villageData.getVname());
                                etPlotVillage.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
                            } else {
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

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyUtility.hideKeyboard(requireActivity());
                new DatePickerDialog(requireActivity(), date, myCalendar
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
                            etPlotAddress.setText(villageData.getVname());
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

        btnReprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ControlData controlData = SurveyDatabase.getInstance(requireActivity()).controlDao().getControlDetails();
                    ReprintData data = SurveyDatabase.getInstance(requireActivity()).reprintDao().getReprintData(Integer.parseInt(etSerial.getText().toString()), etDate.getText().toString(), etPlotVillage.getText().toString());
                    if (data != null) {
                        PrintHelper.printTokenIssuanceSlip(data, controlData.getGmlen(), controlData.getCname(), CommonUtil.getPreferencesString(requireActivity(), AppConstants.FULL_NAME));
                    } else {
                        CommonUtil.toast("No data Found!", requireActivity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateLabel() {
        try {
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            etDate.setText(sdf.format(myCalendar.getTime()));
            etDate.setBackground(getResources().getDrawable(R.drawable.edittext_green_colour));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


}