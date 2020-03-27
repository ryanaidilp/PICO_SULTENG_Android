package com.banuacoders.covidcheck.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.banuacoders.covidcheck.data.dao.DataStatisticsDao;
import com.banuacoders.covidcheck.data.dao.DistrictDao;
import com.banuacoders.covidcheck.data.dao.HospitalDao;
import com.banuacoders.covidcheck.data.dao.ProvinceDao;
import com.banuacoders.covidcheck.data.object.DataStatisticsCovid;
import com.banuacoders.covidcheck.data.object.District;
import com.banuacoders.covidcheck.data.object.Hospital;
import com.banuacoders.covidcheck.data.object.Province;

@Database(
        entities = {District.class, Province.class, Hospital.class, DataStatisticsCovid.class},
        version = 3)
public abstract class CovidDatabase extends RoomDatabase {

    private static CovidDatabase instance;

    public abstract DistrictDao districtDao();

    public abstract ProvinceDao provinceDao();

    public abstract HospitalDao hospitalDao();

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
