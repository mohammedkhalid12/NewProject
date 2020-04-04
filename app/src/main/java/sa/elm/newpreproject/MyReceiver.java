package sa.elm.newpreproject;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class MyReceiver extends BroadcastReceiver {

    String phoneNo;
    String message;
    MyDatabase myDatabase;
    Cursor cursor;
    String number, id;

    @Override
    public void onReceive(Context context, Intent intent) {

        final FlashlightProvider flashlightProvider = new FlashlightProvider(context);
        final VibratorProvider vibratorProvider = new VibratorProvider(context);
        vibratorProvider.turnOnVibrator();
        flashlightProvider.turnFlashlightOn();

//        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("زمن الدواء");
//        alertDialog.setCancelable(false);
//        alertDialog.setMessage("By deleting this, item will permanently be deleted. Are you still want to delete this?");
//        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                flashlightProvider.turnFlashlightOff();
//                vibratorProvider.turnOffVibrator();
//            }
//        });
    }


}
