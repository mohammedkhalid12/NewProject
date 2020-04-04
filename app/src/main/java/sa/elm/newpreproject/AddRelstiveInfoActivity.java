package sa.elm.newpreproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

public class AddRelstiveInfoActivity extends AppCompatActivity {

    String name, num, relativeid;
    EditText edName, edNumber;
    Button btn;
    MyDatabase mydb;
    boolean isEdit = false;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String phoneNo;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relstive_info);


        setTitle(R.string.add_reltive);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        num = intent.getStringExtra("num");
        relativeid = intent.getStringExtra("id");
        isEdit = intent.getBooleanExtra("editkey", false);
        Toast.makeText(getApplicationContext(), " " + isEdit,
                Toast.LENGTH_SHORT).show();


        edName = findViewById(R.id.userName);
        edNumber = findViewById(R.id.contectnum);


        btn = findViewById(R.id.save);

        if (isEdit) {
            edName.setText(name);
            edNumber.setText(num);
        }
        

        mydb = new MyDatabase(AddRelstiveInfoActivity.this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEdit){
                    if(mydb.editRelativeinfo(relativeid,edName.getText().toString(),edNumber.getText().toString())>0){
                        Toast.makeText(getApplicationContext(), " Edit successfully " ,
                                Toast.LENGTH_SHORT).show();

                    }
                }else{
                mydb.insertData(edName.getText().toString(), edNumber.getText().toString());
                    Intent i = new Intent(AddRelstiveInfoActivity.this, RelativeActivity.class);
                    startActivity(i);
                    sendSMSMessage();
                }
            }

        });

    }

    protected void sendSMSMessage() {

        phoneNo = edNumber.getText().toString();
        message = edName.getText().toString();
        message = "تمت إضافتك في أحرص من احدي قريبك ";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null,message, null, null);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null," Done"+message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }




    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    } // end onSupportNavigateUp()


}
