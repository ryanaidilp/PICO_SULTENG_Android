package com.banuacoders.covidcheck.tableutil;

import android.util.Log;
import android.view.Gravity;

import com.banuacoders.covidcheck.object.City;
import com.banuacoders.covidcheck.tableutil.model.CellModel;
import com.banuacoders.covidcheck.tableutil.model.ColumnHeaderModel;
import com.banuacoders.covidcheck.tableutil.model.RowHeaderModel;

import java.util.ArrayList;
import java.util.List;

public class MyTableViewModel {
    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> cellModelList;

    public int getColumnTextAlign(int column) {
        switch (column) {
            case 1:
                return Gravity.LEFT;
            default:
                return Gravity.CENTER;
        }
    }

    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Kabupaten/Kota"));
        list.add(new ColumnHeaderModel("ODP"));
        list.add(new ColumnHeaderModel("PDP"));
        list.add(new ColumnHeaderModel("Positif"));
        list.add(new ColumnHeaderModel("Negatif"));
        list.add(new ColumnHeaderModel("Meninggal"));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<City> cityList) {
        List<List<CellModel>> lists = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i);
            Log.d("Kota ke-" + i, city.getName());
            List<CellModel> list = new ArrayList<>();
            list.add(new CellModel("1-" + i, city.getName()));
            list.add(new CellModel("2-" + i, city.getODP()));
            list.add(new CellModel("3-" + i, city.getPDP()));
            list.add(new CellModel("4-" + i, city.getPositive()));
            list.add(new CellModel("5-" + i, city.getNegative()));
            list.add(new CellModel("6-" + i, city.getDeath()));
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

    public void generateListForTableView(List<City> cities) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        cellModelList = createCellModelList(cities);
        mRowHeaderModelList = createRowHeaderList(cities.size());
    }
}