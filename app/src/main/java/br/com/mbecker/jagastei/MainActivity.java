package br.com.mbecker.jagastei;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import br.com.mbecker.jagastei.adapter.GastoAdapter;
import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.db.JaGasteiDbHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JaGasteiDbHelper db;
    private RecyclerView recyclerView;

    private ViewPager pager;
    private FAdapter fAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intentCadastro = new Intent(getBaseContext(), CadastroActivity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(intentCadastro);
            }
        });

        fAdapter = new FAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.pager);
        pager.setAdapter(fAdapter);

        db = new JaGasteiDbHelper(MainActivity.this);
    }

    public class FAdapter extends FragmentStatePagerAdapter {

        public FAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return newInstance(position, "");
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "teste " + position;
        }
    }

    public static DemoFragment newInstance(int param1, String param2) {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putInt("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static class DemoFragment extends Fragment {
        private JaGasteiDbHelper db;
        private RecyclerView recyclerView;
        private int mes;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mes = getArguments().getInt("ARG_PARAM1");
                //mParam2 = getArguments().getString(ARG_PARAM2);
            }

        }


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_blank, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            db = new JaGasteiDbHelper(view.getContext());

            String mesAtual;
            switch (mes) {
                case 0:
                    mesAtual = getString(R.string.mes_atual);
                    break;
                case 1:
                    mesAtual = getString(R.string.mes_jan);
                    break;
                case 2:
                    mesAtual = getString(R.string.mes_fev);
                    break;
                case 3:
                    mesAtual = getString(R.string.mes_mar);
                    break;
                case 4:
                    mesAtual = getString(R.string.mes_abr);
                    break;
                default:
                    mesAtual = "88888";
            }

            TextView mes = view.findViewById(R.id.tvMes);
            TextView total = view.findViewById(R.id.tvTotal);

            mes.setText(mesAtual);
            total.setText(db.totalMes());

            recyclerView = view.findViewById(R.id.gastos_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

            recyclerView.setAdapter(new GastoAdapter(db.listarGasto()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //carregarLista();
    }

    private void carregarLista() {
        final List<GastoModel> lst = db.listarGasto();

        GastoAdapter mAdapter = new GastoAdapter(lst);
        recyclerView.setAdapter(mAdapter);

        for (GastoModel g : lst) {
            //Log.i(getClass().getName(), g.toString());
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
