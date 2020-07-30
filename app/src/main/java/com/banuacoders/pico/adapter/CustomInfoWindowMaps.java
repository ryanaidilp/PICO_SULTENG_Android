package com.banuacoders.pico.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.District;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowMaps implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public CustomInfoWindowMaps(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.info_window_maps, null);
        TextView tvName = view.findViewById(R.id.district_name);
        TextView tvPositive = view.findViewById(R.id.value_postive);
        TextView tvActive = view.findViewById(R.id.value_active);
        TextView tvRecovered = view.findViewById(R.id.value_recovered);
        TextView tvDeath = view.findViewById(R.id.value_death);
        TextView tvODP = view.findViewById(R.id.value_ODP);
        TextView tvPDP = view.findViewById(R.id.value_PDP);

        District district = (District) marker.getTag();
        String person = " " + context.getResources().getString(R.string.cases);
        tvName.setText(new StringBuilder(district.getName()));
        tvPositive.setText(new StringBuilder().append(district.getPositive()).append(person));
        tvActive.setText(new StringBuilder().append(district.getActive()).append(person));
        tvDeath.setText(new StringBuilder().append(district.getDeath()).append(person));
        tvODP.setText(new StringBuilder().append(district.getODP()));
        tvPDP.setText(new StringBuilder().append(district.getPDP()));
        tvRecovered.setText(new StringBuilder().append(district.getRecovered()).append(person));
        return view;
    }
}
