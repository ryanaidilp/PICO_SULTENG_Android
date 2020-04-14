package com.banuacoders.pico.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.dao.DistrictDao;
import com.banuacoders.pico.data.database.CovidDatabase;
import com.banuacoders.pico.data.model.District;

import java.util.List;

public class DistrictRepository {
    private DistrictDao districtDao;
    private LiveData<List<District>> allDistricts;

    public DistrictRepository(Application application) {
        CovidDatabase database = CovidDatabase.getInstance(application);
        districtDao = database.districtDao();
        allDistricts = districtDao.getAllDistrict();
    }

    public void insert(District district) {
        new InsertDistrictAsyncTask(districtDao).execute(district);
    }

    public void update(District district) {
        new UpdateDistrictAsyncTask(districtDao).execute(district);
    }

    public void delete(District district) {
        new UpdateDistrictAsyncTask.DeleteDistrictAsyncTask(districtDao).execute(district);
    }

    public void deleteAllDistrict() {
        new DeleteAllDistrictsAsyncTask(districtDao).execute();
    }

    public LiveData<List<District>> getAllDistricts() {
        return allDistricts;
    }

    private static class InsertDistrictAsyncTask extends AsyncTask<District, Void, Void> {
        private DistrictDao districtDao;

        private InsertDistrictAsyncTask(DistrictDao districtDao) {
            this.districtDao = districtDao;
        }

        @Override
        protected Void doInBackground(District... districts) {
            districtDao.insert(districts[0]);
            return null;
        }
    }

    private static class UpdateDistrictAsyncTask extends AsyncTask<District, Void, Void> {
        private DistrictDao districtDao;

        private UpdateDistrictAsyncTask(DistrictDao districtDao) {
            this.districtDao = districtDao;
        }

        @Override
        protected Void doInBackground(District... districts) {
            districtDao.update(districts[0]);
            return null;
        }

        private static class DeleteDistrictAsyncTask extends AsyncTask<District, Void, Void> {
            private DistrictDao districtDao;

            private DeleteDistrictAsyncTask(DistrictDao districtDao) {
                this.districtDao = districtDao;
            }

            @Override
            protected Void doInBackground(District... districts) {
                districtDao.delete(districts[0]);
                return null;
            }
        }
    }

    private static class DeleteAllDistrictsAsyncTask extends AsyncTask<Void, Void, Void> {
        private DistrictDao districtDao;

        private DeleteAllDistrictsAsyncTask(DistrictDao districtDao) {
            this.districtDao = districtDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            districtDao.deleteAllDistrict();
            return null;
        }
    }

}
