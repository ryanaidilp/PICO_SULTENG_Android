package com.banuacoders.pico.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.banuacoders.pico.data.model.DataStatisticsCovid;

import java.util.List;

@Dao
public interface DataStatisticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DataStatisticsCovid dataStatisticsCovid);

    @Query("DELETE FROM data_statistic")
    void deleteAllData();

    @Query("SELECT * FROM data_statistic")
    LiveData<List<DataStatisticsCovid>> getAllData();
}
