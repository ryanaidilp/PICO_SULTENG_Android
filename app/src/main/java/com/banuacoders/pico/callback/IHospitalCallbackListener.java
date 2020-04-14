package com.banuacoders.pico.callback;

import com.banuacoders.pico.data.model.Hospital;

import java.util.List;

public interface IHospitalCallbackListener {
    void onLoadHospitalSuccess(List<Hospital> hospitalList);

    void onLoadHospitalFailed(String message);
}
