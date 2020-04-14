package com.banuacoders.pico.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.Hospital;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {
    private List<Hospital> listHospital = new ArrayList<>();
    private Context mContext;

    public HospitalAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListHospital(List<Hospital> listHospital) {
        this.listHospital = listHospital;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hospital, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = listHospital.get(position);
        holder.tvName.setText(hospital.getName());
        holder.tvAddress.setText(hospital.getAddress());
        holder.tvPhone.setText(hospital.getTelephone());
        holder.tvEmail.setText(hospital.getEmail());
        holder.btnDirection.setOnClickListener(view -> getDirection(hospital.getLatitude(),
                hospital.getLongitude(), hospital.getName()));
        holder.cardPhone.setOnClickListener(view -> {
            String number = holder.tvPhone.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });

        holder.cardEmail.setOnClickListener(view -> {
            String email = holder.tvEmail.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email));
            intent.putExtra(Intent.EXTRA_SUBJECT, "COVID-19");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
    }

    private void getDirection(String latitude, String longitude, String placeName) {
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + " (" + placeName + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
            mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(mapIntent);
        }

    }

    @Override
    public int getItemCount() {
        return listHospital.size();
    }

    static class HospitalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hospital_name)
        TextView tvName;
        @BindView(R.id.hospital_address)
        TextView tvAddress;
        @BindView(R.id.hospital_email)
        TextView tvEmail;
        @BindView(R.id.hospital_phone)
        TextView tvPhone;
        @BindView(R.id.hospital_direction_btn)
        MaterialButton btnDirection;
        @BindView(R.id.hospital_phone_container)
        MaterialCardView cardPhone;
        @BindView(R.id.hospital_email_container)
        MaterialCardView cardEmail;

        Unbinder unbinder;

        HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
