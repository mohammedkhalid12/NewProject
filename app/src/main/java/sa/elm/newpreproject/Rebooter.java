package sa.elm.newpreproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Rebooter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("save", 0);
        boolean state = sharedPreferences.getBoolean("state", false);
        long time = sharedPreferences.getLong("mills", System.currentTimeMillis());
        if (state) {
            Intent i = new Intent(context, MyReceiver.class);
            PendingIntent p = PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager v3 = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            v3.set(AlarmManager.RTC_WAKEUP, time, p);
        }

    }
    }

