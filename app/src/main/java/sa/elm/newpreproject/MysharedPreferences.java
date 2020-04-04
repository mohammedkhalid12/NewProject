package sa.elm.newpreproject;

import android.content.Context;
import android.content.SharedPreferences;

public class MysharedPreferences {
    private static SharedPreferences pref;


    private MysharedPreferences() {

    }

    public static SharedPreferences getInstance(Context context) {

        if (pref == null) {

            pref = context.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        }
        return pref;

    } //end getInstance()

    public static void ClearData(Context context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.clear();
        editor.commit();


    }// end ClearData()

    public static void ClearValue(Context context, String key) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.remove(key);
        editor.commit();

    }// end ClearValue()

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, value);
        editor.commit();
    }// end putString()

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }//end  putInt()

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }//end putBoolean

    public static void putFloat(Context context, String key, Float value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putFloat(key, value);
        editor.commit();
    }//end putFloat()

    public static String getString(Context context, String key, String defaultValue) {
        return getInstance(context).getString(key, defaultValue);


    }//end getString()


    public static int getInt(Context context, String key, int defaultValue) {
        return getInstance(context).getInt(key, defaultValue);


    } //end getInt()

    public static Boolean getBoolean(Context context, String key, Boolean defaultValue) {
        return getInstance(context).getBoolean(key, defaultValue);


    }//end  getBoolean()

    public static Float getFloat(Context context, String key, Float defaultValue) {
        return getInstance(context).getFloat(key, defaultValue);


    } //end  getFloat()


} //end class
