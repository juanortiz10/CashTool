package com.rhynyx.cashtool.database;

import android.provider.BaseColumns;

/**
 * Created by abanda on 22/04/16.
 */
public class CategoryTable {
    public abstract class TableCat implements BaseColumns {
        public static final String category_table_name = "tab_category";
        public static final String id_category = "id_category";
        public static final String category_name = "category_name";
        public static final String transaction  = "ingresoegreso";
    }
}
