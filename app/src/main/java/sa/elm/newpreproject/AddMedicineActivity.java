package sa.elm.newpreproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;


public class AddMedicineActivity extends AppCompatActivity implements View.OnClickListener, IPickResult {

    String name, dose, freq, time, day, pill, img, aboutmed, medid;

    EditText medName, about, DaysBox, PillBox, DoseBox, FrequencyBox;
    String prod_img;
    public static EditText txtTime;

    Button minsDose, plusDose, minsFrequency, plusFrequency, btnTimePicker, saveButton;
    public static final int GALLERY_REQUEST_CODE = 1;
    Cursor cursor;
    ImageView medImge, setImg;
    int countPill = 0;
    int countFrequncy = 0;
    MyDatabase mydb;
    boolean isVeiw = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        setTitle(R.string.add_med);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();


        name = intent.getStringExtra("Mname");
        dose = intent.getStringExtra("doseOfpill");
        freq = intent.getStringExtra("freq");
        time = intent.getStringExtra("time");
        day = intent.getStringExtra("Ndays");
        pill = intent.getStringExtra("pill");
        img = intent.getStringExtra("img");
        aboutmed = intent.getStringExtra("about");
        medid = intent.getStringExtra("medid");
        isVeiw = intent.getBooleanExtra("editkey", false);
        Toast.makeText(getApplicationContext(), " " + isVeiw,
                Toast.LENGTH_SHORT).show();


        setImg = findViewById(R.id.personal);


        about = findViewById(R.id.aboutTheMedicine);
        btnTimePicker = findViewById(R.id.btn_time);
        btnTimePicker.setOnClickListener(this);

        minsDose = findViewById(R.id.minusOfpill);
        minsDose.setOnClickListener(this);
        plusDose = findViewById(R.id.plusOfpill);
        plusDose.setOnClickListener(this);

        saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(this);

        minsFrequency = findViewById(R.id.minusFrequncy);
        minsFrequency.setOnClickListener(this);
        plusFrequency = findViewById(R.id.plusFrequncy);
        plusFrequency.setOnClickListener(this);

        medImge = findViewById(R.id.med_img_box);
        medImge.setOnClickListener(this);
        txtTime = findViewById(R.id.in_time);
       // txtTime.setEnabled(false);

        medName = findViewById(R.id.nameET);
        DaysBox = findViewById(R.id.days_box);
        PillBox = findViewById(R.id.pillNumber_box);
        FrequencyBox = findViewById(R.id.frwquency_box);
        DoseBox = findViewById(R.id.dose_box);
        DoseBox.setEnabled(false);
        FrequencyBox.setEnabled(false);


        if (isVeiw) {
            medName.setText(name);
            medName.setEnabled(false);
            DoseBox.setText(dose);
            minsDose.setVisibility(View.GONE);
            plusDose.setVisibility(View.GONE);
            DoseBox.setEnabled(false);
            FrequencyBox.setText(freq);
            FrequencyBox.setEnabled(false);
            minsFrequency.setVisibility(View.GONE);
            plusFrequency.setVisibility(View.GONE);
            txtTime.setText(time);
            btnTimePicker.setEnabled(false);
            DaysBox.setText(day);
            DaysBox.setEnabled(false);
            medImge.setEnabled(false);
            medImge.setVisibility(View.GONE);
            btnTimePicker.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            PillBox.setText(pill);
            PillBox.setEnabled(false);
            about.setText(aboutmed);
            about.setEnabled(false);
        }

