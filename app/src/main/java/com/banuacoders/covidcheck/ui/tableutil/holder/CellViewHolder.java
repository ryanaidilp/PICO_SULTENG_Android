package com.banuacoders.covidcheck.ui.tableutil.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.banuacoders.covidcheck.R;
import com.banuacoders.covidcheck.ui.tableutil.model.CellModel;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

public class CellViewHolder extends AbstractViewHolder {
    private final TextView cellTextView;
    private final LinearLayout cellContainer;

    public CellViewHolder(@NonNull View itemView) {
        super(itemView);
        cellTextView = itemView.findViewById(R.id.cell_data);
        cellContainer = itemView.findViewById(R.id.cell_container);
    }

    public void setCellModel(CellModel cellModel, int pColumnPosition) {
        cellTextView.setGravity(ColumnHeaderViewHolder.COLUMN_TEXT_ALIGNS[pColumnPosition] | Gravity.CENTER_VERTICAL);
        cellTextView.setText(String.valueOf(cellModel.getData()));

        cellContainer.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        cellTextView.requestLayout();
    }

}

