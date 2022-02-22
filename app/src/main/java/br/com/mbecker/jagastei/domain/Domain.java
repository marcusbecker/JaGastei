package br.com.mbecker.jagastei.domain;

import android.app.Activity;
import android.content.Context;

import br.com.mbecker.jagastei.db.JaGasteiDbHelper;

public class Domain {
    public static Service getService(Context context) {
        return new JaGasteiDbHelper(context);
    }
}
