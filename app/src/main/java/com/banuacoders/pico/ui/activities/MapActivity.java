package com.banuacoders.pico.ui.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Map;
import com.anychart.core.map.series.Choropleth;
import com.anychart.core.ui.ColorRange;
import com.anychart.enums.SelectionMode;
import com.anychart.enums.SidePosition;
import com.anychart.graphics.vector.text.FontStyle;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.scales.LinearColor;
import com.banuacoders.pico.R;
import com.banuacoders.pico.data.object.Province;
import com.banuacoders.pico.data.viewmodel.ProvinceViewModel;
import com.banuacoders.pico.network.NetworkClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    private ProvinceViewModel provinceViewModel;
    private AnyChartView anyChartView;
    private ImageView btnSync;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        res = getApplicationContext().getResources();
        provinceViewModel = ViewModelProviders.of(this)
                .get(ProvinceViewModel.class);
        ImageButton btnZoomIn = findViewById(R.id.zoom_in_map);
        ImageButton btnZoomOut = findViewById(R.id.zoom_out_map);
        ImageButton btnZoomReset = findViewById(R.id.zoom_reset_map);
        anyChartView = findViewById(R.id.map_province_positive);
        anyChartView.setProgressBar(findViewById(R.id.progress_map_province_positive));
        Map map = AnyChart.map();
        AtomicInteger zoom = new AtomicInteger();
        btnZoomIn.setOnClickListener(view ->
                map.zoomTo(zoom.incrementAndGet(), zoom.incrementAndGet(),
                        zoom.incrementAndGet())
        );

        btnZoomOut.setOnClickListener(view ->
                map.zoomTo(zoom.decrementAndGet(), zoom.decrementAndGet(),
                        zoom.decrementAndGet()));

        btnZoomReset.setOnClickListener(view -> map.zoomTo(0, 0, 0));
        btnSync = findViewById(R.id.btn_sync);
        btnSync.setOnClickListener(view -> {
            rotateSync();
            if (provinceViewModel.getAllProvinces() == null) {
                getData();
            } else {
                provinceViewModel.deleteAllProvinces();
                getData();
            }
        });
        if (provinceViewModel.getAllProvinces() == null) {
            getData();
        } else {
            provinceViewModel.getAllProvinces().observe(this, provinces -> setData(provinces, map));
        }
        getData();
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

    private void getData() {

        Call<ResponseBody> call = NetworkClient.getInstance().getApiCoder().getAllProvince();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    if (objectResponse.getBoolean("success")) {
                        JSONArray arrayProvince = objectResponse.getJSONArray("data");
                        setData(arrayProvince);
                    } else {
                        Toast.makeText(MapActivity.this, objectResponse
                                        .getJSONObject("errors").getString("message"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(MapActivity.this, res.getString(R.string.failed_to_get_data)
                        + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class CustomDataEntry extends DataEntry {
        CustomDataEntry(String id, String name, Number value) {
            setValue("id", id);
            setValue("name", name);
            setValue("value", value);
        }

        CustomDataEntry(String id, String name, Number value, LabelDataEntry label) {
            setValue("id", id);
            setValue("name", name);
            setValue("value", value);
            setValue("label", label);
        }

        static class LabelDataEntry extends DataEntry {
            LabelDataEntry(Boolean enabled) {
                setValue("enabled", enabled);
            }
        }

    }

    private void setData(JSONArray arrayProvince) {
        for (int i = 0; i < arrayProvince.length(); i++) {
            try {
                JSONObject object = arrayProvince.getJSONObject(i);
                Province province = new Province(
                        object.getString("provinsi"),
                        object.getInt("meninggal"),
                        object.getInt("positif"),
                        object.getInt("sembuh"),
                        object.getString("map_id"),
                        object.getInt("kode_provinsi")
                );
                provinceViewModel.insert(province);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setData(List<Province> arrayProvince, Map map) {
        List<DataEntry> data = new ArrayList<>();
        CustomDataEntry.LabelDataEntry label = new CustomDataEntry.LabelDataEntry(false);
        for (int i = 0; i < arrayProvince.size(); i++) {
            if (arrayProvince.get(i).getMap_id() != null) {
                if (arrayProvince.get(i).getPositive() == 0) {
                    data.add(new CustomDataEntry(
                            arrayProvince.get(i).getMap_id(),
                            arrayProvince.get(i).getName(),
                            arrayProvince.get(i).getPositive(), label));
                } else {
                    data.add(new CustomDataEntry(
                            arrayProvince.get(i).getMap_id(),
                            arrayProvince.get(i).getName(),
                            arrayProvince.get(i).getPositive()));
                }
            }
        }
        map.title()
                .enabled(true)
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .padding(10, 0, 10, 0)
                .text("<span style=\"color:#7c868e; font-size: 18px\"> Peta Penyebaran COVID-19 per " +
                        "Provinsi.</span> <br>" +
                        "<span style=\"color:#545f69; font-size: 14px\">Kasus " +
                        "positif COVID-19 per Provinsi.</span>");

        map.geoData("anychart.maps.indonesia");
        ColorRange colorRange = map.colorRange();
        colorRange.enabled(true)
                .colorLineSize(10)
                .stroke("#B9B9B9")
                .labels("{ 'padding': 3 }")
                .labels("{ 'size': 7 }");
        colorRange.ticks()
                .enabled(true)
                .stroke("#B9B9B9")
                .position(SidePosition.INSIDE)
                .length(10);
        colorRange.minorTicks()
                .enabled(true)
                .stroke("#B9B9B9")
                .position("outside")
                .length(5);

        map.interactivity().keyboardZoomAndMove(true);
        map.interactivity().selectionMode(SelectionMode.NONE);
        map.padding(0, 0, 0, 0);
        Choropleth series = map.choropleth(data);
        LinearColor linearColor = LinearColor.instantiate();
        linearColor.colors(new String[]{"#c2e9fb", "#81d4fa", "#01579b", "#002746"});
        series.colorScale(linearColor);
        series.hovered()
                .fill("#f48fb1")
                .stroke("#f99fb9");
        series.selected()
                .fill("#c2185b")
                .stroke("#c2185b");
        series.labels().enabled(true);
        series.labels().fontSize(10);
        series.labels().fontColor("#212121");
        series.labels().format("{%Value}");
        series.labels().fontStyle(FontStyle.OBLIQUE);

        series.tooltip()
                .useHtml(true)
                .format("function() {\n" +
                        "            return '<span style=\"font-size: 13px\">' + this.value + ' kasus positif.</span>';\n" +
                        "          }");

        anyChartView.addScript("file:///android_asset/indonesia.js");
        anyChartView.addScript("file:///android_asset/proj4.js");
        anyChartView.setChart(map);
    }
}