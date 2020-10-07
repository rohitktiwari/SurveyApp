package com.fourcpplus.survey.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.fourcpplus.survey.interfaces.dao.ControlDao;
import com.fourcpplus.survey.interfaces.dao.GrowerDao;
import com.fourcpplus.survey.interfaces.dao.MDPIDao;
import com.fourcpplus.survey.interfaces.dao.ReprintDao;
import com.fourcpplus.survey.interfaces.dao.SurveyDao;
import com.fourcpplus.survey.interfaces.dao.VarietyDao;
import com.fourcpplus.survey.interfaces.dao.VillageDao;
import com.fourcpplus.survey.model.db.ControlData;
import com.fourcpplus.survey.model.db.GrowerData;
import com.fourcpplus.survey.model.db.MDPIData;
import com.fourcpplus.survey.model.db.ReprintData;
import com.fourcpplus.survey.model.db.SurveyData;
import com.fourcpplus.survey.model.db.VarietyData;
import com.fourcpplus.survey.model.db.VillageData;
import com.fourcpplus.survey.utils.Converters;

/**
 * Created by Rohit on 27-03-2020.
 */
@Database(entities = {VillageData.class, VarietyData.class, ControlData.class, GrowerData.class, MDPIData.class, SurveyData.class, ReprintData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SurveyDatabase extends RoomDatabase {

    private static final String DB_NAME = "survey.db";
    public static SurveyDatabase instance;

    public static SurveyDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (SurveyDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), SurveyDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract VillageDao villageDao();

    public abstract GrowerDao growerDao();

    public abstract MDPIDao mdpiDao();

    public abstract ControlDao controlDao();

    public abstract VarietyDao varietyDao();

    public abstract SurveyDao surveyDao();
    public abstract ReprintDao reprintDao();

}
