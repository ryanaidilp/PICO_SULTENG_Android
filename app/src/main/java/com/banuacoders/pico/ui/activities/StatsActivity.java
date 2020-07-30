package com.banuacoders.pico.ui.activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.DataStatisticsCovid;
import com.banuacoders.pico.network.NetworkClient;
import com.banuacoders.pico.ui.viewmodel.DataStatisticViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {

    private DataStatisticViewModel dataStatisticViewModel;
    private LineChart lineChartDeath, lineChartPositive, lineChartCured;
    private PieChart pieChartCorona;
    private ProgressBar progressBar;
    private ImageView btnSync;
    private TextView tvTotalDeath, tvTotalPositive, tvTotalCured, tvDate;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        res = getApplicationContext().getResources();
        dataStatisticViewModel = ViewModelProviders.of(this)
                .get(DataStatisticViewModel.class);
        checkComponent();
        if (dataStatisticViewModel.getAllDataStatistics() == null) {
            fetchDataStatistics();
        } else {
            dataStatisticViewModel.getAllDataStatistics().observe(this, this::setData);
        }
        btnSync.setOnClickListener(view -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                rotateSync();
                if (dataStatisticViewModel.getAllDataStatistics() != null) {
                    dataStatisticViewModel.deleteAllData();
                }
                fetchDataStatistics();
            }, 10);
        });
    }


    private void checkComponent() {
        progressBar = findViewById(R.id.progress_bar_statistics);
        pieChartCorona = findViewById(R.id.pie_chart_corona);
        lineChartPositive = findViewById(R.id.line_chart_positive);
        lineChartDeath = findViewById(R.id.line_chart_death);
        lineChartCured = findViewById(R.id.line_chart_cured);
        btnSync = findViewById(R.id.btn_sync);
        tvTotalCured = findViewById(R.id.value_total_cured);
        tvTotalDeath = findViewById(R.id.value_total_death);
        tvTotalPositive = findViewById(R.id.value_total_positive);
        tvDate = findViewById(R.id.date);
    }

    private void fetchDataStatistics() {
        Call<ResponseBody> call = NetworkClient
                .getInstance()
                .getApiCoder()
                .getCovidStats();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    JSONArray features = objectResponse.getJSONArray("data");
                    setData(features);
                    progressBar.setVisibility(View.GONE);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StatsActivity.this, "", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLineDataDecoration(LineChart lineChart, String text, int colorCode, LineData lineData) {
        String dataLabel = res.getString(R.string.data_unavailable);
        String dayLabel = res.getString(R.string.day);
        String description = res.getString(R.string.data_stats_helper, text);
        lineChart.setNoDataText(dataLabel);
        lineChart.setNoDataTextColor(getResources().getColor(colorCode));
        lineChart.setData(lineData);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dayLabel + (int) value;
            }

        });
        lineChart.getDescription().setText(description);
        lineChart.invalidate();
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

    private void setData(JSONArray features) {
        for (int i = 0; i < features.length(); i++) {
            try {
                JSONObject data = features.getJSONObject(i);
                JSONObject cumulative = data.getJSONObject("kumulatif");
                JSONObject recap = data.getJSONObject("rekap");
                JSONObject newCases = data.getJSONObject("kasus_baru");
                double deathPercentage = recap
                        .getJSONObject("persentase")
                        .getDouble("meninggal");
                double underTreatmentPercentage = recap
                        .getJSONObject("persentase")
                        .getDouble("dalam_perawatan");
                double curedPercentage = recap
                        .getJSONObject("persentase")
                        .getDouble("sembuh");

                int totalDeath = cumulative
                        .getInt("meninggal");
                int totalPatientInTreatment = cumulative
                        .getInt("dalam_perawatan");
                int cumulativeCase = cumulative
                        .getInt("positif");
                int totalCured = cumulative
                        .getInt("sembuh");

                int totalNewCase = newCases
                        .getInt("positif");
                String[] latestUpdate = data.getString("tanggal").split(" ");
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(latestUpdate[0]);

                DataStatisticsCovid dataStatisticsCovid = new DataStatisticsCovid(
                        data.getInt("hari_ke"),
                        deathPercentage,
                        totalDeath,
                        totalPatientInTreatment,
                        data.getInt("hari_ke"),
                        totalNewCase,
                        underTreatmentPercentage,
                        curedPercentage,
                        cumulativeCase,
                        date.getTime(),
                        totalCured
                );
                dataStatisticViewModel.insert(dataStatisticsCovid);
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setData(List<DataStatisticsCovid> covidList) {
        List<Entry> dataDeath = new ArrayList<>();
        List<Entry> dataPositive = new ArrayList<>();
        List<Entry> dataCured = new ArrayList<>();
        List<PieEntry> pieDeltaPos = new ArrayList<>();
        int delta = 0, cured = 0, death = 0;
        for (int i = 0; i < covidList.size(); i++) {
            DataStatisticsCovid dataStatisticsCovid = covidList.get(i);
            int totalDeath = dataStatisticsCovid.getTotalDeadPatient();
            int cumulativeCase = dataStatisticsCovid.getCumulativeCase();
            int totalCured = dataStatisticsCovid.getTotalCured();
            Date date = new Date(dataStatisticsCovid.getDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String dataDate = sdf.format(date);
            delta = cumulativeCase - (totalCured + totalDeath);
            cured = totalCured;
            death = totalDeath;
            String person = " " + res.getString(R.string.cases);
            tvTotalPositive.setText(cumulativeCase + person);
            tvTotalDeath.setText(totalDeath + person);
            tvTotalCured.setText(totalCured + person);
            tvDate.setText(dataDate);
            dataDeath.add(new Entry((i + 1), totalDeath));
            dataPositive.add(new Entry((i + 1), cumulativeCase));
            dataCured.add(new Entry((i + 1), totalCured));
        }
        String deathLabel = res.getString(R.string.dead);
        String curedLabel = res.getString(R.string.cured);
        String treatmentLabel = res.getString(R.string.under_treatment);
        String positiveLabel = res.getString(R.string.positive);

        pieDeltaPos.add(new PieEntry(cured, curedLabel));
        pieDeltaPos.add(new PieEntry(delta, treatmentLabel));
        pieDeltaPos.add(new PieEntry(death, deathLabel));

        LineDataSet dataSetDeath = new LineDataSet(dataDeath, deathLabel);
        LineDataSet dataSetPositive = new LineDataSet(dataPositive, positiveLabel);
        LineDataSet dataSetCured = new LineDataSet(dataCured, curedLabel);
        dataSetDeath.setColor(getResources().getColor(R.color.colorDeath));
        dataSetDeath.setCircleColor(Color.parseColor("#b40072"));
        dataSetDeath.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSetCured.setColor(getResources().getColor(R.color.colorCured));
        dataSetCured.setCircleColor(Color.parseColor("#00af99"));
        dataSetCured.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSetPositive.setColor(getResources().getColor(R.color.colorPositive));
        dataSetPositive.setCircleColor(Color.parseColor("#f0ce51"));
        dataSetPositive.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineData lineDataDeath = new LineData(dataSetDeath);
        LineData lineDataPositive = new LineData(dataSetPositive);
        LineData lineDataCured = new LineData(dataSetCured);
        setLineDataDecoration(lineChartCured, curedLabel, R.color.colorCured, lineDataCured);
        setLineDataDecoration(lineChartDeath, deathLabel, R.color.colorDeath, lineDataDeath);
        setLineDataDecoration(lineChartPositive, positiveLabel, R.color.colorPositive, lineDataPositive);

        PieDataSet pieDataSet = new PieDataSet(pieDeltaPos, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setSliceSpace(3);
        pieDataSet.setSelectionShift(5);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueFormatter(new PercentFormatter(pieChartCorona));
        pieChartCorona.setUsePercentValues(true);
        pieChartCorona.setData(pieData);
        String deathAndCured = res.getString(R.string.comparison_death_helper);
        pieChartCorona.getDescription().setText(deathAndCured);
        pieChartCorona.setDrawHoleEnabled(false);
        pieChartCorona.highlightValue(null);
        pieChartCorona.setDrawEntryLabels(false);
        pieChartCorona.invalidate();
    }
}
