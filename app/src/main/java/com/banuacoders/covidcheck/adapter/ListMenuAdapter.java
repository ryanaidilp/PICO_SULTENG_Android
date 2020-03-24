package com.banuacoders.covidcheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.object.MenuItem;
import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.tvDescMenu.setText(listMenu.get(position).getDesc());
        holder.tvTitleMenu.setText(listMenu.get(position).getTitle());
        Glide.with(holder.itemView)
                .load(listMenu.get(position).getIcon())
                .into(holder.iconMenu);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView iconMenu;
        TextView tvDescMenu, tvTitleMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            iconMenu = itemView.findViewById(R.id.logo_menu_item);
            tvDescMenu = itemView.findViewById(R.id.desc_menu_item);
            tvTitleMenu = itemView.findViewById(R.id.title_menu_item);
        }
    }
}
