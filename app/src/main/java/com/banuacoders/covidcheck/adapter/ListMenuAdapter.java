package com.banuacoders.covidcheck.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.covidcheck.DetexiActivity;
import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.StatsActivity;
import com.banuacoders.covidcheck.object.MenuItem;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        holder.tvDescMenu.setText(listMenu.get(position).getDesc());
        holder.tvTitleMenu.setText(listMenu.get(position).getTitle());
        holder.cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleCheck(position, "STATISTIK") || titleCheck(position, "STATISTICS")) {
                    Intent intent = new Intent(view.getContext(), StatsActivity.class);
                    view.getContext().startActivity(intent);
                } else if (titleCheck(position, "RUMAH") || titleCheck(position, "HOSPITAL")) {
                    Toast.makeText(view.getContext(), listMenu.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                } else if (titleCheck(position, "SCREENING")) {
                    Intent intent = new Intent(view.getContext(), DetexiActivity.class);
                    view.getContext().startActivity(intent);
                }
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

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView iconMenu;
        TextView tvDescMenu, tvTitleMenu;
        MaterialCardView cardMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            iconMenu = itemView.findViewById(R.id.logo_menu_item);
            tvDescMenu = itemView.findViewById(R.id.desc_menu_item);
            tvTitleMenu = itemView.findViewById(R.id.title_menu_item);
            cardMenu = itemView.findViewById(R.id.card_menu_item);
        }
    }
}
