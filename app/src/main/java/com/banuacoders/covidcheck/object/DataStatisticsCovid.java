package com.banuacoders.covidcheck.object;

public class DataStatisticsCovid {
    private int fid;
    private double deadPatientPercentage;
    private int totalDeadPatient;
    private int totalPatientUnderTreatment;
    private int day;
    private int totalNewCasesPerDay;
    private double patientUnderTreatmentPercentage;
    private double curedPatientPercentage;
    private int cumulativeCase;
    private long date;
    private int totalCured;

    public int getFid() {
        return this.fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public double getDeadPatientPercentage() {
        return this.deadPatientPercentage;
    }

    public void setDeadPatientPercentage(double deadPatientPercentage) {
        this.deadPatientPercentage = deadPatientPercentage;
    }

    public int getTotalDeadPatient() {
        return this.totalDeadPatient;
    }

    public void setTotalDeadPatient(int totalDeadPatient) {
        this.totalDeadPatient = totalDeadPatient;
    }

    public int getTotalPatientUnderTreatment() {
        return this.totalPatientUnderTreatment;
    }

    public void setTotalPatientUnderTreatment(int totalPatientUnderTreatment) {
        this.totalPatientUnderTreatment = totalPatientUnderTreatment;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTotalNewCasesPerDay() {
        return this.totalNewCasesPerDay;
    }

    public void setTotalNewCasesPerDay(int totalNewCasesPerDay) {
        this.totalNewCasesPerDay = totalNewCasesPerDay;
    }

    public double getPatientUnderTreatmentPercentage() {
        return this.patientUnderTreatmentPercentage;
    }

    public void setPatientUnderTreatmentPercentage(double patientUnderTreatmentPercentage) {
        this.patientUnderTreatmentPercentage = patientUnderTreatmentPercentage;
    }

    public double getCuredPatientPercentage() {
        return this.curedPatientPercentage;
    }

    public void setCuredPatientPercentage(double curedPatientPercentage) {
        this.curedPatientPercentage = curedPatientPercentage;
    }

    public int getCumulativeCase() {
        return this.cumulativeCase;
    }

    public void setCumulativeCase(int cumulativeCase) {
        this.cumulativeCase = cumulativeCase;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long Tanggal) {
        this.date = Tanggal;
    }

    public int getTotalCured() {
        return this.totalCured;
    }

    public void setTotalCured(int totalCured) {
        this.totalCured = totalCured;
    }
}
