package com.example.goods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseReference databaseTovary;

    public static final String DATABASE_NAME = "mylist1337228764.db";
    public static final String TABLE_NAME = "mylist_data1337228764";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME1";
    public static final String COL3 = "YEAR1";
    public static final String COL4= "MONTH1";
    public static final String COL5= "DAY1";
    public static final String COL6= "PRICE1";
    public static final String COL7= "STYEAR1";
    public static final String COL8= "STMONTH1";
    public static final String COL9= "STDAY1";
    public static final String COL10= "EMAIL";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"NAME1 TEXT, "
                +"YEAR1 INTEGER, "
                +"MONTH1 INTEGER, "
                +"DAY1 INTEGER, "
                +"PRICE1 INTEGER, "
                +"STYEAR1 INTEGER, "
                +"STMONTH1 INTEGER, "
                +"STDAY1 INTEGER,"
                +"EMAIL TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(String name1, int year1, int month1, int day1, int price1, int styear1, int stmonth1, int stday1, String email ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name1);
        contentValues.put(COL3, year1);
        contentValues.put(COL4, month1);
        contentValues.put(COL5, day1);
        contentValues.put(COL6, price1);
        contentValues.put(COL7, styear1);
        contentValues.put(COL8, stmonth1);
        contentValues.put(COL9, stday1);
        contentValues.put(COL10, email);





        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+COL10+"='"+ email+"'", null);
        return data;
    }
    public Cursor showDataonSelect(String nametext) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+"='"+ nametext+"'", null);

        return c;
    }
    public Cursor updateData(final String textname, final String name, final int year, final int month, final int day, final int price, final int styear, final int stmonth, final int stday, String email ) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cu=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+"='"+ textname+"'", null);
        if(cu.moveToFirst()) {
            db.execSQL("UPDATE "+TABLE_NAME+"  SET "+COL2+" ='"+name+"', "+COL3+"='"+ year +"', "+COL4+"='"+ month +"', "+COL5+"='"+ day +"', "+COL6+"='"+ price+"', "+COL7+"='"+ styear +"', "+COL8+"='"+ stmonth +"', "+COL9+"='"+ stday +"'  WHERE "+COL2+" ='"+textname+"'");



        }
        return cu;
    }

    public Cursor updateData21(final String textname, final String name, final int year, final int month, final int day, final int price, final int styear, final int stmonth, final int stday, String email, final int amount, final int pricebegin,final String telephone,
                               final String city,final String nameprodavec,final String namemagazin,final String adres) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cu=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+"='"+ textname+"'", null);
        if(cu.moveToFirst()) {

            final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot1) {
                    if (dataSnapshot1.exists()) {


                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                            // do something with the individual "issues"
                            final String id = issue.child("id").getValue().toString();
                            final String email = issue.child("email").getValue().toString();
                            final Query query1 = reference.child("user/" + id + "/tovar/").orderByChild("name").equalTo(textname);
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {


                                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                            // do something with the individual "issues"

                                            String idaga = reference.push().getKey();
                                            reference.child("user/" + id + "/tovar/" + textname).removeValue();
                                            reference.child("user/" + id + "/tovar/" + name + "/name").setValue(name);
                                            reference.child("user/" + id + "/tovar/" + name + "/id").setValue(idaga);
                                            reference.child("user/" + id + "/tovar/" + name + "/year").setValue(year);
                                            reference.child("user/" + id + "/tovar/" + name + "/month").setValue(month);
                                            reference.child("user/" + id + "/tovar/" + name + "/day").setValue(day);
                                            reference.child("user/" + id + "/tovar/" + name + "/price").setValue(price);
                                            reference.child("user/" + id + "/tovar/" + name + "/styear").setValue(styear);
                                            reference.child("user/" + id + "/tovar/" + name + "/stmonth").setValue(stmonth);
                                            reference.child("user/" + id + "/tovar/" + name + "/stday").setValue(stday);
                                            reference.child("user/" + id + "/tovar/" + name + "/amount").setValue(amount);
                                            reference.child("user/" + id + "/tovar/" + name + "/pricebegin").setValue(pricebegin);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError1) {

                                }
                            });
                            final Query query2 = reference.child("tovar/").orderByChild("name").equalTo(textname);
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {


                                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                            // do something with the individual "issues"

                                            String idaga = reference.push().getKey();
                                            String emailnew = email.replace(".","");
                                            reference.child("tovar/" + textname+"_"+emailnew).removeValue();
                                            reference.child("tovar/" + name +"_"+emailnew+ "/name").setValue(name);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/year").setValue(year);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/month").setValue(month);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/day").setValue(day);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/price").setValue(price);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/styear").setValue(styear);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/stmonth").setValue(stmonth);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/stday").setValue(stday);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/amount").setValue(amount);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/pricebegin").setValue(pricebegin);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/telephone").setValue(telephone);
                                            reference.child("tovar/" + name +"_"+emailnew+ "/email").setValue(email);
                                            reference.child("tovar/" + name+"_"+emailnew + "/idprodavec").setValue(id);
                                            reference.child("tovar/" + name+"_"+emailnew + "/nameprodavec").setValue(nameprodavec);
                                            reference.child("tovar/" + name+"_"+emailnew + "/namemagazin").setValue(namemagazin);
                                            reference.child("tovar/" + name+"_"+emailnew + "/adres").setValue(adres);
                                            reference.child("tovar/" + name+"_"+emailnew + "/city").setValue(city);
                                            reference.child("tovar/" + name+"_"+emailnew + "/id").setValue(name +"_"+emailnew);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError1) {

                                }
                            });


                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError1) {

                }
            });



        }
        return cu;
    }
    public Cursor deleteData(final String name,final String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL2+" ='"+name+"'", null);
        if(cur.moveToFirst())
        {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+COL2+" ='"+ name+"'");
            final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot1) {
                    if (dataSnapshot1.exists()) {


                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                            String emailnew = email.replace(".","");
                            // do something with the individual "issues"
                            String id = issue.child("id").getValue().toString();
                            String idaga = reference.push().getKey();
                            reference.child("user/" + id + "/tovar/" + name).removeValue();
                            reference.child("tovar/"+name+"_"+emailnew).removeValue();

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError1) {

                }
            });


        }
        return cur;
    }
    public Cursor searchForData (String name1,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ("+COL2+"='"+name1+"') AND ("+COL10+"='"+email+"')", null);
        return data;
    }
    public Cursor deleteIstekshiySrokGodnosy(int year, int month,int day,final String email,final String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curs=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ("+COL10+"='"+ email+"') AND (("+COL3+"<"+year+") OR ("+COL3+"=="+year+" AND "+COL4+"<"+(month+1)+") OR ("+COL3+"=="+year+" AND "+COL4+"=="+(month+1)+" AND "+COL5+"<="+day+"))", null);
        if(curs.moveToFirst())
        {
            final String name = curs.getString(1);
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            final Query query2 = reference.child("user/" + id + "/tovar").orderByChild("name").equalTo(name);
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot2) {
                    if (dataSnapshot2.exists()) {
                        String emailnew = email.replace(".","");
                        reference.child("user/" + id + "/tovar/"+name).removeValue();
                        reference.child("tovar/"+name+"_"+emailnew).removeValue();


                        for (DataSnapshot issue : dataSnapshot2.getChildren()) {
                            // do something with the individual "issues"
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError2) {

                }
            });


            db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE ("+COL10+"='"+ email+"') AND (("+COL3+"<"+year+") OR ("+COL3+"=="+year+" AND "+COL4+"<"+(month+1)+") OR ("+COL3+"=="+year+" AND "+COL4+"=="+(month+1)+" AND "+COL5+"<="+day+"))");



            do {
                final String name1 = curs.getString(1);
                final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                final Query query3 = reference1.child("user/" + id + "/tovar").orderByChild("name").equalTo(name1);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        if (dataSnapshot2.exists()) {
                            String emailnew = email.replace(".","");
                            reference1.child("user/" + id + "/tovar/"+name1).removeValue();
                            reference1.child("tovar/"+name1+"_"+emailnew).removeValue();

                            for (DataSnapshot issue : dataSnapshot2.getChildren()) {
                                // do something with the individual "issues"
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError2) {

                    }
                });

                db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE ("+COL10+"='"+ email+"') AND (("+COL3+"<"+year+") OR ("+COL3+"=="+year+" AND "+COL4+"<"+(month+1)+") OR ("+COL3+"=="+year+" AND "+COL4+"=="+(month+1)+" AND "+COL5+"<="+day+"))");


            } while (curs.moveToNext());
        }
        return curs;
    }
    public Cursor forCount (int year, int month,int day, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ("+COL10+"='"+ email+"') AND (("+COL3+"<"+year+") OR ("+COL3+"=="+year+" AND "+COL4+"<"+(month+1)+") OR ("+COL3+"=="+year+" AND "+COL4+"=="+(month+1)+" AND "+COL5+"<="+day+"))", null);
        return cursor;
    }



}
