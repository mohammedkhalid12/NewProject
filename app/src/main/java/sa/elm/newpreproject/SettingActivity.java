package sa.elm.newpreproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout layout;
    Switch sound, vaiber, flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layout = this.findViewById(R.id.language);
        layout.setOnClickListener(this);

        sound = findViewById(R.id.switchSound);
        vaiber = findViewById(R.id.switchvibration);
        flash = findViewById(R.id.switchflash);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });// end setOnClickListener()
    }//end onCreate()

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    } //end onClick()


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } // end onSupportNavigateUp()


}//end class