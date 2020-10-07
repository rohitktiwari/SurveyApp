package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fourcpplus.survey.model.db.GrowerData;

import java.util.List;

/**
 * Created by Rohit on 27-03-2020.
 */

@Dao
public interface GrowerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGrower(GrowerData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGrower(GrowerData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGrowerList(List<GrowerData> data);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateGrower(GrowerData data);

    @Query("SELECT * from Grower where gvill = :gvill and gno = :gno")
    GrowerData getGrowerDetails(String gvill, String gno);

    @Query("Delete FROM Grower")
    void deleteAll();
}
