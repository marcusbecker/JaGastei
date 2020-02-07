package br.com.mbecker.jagastei.db;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ModelBuilder {

    private static GastoModel convertGastoModel(Cursor query) {
        GastoModel v = new GastoModel();
        v.setId(query.getLong(query.getColumnIndex(JaGasteiContract.GastoEntry._ID)));
        v.setValor(query.getDouble(query.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_VALOR)));
        v.setQuando(query.getLong(query.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_QUANDO)));
        v.setMesAno(query.getString(query.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_MESANO)));
        v.setLat(query.getDouble(query.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_LAT)));
        v.setLng(query.getDouble(query.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_LNG)));
        v.setObs(query.getString(query.getColumnIndex(JaGasteiContract.GastoEntry.COLUMN_NAME_OBS)));
        return v;
    }

    public static GastoModel buildGasto(Cursor query) {
        if (query.moveToFirst()) {
            GastoModel v = convertGastoModel(query);
            query.close();
            return v;
        }

        return null;
    }

    public static List<GastoModel> buildGastoLista(Cursor query) {
        List<GastoModel> lst = new ArrayList<>(40);

        if (query.moveToFirst()) {
            do {
                lst.add(convertGastoModel(query));
            } while (query.moveToNext());

            query.close();
        }

        return lst;
    }
}
