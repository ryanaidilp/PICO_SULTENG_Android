package com.banuacoders.pico.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.pico.R;
import com.banuacoders.pico.adapter.HospitalAdapter;
import com.banuacoders.pico.data.object.Hospital;
import com.banuacoders.pico.data.viewmodel.HospitalViewModel;
import com.banuacoders.pico.network.NetworkClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHospital extends AppCompatActivity implements LifecycleOwner {

    private HospitalAdapter hospitalAdapter;
    HospitalViewModel hospitalViewModel;
    ImageView btnSync;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        RecyclerView rvHospital = findViewById(R.id.rv_hospital);
        btnSync = findViewById(R.id.btn_sync);
        progressBar = findViewById(R.id.progress_hospital);
        hospitalAdapter = new HospitalAdapter(this);
        rvHospital.setLayoutManager(new LinearLayoutManager(this));
        rvHospital.setAdapter(hospitalAdapter);
        hospitalViewModel = ViewModelProviders.of(this)
                .get(HospitalViewModel.class);
        if (hospitalViewModel.getAllHospital() == null) {
            getData();
        } else {
            hospitalViewModel.getAllHospital().observe(this, hospitals -> {
                progressBar.setVisibility(View.GONE);
                ArrayList<Hospital> hospitalArrayList = new ArrayList<>(hospitals);
                hospitalAdapter.setListHospital(hospitalArrayList);
                hospitalAdapter.notifyDataSetChanged();
            });
        }
        btnSync.setOnClickListener(view -> {
            rotateSync();
            if (hospitalViewModel.getAllHospital() != null) {
                hospitalViewModel.deleteAllHospital();
            }
            getData();
        });
    }

    private void getData() {
        Call<ResponseBody> call = NetworkClient.getInstance()
                .getApiCoder().getAllHospital();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    if (objectResponse.getBoolean("success")) {
                        JSONArray arrayHospital = objectResponse.getJSONArray("data");
                        for (int i = 0; i < arrayHospital.length(); i++) {
                            Hospital hospital = new Hospital(
                                    arrayHospital.getJSONObject(i).getInt("no"),
                                    arrayHospital.getJSONObject(i).getString("nama"),
                                    arrayHospital.getJSONObject(i).getString("latitude"),
                                    arrayHospital.getJSONObject(i).getString("telepon"),
                                    arrayHospital.getJSONObject(i).getString("email"),
                                    arrayHospital.getJSONObject(i).getString("alamat"),
                                    arrayHospital.getJSONObject(i).getString("longitude")
                            );
                            hospitalViewModel.insert(hospital);
                        }
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                objectResponse.getJSONObject("errors").getString("message"),
                                Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityHospital.this, "Gagal mendapatkan data! : "
                        + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void rotateSync() {
        int mCurrRotation = 0;
        float fromRotation = mCurrRotation;
        float toRotation = mCurrRotation -= 180;

        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, btnSync.getWidth() / 2, btnSync.getHeight() / 2);

        rotateAnim.setDuration(1000); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        btnSync.startAnimation(rotateAnim);
    }
}
