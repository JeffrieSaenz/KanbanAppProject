package com.example.user.kanbanapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Notificacion {

    String titulo;
    String mensaje;

    public Notificacion(String t, String m){
        titulo = t;
        mensaje = m ;
    }

    public static void creaNotificacion(long when, String notificationTitle,
                                        String notificationContent, String notificationUrl, Context ctx) {
        try {

            Intent notificationIntent;


            Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(),
                    R.mipmap.ic_launcher);
            int smalIcon = R.mipmap.ic_launcher;

            /* Valida la url y crea un Intent */
            //if (!"".equals(notificationUrl)) {
                notificationIntent = new Intent(ctx,Backlog.class);
            //} else {
                //notificationIntent = new Intent();
            //}

            /* Crea PendingIntent */
            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationManager notificationManager = (NotificationManager) ctx
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            /* Construye la notificacion */
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                    ctx).setWhen(when).setContentText(notificationContent)
                    .setContentTitle(notificationTitle).setSmallIcon(smalIcon)
                    .setAutoCancel(true).setTicker(notificationTitle)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent);

            notificationManager.notify((int) when, notificationBuilder.build());

        } catch (Exception e) {
            Log.e("Notificacion", "createNotification::" + e.getMessage());
        }

    }
}
