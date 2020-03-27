package com.banuacoders.covidcheck.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.adapter.ListMenuAdapter;
import com.banuacoders.covidcheck.network.NetworkClient;
import com.banuacoders.covidcheck.data.object.District;
import com.banuacoders.covidcheck.data.object.MenuItem;
import com.banuacoders.covidcheck.ui.tableutil.MyTableViewListener;
import com.banuacoders.covidcheck.ui.tableutil.adapter.MyTableViewAdapter;
import com.banuacoders.covidcheck.data.viewmodel.DistrictViewModel;
import com.evrencoskun.tableview.TableView;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainActivity extends AppCompatActivity {

    private DistrictViewModel districtViewModel;

    private RecyclerView rvMenu;
    private ArrayList<MenuItem> menus = new ArrayList<>();
    private TextView tvDate, tvPDPPercentage, tvODPFinishedPercentage, tvTotalPDP, tvTotalODP, tvDeath, tvPositive, tvNegative,
            tvInPDP, tvFinishPDP, tvInODP, tvFinishODP;
    private ImageView btnSync;
    private TextView tvPDPFinishedPercentage, tvODPPercentage;
    private ScrollingPagerIndicator scrollingPagerIndicator;
    private TableView mTableView;
    private MyTableViewAdapter myTableViewAdapter;
    private MaterialCardView cardEmergency, cardHealthDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        districtViewModel = ViewModelProviders.of(this)
                .get(DistrictViewModel.class);
        getComponents();
        bind();
        if (districtViewModel.getAllDistricts() == null) {
            fetchDataCity();
        } else {
            districtViewModel.getAllDistricts().observe(this, districts ->
                    {
                        setDataDashboard(districts);
                        myTableViewAdapter.setData(districts);
                    }
            );
        }

    }

    private void getComponents() {
        mTableView = findViewById(R.id.data_table);
        tvDate = findViewById(R.id.information_date_value);
        btnSync = findViewById(R.id.btn_sync);
        rvMenu = findViewById(R.id.rv_menu);
        rvMenu.setHasFixedSize(true);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.CENTER);
        snapHelper.attachToRecyclerView(rvMenu);
        menus.addAll(getAllMenu());
        tvTotalODP = findViewById(R.id.odp_total_case_value);
        tvTotalPDP = findViewById(R.id.pdp_total_case_value);
        tvNegative = findViewById(R.id.negative_count);
        tvPositive = findViewById(R.id.positive_count);
        tvDeath = findViewById(R.id.dead_count);
        tvInPDP = findViewById(R.id.pdp_processed_value);
        tvInODP = findViewById(R.id.odp_processed_value);
        tvFinishODP = findViewById(R.id.odp_finished_value);
        tvFinishPDP = findViewById(R.id.pdp_finished_value);
        tvODPFinishedPercentage = findViewById(R.id.odp_finished_percentage);
        tvPDPFinishedPercentage = findViewById(R.id.pdp_finished_percentage);
        tvODPPercentage = findViewById(R.id.odp_processed_percentage);
        tvPDPPercentage = findViewById(R.id.pdp_processed_percentage);
        scrollingPagerIndicator = findViewById(R.id.indicator);
        cardEmergency = findViewById(R.id.card_number_119);
        cardHealthDepartment = findViewById(R.id.card_number_dinkes);
    }

    private ArrayList<MenuItem> getAllMenu() {
        String[] dataTitle = getResources().getStringArray(R.array.array_title_menu);
        String[] dataIcon = getResources().getStringArray(R.array.array_icon_menu);
        String[] dataDesc = getResources().getStringArray(R.array.array_desc_menu);
        ArrayList<MenuItem> listMenu = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            MenuItem menuItem = new MenuItem();
            menuItem.setTitle(dataTitle[i]);
            menuItem.setDesc(dataDesc[i]);
            menuItem.setIcon(getImage(dataIcon[i]));
            listMenu.add(menuItem);
        }
        return listMenu;
    }

    private void bind() {
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ListMenuAdapter adapter = new ListMenuAdapter(menus);
        rvMenu.setAdapter(adapter);
        scrollingPagerIndicator.attachToRecyclerView(rvMenu);
        setDateTime();
        btnSync.setOnClickListener(view -> {
            rotateSync();
            if (districtViewModel.getAllDistricts() == null) {
                fetchDataCity();
            } else {
                districtViewModel.deleteAllDistrict();
                fetchDataCity();
            }
        });
        initializeTable(mTableView);
        cardHealthDepartment.setOnClickListener(view -> onDialClickListener(R.id.dinkes_number));
        cardEmergency.setOnClickListener(view -> onDialClickListener(R.id.call_center_number));
    }

    private void initializeTable(TableView mTableView) {
        myTableViewAdapter = new MyTableViewAdapter(getApplicationContext());
        mTableView.setAdapter(myTableViewAdapter);
        mTableView.setTableViewListener(new MyTableViewListener(mTableView));
        mTableView.setVerticalFadingEdgeEnabled(true);
        mTableView.setHorizontalFadingEdgeEnabled(true);
    }

    void onDialClickListener(int id) {
        TextView tv = findViewById(id);
        String number = tv.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    void setDateTime() {
        final Handler dateHandler = new Handler();
        dateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentDate = java.text.DateFormat.getDateTimeInstance().format(new Date());
                tvDate.setText(currentDate);
                dateHandler.postDelayed(this, 1000);
            }
        }, 10);
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

    private int getImage(String imageName) {
        return this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
    }

    private void fetchDataCity() {
        final Handler dataHandler = new Handler();
        dataHandler.postDelayed(() -> {
            Call<ResponseBody> call = NetworkClient.getInstance()
                    .getApiCoder()
                    .getAllCity();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject objectResponse = new JSONObject(responseBody);
                        if (objectResponse.getBoolean("success")) {
                            JSONArray arrayDistrict = objectResponse.getJSONArray("data");
                            setDataDashboard(arrayDistrict);
                        } else {
                            Toast.makeText(MainActivity.this, objectResponse
                                            .getJSONObject("errors").getString("message"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Gagal mendapatkan data!", Toast.LENGTH_SHORT).show();
                }
            });

        }, 10);
    }

    private String percentageFormat(float num, int total) {
        DecimalFormat df = new DecimalFormat("#.##");
        float percentage = num / total * 100;
        return "(" + df.format(percentage) + "%)";
    }

    private void setDataDashboard(JSONArray arrayCity) {
        int totalPositive = 0;
        int totalNegative = 0;
        int totalDeath = 0;
        int totalODP = 0;
        int totalPDP = 0;
        int inODP = 0;
        int inPDP = 0;
        int finishedODP = 0;
        int finishedPDP = 0;
        for (int i = 0; i < arrayCity.length(); i++) {
            District district = null;
            try {
                district = new District(
                        arrayCity.getJSONObject(i).getInt("negatif"),
                        arrayCity.getJSONObject(i).getInt("no"),
                        arrayCity.getJSONObject(i).getInt("meninggal"),
                        arrayCity.getJSONObject(i).getInt("positif"),
                        arrayCity.getJSONObject(i).getInt("ODP"),
                        arrayCity.getJSONObject(i).getString("kabupaten"),
                        arrayCity.getJSONObject(i).getInt("PDP"),
                        arrayCity.getJSONObject(i).getInt("selesai_pengawasan"),
                        arrayCity.getJSONObject(i).getInt("selesai_pemantauan"),
                        arrayCity.getJSONObject(i).getInt("dalam_pemantauan"),
                        arrayCity.getJSONObject(i).getInt("dalam_pengawasan")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            districtViewModel.insert(district);
            totalDeath += district.getDeath();
            totalPositive += district.getPositive();
            totalNegative += district.getNegative();
            totalODP += district.getODP();
            totalPDP += district.getPDP();
            inPDP += district.getInPDP();
            inODP += district.getInODP();
            finishedPDP += district.getFinishedPDP();
            finishedODP += district.getFinishedODP();
        }
        tvNegative.setText(String.valueOf(totalNegative));
        tvPositive.setText(String.valueOf(totalPositive));
        tvDeath.setText(String.valueOf(totalDeath));
        tvTotalODP.setText(String.valueOf(totalODP));
        tvTotalPDP.setText(String.valueOf(totalPDP));
        tvFinishPDP.setText(String.valueOf(finishedPDP));
        tvInPDP.setText(String.valueOf(inPDP));
        tvFinishODP.setText(String.valueOf(finishedODP));
        tvInODP.setText(String.valueOf(inODP));
        tvPDPPercentage.setText(percentageFormat(inPDP, totalPDP));
        tvODPPercentage.setText(percentageFormat(inODP, totalODP));
        tvPDPFinishedPercentage.setText(percentageFormat(finishedPDP, totalPDP));
        tvODPFinishedPercentage.setText(percentageFormat(finishedODP, totalODP));
    }

    private void setDataDashboard(List<District> districtList) {
        int totalPositive = 0;
        int totalNegative = 0;
        int totalDeath = 0;
        int totalODP = 0;
        int totalPDP = 0;
        int inODP = 0;
        int inPDP = 0;
        int finishedODP = 0;
        int finishedPDP = 0;
        for (int i = 0; i < districtList.size(); i++) {

            totalDeath += districtList.get(i).getDeath();
            totalPositive += districtList.get(i).getPositive();
            totalNegative += districtList.get(i).getNegative();
            totalODP += districtList.get(i).getODP();
            totalPDP += districtList.get(i).getPDP();
            inPDP += districtList.get(i).getInPDP();
            inODP += districtList.get(i).getInODP();
            finishedPDP += districtList.get(i).getFinishedPDP();
            finishedODP += districtList.get(i).getFinishedODP();
        }
        tvNegative.setText(String.valueOf(totalNegative));
        tvPositive.setText(String.valueOf(totalPositive));
        tvDeath.setText(String.valueOf(totalDeath));
        tvTotalODP.setText(String.valueOf(totalODP));
        tvTotalPDP.setText(String.valueOf(totalPDP));
        tvFinishPDP.setText(String.valueOf(finishedPDP));
        tvInPDP.setText(String.valueOf(inPDP));
        tvFinishODP.setText(String.valueOf(finishedODP));
        tvInODP.setText(String.valueOf(inODP));
        tvPDPPercentage.setText(percentageFormat(inPDP, totalPDP));
        tvODPPercentage.setText(percentageFormat(inODP, totalODP));
        tvPDPFinishedPercentage.setText(percentageFormat(finishedPDP, totalPDP));
        tvODPFinishedPercentage.setText(percentageFormat(finishedODP, totalODP));
    }
}
