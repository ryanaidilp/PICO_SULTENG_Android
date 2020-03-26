package com.banuacoders.covidcheck;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.covidcheck.adapter.HospitalAdapter;
import com.banuacoders.covidcheck.network.NetworkClient;
import com.banuacoders.covidcheck.object.Hospital;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHospital extends AppCompatActivity {

    private RecyclerView rvHospital;
    private HospitalAdapter hospitalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        rvHospital = findViewById(R.id.rv_hospital);
        rvHospital.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Hospital> hospitals = new ArrayList<>();
        Call<ResponseBody> call = NetworkClient.getInstance()
                .getApiCoder().getAllHospital();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    if (objectResponse.getBoolean("success")) {
                        JSONArray arrayHospital = objectResponse.getJSONArray("data");
                        for (int i = 0; i < arrayHospital.length(); i++) {
                            Hospital hospital = new Hospital();
                            hospital.setName(arrayHospital.getJSONObject(i).getString("nama"));
                            hospital.setAddress(arrayHospital.getJSONObject(i).getString("alamat"));
                            hospital.setTelephone(arrayHospital.getJSONObject(i).getString("telepon"));
                            hospital.setEmail(arrayHospital.getJSONObject(i).getString("email"));
                            hospital.setLatitude(arrayHospital.getJSONObject(i).getString("latitude"));
                            hospital.setLongitude(arrayHospital.getJSONObject(i).getString("longitude"));
                            hospitals.add(hospital);
                        }
                        hospitalAdapter = new HospitalAdapter(hospitals, getApplicationContext());
                        rvHospital.setHasFixedSize(true);
                        rvHospital.setAdapter(hospitalAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), objectResponse.getJSONObject("errors").getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
