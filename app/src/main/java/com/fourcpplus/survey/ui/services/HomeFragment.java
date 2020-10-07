package com.fourcpplus.survey.ui.services;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.fourcpplus.survey.MainActivity;
import com.fourcpplus.survey.R;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.CommonUtil;
import com.fourcpplus.survey.utils.JobUtil;
import com.fourcpplus.survey.utils.SurveyUtility;

public class HomeFragment extends Fragment {

    //private HomeViewModel homeViewModel;
    private LinearLayout dataDownload;
    private LinearLayout plotRecheck;
    private LinearLayout uploadSurvey;
    private AlertDialog dialog;
    private Context mContext;
    private View root;
    private TextView tvAppVersion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
       /* final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        JobUtil.scheduleJob(getContext());
        mContext = container.getContext();


        dataDownload = root.findViewById(R.id.data_download);
        plotRecheck = root.findViewById(R.id.plotReCheck);
        uploadSurvey = root.findViewById(R.id.upload_pending);

        plotRecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Navigation.findNavController(root).navigate(R.id.action_nav_services_to_plotRecheck);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        uploadSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Navigation.findNavController(root).navigate(R.id.action_nav_services_to_uploadpending);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dataDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Navigation.findNavController(root).navigate(R.id.action_nav_services_to_dataDownLoadFrag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tvAppVersion = root.findViewById(R.id.tv_appVersion);
        tvAppVersion.setText(SurveyUtility.getVersionName(requireActivity()));

        //  SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);

        /*String showDialog = CommonUtil.getPreferencesString(mContext, AppConstants.IS_USER_LOGIN);
        String user_id = CommonUtil.getPreferencesString(mContext, AppConstants.USER_ID);
        if (showDialog.equals("true"))
            if (user_id.equals("12345"))
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showUserDialog(mContext);
                    }
                }, 500);
            }*/
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) requireActivity())
                .setActionBarTitle(getString(R.string.services));

    }


    private void showUserDialog(Context context) {
        dialog = new AlertDialog.Builder(mContext).create();
        View factory = LayoutInflater.from(mContext).inflate(R.layout.customer_info_dialogbox, null);
        dialog.setView(factory);
        dialog.setCancelable(false);

        dialog.show();

/*      LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.customer_info_dialogbox, null);
      final Dialog dialog = new Dialog(this);
      dialog.setContentView(view);
      dialog.setCancelable(true);
      dialog.setCanceledOnTouchOutside(true);
      dialog.show();*/

        final EditText centreEDT = factory.findViewById(R.id.centre_edt_id);
        final EditText divisionEDT = factory.findViewById(R.id.division_edt_id);
        final EditText userNameEDT = factory.findViewById(R.id.userName_edt_id);

        String myCentre = CommonUtil.getPreferencesString(mContext, AppConstants.MYCENTER);
        if (!myCentre.isEmpty()) {
            centreEDT.setText(myCentre);
        }
        String myDivisn = CommonUtil.getPreferencesString(mContext, AppConstants.MYDIVISION);
        if (!myDivisn.isEmpty()) {
            divisionEDT.setText(myDivisn);
        }
        String myRole = CommonUtil.getPreferencesString(mContext, AppConstants.Role);
        if (!myRole.isEmpty()) {
            userNameEDT.setText(myRole);
        }
        Button save = factory.findViewById(R.id.save_info_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isValid(centreEDT, divisionEDT, userNameEDT);
                {
                    CommonUtil.saveStringPreferences(mContext, AppConstants.MYCENTER, centreEDT.getText().toString().trim());
                    CommonUtil.saveStringPreferences(mContext, AppConstants.MYDIVISION, divisionEDT.getText().toString().trim());
                    CommonUtil.saveStringPreferences(mContext, AppConstants.Role, userNameEDT.getText().toString().trim());


                    CommonUtil.toast("Save", mContext);

                    CommonUtil.saveStringPreferences(mContext, AppConstants.IS_USER_LOGIN, "false");

                    dialog.dismiss();
                }


            }
        });

    }

    private boolean isValid(EditText centreEDT, EditText divisionEDT, EditText userNameEDT) {
        if (centreEDT.getText().toString().trim().isEmpty()) {
            centreEDT.requestFocus();
            centreEDT.setText("enter centre");
            return false;
        }
        if (divisionEDT.getText().toString().trim().isEmpty()) {
            divisionEDT.requestFocus();
            divisionEDT.setText("enter division");
            return false;
        }

        if (userNameEDT.getText().toString().trim().isEmpty()) {
            userNameEDT.requestFocus();
            userNameEDT.setText("enter name");
            return false;
        }
        return true;
    }
}