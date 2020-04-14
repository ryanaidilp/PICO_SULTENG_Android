package com.banuacoders.pico.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.banuacoders.pico.data.dao.DataStatisticsDao;
import com.banuacoders.pico.data.dao.DistrictDao;
import com.banuacoders.pico.data.dao.HospitalDao;
import com.banuacoders.pico.data.dao.PostsDao;
import com.banuacoders.pico.data.dao.ProvinceDao;
import com.banuacoders.pico.data.model.DataStatisticsCovid;
import com.banuacoders.pico.data.model.District;
import com.banuacoders.pico.data.model.DistrictPost;
import com.banuacoders.pico.data.model.Hospital;
import com.banuacoders.pico.data.model.Province;

@Database(
        entities = {District.class, Province.class, Hospital.class, DataStatisticsCovid.class, DistrictPost.class},
        version = 4, exportSchema = false)
public abstract class CovidDatabase extends RoomDatabase {

    private static CovidDatabase instance;

    public abstract DistrictDao districtDao();

    public abstract ProvinceDao provinceDao();

    public abstract HospitalDao hospitalDao();

    public abstract PostsDao postsDao();

    public abstract DataStatisticsDao dataStatisticsDao();

    public static synchronized CovidDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CovidDatabase.class, "covid_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
