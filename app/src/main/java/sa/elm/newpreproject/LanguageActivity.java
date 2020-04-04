package sa.elm.newpreproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout layout, linearLayout;
    ImageView img1, img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setTitle(R.string.langoug);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        init();
    }// end onCreate()

    public void init() {

        layout = findViewById(R.id.engilsh);
        linearLayout = findViewById(R.id.arabic);
        img1 = findViewById(R.id.containerImg);
        img2 = findViewById(R.id.containerImg2);
        layout.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

        //if arabic language set the check icon
        if (MysharedPreferences.getString(this, Constants.Keys.APP_LANGUAGE, "en").equals("ar")) {
            img2.setVisibility(View.VISIBLE);
            img1.setVisibility(View.INVISIBLE);
        }//end if

        //for default english language set the check icon
        else {
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.INVISIBLE);

        } //end else

    } //end init()

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.engilsh:
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                setLanguage("en");
                break;
            case R.id.arabic:
                img2.setVisibility(View.VISIBLE);
                img1.setVisibility(View.INVISIBLE);
                setLanguage("ar");
                break;
        } //end switch

    } //end onClick()

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } // end onSupportNavigateUp()


    public void setLanguage(String language) {
        MysharedPreferences.putString(this, Constants.Keys.APP_LANGUAGE, language);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    } //end setLanguage()


}//endClass



