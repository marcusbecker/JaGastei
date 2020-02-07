package br.com.mbecker.jagastei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class JaGasteiDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "JaGastei.db";

    public JaGasteiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JaGasteiContract.SQL_CREATE_GASTO);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(JaGasteiContract.SQL_DELETE_GASTO);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void salvarGasto(GastoModel g) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_VALOR, g.getValor());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_QUANDO, g.getQuandoToTime());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO, g.getMesAno());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_LAT, g.getLat());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_LNG, g.getLng());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_OBS, g.getObs());

            db.insert(JaGasteiContract.GastoEntry.TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.d(getClass().getName(), "salvarGasto: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public List<GastoModel> listarGasto() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(JaGasteiContract.GastoEntry.TABLE_NAME, null, null, null, null, null, null);
        return ModelBuilder.buildGastoLista(c);
    }
}


