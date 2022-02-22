package br.com.mbecker.jagastei;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.mbecker.jagastei.adapter.GastoAdapter;
import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.db.TagModel;
import br.com.mbecker.jagastei.domain.Domain;
import br.com.mbecker.jagastei.domain.ServiceDomain;

public class TagActivity extends AppCompatActivity {

    private ServiceDomain service;
    private List<TagModel> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        service = Domain.getService(TagActivity.this);
        Spinner spinner = (Spinner) findViewById(R.id.spTag);

        tags = service.listarTags();
        String[] items = tags.stream().map(t -> t.getTag()).toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        List<GastoModel> lst = service.listarGastosPorTag(tags.get(0).getId());
        RecyclerView recyclerView = findViewById(R.id.gastos_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TagActivity.this));

        recyclerView.setAdapter(new GastoAdapter(lst));
    }

}