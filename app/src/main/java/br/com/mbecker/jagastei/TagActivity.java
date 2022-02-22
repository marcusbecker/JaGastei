package br.com.mbecker.jagastei;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.mbecker.jagastei.db.TagModel;
import br.com.mbecker.jagastei.domain.Domain;
import br.com.mbecker.jagastei.domain.ServiceDomain;

public class TagActivity extends AppCompatActivity {

    private ServiceDomain service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        service = Domain.getService(TagActivity.this);
        Spinner spinner = (Spinner) findViewById(R.id.spTag);

        List<TagModel> tags = service.listarTags();
        String[] items = tags.stream().map(t -> t.getTag()).toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

}