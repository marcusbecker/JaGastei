package br.com.mbecker.jagastei;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.List;

import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.domain.Domain;
import br.com.mbecker.jagastei.domain.ServiceDomain;
import br.com.mbecker.jagastei.util.Util;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 0;
    private static final int NOTIFICATION_REQUEST_CODE = 40;

    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);

        if (dia == 1 || dia == 16) {
            ServiceDomain service = Domain.getService(context);

            String texto;
            if (dia == 1) {
                c = Util.ajustarMes((short) (mes - 1));
                texto = context.getString(R.string.notfication_msg_a);
            } else {
                texto = context.getString(R.string.notfication_msg_b);
            }

            String mesAno = Util.mesAno(c);
            List<GastoModel> lista = service.listarGastos(mesAno);

            if (lista.isEmpty()) {
                return;
            }

            String gastos = Util.somarGastos(lista);
            texto += gastos;

            PendingIntent p = PendingIntent.getActivity(context, AlarmBroadcastReceiver.NOTIFICATION_REQUEST_CODE, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder b = new NotificationCompat.Builder(context, "NO_ID");
            b.setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(context.getString(R.string.notfication_title))
                    .setContentText(texto)
                    .setContentIntent(p)
                    .setAutoCancel(true);

            NotificationManagerCompat nm = NotificationManagerCompat.from(context);
            nm.notify(AlarmBroadcastReceiver.NOTIFICATION_REQUEST_CODE, b.build());
        }
    }
}
