package com.banuacoders.covidcheck;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.banuacoders.covidcheck.network.NetworkClient;
import com.banuacoders.covidcheck.object.DataStatisticsCovid;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {
    Map<String, Object> queryMap = new HashMap<>();
    private static ArrayList<DataStatisticsCovid> dataStatisticsCovidArrayList = new ArrayList<>();
    private LineChart lineChartDeath, lineChartPositive, lineChartCured;
    private PieChart pieChartCorona;
    private ProgressBar progressBar;
    private ImageView btnSync;
    private TextView tvTotalDeath, tvTotalPositive, tvTotalCured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        checkComponent();
        dataStatisticsCovidArrayList = fetchDataStatistics();
        btnSync.setOnClickListener(view -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                rotateSync();
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
    }

    private ArrayList<DataStatisticsCovid> fetchDataStatistics() {
        ArrayList<DataStatisticsCovid> dataStatisticsCovids = new ArrayList<>();
        queryMap.put("where", "1=1");
        queryMap.put("outFields", "*");
        queryMap.put("outSR", 4326);
        queryMap.put("f", "json");
        Call<ResponseBody> call = NetworkClient
                .getInstance()
                .getApiStats()
                .getCovidStats(queryMap);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    JSONArray features = objectResponse.getJSONArray("features");
                    List<Entry> dataDeath = new ArrayList<>();
                    List<Entry> dataPositive = new ArrayList<>();
                    List<Entry> dataCured = new ArrayList<>();
                    List<PieEntry> pieDeltaPos = new ArrayList<>();
                    int delta = 0, cured = 0, death = 0;
                    for (int i = 0; i < features.length(); i++) {
                        DataStatisticsCovid dataStatisticsCovid = new DataStatisticsCovid();
                        dataStatisticsCovid.setDay(features.getJSONObject(i).getJSONObject("attributes").getInt("Hari_ke"));
                        dataStatisticsCovid.setDate(features.getJSONObject(i).getJSONObject("attributes").getLong("Tanggal"));
                        int totalNewCase = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Jumlah_Kasus_Baru_per_Hari"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getInt("Jumlah_Kasus_Baru_per_Hari")
                                : 0;
                        dataStatisticsCovid.setTotalNewCasesPerDay(totalNewCase);
                        int cumulativeCase = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Jumlah_Kasus_Kumulatif"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getInt("Jumlah_Kasus_Kumulatif")
                                : 0;
                        dataStatisticsCovid.setCumulativeCase(cumulativeCase);
                        int totalPatientInTreatment = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Jumlah_pasien_dalam_perawatan"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getInt("Jumlah_pasien_dalam_perawatan")
                                : 0;
                        dataStatisticsCovid.setTotalPatientUnderTreatment(totalPatientInTreatment);
                        double underTreatmentPercentage = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Persentase_Pasien_dalam_Perawatan"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getDouble("Persentase_Pasien_dalam_Perawatan")
                                : 0;
                        dataStatisticsCovid.setPatientUnderTreatmentPercentage(underTreatmentPercentage);
                        int totalCured = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Jumlah_Pasien_Sembuh"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getInt("Jumlah_Pasien_Sembuh")
                                : 0;
                        dataStatisticsCovid.setTotalCured(totalCured);
                        double curedPercentage = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Persentase_Pasien_Sembuh"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getDouble("Persentase_Pasien_Sembuh")
                                : 0;
                        dataStatisticsCovid.setCuredPatientPercentage(curedPercentage);
                        int totalDeath = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Jumlah_Pasien_Meninggal"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getInt("Jumlah_Pasien_Meninggal")
                                : 0;
                        dataStatisticsCovid.setTotalDeadPatient(totalDeath);
                        double deathPercentage = checkNullFields(features.getJSONObject(i).getJSONObject("attributes").getString("Persentase_Pasien_Meninggal"))
                                ? features.getJSONObject(i).getJSONObject("attributes").getDouble("Persentase_Pasien_Meninggal")
                                : 0;
                        dataStatisticsCovid.setDeadPatientPercentage(deathPercentage);
                        dataStatisticsCovid.setFid(features.getJSONObject(i).getJSONObject("attributes").getInt("FID"));
                        dataStatisticsCovids.add(dataStatisticsCovid);
                        int idx = i + 1;
                        if (checkDate(features.getJSONObject(i).getJSONObject("attributes").getLong("Tanggal"))) {
                            break;
                        }
                        if (idx < features.length()) {
                            if (checkDate(features.getJSONObject(idx).getJSONObject("attributes").getLong("Tanggal")) && totalDeath == 0) {
                                break;
                            }
                        }
                        int deltaPositive = cumulativeCase - (totalCured + totalDeath);
                        delta = deltaPositive;
                        cured = totalCured;
                        death = totalDeath;
                        dataDeath.add(new Entry((i + 1), totalDeath));
                        dataPositive.add(new Entry((i + 1), cumulativeCase));
                        dataCured.add(new Entry((i + 1), totalCured));
                        tvTotalPositive.setText(String.valueOf(cumulativeCase));
                        tvTotalDeath.setText(String.valueOf(totalDeath));
                        tvTotalCured.setText(String.valueOf(totalCured));
                    }
                    pieDeltaPos.add(new PieEntry(delta, "Dirawat"));
                    pieDeltaPos.add(new PieEntry(cured, "Sembuh"));
                    pieDeltaPos.add(new PieEntry(death, "Meninggal"));

                    LineDataSet dataSetDeath = new LineDataSet(dataDeath, "Meninggal");
                    LineDataSet dataSetPositive = new LineDataSet(dataPositive, "Positif");
                    LineDataSet dataSetCured = new LineDataSet(dataCured, "Sembuh");
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
                    setLineDataDecoration(lineChartCured, "sembuh", R.color.colorCured, lineDataCured);
                    setLineDataDecoration(lineChartDeath, "meninggal", R.color.colorDeath, lineDataDeath);
                    setLineDataDecoration(lineChartPositive, "positif", R.color.colorPositive, lineDataPositive);

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
                    pieChartCorona.getDescription().setText("*) Perbandingan Pasien Sembuh dan Meninggal");
                    pieChartCorona.setDrawHoleEnabled(false);
                    pieChartCorona.highlightValue(null);
                    pieChartCorona.setDrawEntryLabels(false);
                    pieChartCorona.invalidate();
                    progressBar.setVisibility(View.GONE);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StatsActivity.this, "Gagal Mendapatkan Data!", Toast.LENGTH_LONG).show();
            }
        });
        return dataStatisticsCovids;
    }

    private void setLineDataDecoration(LineChart lineChart, String text, int colorCode, LineData lineData) {
        lineChart.setNoDataText("Data tidak tersedia!");
        lineChart.setNoDataTextColor(getResources().getColor(colorCode));
        lineChart.setData(lineData);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Hari ke-" + (int) value;
            }

        });
        lineChart.getDescription().setText("*) Trend pasien " + text + " COVID19 per hari. Data dari BNPB Indonesia.");
        lineChart.invalidate();
    }

    private boolean checkNullFields(String field) {
        boolean res = false;
        if (!field.equalsIgnoreCase("null")) {
            res = true;
        }
        return res;
    }

    private boolean checkDate(long curr) {
        Date date = new Date(curr);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataDate = sdf.format(date);
        Date currDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currDate);
        c.add(Calendar.DATE, 1);
        currDate = c.getTime();
        String currentDate = sdf.format(currDate);
        return currentDate.equalsIgnoreCase(dataDate);
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

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
