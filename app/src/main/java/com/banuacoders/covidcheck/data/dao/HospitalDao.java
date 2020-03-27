package com.banuacoders.covidcheck.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.banuacoders.covidcheck.data.object.Hospital;

import java.util.List;

@Dao
public interface HospitalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hospital hospital);

    @Query("DELETE FROM Hospital")
    void deleteAllHospital();

    @Query("SELECT * FROM Hospital")
    LiveData<List<Hospital>> getAllHospital();
}
