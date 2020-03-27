package com.banuacoders.pico.data.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hospital {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int no;
    private String name;
    private String latitude;
    private String telephone;
    private String email;
    private String address;
    private String longitude;

    public Hospital(int no, String name, String latitude, String telephone, String email, String address, String longitude) {
        this.no = no;
        this.name = name;
        this.latitude = latitude;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
