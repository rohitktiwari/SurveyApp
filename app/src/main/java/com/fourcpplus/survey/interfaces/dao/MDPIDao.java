package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fourcpplus.survey.model.db.MDPIData;

import java.util.List;

/**
 * Created by Rohit on 27-03-2020.
 */

@Dao
public interface MDPIDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMDPI(MDPIData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMDPI(MDPIData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMDPIList(List<MDPIData> data);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateMDPI(MDPIData data);

    @Query("SELECT * from MDPI where mtype = :mtype and mcode = :mcode")
    MDPIData getMDPIDetails(String mtype, String mcode);

    @Query("Delete FROM MDPI")
    void deleteAll();
}
