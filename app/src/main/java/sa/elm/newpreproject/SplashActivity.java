package sa.elm.newpreproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    public static int timer_splash = 2000;


    //start of onCreate()-----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
                                      public void run() {
                                          loginHandler();
                                   /*      Intent intent = new Intent(SplashActivity.this, SginUpActivity.class);
                                          startActivity(intent);
                                          finish();*/
                                      }//end run()
                                  }//end postDelayed()
                , timer_splash);
    }//end onCreate()---------

    public void loginHandler() {

        if (MysharedPreferences.getBoolean(this,Constants.Keys.IS_LOGGED_IN, false) == true) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, SginUpActivity.class);
            startActivity(intent);
            finish();
        } //end else


    } // end loginHandler()

}//end SplashActivity
