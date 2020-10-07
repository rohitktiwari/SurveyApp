package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fourcpplus.survey.model.db.VarietyData;

import java.util.List;

/**
 * Created by Rohit on 27-03-2020.
 */
@Dao
public interface VarietyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVariety(VarietyData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVariety(VarietyData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVarietyList(List<VarietyData> data);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateVariety(VarietyData data);

    @Query("SELECT * from Variety where varcode = :varcode")
    VarietyData getVarietyDetails(String varcode);

    @Query("Delete FROM Variety")
    void deleteAll();
}
