package com.banuacoders.pico.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Province {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int death;
    private int positive;
    private int cured;
    private String map_id;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Province(String name, int death, int positive, int cured, String map_id, int provinceCode) {
        this.name = name;
        this.death = death;
        this.positive = positive;
        this.cured = cured;
        this.map_id = map_id;
        this.provinceCode = provinceCode;
    }

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
