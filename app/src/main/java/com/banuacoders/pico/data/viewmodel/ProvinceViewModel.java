package com.banuacoders.pico.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.object.Province;
import com.banuacoders.pico.data.repository.ProvinceRepository;

import java.util.List;

public class ProvinceViewModel extends AndroidViewModel {
    private ProvinceRepository repository;
    private LiveData<List<Province>> allProvinces;

    public ProvinceViewModel(@NonNull Application application) {
        super(application);
        repository = new ProvinceRepository(application);
        allProvinces = repository.getAllProvinces();
    }

    public void insert(Province province) {
        repository.insert(province);
    }

    public void update(Province province) {
        repository.update(province);
    }

    public void delete(Province province) {
        repository.delete(province);
    }

    public void deleteAllProvinces() {
        repository.deleteAllProvince();
    }

    public LiveData<List<Province>> getAllProvinces() {
        return allProvinces;
    }
}
