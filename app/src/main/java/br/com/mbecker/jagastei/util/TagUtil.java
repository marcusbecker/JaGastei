package br.com.mbecker.jagastei.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TagUtil {
    private static final String DELIMITER = ";";

    public static String tagsToString(List<String> tags) {
        StringBuilder sb = new StringBuilder();
        for (String s : tags) {
            if (sb.length() != 0) {
                sb.append(TagUtil.DELIMITER);
            }

            sb.append(limpaTag(s));
        }
        return sb.toString();
    }

    private static String limpaTag(String tag) {
        return tag.replaceAll(TagUtil.DELIMITER, ",").trim().toLowerCase();
    }

    public static Long[] splitGastos(String gastos) {
        String[] arr = gastos.split(TagUtil.DELIMITER);
        List<Long> ids = new ArrayList<>(arr.length);
        for (String s : arr) {
            if (!s.isEmpty()) {
                try {
                    ids.add(Long.valueOf(s));
                } catch (NumberFormatException e) {
                    Log.e(Util.class.getName(), e.getMessage(), e);
                }
            }
        }

        return ids.toArray(new Long[0]);
    }
}
