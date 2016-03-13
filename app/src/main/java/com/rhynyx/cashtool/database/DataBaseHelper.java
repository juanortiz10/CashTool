package com.rhynyx.cashtool.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rhynyx.cashtool.fragments.Expenses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by juan on 6/03/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int database_version = 1;
    private static final String DATABASE_NAME = "gastos";

    public StringBuilder queryExp = new StringBuilder();
    public StringBuilder queryRev = new StringBuilder();


    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        queryExp.append("CREATE TABLE IF NOT EXISTS ");
        queryExp.append(ExpensesTable.TableExp.expense_table_name);
        queryExp.append("(");
        queryExp.append(ExpensesTable.TableExp.id_expense.concat(" INTEGER PRIMARY KEY,"));
        queryExp.append(ExpensesTable.TableExp.category_expense.concat(" TEXT,"));
        queryExp.append(ExpensesTable.TableExp.cuantity_expense.concat(" LONG,"));
        queryExp.append(ExpensesTable.TableExp.is_repeat.concat(" TEXT,"));
        queryExp.append(ExpensesTable.TableExp.when_days.concat(" INTEGER,"));
        queryExp.append(ExpensesTable.TableExp.date_now.concat(" TEXT,"));
        queryExp.append(ExpensesTable.TableExp.date_next.concat(" TEXT"));
        queryExp.append(");");

        queryRev.append("CREATE TABLE IF NOT EXISTS ");
        queryRev.append(RevenueTable.TableRev.revenue_table_name);
        queryRev.append("(");
        queryRev.append(RevenueTable.TableRev.id_revenue.concat(" INTEGER PRIMARY KEY,"));
        queryRev.append(RevenueTable.TableRev.category_revenue.concat(" TEXT,"));
        queryRev.append(RevenueTable.TableRev.cuantity_revenue.concat(" LONG,"));
        queryRev.append(RevenueTable.TableRev.is_repeat.concat(" TEXT,"));
        queryRev.append(RevenueTable.TableRev.when_days.concat(" INTEGER,"));
        queryRev.append(RevenueTable.TableRev.date_now.concat(" TEXT,"));
        queryRev.append(RevenueTable.TableRev.date_next.concat(" TEXT"));
        queryRev.append(");");

        db.execSQL(queryExp.toString());
        db.execSQL(queryRev.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public long insertNewExpense(DataBaseHelper dataBaseHelper, String category, Double cuantity, boolean isRepeat, int when){
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        int whenDays;
        if (isRepeat)
            whenDays = checkDays(when);
        else
            whenDays = 0;

        //Add days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, whenDays);
        String untilDays = dateFormat.format(calendar.getTime());
        String todayDate = dateFormat.format(today);

        ContentValues values = new ContentValues();
        values.put(ExpensesTable.TableExp.category_expense, category);
        values.put(ExpensesTable.TableExp.cuantity_expense, String.valueOf(cuantity));
        values.put(ExpensesTable.TableExp.is_repeat, String.valueOf(isRepeat));
        values.put(ExpensesTable.TableExp.when_days, whenDays);
        values.put(ExpensesTable.TableExp.date_now, todayDate);
        values.put(ExpensesTable.TableExp.date_next, untilDays);
        long id = db.insert(ExpensesTable.TableExp.expense_table_name,null, values);

        db.close();
        return id;
    }

    public long insertNewRevenue(DataBaseHelper dataBaseHelper, String category, Double cuantity, boolean isRepeat, int when){
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date today = Calendar.getInstance().getTime();
        int whenDays;
        if (isRepeat)
            whenDays = checkDays(when);
        else
            whenDays = 0;

        //Add days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, whenDays);
        String untilDays = dateFormat.format(calendar.getTime());
        String todayDate = dateFormat.format(today);

        ContentValues values = new ContentValues();
        values.put(RevenueTable.TableRev.category_revenue, category);
        values.put(RevenueTable.TableRev.cuantity_revenue, String.valueOf(cuantity));
        values.put(RevenueTable.TableRev.is_repeat, String.valueOf(isRepeat));
        values.put(RevenueTable.TableRev.when_days, whenDays);
        values.put(RevenueTable.TableRev.date_now, todayDate);
        values.put(RevenueTable.TableRev.date_next, untilDays);
        System.out.println(todayDate);
        long id = db.insert(RevenueTable.TableRev.revenue_table_name, null, values);

        db.close();
        return id;
    }
    //Get all expenses (sum) that is saved on the BD
    public double getAllExp(){
        StringBuilder queryExp = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();
        queryExp.append("SELECT ");
        queryExp.append("SUM(".concat(ExpensesTable.TableExp.cuantity_expense).concat(")"));
        queryExp.append(" FROM ");
        queryExp.append(ExpensesTable.TableExp.expense_table_name);

        Cursor cursor = database.rawQuery(queryExp.toString(),null);
        double value = 0.0;
        if (cursor.moveToFirst())
                value = Double.parseDouble(cursor.getString(0));

        database.close();
        return value;
    }

    //Get all revenue (sum) that is saved on the BD
    public double getAllRev(){
        StringBuilder queryRev = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();
        queryRev.append("SELECT ");
        queryRev.append("SUM(".concat(RevenueTable.TableRev.cuantity_revenue).concat(")"));
        queryRev.append(" FROM ");
        queryRev.append(RevenueTable.TableRev.revenue_table_name);

        Cursor cursor =  database.rawQuery(queryRev.toString(),null);
        double value = 0.0;

        if (cursor.moveToFirst())
                 value = Double.parseDouble(cursor.getString(0));

        database.close();
        return value;
    }
    public double getMonthlyExpenses(){
        StringBuilder queryExp = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();

        queryExp.append(" SELECT ");
        queryExp.append("SUM(".concat(ExpensesTable.TableExp.cuantity_expense).concat(")"));
        queryExp.append(" FROM ");
        queryExp.append(ExpensesTable.TableExp.expense_table_name);
        queryExp.append(" WHERE substr(".concat(ExpensesTable.TableExp.date_now).concat(",4,2)='").concat(getMonth()).concat("';"));

        Cursor cursor =  database.rawQuery(queryExp.toString(),null);
        double valExp = 0.0;

        if (cursor.moveToFirst())
                valExp = Double.parseDouble(cursor.getString(0));
        database.close();
        return valExp;
    }

    public double getAnnualExpenses(){
        StringBuilder queryExp = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();

        queryExp.append(" SELECT ");
        queryExp.append("SUM(".concat(ExpensesTable.TableExp.cuantity_expense).concat(")"));
        queryExp.append(" FROM ");
        queryExp.append(ExpensesTable.TableExp.expense_table_name);
        queryExp.append(" WHERE substr(".concat(ExpensesTable.TableExp.date_now).concat(",7,4)='").concat(this.getYear()).concat("';"));

        Cursor cursor =  database.rawQuery(queryExp.toString(),null);
        double valExp = 0.0;

        if (cursor.moveToFirst())
                valExp = Double.parseDouble(cursor.getString(0));

        database.close();
        return valExp;
    }

    public double getAnnualRevenue(){
        SQLiteDatabase database = this.getReadableDatabase();

        StringBuilder queryRev = new StringBuilder();
        queryRev.append("SELECT ");
        queryRev.append("SUM(".concat(RevenueTable.TableRev.cuantity_revenue).concat(")"));
        queryRev.append(" FROM ");
        queryRev.append(RevenueTable.TableRev.revenue_table_name);
        queryRev.append(" WHERE substr(".concat(RevenueTable.TableRev.date_now).concat(",7,4)='").concat(this.getYear()).concat("';"));

        Cursor cursor =  database.rawQuery(queryRev.toString(),null);
        double valRev =0.0;
        if(cursor.moveToFirst())
                valRev = Double.parseDouble(cursor.getString(0));

        database.close();
        return valRev;
    }

    public double getMonthlyRevenue(){
        SQLiteDatabase database = this.getReadableDatabase();

        StringBuilder queryRev = new StringBuilder();
        queryRev.append("SELECT ");
        queryRev.append("SUM(".concat(RevenueTable.TableRev.cuantity_revenue).concat(")"));
        queryRev.append(" FROM ");
        queryRev.append(RevenueTable.TableRev.revenue_table_name);
        queryRev.append(" WHERE substr("+RevenueTable.TableRev.date_now+",4,2)='"+getMonth()+"';");

        Cursor cursor =  database.rawQuery(queryRev.toString(),null);

        double valRev =0.0;
        if (cursor.moveToFirst())
                valRev = Double.parseDouble(cursor.getString(0));

        database.close();
        return valRev;
    }

    public double getAverageExp(){
        StringBuilder queryExp = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();

        queryExp.append(" SELECT ");
        queryExp.append(ExpensesTable.TableExp.cuantity_expense);
        queryExp.append(" FROM ");
        queryExp.append(ExpensesTable.TableExp.expense_table_name);
        queryExp.append(" WHERE substr(".concat(ExpensesTable.TableExp.date_now).concat(",4,2)='").concat(this.getMonth()).concat("';"));

        Cursor cursor =  database.rawQuery(queryExp.toString(),null);
        double valExp = 0.0;
        int counter= 0;
        if (cursor.moveToFirst()) {
            do{
                valExp += Double.parseDouble(cursor.getString(cursor.getColumnIndex(ExpensesTable.TableExp.cuantity_expense)));
                counter ++;
            }while (cursor.moveToNext());
        }
        database.close();
        return valExp/counter;
    }
    //Get money amount per month
    public double getMonthlyAmount(){
        return this.getMonthlyRevenue()-this.getMonthlyExpenses();
    }

    //Get money amount per year
    public double getAnnualAmount(){
        return this.getAnnualRevenue()-this.getAnnualExpenses();
    }

    public double getRichLevel(){
        double total_acum = this.getAllRev()-this.getAllExp();
        return total_acum/this.getAverageExp();
    }

    public int checkDays(int position){
        if (position == 0){
            return 7;
        }else if(position == 1){
            return 15;
        }else if(position == 2){
            return 30;
        }else if(position == 3){
            return 90;
        }else if(position == 4){
            return 180;
        }else if(position == 5){
            return 360;
        }
        return 0;
    }
    //This method returns the number of an actual month
    private String getMonth(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String monthNumber = String.valueOf(cal.get(Calendar.MONTH)+1);

        if(monthNumber.length() < 2){
            return "0"+monthNumber;
        }
        return monthNumber;
    }

    //This method returns the number of an actual year
    private String getYear(){
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }
}
