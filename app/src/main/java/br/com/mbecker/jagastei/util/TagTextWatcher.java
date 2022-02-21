package br.com.mbecker.jagastei.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import br.com.mbecker.jagastei.adapter.CreateTag;

public class TagTextWatcher implements TextWatcher {
    private static final String DELIMITER = " ";
    private final List<String> tags;
    private final WeakReference<EditText> editTextWeakReference;
    private final WeakReference<LinearLayout> resultWeakReference;

    private CreateTag tagCreate;

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
        if (!text.trim().isEmpty() && text.endsWith(DELIMITER)) {
            EditText editText = editTextWeakReference.get();
            String value = text.trim();
            if (addUniqueTag(value)) {

                TextView tv;
                if (tagCreate == null) {
                    tv = new TextView(editText.getContext());
                    tv.setText(value);
                } else {
                    tv = (TextView) tagCreate.createTag(value);
                }
                resultWeakReference.get().addView(tv);
            }

            editText.removeTextChangedListener(this);
            editText.setText("");
            editText.addTextChangedListener(this);
        }
    }

    private boolean addUniqueTag(String tag) {
        for (int i = 0; i < tags.size(); ++i) {
            if (tag.equalsIgnoreCase(tags.get(i))) {
                tags.set(i, tag);
                return false;
            }
        }

        tags.add(tag);
        return true;
    }

    public void setOnCreateTag(CreateTag tag) {
        this.tagCreate = tag;
    }

    public List<String> getTags() {
        return tags;
    }
}
