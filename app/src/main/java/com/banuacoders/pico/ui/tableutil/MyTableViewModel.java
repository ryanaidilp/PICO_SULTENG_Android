package com.banuacoders.pico.ui.tableutil;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.District;
import com.banuacoders.pico.ui.tableutil.model.CellModel;
import com.banuacoders.pico.ui.tableutil.model.ColumnHeaderModel;
import com.banuacoders.pico.ui.tableutil.model.RowHeaderModel;

import java.util.ArrayList;
import java.util.List;

public class MyTableViewModel {
    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> cellModelList;
    private Resources res;

    public MyTableViewModel(Context context) {
        res = context.getResources();
    }

    public int getColumnTextAlign(int column) {
        if (column == 1) {
            return Gravity.START;
        }
        return Gravity.CENTER;
    }

    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel(res.getString(R.string.district)));
        list.add(new ColumnHeaderModel(res.getString(R.string.odp)));
        list.add(new ColumnHeaderModel(res.getString(R.string.completed_odp)));
        list.add(new ColumnHeaderModel(res.getString(R.string.in_odp)));
        list.add(new ColumnHeaderModel(res.getString(R.string.pdp)));
        list.add(new ColumnHeaderModel(res.getString(R.string.completed_pdp)));
        list.add(new ColumnHeaderModel(res.getString(R.string.in_pdp)));
        list.add(new ColumnHeaderModel(res.getString(R.string.positive)));
        list.add(new ColumnHeaderModel(res.getString(R.string.negative)));
        list.add(new ColumnHeaderModel(res.getString(R.string.dead)));
        list.add(new ColumnHeaderModel(res.getString(R.string.cured)));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<District> districtList) {
        List<List<CellModel>> lists = new ArrayList<>();
        for (int i = 0; i < districtList.size(); i++) {
            District district = districtList.get(i);
            List<CellModel> list = new ArrayList<>();
            list.add(new CellModel("1-" + i, district.getName()));
            list.add(new CellModel("2-" + i, district.getODP()));
            list.add(new CellModel("3-" + i, district.getFinishedODP()));
            list.add(new CellModel("4-" + i, district.getInODP()));
            list.add(new CellModel("5-" + i, district.getPDP()));
            list.add(new CellModel("6-" + i, district.getFinishedPDP()));
            list.add(new CellModel("7-" + i, district.getInPDP()));
            list.add(new CellModel("8-" + i, district.getPositive()));
            list.add(new CellModel("9-" + i, district.getNegative()));
            list.add(new CellModel("10-" + i, district.getDeath()));
            list.add(new CellModel("11-" + i, district.getRecovered()));
            lists.add(list);
        }
        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(int size) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }

    public List<ColumnHeaderModel> getColumnHeaderModelList() {
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        return cellModelList;
    }

    public void generateListForTableView(List<District> cities) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        cellModelList = createCellModelList(cities);
        mRowHeaderModelList = createRowHeaderList(cities.size());
    }
}