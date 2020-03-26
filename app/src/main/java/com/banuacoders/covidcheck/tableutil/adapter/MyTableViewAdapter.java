package com.banuacoders.covidcheck.tableutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.object.City;
import com.banuacoders.covidcheck.tableutil.MyTableViewModel;
import com.banuacoders.covidcheck.tableutil.holder.CellViewHolder;
import com.banuacoders.covidcheck.tableutil.holder.ColumnHeaderViewHolder;
import com.banuacoders.covidcheck.tableutil.holder.RowHeaderViewHolder;
import com.banuacoders.covidcheck.tableutil.model.CellModel;
import com.banuacoders.covidcheck.tableutil.model.ColumnHeaderModel;
import com.banuacoders.covidcheck.tableutil.model.RowHeaderModel;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import java.util.List;

public class MyTableViewAdapter extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel, CellModel> {

    private MyTableViewModel myTableViewModel;
    private Context mContext;

    public MyTableViewAdapter(Context context) {
        this.mContext = context;
        this.myTableViewModel = new MyTableViewModel();
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_cell_layout, parent, false);
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable CellModel cellItemModel, int columnPosition, int rowPosition) {
        CellModel cell = cellItemModel;
        ((CellViewHolder) holder).setCellModel(cell, columnPosition);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_column_header_layout, parent, false);
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeaderModel columnHeaderItemModel, int columnPosition) {
        ColumnHeaderModel columnHeaderModel = columnHeaderItemModel;
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeaderModel(columnHeaderModel, columnPosition);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_row_header_layout, parent, false);
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeaderModel rowHeaderItemModel, int rowPosition) {
        RowHeaderModel rowHeaderModel = rowHeaderItemModel;
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.rowHeaderTextView.setText(rowHeaderModel.getData());
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, null, false);
    }

    public void setData(List<City> cityList) {
        myTableViewModel.generateListForTableView(cityList);
        setAllItems(myTableViewModel.getColumnHeaderModelList(),
                myTableViewModel.getRowHeaderModelList(),
                myTableViewModel.getCellModelList());
    }
}
