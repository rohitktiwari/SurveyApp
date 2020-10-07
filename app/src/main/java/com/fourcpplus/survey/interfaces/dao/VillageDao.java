package com.fourcpplus.survey.interfaces.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fourcpplus.survey.model.db.VillageData;

import java.util.List;

/**
 * Created by Rohit on 27-03-2020.
 */
@Dao
public interface VillageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVillage(VillageData... data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVillage(VillageData data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVillageList(List<VillageData> villageList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateVillage(VillageData data);

    @Query("Update Village SET plsrno = :serail WHERE vcode =  :villCode")
    void updateVillageSerial(String serail, String villCode);

    @Query("SELECT * from Village where vcode = :villCode")
    VillageData getVillageName(String villCode);

    @Query("Delete FROM Village")
    void deleteAll();

    @Query("Update Village SET villtype = :status WHERE vcode =  :villCode")
    void updateVillage(String villCode, String status);
}
