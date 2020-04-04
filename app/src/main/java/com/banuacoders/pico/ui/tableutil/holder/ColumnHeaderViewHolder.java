package com.banuacoders.pico.ui.tableutil.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.banuacoders.pico.R;
import com.banuacoders.pico.ui.tableutil.model.ColumnHeaderModel;
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

    static final int[] COLUMN_TEXT_ALIGNS = {
            //city name
            Gravity.START,
            //pdp
            Gravity.CENTER,
            //Finish PDP
            Gravity.CENTER,
            //In PDP
            Gravity.CENTER,
            //odp
            Gravity.CENTER,
            //Finish ODP
            Gravity.CENTER,
            //In ODP
            Gravity.CENTER,
            //positive
            Gravity.CENTER,
            //negative
            Gravity.CENTER,
            //death
            Gravity.CENTER,
    };

}
