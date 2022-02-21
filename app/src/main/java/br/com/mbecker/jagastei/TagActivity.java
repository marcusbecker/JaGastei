package br.com.mbecker.jagastei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;
import java.util.stream.Collectors;

import br.com.mbecker.jagastei.db.JaGasteiDbHelper;
import br.com.mbecker.jagastei.db.TagModel;

public class TagActivity extends AppCompatActivity {

    private JaGasteiDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        db = new JaGasteiDbHelper(TagActivity.this);
        Spinner spinner = (Spinner) findViewById(R.id.spTag);

        List<TagModel> tags = db.listarTags();
        String[] items = tags.stream().map(t -> t.getTag()).toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

}