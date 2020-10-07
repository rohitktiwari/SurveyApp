package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fourcpplus.survey.model.db.SurveyData;

import java.util.Date;
import java.util.List;

/**
 * Created by Rohit on 25-04-2020.
 */
@Dao
public interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSurvey(SurveyData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSurvey(SurveyData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSurveyList(List<SurveyData> data);

    @Query("Update Survey SET status = :status WHERE PLVsno =  :serial")
    void updateSurvey(String status, int serial);

    @Query("Delete FROM Survey")
    void deleteAll();

    @Query("SELECT * FROM Survey where date= :date ORDER BY date ASC")
    List<SurveyData> getSurveyData(Date date);

    @Query("SELECT * FROM Survey where date<= :date AND PLGastino= :plotVill ORDER BY date ASC")
    List<SurveyData> getSurveyData(Date date, String plotVill);

    @Query("SELECT * FROM Survey where PLVsno= :serial AND PLGastino= :plotVill")
    SurveyData getSurveyDatabySerial(String serial,String plotVill);

    @Query("SELECT * FROM Survey where status= :status")
    List<SurveyData> getPendingSurveyData(String status);

    @Query("SELECT * FROM Survey where date BETWEEN :fromDate AND :tillDate AND PLGastino = :plotVill AND PLVsno = :serial AND status= :status")
    List<SurveyData> getPendingSurveyData(Date fromDate, Date tillDate, String plotVill, String serial, String status);

    @Query("SELECT * FROM Survey where date BETWEEN :fromDate AND :tillDate AND status= :status ")
    List<SurveyData> getPendingSurveyData(Date fromDate, Date tillDate, String status);

    @Query("SELECT * FROM Survey where date BETWEEN :fromDate AND :tillDate AND PLGastino= :plotVill AND status= :status")
    List<SurveyData> getPendingSurveyData(Date fromDate, Date tillDate, String plotVill, String status);

    @Query("SELECT MAX(PLVsno) from Survey where PLGastino= :plotVill")
    String getMaxSerialNo(String plotVill);


}
