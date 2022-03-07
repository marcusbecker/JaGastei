package br.com.mbecker.jagastei.domain;

import android.content.Context;

import br.com.mbecker.jagastei.db.JaGasteiDbHelper;

public class Domain {
    public static ServiceDomain getService(Context context) {
        return new JaGasteiDbHelper(context);
    }
}
