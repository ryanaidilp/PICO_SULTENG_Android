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
        TextView tvNegative = view.findViewById(R.id.value_negative);
        TextView tvDeath = view.findViewById(R.id.value_death);
        TextView tvODP = view.findViewById(R.id.value_ODP);
        TextView tvPDP = view.findViewById(R.id.value_PDP);

        District district = (District) marker.getTag();
        String person = " " + context.getResources().getString(R.string.cases);
        tvName.setText(district.getName());
        tvPositive.setText(district.getPositive() + person);
        tvNegative.setText(district.getNegative() + person);
        tvDeath.setText(district.getDeath() + person);
        tvODP.setText(district.getODP() + "");
        tvPDP.setText(district.getPDP() + "");
        return view;
    }
}
