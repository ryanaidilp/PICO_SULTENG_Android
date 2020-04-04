package com.banuacoders.pico.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.banuacoders.pico.R;
import com.banuacoders.pico.adapter.CustomInfoWindowMaps;
import com.banuacoders.pico.adapter.ListMenuAdapter;
import com.banuacoders.pico.data.object.District;
import com.banuacoders.pico.data.object.MenuItem;
import com.banuacoders.pico.data.viewmodel.DistrictViewModel;
import com.banuacoders.pico.network.NetworkClient;
import com.banuacoders.pico.ui.tableutil.MyTableViewListener;
import com.banuacoders.pico.ui.tableutil.adapter.MyTableViewAdapter;
import com.evrencoskun.tableview.TableView;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonGeometryCollection;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiPoint;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DistrictViewModel districtViewModel;
    GoogleMap googleMap;
    Marker marker;

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
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        getComponents();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_google_maps);
        supportMapFragment.getMapAsync(this);
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
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
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
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Resources res = getApplicationContext().getResources();
                    Toast.makeText(MainActivity.this, res.getString(R.string.failed_to_get_data), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMaxZoomPreference(12);
        googleMap.setMinZoomPreference(6);
        loadLayerToMap(googleMap);
    }

    private String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject loadJsonObjects(String filename) {
        String fileJson = loadJSONFromAsset(this, filename);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(fileJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void loadLayerToMap(final GoogleMap gMap) {
        GeoJsonLayer layer = new GeoJsonLayer(gMap, loadJsonObjects("map"));
        final GeoJsonPolygonStyle geoJsonPolygonStyle = layer.getDefaultPolygonStyle();
        geoJsonPolygonStyle.setClickable(true);
        geoJsonPolygonStyle.setStrokeColor(Color.BLUE);
        geoJsonPolygonStyle.setStrokeWidth(2);
        layer.setOnFeatureClickListener((GeoJsonLayer.GeoJsonOnFeatureClickListener) feature -> districtViewModel.getAllDistricts().observe(this, districts -> {
            for (int i = 0; i < districts.size(); i++) {
                if (districts.get(i).getName().contains(feature.getProperty("name"))) {
                    District district = districts.get(i);
                    CustomInfoWindowMaps customInfoWindowMaps = new CustomInfoWindowMaps(MainActivity.this);
                    gMap.setInfoWindowAdapter(customInfoWindowMaps);
                    LatLngBounds latLngBounds = getLatLngBoundingBox(feature);
                    double lat = latLngBounds.northeast.latitude + latLngBounds.southwest.latitude;
                    double lng = latLngBounds.northeast.longitude + latLngBounds.southwest.longitude;
                    LatLng latLng = new LatLng(lat / 2, lng / 2);
                    if (marker == null) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng);
                        marker = gMap.addMarker(markerOptions);
                        marker.setTag(district);
                        marker.showInfoWindow();
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8), 2000, null);
                    } else {
                        if (marker.isInfoWindowShown()) {
                            marker.hideInfoWindow();
                        }
                        if (marker.getTag() == district) {
                            marker.remove();
                            marker = null;
                            LatLngBounds bounds = getLatLngBoundingBox(layer);
                            lat = (bounds.northeast.latitude + bounds.southwest.latitude) / 2;
                            lng = (bounds.southwest.longitude + bounds.northeast.longitude) / 2;
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),
                                    6), 2000, null);
                        } else {
                            marker.remove();
                            marker = null;
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(latLng);
                            marker = gMap.addMarker(markerOptions);
                            marker.setTag(district);
                            marker.showInfoWindow();
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8), 2000, null);
                        }
                    }
                    break;
                }
            }
        }));
        layer.addLayerToMap();
        LatLngBounds bounds = getLatLngBoundingBox(layer);
        double lat = (bounds.northeast.latitude + bounds.southwest.latitude) / 2;
        double lng = (bounds.southwest.longitude + bounds.northeast.longitude) / 2;
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),
                6), 2000, null);
    }

    private LatLngBounds getLatLngBoundingBox(GeoJsonLayer layer) {

        List<LatLng> coordinates = new ArrayList<>();

        for (GeoJsonFeature feature : layer.getFeatures()) {

            if (feature.hasGeometry()) {
                Geometry geometry = feature.getGeometry();

                if (geometry.getGeometryType().equals("GeometryCollection")) {
                    List<Geometry> geometries =
                            ((GeoJsonGeometryCollection) geometry).getGeometries();

                    for (Geometry geom : geometries) {
                        coordinates.addAll(getCoordinatesFromGeometry(geom));
                    }
                } else {
                    coordinates.addAll(getCoordinatesFromGeometry(geometry));
                }
            }
        }

        LatLngBounds.Builder builder = LatLngBounds.builder();

        // Feed the coordinates to the builder.
        for (LatLng latLng : coordinates) {
            builder.include(latLng);
        }

        LatLngBounds boundingBoxFromBuilder = builder.build();

        //return boundingBox;
        return boundingBoxFromBuilder;
    }

    private LatLngBounds getLatLngBoundingBox(Feature feature) {

        List<LatLng> coordinates = new ArrayList<>();

        if (feature.hasGeometry()) {
            Geometry geometry = feature.getGeometry();

            if (geometry.getGeometryType().equals("GeometryCollection")) {
                List<Geometry> geometries =
                        ((GeoJsonGeometryCollection) geometry).getGeometries();

                for (Geometry geom : geometries) {
                    coordinates.addAll(getCoordinatesFromGeometry(geom));
                }
            } else {
                coordinates.addAll(getCoordinatesFromGeometry(geometry));
            }
        }

        LatLngBounds.Builder builder = LatLngBounds.builder();

        // Feed the coordinates to the builder.
        for (LatLng latLng : coordinates) {
            builder.include(latLng);
        }

        //return boundingBox;
        return builder.build();
    }

    private List<LatLng> getCoordinatesFromGeometry(Geometry geometry) {

        List<LatLng> coordinates = new ArrayList<>();

        switch (geometry.getGeometryType()) {
            case "Point":
                coordinates.add(((GeoJsonPoint) geometry).getCoordinates());
                break;
            case "MultiPoint":
                List<GeoJsonPoint> points = ((GeoJsonMultiPoint) geometry).getPoints();
                for (GeoJsonPoint point : points) {
                    coordinates.add(point.getCoordinates());
                }
                break;
            case "LineString":
                coordinates.addAll(((GeoJsonLineString) geometry).getCoordinates());
                break;
            case "MultiLineString":
                List<GeoJsonLineString> lines =
                        ((GeoJsonMultiLineString) geometry).getLineStrings();
                for (GeoJsonLineString line : lines) {
                    coordinates.addAll(line.getCoordinates());
                }
                break;
            case "Polygon":
                List<? extends List<LatLng>> lists =
                        ((GeoJsonPolygon) geometry).getCoordinates();
                for (List<LatLng> list : lists) {
                    coordinates.addAll(list);
                }
                break;
            case "MultiPolygon":
                List<GeoJsonPolygon> polygons =
                        ((GeoJsonMultiPolygon) geometry).getPolygons();
                for (GeoJsonPolygon polygon : polygons) {
                    for (List<LatLng> list : polygon.getCoordinates()) {
                        coordinates.addAll(list);
                    }
                }
                break;
        }

        return coordinates;
    }
}
