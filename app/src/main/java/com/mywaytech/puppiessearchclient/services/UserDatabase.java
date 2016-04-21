package com.mywaytech.puppiessearchclient.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by m.maigua on 4/20/2016.
 */
public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Users.db";
    private static final String TABLE_NAME = "users_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "PASSWORD";
    private static final String COL4 = "EMAIL";
    private static final String COL5 = "ADDRESS";


    public UserDatabase(Context context) {
        super(context, context.getExternalFilesDir(null) + "/" + DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PASSWORD TEXT,EMAIL TEXT,ADDRESS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String name, String password, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, password);
        contentValues.put(COL4, email);
        contentValues.put(COL5, address);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean consultData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=?", new String[]{email});
        Log.d("resultCount:", "" + res.getCount());
        return res.moveToFirst();
    }

    public String getEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=?", new String[]{email});
        if (!res.moveToFirst()) {
            return null;
        } else {
            return res.getString(res.getColumnIndex("EMAIL"));
        }
    }

    public String[] getAllDatabyEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=?", new String[]{email});
        if (!res.moveToFirst()) {
            return null;
        } else {
            return new String[]{res.getString(res.getColumnIndex("NAME")),res.getString(res.getColumnIndex("EMAIL")),res.getString(res.getColumnIndex("ADDRESS"))};
        }
    }

    public String[] getLastRow(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" ORDER BY EMAIL DESC LIMIT 1",null);
        if (!res.moveToFirst()) {
            return null;
        } else {
            return new String[]{res.getString(res.getColumnIndex("NAME")),res.getString(res.getColumnIndex("EMAIL")),res.getString(res.getColumnIndex("ADDRESS"))};
        }
    }
}
