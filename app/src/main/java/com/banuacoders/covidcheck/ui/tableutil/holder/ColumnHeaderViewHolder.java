package com.banuacoders.covidcheck.ui.tableutil.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.ui.tableutil.model.ColumnHeaderModel;
import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

public class ColumnHeaderViewHolder extends AbstractViewHolder {
    private final LinearLayout columnHeaderContainer;
    private final TextView columnHeaderTextView;
    private final ITableView tableView;

    public ColumnHeaderViewHolder(@NonNull View itemView, ITableView pTableView) {
        super(itemView);
        tableView = pTableView;
        columnHeaderTextView = itemView.findViewById(R.id.column_header_textView);
        columnHeaderContainer = itemView.findViewById(R.id.column_header_container);
    }

    public void setColumnHeaderModel(ColumnHeaderModel pColumnHeaderModel, int pColumnPosition) {
        columnHeaderTextView.setGravity(COLUMN_TEXT_ALIGNS[pColumnPosition] | Gravity.CENTER_VERTICAL);
        columnHeaderTextView.setText(pColumnHeaderModel.getData());
        columnHeaderContainer.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        columnHeaderTextView.requestLayout();
    }

    public static final int[] COLUMN_TEXT_ALIGNS = {
            //city name
            Gravity.START,
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
