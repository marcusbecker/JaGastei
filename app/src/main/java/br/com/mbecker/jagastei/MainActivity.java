package br.com.mbecker.jagastei;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.db.JaGasteiDbHelper;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText mValor;
    private EditText mObs;
    private Button btnGastar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView aviso = findViewById(R.id.tvAviso);
        String[] arr = getResources().getStringArray(R.array.avisos_array);
        aviso.setText(arr[new Random().nextInt(arr.length)]);

        mValor = findViewById(R.id.etValor);
        mObs = findViewById(R.id.etObs);
        btnGastar = findViewById(R.id.btGastar);
        btnGastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = GregorianCalendar.getInstance();
                String mesAno = (c.get(Calendar.MONTH) + 1) + "" + c.get(Calendar.YEAR);
                GastoModel g = new GastoModel();
                g.setMesAno(mesAno);
                g.setObs(mObs.getText().toString());
                g.setQuando(c.getTimeInMillis());
                g.setValor(Double.valueOf(mValor.getText().toString()));

                JaGasteiDbHelper db = new JaGasteiDbHelper(MainActivity.this);
                db.salvarGasto(g);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        JaGasteiDbHelper db = new JaGasteiDbHelper(MainActivity.this);
        List<GastoModel> lst = db.listarGasto();
        for (GastoModel g : lst) {
            Log.i(getClass().getName(), g.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
