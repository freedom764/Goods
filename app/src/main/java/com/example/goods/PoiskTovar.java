package com.example.goods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PoiskTovar extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    AdapterForPoiskTovar adaptertovar;
    RecyclerView mRecyclerView;
    int count;
    ArrayList<MyObjectForPoiskTovar> myObjects;
    DatabaseReference reference;
    ProgressDialog PD1;
    int year,month,day;
    public DatabaseHelperForID dbid = new DatabaseHelperForID(this);
    public String id,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisk_tovar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        PD1 = new ProgressDialog(this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        mRecyclerView = findViewById(R.id.recyclerView228);
        myObjects = new ArrayList<>();
        email = SaveSharedPreference.getUserName(PoiskTovar.this);
        reference = FirebaseDatabase.getInstance().getReference().child("tovar");
        PD1.show();
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
                    String namemagazin = issue.child("namemagazin").getValue().toString();
                    String adresmagazin = issue.child("adres").getValue().toString();

                    int endyear = Integer.parseInt(endyearstr);
                    int endmonth = Integer.parseInt(endmonthstr);
                    int endday = Integer.parseInt(enddaystr);
                    if ((endyear < year) || (endyear == year && (endmonth) < (month + 1)) || (endyear == year && (endmonth) == (month + 1) && endday <= day)) {
                    }
                    else {
                        myObjects.add(new MyObjectForPoiskTovar(nametovar, "Цена: " + pricetovar + " тенге", "Количество товаров: "+amounttovar, namemagazin,adresmagazin));
                    }
                    PD1.dismiss();
                }

                adaptertovar = new AdapterForPoiskTovar(myObjects);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(PoiskTovar.this));
                mRecyclerView.setAdapter(adaptertovar);
                adaptertovar.updateData(myObjects);
                reference.removeEventListener(this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adaptertovar.getFilter().filter(newText);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    public static Activity fateam;
    {
        fateam = this;}
}
