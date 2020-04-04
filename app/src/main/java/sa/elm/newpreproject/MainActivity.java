package sa.elm.newpreproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    String phoneNo;
    String message;
    MyDatabase myDatabase;
    Cursor cursor;
    String number, id;
    ArrayList<RelativeInfo> relativeInfos = new ArrayList<>();
    String medicine_name, medicine_time;
    ArrayList<MedicineCardInfo> medicineCardInfos = new ArrayList<>();

    ImageView pills_img;
    ImageView setting_Img;
    ImageView relative_img;
    ImageView mylist_img;


    TextView pills_txt;
    TextView setting_txt;
    TextView relative_txt;
    TextView mylist_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pills_txt = findViewById(R.id.add_medicine_txt);
        pills_txt.setOnClickListener(this);
        setting_txt = findViewById(R.id.setting_txt);
        setting_txt.setOnClickListener(this);
        relative_txt = findViewById(R.id.add_relative_text);
        relative_txt.setOnClickListener(this);
        mylist_txt = findViewById(R.id.my_list_txt);
        mylist_txt.setOnClickListener(this);


        pills_img = findViewById(R.id.pills_img);
        pills_img.setOnClickListener(this);

        setting_Img = findViewById(R.id.setting_img);
        setting_Img.setOnClickListener(this);

        relative_img = findViewById(R.id.relative_img);
        relative_img.setOnClickListener(this);

        mylist_img = findViewById(R.id.mylist_img);
        mylist_img.setOnClickListener(this);
        //stratTimer();
        alarm();
    }
    //end onCreate()

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.setting_txt:
                intent = new Intent(this, SettingActivity.class);

                startActivity(intent);
                break;

            case R.id.setting_img:
                intent = new Intent(this, SettingActivity.class);

                startActivity(intent);
                break;

            case R.id.add_medicine_txt:
                intent = new Intent(this, AddMedicineActivity.class);

                startActivity(intent);

                break;

            case R.id.pills_img:
                intent = new Intent(this, AddMedicineActivity.class);

                startActivity(intent);
                break;

            case R.id.my_list_txt:
                intent = new Intent(this, medicine_List.class);

                startActivity(intent);
                break;

            case R.id.mylist_img:
                intent = new Intent(this, medicine_List.class);

                startActivity(intent);
                break;

            case R.id.add_relative_text:
                intent = new Intent(this, RelativeActivity.class);

                startActivity(intent);
                break;

            case R.id.relative_img:
                intent = new Intent(this, RelativeActivity.class);

                startActivity(intent);
                break;

        }//end swich

    }//end onClick

    Timer timer = new Timer();
    CountDownTimer countDownTimer = null;
    void stratTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long l) {
                myDatabase = new MyDatabase(MainActivity.this);
                cursor = myDatabase.getMedicine();
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            id = cursor.getString(0);
                            medicine_name = cursor.getString(1);
                            medicine_time = cursor.getString(4);
                            // Toast.makeText(this, medicine_time.toString(), Toast.LENGTH_LONG).show();
                            MedicineCardInfo info = new MedicineCardInfo(medicine_name, null, null, null, null, null
                                    , medicine_time, null, id);
                            medicineCardInfos.add(info);
                        } while (cursor.moveToNext());
                    }
                }

                Calendar c = Calendar.getInstance();
                long time = c.getTimeInMillis();
                SharedPreferences sh = getSharedPreferences("save", 0);
                SharedPreferences.Editor e = sh.edit();
                e.putLong("mills", time);
                e.putBoolean("state", true);
                e.commit();

                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

                String time_string = simpleDateFormat.format(date);
                sendSMSMessage();
                if (!time_string.equals(medicine_time)) {

                    Intent intent = new Intent(MainActivity.this, MyReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pendingIntent);

                    final FlashlightProvider flashlightProvider = new FlashlightProvider(MainActivity.this);
                    final VibratorProvider vibratorProvider = new VibratorProvider(MainActivity.this);

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("زمن الدواء " + medicine_name);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(" عزيزي الرجاء مرعاة مواعيد " + medicine_name + " والتزام اخذ الدواء لضمان صحتك ");
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flashlightProvider.turnFlashlightOff();
                            vibratorProvider.turnOffVibrator();
                        }
                    });
                    alertDialog.show();

                }
            }

            @Override
            public void onFinish() {
                sendSMSMessage();
            }
        };
        countDownTimer.start();
    }
    void cancelTimer(){
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }
    public void alarm() {

        myDatabase = new MyDatabase(MainActivity.this);
        cursor = myDatabase.getMedicine();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(0);
                    medicine_name = cursor.getString(1);
                    medicine_time = cursor.getString(4);
                    // Toast.makeText(this, medicine_time.toString(), Toast.LENGTH_LONG).show();
                    MedicineCardInfo info = new MedicineCardInfo(medicine_name, null, null, null, null, null
                            , medicine_time, null, id);
                    medicineCardInfos.add(info);
                } while (cursor.moveToNext());
            }
        }

        Calendar c = Calendar.getInstance();
        long time = c.getTimeInMillis();
        SharedPreferences sh = getSharedPreferences("save", 0);
        SharedPreferences.Editor e = sh.edit();
        e.putLong("mills", time);
        e.putBoolean("state", true);
        e.commit();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String time_string = simpleDateFormat.format(date);
        sendSMSMessage();
        if (!time_string.equals(medicine_time)) {

            Intent intent = new Intent(MainActivity.this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pendingIntent);

            final FlashlightProvider flashlightProvider = new FlashlightProvider(MainActivity.this);
            final VibratorProvider vibratorProvider = new VibratorProvider(MainActivity.this);

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("زمن الدواء " + medicine_name);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(" عزيزي الرجاء مرعاة مواعيد " + medicine_name + " والتزام اخذ الدواء لضمان صحتك ");
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    flashlightProvider.turnFlashlightOff();
                    vibratorProvider.turnOffVibrator();
                }
            });
            alertDialog.show();
            if (true) {
                // Toast.makeText(this,"Step one",Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
                alarmManager.cancel(pendingIntent);
                sendSMSMessage();
            }
        }
    }
//

    public void sendSMSMessage() {
        Context context = null;

        myDatabase = new MyDatabase(MainActivity.this);
        cursor = myDatabase.getData();

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(0);
                    number = cursor.getString(2);
                    RelativeInfo g1 = new RelativeInfo(id, null, number);
                    relativeInfos.add(g1);

                } while (cursor.moveToNext());
            }

        }
        phoneNo = number;
        message = "الرجاء تبليغ عزيزك بمؤعد الدواء";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
       // Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
