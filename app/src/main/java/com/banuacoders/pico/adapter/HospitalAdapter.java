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
import com.banuacoders.pico.data.object.Hospital;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {
    private ArrayList<Hospital> listHospital = new ArrayList<>();
    private Context mContext;

    public HospitalAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListHospital(ArrayList<Hospital> listHospital) {
        this.listHospital = listHospital;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hospital_item, parent, false);
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
        TextView tvName, tvAddress, tvEmail, tvPhone;
        MaterialButton btnDirection;
        MaterialCardView cardPhone, cardEmail;

        HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.hospital_name);
            tvAddress = itemView.findViewById(R.id.hospital_address);
            tvPhone = itemView.findViewById(R.id.hospital_phone);
            tvEmail = itemView.findViewById(R.id.hospital_email);
            cardPhone = itemView.findViewById(R.id.hospital_phone_container);
            cardEmail = itemView.findViewById(R.id.hospital_email_container);
            btnDirection = itemView.findViewById(R.id.hospital_direction_btn);
        }
    }
}
