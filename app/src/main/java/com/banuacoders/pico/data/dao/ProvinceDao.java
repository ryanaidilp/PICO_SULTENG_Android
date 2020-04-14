package com.banuacoders.pico.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.banuacoders.pico.data.model.Province;

import java.util.List;

@Dao
public interface ProvinceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Province province);

    @Update
    void update(Province province);

    @Delete
    void delete(Province province);

    @Query("DELETE FROM Province")
    void deleteAllProvince();

    @Query("SELECT * FROM Province")
    LiveData<List<Province>> getAllProvince();
}
