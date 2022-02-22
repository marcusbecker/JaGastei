package br.com.mbecker.jagastei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import br.com.mbecker.jagastei.adapter.TextViewHelper;
import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.domain.Domain;
import br.com.mbecker.jagastei.domain.ServiceDomain;
import br.com.mbecker.jagastei.util.MoneyTextWatcher;
import br.com.mbecker.jagastei.util.TagTextWatcher;
import br.com.mbecker.jagastei.util.TagUtil;
import br.com.mbecker.jagastei.util.Util;


public class CadastroActivity extends AppCompatActivity {

    private EditText mValor;
    private EditText mObs;
    private LinearLayout mTagResult;
    private ServiceDomain service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean exibirFrases = pref.getBoolean(getResources().getString(R.string.pref_key_frases_gastos), true);

        TextView aviso = findViewById(R.id.tvAviso);
        if (exibirFrases) {
            String[] arr = getResources().getStringArray(R.array.avisos_array);
            aviso.setText(String.format("\"%s\"", arr[new Random().nextInt(arr.length)]));
        } else {
            aviso.setVisibility(View.INVISIBLE);
        }

        service = Domain.getService(CadastroActivity.this);

        //List<TagModel> tagModels = db.listarTags();

        mObs = findViewById(R.id.etObs);
        mValor = findViewById(R.id.etValor);
        mTagResult = findViewById(R.id.llTags);

        TextViewHelper helper = new TextViewHelper(this);
        TagTextWatcher tagWatcher = new TagTextWatcher(mObs, mTagResult);
        tagWatcher.setOnCreateTag((tag) -> helper.build(tag));

        mObs.addTextChangedListener(tagWatcher);

        mValor.requestFocus();
        mValor.addTextChangedListener(new MoneyTextWatcher(mValor, NumberFormat.getCurrencyInstance()));

        Button btnGastar = findViewById(R.id.btGastar);
        btnGastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigDecimal parsed = Util.toBigDecimal(Util.somenteNumeros(mValor.getText().toString()));
                double valor = parsed.doubleValue();

                if (valor == 0.0) {
                    Snackbar.make(view, R.string.erro_valor, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }

                Calendar c = GregorianCalendar.getInstance();
                String mesAno = Util.mesAno(c);
                GastoModel g = new GastoModel();

                List<String> tags = tagWatcher.getTags();
                String obs = mObs.getText().toString().trim();
                if (!obs.isEmpty()) {
                    tags.add(obs);
                }

                g.setMesAno(mesAno);
                g.setObs(TagUtil.tagsToString(tags));
                g.setQuando(c.getTimeInMillis());
                g.setValor(valor);

                mObs.setText("");
                mValor.setText("");

                long id = service.salvarGasto(g);
                service.atualizaTags(id, tags);
                carregarLista();
            }
        });
    }

    private void carregarLista() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}
