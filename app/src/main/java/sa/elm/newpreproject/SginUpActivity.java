package sa.elm.newpreproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class SginUpActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SginUpActivity";
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    ImageView downArrow;
    EditText username, dob;

    String date;
    MysharedPreferences mysharedPreferences;
    Button start;
    int CurrentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgin_up);
        username = findViewById(R.id.usernemwET);
        downArrow = findViewById(R.id.downArrow);
        downArrow.setOnClickListener(this);

        dob = findViewById(R.id.date_of_birth);
        dob.setEnabled(false);
        start = findViewById(R.id.start_btn);
        start.setOnClickListener(this);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                CurrentYear = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SginUpActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDataSetListener, CurrentYear, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }//end onClick()
        });//end setOnClickListener()

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, final int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateset: mm/dd/yyy :" + month + "/" + day + "/" + year);
                int different = CurrentYear - year;
                if (different >= 18) {
                    date = month + "/" + day + "/" + year;

                    dob.setText(date);

                } else {
                    Toast.makeText(getApplicationContext(), "The eligible age is 18 years or older ",
                            Toast.LENGTH_SHORT).show();
                }//end else

            }//end onDateSet()

        };
    }//end onCreate()

    @Override
    public void onClick(View view) {
        final String KEY = "log_in";
        switch (view.getId()) {

            case R.id.start_btn:
                if (username.getText().toString().equals("") || dob.getText().toString().equals("")) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(SginUpActivity.this).create();
                    alertDialog.setTitle(R.string.error);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getText(R.string.complet));
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();

                }else{
                    MysharedPreferences.putBoolean(this, Constants.Keys.IS_LOGGED_IN, true);
                    Intent intent = new Intent(SginUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }





                break;
        }//end switch()
    }//end onClick()
}//end SginUpActivity