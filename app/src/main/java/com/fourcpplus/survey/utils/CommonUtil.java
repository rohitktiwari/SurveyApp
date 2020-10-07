package com.fourcpplus.survey.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fourcpplus.survey.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommonUtil {

    public static String HHC_MST_StatusSTR = "null";

    public static String getPreferencesString(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public static void saveStringPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    @SuppressWarnings({"MissingPermission"})
    public static String getDeviceIMEI(Context context) {

        String deviceUniqueIdentifier = "";
        try{
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            try {
                deviceUniqueIdentifier = tm.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }}catch (Exception e){
            e.printStackTrace();
        }
        return deviceUniqueIdentifier;
    }


    public static void toast(String message, Context context) {

        // Call toast.xml file for toast layout
        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);

        TextView tvMsg = view.findViewById(R.id.CostumText);
        tvMsg.setText(message);
        final Toast toast = new Toast(context);
        // Set layout to toast
        toast.setView(view);
        toast.setGravity(
                Gravity.CENTER_VERTICAL | Gravity.BOTTOM,
                0, 300
        );
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }




    public static String finalprintDataUpdated(String value) {

        Log.i("CONVERT", "finalprintData: datatatata before=" + value);

        String result = "";


        try {

            if (value == null || value.length() == 0) {
                return result;

            }

            if (value.length() <= 23) {
                result = value;

            }

            if (value.length() > 23) {
                result = value.substring(0, 23);

            }

        } catch (Exception e) {
            Log.e("Error", "finalprintDataUpdated: datatatata before=" + e.getMessage());

        }

        return result;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

   /* public static void updateRealmDataBase(final String returnTxID)
    {

        Realm srealm = null;
        srealm = Realm.getDefaultInstance();

        try {
            srealm = Realm.getDefaultInstance();
            srealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmStorageModelClass obj = realm.where(RealmStorageModelClass.class).equalTo("txid", returnTxID).findFirst();

                    if (obj != null) {
                        obj.setOnlineStatus("1");
                        realm.insertOrUpdate(obj);
                        Log.i("realm", "execute: servidmdmgfdmgfhklhklhklhklhklhklhklhklhklhklhklhklhklhklhklhklhkl");
                    }

                }
            });


        }catch (Exception e)
        {
            Log.i("service", "updateRealmDataBase: "+e.getLocalizedMessage());
        }finally {
            if(srealm != null) {
                srealm.close();
            }
        }
    }
*/


    @TargetApi(Build.VERSION_CODES.N)
    public static String validTare(String validTareSlip, String getGrossDate_STR, String fromWhere) {


        try {
            Date date;

            if (fromWhere.equals("online"))
                date = new SimpleDateFormat("yyyy-MM-dd").parse(getGrossDate_STR);
            else
                date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(getGrossDate_STR);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            //Gross Date
            String grossDateS = sdf.format(c.getTime());
            Date grossDate = sdf.parse(grossDateS);


            if (!validTareSlip.equals("0")) {

                c.add(Calendar.DATE, Integer.parseInt(validTareSlip)); // Adding  days
                String output = sdf.format(c.getTime());
                Date strDate = sdf.parse(output);

                Log.i("DTIME", "validTare: validTareSlip=" + validTareSlip);
                Log.i("DTIME", "validTare: output=" + output);
                Log.i("DTIME", "validTare: System.currentTimeMillis()=" + System.currentTimeMillis());
                Log.i("DTIME", "validTare: strDate.getTime()=" + strDate.getTime());
                Log.i("DTIME", "validTare: strDate.getTime() >= System.currentTimeMillis()=" + (strDate.getTime() > System.currentTimeMillis()));
                Log.i("DTIME", "validTare: new Date().after(strDate)=" + (new Date().after(strDate)));
                Log.i("DTIME", "validTare: strDate.after(new Date())=" + (strDate.after(new Date())));
                Log.i("DTIME", "validTare: new Date().after(grossDate)=" + new Date().after(grossDate));

                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(new Date());

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(strDate);

                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

                Log.i("DTIME", "validTare: sameDay=" + sameDay);

                if ((strDate.after(new Date())||sameDay)&&(new Date().after(grossDate))) {
                    return "yes";
                } else return "no";

               /* if (strDate.getTime() > System.currentTimeMillis()) {
                    return "yes";
                } else return "no";*/

            } else {

                String GrossDate_STR = sdf.format(c.getTime());

                Calendar cal = Calendar.getInstance();
                String currentDate = sdf.format(cal.getTime());


                Log.i("DTIME", "validTare:GrossDate_STR=" + GrossDate_STR);
                Log.i("DTIME", "validTare: currentDate=" + currentDate);
                Log.i("DTIME", "validTare: currentDate= GrossDate_STR->" + (currentDate.equals(GrossDate_STR)));

                if (currentDate.equals(GrossDate_STR)) {
                    return "yes";
                } else return "no";
            }// Now use today date.


        } catch (ParseException e) {
            Log.i("DTIME", "validTare: Erroror=" + e.getMessage());
            e.printStackTrace();
        }
        return "no";
    }


}
