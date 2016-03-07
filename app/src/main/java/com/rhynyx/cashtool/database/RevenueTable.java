package com.rhynyx.cashtool.database;

import android.provider.BaseColumns;

/**
 * Created by juan on 6/03/16.
 */
public class RevenueTable {
    public RevenueTable(){}

    public static abstract class TableRev implements BaseColumns{
        public static final String revenue_table_name = "revenue";
        public static final String id_revenue = "id_revenue";
        public static final String category_revenue = "category_revenue";
        public static final String cuantity_revenue = "cuantity_revenue";
        public static final String is_repeat = "is_repeat";
        public static final String date_now = "date_now";
        public static final String date_next = "date_next";
        public static final String when_days = "when_days";
    }
}
