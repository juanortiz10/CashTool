package com.rhynyx.cashtool.models;

/**
 * Created by juan on 6/03/16.
 */
public class Revenues {
    private int id_revenue;
    private String category_revenue;
    private double cuantity_revenue;
    private boolean isRepeat;
    private String date_now;
    private String date_next;
    private int when_days;

    public Revenues(){}

    public Revenues(int id_revenue, String category_revenue, double cuantity_revenue,
                    boolean isRepeat, String date_now, String date_next, int when_days){
        this.category_revenue= category_revenue;
        this.id_revenue = id_revenue;
        this.cuantity_revenue = cuantity_revenue;
        this.isRepeat = isRepeat;
        this.date_now = date_now;
        this.date_next = date_next;
        this.when_days = when_days;
    }

    public int getId_revenue() {
        return id_revenue;
    }

    public void setId_revenue(int id_revenue) {
        this.id_revenue = id_revenue;
    }

    public String getCategory_revenue() {
        return category_revenue;
    }

    public void setCategory_revenue(String category_revenue) {
        this.category_revenue = category_revenue;
    }

    public double getCuantity_revenue() {
        return cuantity_revenue;
    }

    public void setCuantity_revenue(double cuantity_revenue) {
        this.cuantity_revenue = cuantity_revenue;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getDate_now() {
        return date_now;
    }

    public void setDate_now(String date_now) {
        this.date_now = date_now;
    }

    public String getDate_next() {
        return date_next;
    }

    public void setDate_next(String date_next) {
        this.date_next = date_next;
    }

    public int getWhen_days() {
        return when_days;
    }

    public void setWhen_days(int when_days) {
        this.when_days = when_days;
    }
}
