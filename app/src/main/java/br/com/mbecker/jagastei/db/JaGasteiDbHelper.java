package br.com.mbecker.jagastei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;

import br.com.mbecker.jagastei.domain.Service;
import br.com.mbecker.jagastei.util.TagUtil;

public class JaGasteiDbHelper extends SQLiteOpenHelper implements Service {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "JaGastei.db";

    public JaGasteiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JaGasteiContract.SQL_CREATE_GASTO);
        db.execSQL(JaGasteiContract.SQL_CREATE_TAG);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion) {
            case 2:
                db.execSQL(JaGasteiContract.SQL_CREATE_TAG);
                migrarVersao2(db);
                break;
        }

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(JaGasteiContract.SQL_DELETE_GASTO);
                break;
            case 2:
                db.execSQL(JaGasteiContract.SQL_DELETE_TAG);
                break;
        }

    }

    private void migrarVersao2(SQLiteDatabase db) {
        Cursor c = db.query(JaGasteiContract.GastoEntry.TABLE_NAME, new String[]{JaGasteiContract.GastoEntry._ID, JaGasteiContract.GastoEntry.COLUMN_NAME_OBS}, null, null, null, null, null);
        if (c.moveToFirst()) {
            Map<String, String> mapTags = new HashMap<>(20);
            do {
                long id = c.getLong(c.getColumnIndex(JaGasteiContract.GastoEntry._ID));
                String obs = c.getString(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_OBS));

                if (obs == null || obs.trim().isEmpty()) {
                    continue;
                }

                obs = obs.replaceAll(";", ",").replaceAll(" ", ";").toLowerCase();
                ContentValues cv = new ContentValues();
                cv.put(JaGasteiContract.GastoEntry.COLUMN_NAME_OBS, obs);
                db.update(JaGasteiContract.GastoEntry.TABLE_NAME, cv, "_id = ?", new String[]{String.valueOf(id)});

                String[] tags = obs.split(";");
                for (String t : tags) {
                    String idTags = mapTags.get(t);
                    if (idTags != null) {
                        idTags += ";" + id;
                    } else {
                        idTags = String.valueOf(id);
                    }
                    mapTags.put(t, idTags);
                }
            } while (c.moveToNext());
            c.close();

            mapTags.forEach((k, v) -> {
                ContentValues cv = new ContentValues();
                cv.put(JaGasteiContract.TagEntry.COLUMN_NAME_TAG_NAME, k);
                cv.put(JaGasteiContract.TagEntry.COLUMN_NAME_ID_GASTO, v);
                db.insert(JaGasteiContract.TagEntry.TABLE_NAME, null, cv);
            });
        }
    }


    @Override
    public long salvarGasto(GastoModel g) {
        long id = 0;

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_VALOR, g.getValor());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_QUANDO, g.getQuandoToTime());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO, g.getMesAno());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_LAT, g.getLat());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_LNG, g.getLng());
            values.put(JaGasteiContract.GastoEntry.COLUMN_NAME_OBS, g.getObs());

            id = db.insert(JaGasteiContract.GastoEntry.TABLE_NAME, null, values);

        } catch (Exception e) {
            Log.d(getClass().getName(), "salvarGasto: " + e.getMessage());
        }

        return id;
    }

    @Override
    public List<GastoModel> listarGastos(String mesAno) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        if (mesAno == null) {
            c = db.query(JaGasteiContract.GastoEntry.TABLE_NAME, null, null, null, null, null, null);
        } else {
            c = db.query(JaGasteiContract.GastoEntry.TABLE_NAME, null, JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO + "=?", new String[]{mesAno}, null, null, null, null);
        }

        return ModelBuilder.buildGastoLista(c);
    }

    @Override
    public List<TagModel> listarTags() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(JaGasteiContract.TagEntry.TABLE_NAME, null, null, null, null, null, null);
        return ModelBuilder.buildTagLista(c);
    }

    @Override
    public void atualizaTags(long id, List<String> tags) {
        Cursor c;
        SQLiteDatabase db = getWritableDatabase();
        for (String t : tags) {
            c = db.query(JaGasteiContract.TagEntry.TABLE_NAME, null, JaGasteiContract.TagEntry.COLUMN_NAME_TAG_NAME + "=?", new String[]{t}, null, null, null, "1");
            if (c.moveToFirst()) {
                TagModel tagModel = ModelBuilder.buildTag(c);
                Set<Long> newTags = new HashSet<>(Arrays.asList(tagModel.getGastos()));
                newTags.add(id);
                if (newTags.size() > tagModel.getGastos().length) {
                    ContentValues cv = new ContentValues();
                    cv.put(JaGasteiContract.TagEntry.COLUMN_NAME_TAG_NAME, t);
                    cv.put(JaGasteiContract.TagEntry.COLUMN_NAME_ID_GASTO, TagUtil.tagsGastosToString(newTags));
                    db.update(JaGasteiContract.TagEntry.TABLE_NAME, cv, JaGasteiContract.TagEntry._ID + "=?", new String[]{String.valueOf(tagModel.getId())});
                }

                c.close();

            } else {
                ContentValues cv = new ContentValues();
                cv.put(JaGasteiContract.TagEntry.COLUMN_NAME_TAG_NAME, t);
                cv.put(JaGasteiContract.TagEntry.COLUMN_NAME_ID_GASTO, String.valueOf(id));
                db.insert(JaGasteiContract.TagEntry.TABLE_NAME, null, cv);
            }

        }
    }

    /*
    public String totalMes(String mesAno) {
        double res = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(JaGasteiContract.SQL_TOTAL_MES, new String[]{mesAno});
        if (c.moveToFirst()) {
            res = c.getDouble(0);
        }

        c.close();
        return Util.frmValor(res);
    }
     */
}


