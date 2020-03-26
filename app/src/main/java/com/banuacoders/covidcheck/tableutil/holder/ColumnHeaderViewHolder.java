package com.banuacoders.covidcheck.tableutil.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.tableutil.model.ColumnHeaderModel;
import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

public class ColumnHeaderViewHolder extends AbstractViewHolder {
    final LinearLayout columnHeaderContainer;
    final TextView columnHeaderTextview;
    final ITableView tableView;

    public ColumnHeaderViewHolder(@NonNull View itemView, ITableView pTableView) {
        super(itemView);
        tableView = pTableView;
        columnHeaderTextview = itemView.findViewById(R.id.column_header_textView);
        columnHeaderContainer = itemView.findViewById(R.id.column_header_container);
    }

    public void setColumnHeaderModel(ColumnHeaderModel pColumnHeaderModel, int pColumnPosition) {
        columnHeaderTextview.setGravity(COLUMN_TEXT_ALIGNS[pColumnPosition] | Gravity.CENTER_VERTICAL);
        columnHeaderTextview.setText(pColumnHeaderModel.getData());
        columnHeaderContainer.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        columnHeaderTextview.requestLayout();
    }

    public static final int[] COLUMN_TEXT_ALIGNS = {
            //city name
            Gravity.LEFT,
            //pdp
            Gravity.CENTER,
            //odp
            Gravity.CENTER,
            //positive
            Gravity.CENTER,
            //negative
            Gravity.CENTER,
            //death
            Gravity.CENTER
    };

}
