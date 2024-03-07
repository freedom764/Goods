package com.example.goods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SpisokShowed extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    DatabaseHelper myDB;
    MyAdapterForShowed mAdapter;
    RecyclerView mRecyclerView;
    int year,month,day;
    int count;
    ArrayList<MyObjectForShowed> myObjects;
    ProgressDialog PD1;
    String id;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok_showed);
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
        mRecyclerView = findViewById(R.id.recyclerView);
        myObjects = new ArrayList<>();

        PD1.show();
        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
        final Query query1 = reference1.child("user").orderByChild("email").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        id = issue.child("id").getValue().toString();
                        reference = FirebaseDatabase.getInstance().getReference().child("user/" + id + "/tovar");

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {

                                    String nametovar = issue.child("name").getValue().toString();
                                    String pricetovar = issue.child("price").getValue().toString();
                                    String endyearstr = issue.child("year").getValue().toString();
                                    String endmonthstr = issue.child("month").getValue().toString();
                                    String enddaystr = issue.child("day").getValue().toString();
                                    String amounttovar = issue.child("amount").getValue().toString();
                                    int endyear = Integer.parseInt(endyearstr);
                                    int endmonth = Integer.parseInt(endmonthstr);
                                    int endday = Integer.parseInt(enddaystr);
                                    if ((endyear < year) || (endyear == year && (endmonth) < (month + 1)) || (endyear == year && (endmonth) == (month + 1) && endday <= day)) {
                                    }
                                    else {
                                        myObjects.add(new MyObjectForShowed(nametovar, "Цена: " + pricetovar + " тенге", "Количество товаров: "+amounttovar));
                                    }

                                }

                                mAdapter = new MyAdapterForShowed(myObjects);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(SpisokShowed.this));
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.updateData(myObjects);
                                reference.removeEventListener(this);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });

        PD1.dismiss();








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
            Intent intent = new Intent(SpisokShowed.this, GoodsSeller.class);
            intent.putExtra("From","FromShowed");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public AlertDialog alertWhenDelete(int count){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(SpisokShowed.this);
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
    public Date getDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        return date;
    }
}
