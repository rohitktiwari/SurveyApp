package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fourcpplus.survey.model.db.ReprintData;

import java.util.List;

/**
 * Created by Rohit on 25-04-2020.
 */
@Dao
public interface ReprintDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReprint(ReprintData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReprint(ReprintData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReprintList(List<ReprintData> data);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateReprint(ReprintData data);

    @Query("Delete FROM Reprint")
    void deleteAll();

    @Query("SELECT * FROM Reprint WHERE plSerial= :serial AND date = :date AND plotVill= :plotVill")
    ReprintData getReprintData(int serial, String date, String plotVill);
}
