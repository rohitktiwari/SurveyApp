package com.fourcpplus.survey.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.fourcpplus.survey.model.db.SurveyData;

import java.util.List;

/**
 * Created by Rohit on 26-04-2020.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SurveyJobService extends JobService {
    private static final String TAG = "SurveyJobService";

    @Override
    public boolean onStartJob(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Job Started:" + System.currentTimeMillis());
                try {
                    Looper.prepare();

                    List<SurveyData> surveyDataList = SurveyUtility.getPendingSurveys(getApplicationContext());
                    if (surveyDataList != null && surveyDataList.size() != 0) {
                        for (SurveyData data : surveyDataList) {
                            SurveyUtility.submitFormDataWithImage(getApplicationContext(), data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jobFinished(params, true);
                Log.d(TAG, "Job Finished");
            }
        }).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Sync Job Failed");
        return true;
    }
}
