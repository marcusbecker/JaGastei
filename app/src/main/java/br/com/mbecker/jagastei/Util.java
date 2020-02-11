package br.com.mbecker.jagastei;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static String mesAno(Calendar c) {
        return (c.get(Calendar.MONTH) + 1) + "" + c.get(Calendar.YEAR);
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
}
