package com.fourcpplus.survey.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fourcpplus.survey.R;
import com.fourcpplus.survey.UserLoginActivity;
import com.fourcpplus.survey.utils.AppConstants;
import com.fourcpplus.survey.utils.CommonUtil;

public class LogOutFragment extends Fragment {

    private LogOutViewModel logOutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        CommonUtil.saveStringPreferences(requireActivity(), AppConstants.USER_ID,"");
        CommonUtil.saveStringPreferences(requireActivity(), AppConstants.USER_PASSWORD,"");
        Intent intent = new Intent(requireActivity(), UserLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        requireActivity().startActivity(intent);
        requireActivity().finish();
        return null;
    }
}