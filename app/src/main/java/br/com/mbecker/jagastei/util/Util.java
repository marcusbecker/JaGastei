package br.com.mbecker.jagastei.util;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mbecker.jagastei.db.GastoModel;

public class Util {
    private static final int scale = 2;
    private static final BigDecimal divisor = new BigDecimal(100);
    public static DateFormat sdf;

    public static String mesAno(Calendar c) {
        return c.get(Calendar.MONTH) + "" + c.get(Calendar.YEAR);
    }

    public static String frmValor(double res) {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(res);
    }

    public static String frmData(Date dt) {
        if (sdf == null) {
            sdf = DateFormat.getDateInstance();
        }

        return sdf.format(dt);
    }

    public static Calendar ajustarMes(short mes) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -mes);
        return c;
    }

    public static String somarGastos(List<GastoModel> lst) {
        double res = 0;
        for (GastoModel g : lst) {
            res += g.getValor();
        }
        return frmValor(res);
    }

    public static String somenteNumeros(String s) {
        final String charNumeros = "0123456789";
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); ) {
            if (!charNumeros.contains(String.valueOf(sb.charAt(i)))) {
                sb.delete(i, i + 1);
            } else {
                ++i;
            }
        }

        return sb.toString();
    }

    public static BigDecimal toBigDecimal(String valor) {
        final BigDecimal parsed;
        if (valor == null || valor.isEmpty()) {
            parsed = new BigDecimal(0);
        } else {
            parsed = new BigDecimal(valor).setScale(scale, BigDecimal.ROUND_FLOOR).divide(divisor, BigDecimal.ROUND_FLOOR);
        }

        return parsed;
    }
}
