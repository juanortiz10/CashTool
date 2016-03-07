package com.rhynyx.cashtool.models;

/**
 * Created by juan on 6/03/16.
 */
public class Expenses {
    private int id_expense;
    private String category_expense;
    private double cuantity_expense;
    private boolean isRepeat;
    private String date_now;
    private String date_next;
    public int when_days;

    public Expenses(){}

    public Expenses(int id_expense, String category_expense, double cuantity_expense,
                    boolean isRepeat, String date_now, String date_next, int when_days){
        this.category_expense = category_expense;
        this.cuantity_expense = cuantity_expense;
        this.isRepeat = isRepeat;
        this.date_now = date_now;
        this.date_next = date_next;
        this.when_days = when_days;
    }

    public int getId_expense() {
        return id_expense;
    }

    public void setId_expense(int id_expense) {
        this.id_expense = id_expense;
    }

    public String getCategory_expense() {
        return category_expense;
    }

    public void setCategory_expense(String category_expense) {
        this.category_expense = category_expense;
    }

    public double getCuantity_expense() {
        return cuantity_expense;
    }

    public void setCuantity_expense(double cuantity_expense) {
        this.cuantity_expense = cuantity_expense;
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
