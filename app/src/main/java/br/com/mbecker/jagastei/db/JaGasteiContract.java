package br.com.mbecker.jagastei.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

class JaGasteiContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private static final String CONTENT_AUTHORITY = "br.com.mbecker.jagastei";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_GASTO = "gasto";
    private static final String PATH_TAG = "tag";

    static final String SQL_CREATE_GASTO =
            "CREATE TABLE IF NOT EXISTS " + JaGasteiContract.GastoEntry.TABLE_NAME + " (" + JaGasteiContract.GastoEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP + JaGasteiContract.GastoEntry.COLUMN_NAME_VALOR + REAL_TYPE + COMMA_SEP + JaGasteiContract.GastoEntry.COLUMN_NAME_QUANDO + INT_TYPE + COMMA_SEP + JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO + TEXT_TYPE + COMMA_SEP + JaGasteiContract.GastoEntry.COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP + JaGasteiContract.GastoEntry.COLUMN_NAME_LNG + REAL_TYPE + COMMA_SEP + JaGasteiContract.GastoEntry.COLUMN_NAME_OBS + TEXT_TYPE + " )";

    static final String SQL_CREATE_TAG =
            "CREATE TABLE IF NOT EXISTS " + JaGasteiContract.TagEntry.TABLE_NAME + " (" + JaGasteiContract.TagEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP + TagEntry.COLUMN_NAME_TAG_NAME + TEXT_TYPE + COMMA_SEP + TagEntry.COLUMN_NAME_ID_GASTO + TEXT_TYPE + " )";

    static final String SQL_DELETE_GASTO =
            "DROP TABLE IF EXISTS " + JaGasteiContract.GastoEntry.TABLE_NAME;

    static final String SQL_DELETE_TAG =
            "DROP TABLE IF EXISTS " + JaGasteiContract.TagEntry.TABLE_NAME;

    protected static final String SQL_TOTAL_MES =
            "SELECT SUM(" + JaGasteiContract.GastoEntry.COLUMN_NAME_VALOR + ") FROM " + JaGasteiContract.GastoEntry.TABLE_NAME + " WHERE " + JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO + " = ?";


    public static class GastoEntry implements BaseColumns {
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GASTO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GASTO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GASTO;

        public static final String TABLE_NAME = "gasto_entry";
        public static final String COLUMN_NAME_VALOR = "gasto_valor";
        public static final String COLUMN_NAME_QUANDO = "gasto_quando";
        public static final String COLUMN_NAME_MESANO = "gasto_mesano";
        public static final String COLUMN_NAME_LAT = "gasto_lat";
        public static final String COLUMN_NAME_LNG = "gasto_lng";
        public static final String COLUMN_NAME_OBS = "gasto_obs";

        public static Uri buildGastoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class TagEntry implements BaseColumns {
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAG).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAG;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAG;

        public static final String TABLE_NAME = "tag_entry";
        public static final String COLUMN_NAME_TAG_NAME = "tag_name";
        public static final String COLUMN_NAME_ID_GASTO = "id_gasto";

        public static Uri buildTagUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}