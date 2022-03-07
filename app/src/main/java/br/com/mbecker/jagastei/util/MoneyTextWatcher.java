package br.com.mbecker.jagastei.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    private final NumberFormat nf;

    public MoneyTextWatcher(EditText editText, NumberFormat nf) {
        this.nf = nf;
        editTextWeakReference = new WeakReference<>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        String s;

        if (editText == null || (s = editable.toString()).isEmpty()) {
            return;
        }

        editText.removeTextChangedListener(this);

        BigDecimal parsed = Util.toBigDecimal(Util.somenteNumeros(s));
        String formatted = nf.format(parsed);

        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}