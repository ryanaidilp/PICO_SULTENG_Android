package com.banuacoders.covidcheck;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.banuacoders.covidcheck.adapter.ListMenuAdapter;
import com.banuacoders.covidcheck.network.NetworkClient;
import com.banuacoders.covidcheck.object.MenuItem;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rvMenu;
    private ArrayList<MenuItem> menus = new ArrayList<>();
    private TextView tvDate, tvPDPPercentage, tvODPFinishedPercentage, tvTotalPDP, tvTotalODP, tvDeath, tvPositive, tvNegative,
            tvInPDP, tvFinishPDP, tvInODP, tvFinishODP;
    private ImageView btnSync;
    int totalPositif = 0;
    int totalNegatif = 0;
    int totalMeninggal = 0;
    int totalODP = 0;
    int totalPDP = 0;
    private TextView tvPDPFinishedPercentage, tvODPPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponents();
        fetchDataKabupaten();
    }

    private void getComponents() {
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
        bind();
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
        setDateTime();
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateSync();
            }
        });
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

    private void fetchDataKabupaten() {
        final Handler dataHandler = new Handler();
        dataHandler.postDelayed(() -> {
            Call<ResponseBody> call = NetworkClient.getInstance()
                    .getApiKabupaten()
                    .getAllKabupaten();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseBody = new String(response.body().string());
                        JSONArray arrayKabupaten = new JSONArray(responseBody);
                        for (int i = 0; i < arrayKabupaten.length(); i++) {
                            totalMeninggal += arrayKabupaten.getJSONObject(i).getInt("meninggal");
                            totalPositif += arrayKabupaten.getJSONObject(i).getInt("positif");
                            totalNegatif += arrayKabupaten.getJSONObject(i).getInt("negatif");
                            totalODP += arrayKabupaten.getJSONObject(i).getInt("ODP");
                            totalPDP += arrayKabupaten.getJSONObject(i).getInt("PDP");
                        }
                        tvNegative.setText(String.valueOf(totalNegatif));
                        tvPositive.setText(String.valueOf(totalPositif));
                        tvDeath.setText(String.valueOf(totalMeninggal));
                        tvTotalODP.setText(String.valueOf(totalODP));
                        tvTotalPDP.setText(String.valueOf(totalPDP));
                        tvFinishPDP.setText("3");
                        tvInPDP.setText("9");
                        tvFinishODP.setText("0");
                        tvInODP.setText(String.valueOf(totalODP));
                        tvPDPPercentage.setText(percentageFormat(9, totalPDP));
                        tvODPPercentage.setText(percentageFormat(17, totalODP));
                        tvPDPFinishedPercentage.setText(percentageFormat(totalNegatif, totalPDP));
                        tvODPFinishedPercentage.setText(percentageFormat(0, totalODP));
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
}