        mydb = new MyDatabase(AddMedicineActivity.this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (medName.getText().toString().equals("") || DoseBox.getText().toString().equals("") || FrequencyBox.getText().toString().equals("") || DaysBox.getText().toString().equals("") || PillBox.getText().toString().equals("") || txtTime.getText().toString().equals("")) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(AddMedicineActivity.this).create();
                    alertDialog.setTitle(getString(R.string.error));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.complet));
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();

                } else {
                    mydb.insertmedicne(medName.getText().toString(), DoseBox.getText().toString(), FrequencyBox.getText().toString(), txtTime.getText().toString(), DaysBox.getText().toString(), PillBox.getText().toString(), prod_img, about.getText().toString());
                    Intent i = new Intent(AddMedicineActivity.this, medicine_List.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }//onCreate()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delet:

                final AlertDialog alertDialog = new AlertDialog.Builder(AddMedicineActivity.this).create();
                alertDialog.setTitle(getString(R.string.want_delet));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.if_want_to_delet));
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.cancele), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.Delet_med), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MyDatabase dbHelper = new MyDatabase(AddMedicineActivity.this);
                        dbHelper.removeMedicine(medid);
                        Intent i = new Intent(AddMedicineActivity.this, medicine_List.class);
                        startActivity(i);
                        finish();

                    }
                });
                alertDialog.show();


                break;
            case R.id.edit:

                medName.setText(name);
                medName.setEnabled(true);
                DoseBox.setText(dose);
                medImge.setEnabled(true);
                minsDose.setVisibility(View.VISIBLE);
                plusDose.setVisibility(View.VISIBLE);
                DoseBox.setEnabled(true);
                FrequencyBox.setText(freq);
                FrequencyBox.setEnabled(true);
                minsFrequency.setVisibility(View.VISIBLE);
                plusFrequency.setVisibility(View.VISIBLE);
                txtTime.setText(time);
                btnTimePicker.setEnabled(true);
                DaysBox.setText(day);
                medImge.setVisibility(View.VISIBLE);
                DaysBox.setEnabled(true);
                btnTimePicker.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                PillBox.setText(pill);
                PillBox.setEnabled(true);
                about.setText(aboutmed);
                about.setEnabled(true);
                saveButton.setText(R.string.save_chang);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mydb.editMedicine(medid, medName.getText().toString(), DoseBox.getText().toString(), FrequencyBox.getText().toString(), txtTime.getText().toString(), DaysBox.getText().toString(), PillBox.getText().toString(), prod_img, about.getText().toString()) > 0) {
                            Toast.makeText(getApplicationContext(), " Edit successfully ",
                                    Toast.LENGTH_SHORT).show();


                        }
                        Intent i = new Intent(AddMedicineActivity.this, medicine_List.class);
                        startActivity(i);
                        finish();
                    }
                });

                break;

        } //end switch
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isVeiw) {

            getMenuInflater().inflate(R.menu.selectoption_tool, menu);
        }
        return true;
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            prod_img = pickResult.getUri().toString();
            setImg.setImageURI(pickResult.getUri());
        } else {
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    ///String path = selectedImage.toString();
                    setImg.setImageURI(selectedImage);

                    break;

            } //end switch
    } //end onActivityResult()*/


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.plusOfpill:
                countPill = Integer.parseInt(DoseBox.getText().toString());
                countPill++;
                DoseBox.setText(countPill + "");

                break;

            case R.id.minusOfpill:
                if (countPill > 0) {
                    countPill--;
                    DoseBox.setText(countPill + "");
                }//end if
                break;

            case R.id.plusFrequncy:
                countFrequncy = Integer.parseInt(FrequencyBox.getText().toString());
                countFrequncy++;
                FrequencyBox.setText(countFrequncy + "");
                break;

            case R.id.minusFrequncy:
                if (countFrequncy > 0) {
                    countFrequncy--;
                    FrequencyBox.setText(countFrequncy + "");
                }//end if
                break;

            case R.id.btn_time:
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(this.getSupportFragmentManager(), "timePicker");
                break;

            case R.id.med_img_box:
                PickImageDialog.build(new PickSetup()).show(AddMedicineActivity.this);
                break;

        }//end switch()

    }//end onClick

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } // end onSupportNavigateUp()


}//end AddMedicineActivity




