package br.com.mbecker.jagastei.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import br.com.mbecker.jagastei.R;

public class TextViewHelper {
    private final Context context;
    private final LinearLayout.LayoutParams ll;

    public TextViewHelper(Context context) {
        this.context = context;
        ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(16, 16, 16, 16);
    }


    public TextView build(String text) {
        TextView t = new TextView(context);
        t.setLayoutParams(ll);
        t.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        t.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLight));
        t.setPadding(16, 16, 16, 16);
        t.setText(text);

        return t;
    }
}
