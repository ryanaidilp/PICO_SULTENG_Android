package com.banuacoders.covidcheck;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.graphics.vector.Stroke;
import com.banuacoders.covidcheck.network.NetworkClient;
import com.banuacoders.covidcheck.object.DataStatisticsCovid;

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
    private AnyChartView chartView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        checkComponent();
        progressBar.setVisibility(View.VISIBLE);
        dataStatisticsCovidArrayList = fetchDataStatistics();
    }


    private void checkComponent() {
        progressBar = findViewById(R.id.progress_bar_statistics);
        chartView = findViewById(R.id.statistic_chart_view);
        chartView.setProgressBar(findViewById(R.id.progress_bar_statistics_total));
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
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    JSONArray features = objectResponse.getJSONArray("features");
                    Cartesian cartesian = AnyChart.line();
                    cartesian.animation(true);
                    cartesian.padding(10d, 20d, 5d, 20d);
                    cartesian.crosshair().enabled(true);
                    cartesian.crosshair()
                            .yLabel(true)
                            .yStroke((Stroke) null, null, null, (String) null, (String) null);


                    cartesian.title("Trend Perkembangan Kasus COVID-19 di Indonesia.");

                    cartesian.yAxis(0).title("Total Kasus");
                    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                    List<DataEntry> data = new ArrayList<>();
                    for (int i = 0; i < features.length(); i++) {
                        if (checkDate(features.getJSONObject(i).getJSONObject("attributes").getLong("Tanggal"))) {
                            break;
                        }
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
                        data.add(new CustomDataEntry("Day " + (i + 1), totalNewCase, totalDeath, totalCured));
                    }
                    Set set = Set.instantiate();
                    set.data(data);
                    Mapping positiveMapping = set.mapAs("{ x: 'x', value: 'value' }");
                    Mapping deathMapping = set.mapAs("{ x: 'x', value: 'value2' }");
                    Mapping curedMapping = set.mapAs("{ x: 'x', value: 'value3' }");

                    Line positiveLine = cartesian.line(positiveMapping);
                    positiveLine.name("Positif");
                    positiveLine.hovered().markers().enabled(true);
                    positiveLine.hovered().markers()
                            .type(MarkerType.CIRCLE)
                            .size(4d);
                    positiveLine.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER)
                            .offsetX(5d)
                            .offsetY(5d);

                    Line deathLine = cartesian.line(deathMapping);
                    deathLine.name("Meninggal");
                    deathLine.hovered().markers().enabled(true);
                    deathLine.hovered().markers()
                            .type(MarkerType.CIRCLE)
                            .size(4d);
                    deathLine.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER)
                            .offsetX(5d)
                            .offsetY(5d);

                    Line curedLine = cartesian.line(curedMapping);
                    curedLine.name("Sembuh");
                    curedLine.hovered().markers().enabled(true);
                    curedLine.hovered().markers()
                            .type(MarkerType.CIRCLE)
                            .size(4d);
                    curedLine.tooltip()
                            .position("right")
                            .anchor(Anchor.LEFT_CENTER)
                            .offsetX(5d)
                            .offsetY(5d);

                    cartesian.legend().enabled(true);
                    cartesian.legend().fontSize(13d);
                    cartesian.legend().padding(0d, 0d, 10d, 0d);

                    chartView.setChart(cartesian);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StatsActivity.this, "Gagal Mendapatkan Data!", Toast.LENGTH_LONG).show();
            }
        });
        progressBar.setVisibility(View.GONE);
        return dataStatisticsCovids;
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
        Date currDate = Calendar.getInstance().getTime();
        String currentDate = sdf.format(currDate);
        return currentDate.equalsIgnoreCase(dataDate);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
