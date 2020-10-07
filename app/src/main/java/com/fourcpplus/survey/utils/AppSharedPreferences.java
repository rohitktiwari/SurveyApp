package com.fourcpplus.survey.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rohit on 04-04-2020.
 */
public class AppSharedPreferences {
    private static final String PREF_NAME = "SurveyApp";
    private static final String SERIAL_NO = "serial_no";
    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Context _context;
    private static int PRIVATE_MODE = 0;
    @SuppressLint("StaticFieldLeak")
    private static AppSharedPreferences uniqInstance;

    public static synchronized AppSharedPreferences getInstance(Context context) {
        if (uniqInstance == null) {
            _context = context;
            sSharedPreferences = _context.getSharedPreferences(PREF_NAME,
                    PRIVATE_MODE);
            editor = sSharedPreferences.edit();
            editor.apply();
            uniqInstance = new AppSharedPreferences();
        }
        return uniqInstance;
    }

    public int getSerialNo() {
        return sSharedPreferences.getInt(SERIAL_NO, 1);
    }

    public void setSerialNo(int serialno) {
        editor.putInt(SERIAL_NO, serialno);
        editor.commit();
    }
}
