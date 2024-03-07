package com.example.goods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperForID extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fbidfree12.db";
    public static final String TABLE_NAME = "fbidfree12_data1";
    public static final String COL1 = "ID";
    public static final String COL2 = "CODE";
    public static final String COL3= "FBID";




    public DatabaseHelperForID(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"CODE INTEGER, "
                +"FBID TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData2( String fbid ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int code = 1;
        contentValues.put(COL2, code);
        contentValues.put(COL3, fbid);





        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getListContents2(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data1 = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+COL2+"=1", null);
        return data1;
    }
    public Cursor updateData2( String fbid ) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cu=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+"=1", null);
        if(cu.moveToFirst()) {
            db.execSQL("UPDATE "+TABLE_NAME+"  SET "+COL2+" ='"+1+"', "+COL3+"='"+ fbid +"'  WHERE "+COL2+" =1");




        }
        return cu;
    }
}

