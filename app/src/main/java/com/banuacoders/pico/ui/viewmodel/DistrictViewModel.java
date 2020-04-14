package com.banuacoders.pico.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.model.District;
import com.banuacoders.pico.data.repository.DistrictRepository;

import java.util.List;

public class DistrictViewModel extends AndroidViewModel {
    private DistrictRepository repository;
    private LiveData<List<District>> allDistricts;

    public DistrictViewModel(@NonNull Application application) {
        super(application);
        repository = new DistrictRepository(application);
        allDistricts = repository.getAllDistricts();
    }

    public void insert(District district) {
        repository.insert(district);
    }

    public void update(District district) {
        repository.update(district);
    }

    public void delete(District district) {
        repository.delete(district);
    }

    public void deleteAllDistrict() {
        repository.deleteAllDistrict();
    }

    public LiveData<List<District>> getAllDistricts() {
        return allDistricts;
    }

}
