package com.example.goods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class PortyashyesyaSpisok extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    DatabaseHelper myDB;
    MyAdapter mAdapter;
    RecyclerView mRecyclerView;
    int year,month,day;
    int count;
    ArrayList<MyObject> myObjects;
    ProgressDialog PD1;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portyashyesya_spisok);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PD1 = new ProgressDialog(this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        final String email = SaveSharedPreference.getUserName(this);
        myDB = new DatabaseHelper(this);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Cursor cursor = myDB.forCount(year,month,day,email);
        count = cursor.getCount();
        if (count != 0) {
            alertWhenDelete(count);}

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        PD1.show();
        final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        id = issue.child("id").getValue().toString();
                        myDB.deleteIstekshiySrokGodnosy(year,month,day,email,id);


                        mRecyclerView = findViewById(R.id.recyclerView);

                        myObjects = new ArrayList<>();


                        Cursor data = myDB.getListContents(email);
                            while(data.moveToNext()){
                                int year228 = data.getInt(2);
                                int month228 = data.getInt(3);
                                int day228 = data.getInt(4);
                                int styear228 = data.getInt(6);
                                int stmonth228 = data.getInt(7);
                                int stday228 = data.getInt(8);
                                String email228 = data.getString(9);
                                String datestart,dateend;
                                Date dateendtofind = getDate(year228,(month228-1),day228);
                                Date datetoday = getDate(year,month,day);
                                double millisend = dateendtofind.getTime();
                                double millistoday = datetoday.getTime();
                                double daysleft = (millisend/8.64e+7)-(millistoday/8.64e+7);
                                DecimalFormat df = new DecimalFormat("#");
                                df.setRoundingMode(RoundingMode.CEILING);
                                String daysleftstring = df.format(daysleft);
                                if(stmonth228<10){

                                    if (stday228<10){
                                        datestart = "0"+stday228+".0"+stmonth228+"."+styear228;            }
                                    else{
                                        datestart = stday228+".0"+stmonth228+"."+styear228;                 }
                                }
                                else {
                                    if (stday228<10){
                                        datestart = "0"+stday228+"."+stmonth228+"."+styear228;             }
                                    else {
                                        datestart = stday228+"."+stmonth228+"."+styear228;
                                    }}


                                if(month228<10){

                                    if (day228<10){
                                        dateend = "0"+day228+".0"+month228+"."+year228;             }
                                    else{
                                        dateend = day228+".0"+month228+"."+year228;             }
                                }
                                else {
                                    if (day228<10){
                                        dateend = "0"+day228+"."+month228+"."+year228;                }
                                    else{
                                        dateend = day228+"."+month228+"."+year228;              }}



                                if (daysleft<=10) {
                                    myObjects.add(new MyObject(data.getString(1), "Количество дней до конца срока годности - "+daysleftstring));
                                }



                                Collections.sort(myObjects,new Comparator<MyObject>(){
                                    @Override
                                    public int compare(MyObject obj1, MyObject obj2){
                                        return obj1.title.compareTo(obj2.title);
                                    }
                                });

                                mAdapter = new MyAdapter(myObjects);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(PortyashyesyaSpisok.this));
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.updateData(myObjects);



                            }





                    }

                }
                PD1.dismiss();

            }



            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (SearchView) menuItem.getActionView();



        String email = SaveSharedPreference.getUserName(this);
        Cursor data1 = myDB.getListContents(email);
        if (data1.getCount() != 0) {
            searchView.setOnQueryTextListener(this);
        }
        return true;



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {

            GoodsSeller.fa.finish();
            Intent intent = new Intent(PortyashyesyaSpisok.this, GoodsSeller.class);
            intent.putExtra("From","FromSpisok");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public AlertDialog alertWhenDelete(int count){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(PortyashyesyaSpisok.this);
        a_builder.setMessage("Срок годности товаров истек, поэтому мы удалили их из вашего списка товаров! (Число удаленных товаров: "+count+")")
                .setCancelable(false)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){



                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Истек срок годности товаров");
        alert.show();
        return alert;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        mAdapter.getFilter().filter(newText);
        return true;
    }
    public static Activity fa;
    {
        fa = this;
    }
    public Date getDate(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        return date;
    }

}
