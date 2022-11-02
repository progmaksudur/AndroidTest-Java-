package com.example.androidtestjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidtestjava.model.UserInfo;

public class Database extends SQLiteOpenHelper {

    private static  final String DatabaseName="Userinfo.db";
    private static  final String TableName="user_list";
    private static  final String UserName="name";
    private static  final String DateOFBirth="dob";
    private static  final String Country="country";
    private static  final String City="city";
    private static  final String Skills="skill";
    private static  final String Flie="filepath";
    private static  final String ID="id";
    private static  final int Version=1;

    private Context context;
    public Database(@Nullable Context context) {
        super(context, DatabaseName, null, Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context, "Creating", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE "+TableName+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+UserName+" varchar(255),"+DateOFBirth+" varchar(255),"+
                    Country+" varchar(255),"+City+" varchar(255),"+Skills +" varchar(255) ,"+Flie +" varchar(255) )");
        }catch (Exception e){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long  insert(UserInfo info){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(UserName,info.getName());
        contentValues.put(DateOFBirth,info.getDateOfBirth());
        contentValues.put(Country,info.getCountryName());
        contentValues.put(City,info.getCityName());
        contentValues.put(Skills,info.getLanguageSkill());
        contentValues.put(Flie,info.getResumePath());
       long rowid= sqLiteDatabase.insert(TableName,null,contentValues);
       return rowid;
    }
    public Cursor viewData(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from "+TableName,null);
        return cursor;
    }
    public int deleteData(String id){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return  sqLiteDatabase.delete(TableName, ID + "=" + id, null);

    }
     public  int upgrade(String id,UserInfo info){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
         ContentValues contentValues= new ContentValues();
         contentValues.put(UserName,info.getName());
         contentValues.put(DateOFBirth,info.getDateOfBirth());
         contentValues.put(Country,info.getCountryName());
         contentValues.put(City,info.getCityName());
         contentValues.put(Skills,info.getLanguageSkill());
         contentValues.put(Flie,info.getResumePath());
        return sqLiteDatabase.update(TableName, contentValues, ID+"="+id,null);
    }

}
