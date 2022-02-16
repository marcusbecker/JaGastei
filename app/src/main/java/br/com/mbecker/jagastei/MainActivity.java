package br.com.mbecker.jagastei;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import br.com.mbecker.jagastei.util.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String ARG_PARAM_MES_SEL = "mesSel";
    private static final short NUM_MESES = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intentCadastro = new Intent(this, CadastroActivity.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCadastro);
            }
        });

        ExtratoMesPagerAdapter extMesAdapter = new ExtratoMesPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(extMesAdapter);

        Util.sdf = new SimpleDateFormat(getString(R.string.frm_mes_lista), Locale.getDefault());

        Context context = getApplicationContext();

        final Intent alrmIntent = new Intent(context, AlarmBroadcastReceiver.class);
        boolean noAlarm = PendingIntent.getBroadcast(MainActivity.this, AlarmBroadcastReceiver.REQUEST_CODE, alrmIntent, PendingIntent.FLAG_NO_CREATE) == null;
        if (noAlarm) {
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AlarmBroadcastReceiver.REQUEST_CODE, alrmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, 9);
            alarmMgr.setInexactRepeating(AlarmManager.RTC,
                    c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private static ExtratoMesFragment newInstance(short mesSel) {
        ExtratoMesFragment fragment = new ExtratoMesFragment();
        Bundle args = new Bundle();
        args.putShort(ARG_PARAM_MES_SEL, mesSel);
        fragment.setArguments(args);
        return fragment;
    }

    class ExtratoMesPagerAdapter extends FragmentStatePagerAdapter {

        ExtratoMesPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return MainActivity.newInstance((short) position);
        }

        @Override
        public int getCount() {
            return NUM_MESES;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "" + position;
        }
    }


    @SuppressWarnings("WeakerAccess")
    public static class ExtratoMesFragment extends Fragment {
        private short mes;
        private JaGasteiDbHelper db;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                db = new JaGasteiDbHelper(getContext());
                mes = getArguments().getShort(ARG_PARAM_MES_SEL);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_valor_mes, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            String mesAtual;
            Calendar c = Util.ajustarMes(mes);
            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

            mesAtual = legendaMes(c);
            if (c.get(Calendar.YEAR) != anoAtual) {
                mesAtual += " (" + c.get(Calendar.YEAR) + ")";
            }

            String mesAno = Util.mesAno(c);
            List<GastoModel> lst = db.listarGastos(mesAno);

            TextView mes = view.findViewById(R.id.tvMes);
            TextView total = view.findViewById(R.id.tvTotal);

            mes.setText(mesAtual);
            total.setText(Util.somarGastos(lst));

            RecyclerView recyclerView = view.findViewById(R.id.gastos_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

            recyclerView.setAdapter(new GastoAdapter(lst));
        }

        private String legendaMes(Calendar c) {
            String mesAtual;
            if (mes == 0) {
                mesAtual = getString(R.string.mes_atual);
            } else {
                mesAtual = getResources().getStringArray(R.array.meses_array)[c.get(Calendar.MONTH)];
            }

            return mesAtual;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, PreferenciaActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
