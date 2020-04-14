package com.banuacoders.pico.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.banuacoders.pico.data.model.District;

import java.util.List;

@Dao
public interface DistrictDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(District district);

    @Update
    void update(District district);

    @Delete
    void delete(District district);

    @Query("DELETE FROM District")
    void deleteAllDistrict();

    @Query("SELECT * FROM District")
    LiveData<List<District>> getAllDistrict();
}
