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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MagazinSpisokTovarov extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    AdapterForTovar adaptertovar;
    RecyclerView mRecyclerView;
    int count;
    ArrayList<MyObjectForShowed> myObjects;
    DatabaseReference reference;
    ProgressDialog PD1;
    int year11,month11,day11;
    public DatabaseHelperForID dbid = new DatabaseHelperForID(this);
    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazin_spisok_tovarov);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Cursor cursor = dbid.getListContents2();
        int count = cursor.getCount();
        if (count ==0) {
            dbid.addData2(id);
        }
        else {
            dbid.updateData2(id);
        }

        PD1 = new ProgressDialog(this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        Calendar cal = Calendar.getInstance();
        year11 = cal.get(Calendar.YEAR);
        month11 = cal.get(Calendar.MONTH);
        day11 = cal.get(Calendar.DAY_OF_MONTH);
        mRecyclerView = findViewById(R.id.recyclerView);
        myObjects = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("user/" + id + "/tovar");
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
                    int endyear = Integer.parseInt(endyearstr);
                    int endmonth = Integer.parseInt(endmonthstr);
                    int endday = Integer.parseInt(enddaystr);
                    if ((endyear < year11) || (endyear == year11 && (endmonth) < (month11 + 1)) || (endyear == year11 && (endmonth) == (month11 + 1) && endday <= day11)) {
                    }
                    else {
                        myObjects.add(new MyObjectForShowed(nametovar, "Цена: " + pricetovar + " тенге","Количество товаров: "+amounttovar));
                    }
                    PD1.dismiss();
                }
                if (myObjects.isEmpty()){

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MagazinSpisokTovarov.this);
                    a_builder.setMessage("Данный магазин не выставил никаких товаров!")
                            .setCancelable(false)

                            .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MagazinSpisokTovarov.this.onBackPressed();
                                    fateam.finish();
                                }
                            });

                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Внимание");
                    alert.show();
                    alert.setCanceledOnTouchOutside(false);
                }
                adaptertovar = new AdapterForTovar(myObjects);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MagazinSpisokTovarov.this));
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
