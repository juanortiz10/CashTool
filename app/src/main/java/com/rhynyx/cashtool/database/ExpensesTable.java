package com.rhynyx.cashtool.database;

import android.provider.BaseColumns;

/**
 * Created by juan on 6/03/16.
 */
public class ExpensesTable {
    public ExpensesTable(){}

    public abstract class TableExp implements BaseColumns{
        public static final String expense_table_name = "expenses";
        public static final String id_expense = "id_expense";
        public static final String category_expense = "category_expense";
        public static final String cuantity_expense = "cuantity_expense";
        public static final String is_repeat = "is_repeat";
        public static final String date_now = "date_now";
        public static final String date_next = "date_next";
        public static final String when_days = "when_days";
        public static final String how_many = "how_many";
    }
}
