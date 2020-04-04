package sa.elm.newpreproject;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //call set language action
        handleDefaultAppLocale();

    } //end onCreate()


    public void handleDefaultAppLocale() {

        setAppLocale(MysharedPreferences.getString(this, Constants.Keys.APP_LANGUAGE, getSystemLocaleLanguage()));


    } //end handleDefaultAppLocale()


    public void setAppLocale(String language) {

        MysharedPreferences.putString(this, Constants.Keys.APP_LANGUAGE, language);

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

    } //end setAppLocale()


    protected String getSystemLocaleLanguage() {
        Locale locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getSystem().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getSystem().getConfiguration().locale;
        }//end else
        return locale.getLanguage();

    }// end getSystemLocaleLanguage()


} //end class
