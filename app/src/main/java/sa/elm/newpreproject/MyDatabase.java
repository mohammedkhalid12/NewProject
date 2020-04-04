package sa.elm.newpreproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {


    public MyDatabase(@Nullable Context context) {
        super(context, "MyDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table relative(id integer primary key autoincrement,relativename varchar(50),number varchar(50) )");
        db.execSQL("create table medicine(id integer primary key autoincrement,medicineName varchar(50),doseOfpill varchar(50),frquncy varchar(50),startTime varchar(50),numOfDys varchar(50),pillNum varchar(50),imgpath varchar(50) ,nots varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
     }


    public void insertmedicne(String medicineName, String doseOfpill, String frquncy, String startTime, String numOfDys, String pillNum,String imgpath, String nots) {
        SQLiteDatabase sd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("medicineName", medicineName);
        cv.put("doseOfpill", doseOfpill);
        cv.put("frquncy", frquncy);
        cv.put("startTime", startTime);
        cv.put("numOfDys", numOfDys);
        cv.put("pillNum", pillNum);
        cv.put("imgpath", imgpath);
        cv.put("nots", nots);
        sd.insert("medicine", null, cv);
    }
    public Cursor getMedicine() {
        SQLiteDatabase asd = this.getReadableDatabase();
        Cursor c = asd.rawQuery("select * from medicine ", null);
        return c;

    }

    //----------------
    public void removeMedicine(String medId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("medicine", "id = ? ", new String[]{(medId)});
    }


    public int editMedicine(String id,String medicineName, String doseOfpill, String frquncy, String startTime, String numOfDys, String pillNum,String imgpath, String nots) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("medicineName", medicineName);
        cv.put("doseOfpill", doseOfpill);
        cv.put("frquncy", frquncy);
        cv.put("startTime", startTime);
        cv.put("numOfDys", numOfDys);
        cv.put("pillNum", pillNum);
        cv.put("imgpath", imgpath);
        cv.put("nots", nots);
      return db.update("medicine", cv, "id = ? ", new String[]{(id)});
    }


    //end of Medicine Functions

    public void insertData(String relativename, String number) {
        SQLiteDatabase sd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("relativename", relativename);
        cv.put("number", number);
        sd.insert("relative", null, cv);
    }

    public void removePlace(String placeId) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("relative", "id = ? ", new String[]{(placeId)});
    }

    public int editRelativeinfo(String id, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("relativename", name);
        contentValues.put("number", phone);
        return db.update("relative", contentValues, "id = ? ", new String[]{(id)});
    }


    public Cursor getData() {

        SQLiteDatabase asd = this.getReadableDatabase();
        Cursor c = asd.rawQuery("select * from relative ", null);
        return c;

    }

    public int getNotesCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from relative ", null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
}
