package br.com.mbecker.jagastei.db;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

class ModelBuilder {

    private static GastoModel convertGastoModel(Cursor c) {
        GastoModel v = new GastoModel();
        v.setId(c.getLong(c.getColumnIndex(JaGasteiContract.GastoEntry._ID)));
        v.setValor(c.getDouble(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_VALOR)));
        v.setQuando(c.getLong(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_QUANDO)));
        v.setMesAno(c.getString(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO)));
        v.setLat(c.getDouble(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_LAT)));
        v.setLng(c.getDouble(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_LNG)));
        v.setObs(c.getString(c.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_OBS)));
        return v;
    }

    private static TagModel convertTagModel(Cursor c) {
        long id = c.getLong(c.getColumnIndex(JaGasteiContract.TagEntry._ID));
        String tag = c.getString(c.getColumnIndex(JaGasteiContract.TagEntry.COLUMN_NAME_TAG_NAME));
        String gastos = c.getString(c.getColumnIndex(JaGasteiContract.TagEntry.COLUMN_NAME_ID_GASTO));
        return new TagModel(id, tag, gastos);
    }

    public static GastoModel buildGasto(Cursor query) {
        if (query.moveToFirst()) {
            GastoModel v = convertGastoModel(query);
            query.close();
            return v;
        }

        return null;
    }

    public static List<GastoModel> buildGastoLista(Cursor c) {
        List<GastoModel> lst = new ArrayList<>(40);

        if (c.moveToFirst()) {
            do {
                lst.add(convertGastoModel(c));
            } while (c.moveToNext());

            c.close();
        }

        return lst;
    }

    public static List<TagModel> buildTagLista(Cursor c) {
        List<TagModel> lst = new ArrayList<>(40);
        if (c.moveToFirst()) {
            do {
                lst.add(convertTagModel(c));
            } while (c.moveToNext());

            c.close();
        }

        return lst;
    }

    public static TagModel buildTag(Cursor c) {
        return convertTagModel(c);
    }
}
