package com.banuacoders.covidcheck.ui.tableutil.model;

public class CellModel {
    private String mId;
    private Object mData;

    public CellModel(String mId, Object mData) {
        this.mId = mId;
        this.mData = mData;
    }

    public String getId() {
        return mId;
    }

    public Object getData() {
        return mData;
    }
}
