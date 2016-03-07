package com.rhynyx.cashtool.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        ContentValues values = new ContentValues();
        values.put(ExpensesTable.TableExp.category_expense, category);
        values.put(ExpensesTable.TableExp.cuantity_expense, cuantity);
        values.put(ExpensesTable.TableExp.is_repeat, isRepeat);

        long id = db.insert(ExpensesTable.TableExp.expense_table_name,null, values);
        return id;
    }

    public long insertNewRevenue(DataBaseHelper dataBaseHelper, String category, Double cuantity, boolean isRepeat, int when){
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
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
        long id = db.insert(RevenueTable.TableRev.revenue_table_name, null, values);

        return id;
    }

    public double getAllExp(){
        StringBuilder queryExp = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();
        queryExp.append("SELECT ");
        queryExp.append(ExpensesTable.TableExp.cuantity_expense);
        queryExp.append("FROM ");
        queryExp.append(ExpensesTable.TableExp.expense_table_name);

        Cursor cursor = database.rawQuery(queryExp.toString(),null);
        if (cursor.moveToFirst()){
            do {
                //TODO
            }while (cursor.moveToNext());
        }
        return 0.0;
    }

    public double getAllRev(){
        StringBuilder queryRev = new StringBuilder();
        SQLiteDatabase database = this.getReadableDatabase();
        queryRev.append("SELECT ");
        queryRev.append(RevenueTable.TableRev.cuantity_revenue);
        queryRev.append("FROM ");
        queryRev.append(RevenueTable.TableRev.revenue_table_name);

        Cursor cursor =  database.rawQuery(queryRev.toString(),null);
        if (cursor.moveToFirst()){
            do {
                 //TODO
            }while (cursor.moveToNext());
        }
        return 0.0;
    }

    public void getAll(){
        String query = "SELECT * FROM ".concat(ExpensesTable.TableExp.expense_table_name);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                //TODO
            }while (cursor.moveToNext());
        }
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
}
