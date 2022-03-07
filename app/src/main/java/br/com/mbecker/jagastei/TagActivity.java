package br.com.mbecker.jagastei;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.mbecker.jagastei.adapter.GastoAdapter;
import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.db.TagModel;
import br.com.mbecker.jagastei.domain.Domain;
import br.com.mbecker.jagastei.domain.ServiceDomain;

public class TagActivity extends AppCompatActivity {

    private ServiceDomain service;
    private List<TagModel> tags;
    private Spinner spinner;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        service = Domain.getService(TagActivity.this);

        tags = service.listarTags();
        tags.add(0, new TagModel(0, getString(R.string.tag_filter), ""));
        String[] items = tags.stream().map(t -> t.getTag()).toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spinner = findViewById(R.id.spTag);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (tags.get(position).getId() > 0) {
                    List<GastoModel> lst = service.listarGastosPorTag(tags.get(position).getId());
                    recyclerView.setAdapter(new GastoAdapter(lst));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        recyclerView = findViewById(R.id.gastos_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TagActivity.this));
    }

}