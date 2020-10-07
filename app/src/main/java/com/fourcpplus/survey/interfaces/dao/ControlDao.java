package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fourcpplus.survey.model.db.ControlData;

import java.util.List;

/**
 * Created by Rohit on 27-03-2020.
 */
@Dao
public interface ControlDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertControl(ControlData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertControl(ControlData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertControlList(List<ControlData> data);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateControl(ControlData data);

    @Query("Delete FROM Control")
    void deleteAll();

    @Query("SELECT * from Control")
    ControlData getControlDetails();
}
