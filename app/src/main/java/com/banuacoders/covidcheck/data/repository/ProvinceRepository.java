package com.banuacoders.covidcheck.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.banuacoders.covidcheck.data.dao.ProvinceDao;
import com.banuacoders.covidcheck.data.database.CovidDatabase;
import com.banuacoders.covidcheck.data.object.Province;

import java.util.List;

public class ProvinceRepository {
    private ProvinceDao provinceDao;
    private LiveData<List<Province>> allProvinces;

    public ProvinceRepository(Application application) {
        CovidDatabase database = CovidDatabase.getInstance(application);
        provinceDao = database.provinceDao();
        allProvinces = provinceDao.getAllProvince();
    }

    public void insert(Province province) {
        new InsertProvinceAsyncTask(provinceDao).execute(province);
    }

    public void update(Province province) {
        new UpdateProvinceAsyncTask(provinceDao).execute(province);
    }

    public void delete(Province province) {
        new DeleteProvinceAsyncTask(provinceDao).execute(province);
    }

    public void deleteAllProvince() {
        new DeleteAllProvinceAsyncTask(provinceDao).execute();
    }

    public LiveData<List<Province>> getAllProvinces() {
        return allProvinces;
    }

    private static class InsertProvinceAsyncTask extends AsyncTask<Province, Void, Void> {
        private ProvinceDao provinceDao;

        InsertProvinceAsyncTask(ProvinceDao provinceDao) {
            this.provinceDao = provinceDao;
        }

        @Override
        protected Void doInBackground(Province... provinces) {
            provinceDao.insert(provinces[0]);
            return null;
        }
    }

    private static class UpdateProvinceAsyncTask extends AsyncTask<Province, Void, Void> {
        private ProvinceDao provinceDao;

        UpdateProvinceAsyncTask(ProvinceDao provinceDao) {
            this.provinceDao = provinceDao;
        }

        @Override
        protected Void doInBackground(Province... provinces) {
            provinceDao.update(provinces[0]);
            return null;
        }
    }

    private static class DeleteProvinceAsyncTask extends AsyncTask<Province, Void, Void> {
        private ProvinceDao provinceDao;

        DeleteProvinceAsyncTask(ProvinceDao provinceDao) {
            this.provinceDao = provinceDao;
        }

        @Override
        protected Void doInBackground(Province... provinces) {
            provinceDao.delete(provinces[0]);
            return null;
        }
    }

    private static class DeleteAllProvinceAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProvinceDao provinceDao;

        DeleteAllProvinceAsyncTask(ProvinceDao provinceDao) {
            this.provinceDao = provinceDao;
        }

        @Override
        protected Void doInBackground(Void... provinces) {
            provinceDao.deleteAllProvince();
            return null;
        }
    }

}
