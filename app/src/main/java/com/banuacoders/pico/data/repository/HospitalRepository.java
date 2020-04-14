package com.banuacoders.pico.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.dao.HospitalDao;
import com.banuacoders.pico.data.database.CovidDatabase;
import com.banuacoders.pico.data.model.Hospital;

import java.util.List;

public class HospitalRepository {
    private HospitalDao hospitalDao;
    private LiveData<List<Hospital>> allHospitals;

    public HospitalRepository(Application application) {
        hospitalDao = CovidDatabase.getInstance(application)
                .hospitalDao();
        allHospitals = hospitalDao.getAllHospital();
    }

    public void insert(Hospital hospital) {
        new InsertHospitalAsyncTask(hospitalDao).execute(hospital);
    }

    public void deleteAllHospital() {
        new DeleteAllHospitalAsyncTask(hospitalDao).execute();
    }

    public LiveData<List<Hospital>> getAllHospitals() {
        return allHospitals;
    }

    private static class InsertHospitalAsyncTask extends AsyncTask<Hospital, Void, Void> {
        private HospitalDao hospitalDao;

        InsertHospitalAsyncTask(HospitalDao hospitalDao) {
            this.hospitalDao = hospitalDao;
        }

        @Override
        protected Void doInBackground(Hospital... hospitals) {
            hospitalDao.insert(hospitals[0]);
            return null;
        }
    }

    private static class DeleteAllHospitalAsyncTask extends AsyncTask<Void, Void, Void> {
        private HospitalDao hospitalDao;

        DeleteAllHospitalAsyncTask(HospitalDao hospitalDao) {
            this.hospitalDao = hospitalDao;
        }

        @Override
        protected Void doInBackground(Void... hospitals) {
            hospitalDao.deleteAllHospital();
            return null;
        }
    }

}
