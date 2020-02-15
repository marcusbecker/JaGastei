package br.com.mbecker.jagastei;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.mbecker.jagastei.db.GastoModel;

public class Util {
    public static String mesAno(Calendar c) {
        return c.get(Calendar.MONTH) + "" + c.get(Calendar.YEAR);
    }

    public static String frmValor(double res) {
        NumberFormat f = NumberFormat.getNumberInstance();
        f.setCurrency(Currency.getInstance(Locale.getDefault()));
        return f.format(res);
    }

    public static String frmData(Date dt) {
        DateFormat df = DateFormat.getDateInstance();
        return df.format(dt);
    }

    public static Calendar ajustarMes(short mes) {
        Calendar c = Calendar.getInstance();
        int t = c.get(Calendar.MONTH) - mes - 1;
        c.add(Calendar.MONTH, t);
        return c;
    }

    public static String somarGastos(List<GastoModel> lst) {
        double res = 0;
        for (GastoModel g : lst) {
            res += g.getValor();
        }
        return frmValor(res);
    }
}
