package br.com.mbecker.jagastei.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TagTextWatcher implements TextWatcher {
    private final List<String> tags;
    private final WeakReference<EditText> editTextWeakReference;
    private final WeakReference<LinearLayout> resultWeakReference;

    public TagTextWatcher(EditText editText, LinearLayout result) {
        this.tags = new ArrayList<>(5);
        this.editTextWeakReference = new WeakReference<>(editText);
        this.resultWeakReference = new WeakReference<>(result);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        if (!text.isEmpty() && text.endsWith(" ")) {
            EditText editText = editTextWeakReference.get();
            editText.removeTextChangedListener(this);
            editText.setText("");

            String value = text.toLowerCase().trim();
            tags.add(value);

            TextView tv = new TextView(editText.getContext());
            tv.setText(value);
            resultWeakReference.get().addView(tv);
            editText.addTextChangedListener(this);
        }
    }

    public List<String> getTags() {
        return tags;
    }
}
