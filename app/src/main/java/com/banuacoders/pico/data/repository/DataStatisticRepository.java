package com.banuacoders.pico.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.dao.DataStatisticsDao;
import com.banuacoders.pico.data.database.CovidDatabase;
import com.banuacoders.pico.data.object.DataStatisticsCovid;

import java.util.List;

public class DataStatisticRepository {
    private DataStatisticsDao dataStatisticsDao;
    private LiveData<List<DataStatisticsCovid>> allDataStatistics;

    public DataStatisticRepository(Application application) {
        dataStatisticsDao = CovidDatabase.getInstance(application)
                .dataStatisticsDao();
        allDataStatistics = dataStatisticsDao.getAllData();
    }

    public void insert(DataStatisticsCovid dataStatisticsCovid) {
        new InsertDataStatisticAsyncTask(dataStatisticsDao).execute(dataStatisticsCovid);
    }

    public void deleteAllData() {
        new DeleteAllDataStatisticAsyncTask(dataStatisticsDao).execute();
    }

    public LiveData<List<DataStatisticsCovid>> getAllDataStatistics() {
        return allDataStatistics;
    }

    private static class InsertDataStatisticAsyncTask extends AsyncTask<DataStatisticsCovid, Void, Void> {
        private DataStatisticsDao dataStatisticsDao;

        InsertDataStatisticAsyncTask(DataStatisticsDao dataStatisticsDao) {
            this.dataStatisticsDao = dataStatisticsDao;
        }

        @Override
        protected Void doInBackground(DataStatisticsCovid... dataStatisticsCovids) {
            dataStatisticsDao.insert(dataStatisticsCovids[0]);
            return null;
        }
    }

    private static class DeleteAllDataStatisticAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataStatisticsDao dataStatisticsDao;

        DeleteAllDataStatisticAsyncTask(DataStatisticsDao dataStatisticsDao) {
            this.dataStatisticsDao = dataStatisticsDao;
        }

        @Override
        protected Void doInBackground(Void... dataStatisticsCovids) {
            dataStatisticsDao.deleteAllData();
            return null;
        }
    }

}
