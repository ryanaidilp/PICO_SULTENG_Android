package com.banuacoders.pico.ui.tableutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.District;
import com.banuacoders.pico.ui.tableutil.MyTableViewModel;
import com.banuacoders.pico.ui.tableutil.holder.CellViewHolder;
import com.banuacoders.pico.ui.tableutil.holder.ColumnHeaderViewHolder;
import com.banuacoders.pico.ui.tableutil.holder.RowHeaderViewHolder;
import com.banuacoders.pico.ui.tableutil.model.CellModel;
import com.banuacoders.pico.ui.tableutil.model.ColumnHeaderModel;
import com.banuacoders.pico.ui.tableutil.model.RowHeaderModel;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import java.util.List;

public class MyTableViewAdapter extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel, CellModel> {

    private MyTableViewModel myTableViewModel;
    private Context mContext;

    public MyTableViewAdapter(Context context) {
        this.mContext = context;
        this.myTableViewModel = new MyTableViewModel(context);
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
        ((CellViewHolder) holder).setCellModel(cellItemModel, columnPosition);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_column_header_layout, parent, false);
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeaderModel columnHeaderItemModel, int columnPosition) {
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeaderModel(columnHeaderItemModel, columnPosition);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_row_header_layout, parent, false);
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeaderModel rowHeaderItemModel, int rowPosition) {
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.rowHeaderTextView.setText(rowHeaderItemModel.getData());
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, parent, false);
    }

    public void setData(List<District> districtList) {
        myTableViewModel.generateListForTableView(districtList);
        setAllItems(myTableViewModel.getColumnHeaderModelList(),
                myTableViewModel.getRowHeaderModelList(),
                myTableViewModel.getCellModelList());
    }
}
