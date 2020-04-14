package com.banuacoders.pico.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.MenuItem;
import com.banuacoders.pico.ui.activities.ActivityHospital;
import com.banuacoders.pico.ui.activities.DetexiActivity;
import com.banuacoders.pico.ui.activities.MapActivity;
import com.banuacoders.pico.ui.activities.PostsActivity;
import com.banuacoders.pico.ui.activities.StatsActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;


public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.MenuViewHolder> {

    private ArrayList<MenuItem> listMenu;

    public ListMenuAdapter(ArrayList<MenuItem> listMenu) {
        this.listMenu = listMenu;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        holder.tvDescMenu.setText(listMenu.get(position).getDesc());
        holder.tvTitleMenu.setText(listMenu.get(position).getTitle());
        holder.cardMenu.setOnClickListener(view -> {
            try {
                Intent intent;
                if (titleCheck(position, "STATISTIK") || titleCheck(position, "STATISTICS")) {
                    intent = new Intent(view.getContext(), StatsActivity.class);
                } else if (titleCheck(position, "RUMAH") || titleCheck(position, "HOSPITAL")) {
                    intent = new Intent(view.getContext(), ActivityHospital.class);
                } else if (titleCheck(position, "SCREENING") || titleCheck(position, "MANDIRI")) {
                    intent = new Intent(view.getContext(), DetexiActivity.class);
                } else if (titleCheck(position, "PETA") || titleCheck(position, "MAP")) {
                    intent = new Intent(view.getContext(), MapActivity.class);
                } else if (titleCheck(position, "GUGUS") || titleCheck(position, "FORCE")) {
                    intent = new Intent(view.getContext(), PostsActivity.class);
                } else {
                    Uri webpage = Uri.parse("https://instagram.com/ryanaidilp_");
                    intent = new Intent(Intent.ACTION_VIEW, webpage);
                }
                view.getContext().startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(holder.itemView.getContext(), "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
        Glide.with(holder.itemView)
                .load(listMenu.get(position).getIcon())
                .into(holder.iconMenu);
    }

    private boolean titleCheck(int index, String text) {
        return listMenu.get(index).getTitle().contains(text);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView iconMenu;
        TextView tvDescMenu, tvTitleMenu;
        MaterialCardView cardMenu;

        MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            iconMenu = itemView.findViewById(R.id.logo_menu_item);
            tvDescMenu = itemView.findViewById(R.id.desc_menu_item);
            tvTitleMenu = itemView.findViewById(R.id.title_menu_item);
            cardMenu = itemView.findViewById(R.id.card_menu_item);
        }
    }
}
