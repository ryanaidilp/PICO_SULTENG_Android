package com.banuacoders.pico.data.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class District {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int negative;
    private int no;
    private int death;
    private int positive;
    private int ODP;
    private String name;
    private int PDP;
    private int finishedPDP;
    private int finishedODP;
    private int inODP;
    private int inPDP;


    public District(int negative, int no, int death, int positive, int ODP, String name, int PDP, int finishedPDP, int finishedODP, int inODP, int inPDP) {
        this.negative = negative;
        this.no = no;
        this.death = death;
        this.positive = positive;
        this.ODP = ODP;
        this.name = name;
        this.PDP = PDP;
        this.finishedPDP = finishedPDP;
        this.finishedODP = finishedODP;
        this.inODP = inODP;
        this.inPDP = inPDP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFinishedPDP() {
        return finishedPDP;
    }

    public void setFinishedPDP(int finishedPDP) {
        this.finishedPDP = finishedPDP;
    }

    public int getFinishedODP() {
        return finishedODP;
    }

    public void setFinishedODP(int finishedODP) {
        this.finishedODP = finishedODP;
    }

    public int getInODP() {
        return inODP;
    }

    public void setInODP(int inODP) {
        this.inODP = inODP;
    }

    public int getInPDP() {
        return inPDP;
    }

    public void setInPDP(int inPDP) {
        this.inPDP = inPDP;
    }

    public int getNegative() {
        return this.negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public int getODP() {
        return this.ODP;
    }

    public void setODP(int ODP) {
        this.ODP = ODP;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPDP() {
        return this.PDP;
    }

    public void setPDP(int PDP) {
        this.PDP = PDP;
    }
}
