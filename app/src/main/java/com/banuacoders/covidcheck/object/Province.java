package com.banuacoders.covidcheck.object;

public class Province {
    private String name;
    private int death;
    private Object updated_at;
    private int positive;
    private int cured;
    private String map_id;
    private int provinceCode;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeath() {
        return this.death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public Object getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
    }

    public int getPositive() {
        return this.positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getCured() {
        return this.cured;
    }

    public void setCured(int cured) {
        this.cured = cured;
    }

    public String getMap_id() {
        return this.map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public int getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
