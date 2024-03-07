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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PoiskMagazin extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    RecyclerView mRecyclerView;
    int year,month,day;
    int count;
    ArrayList<MyObject> myObjects;
    ProgressDialog PD1;
    String id,email,city;
    AdapterForFirebase adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisk_magazin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PD1 = new ProgressDialog(this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        mRecyclerView = findViewById(R.id.recyclerView);
        myObjects = new ArrayList<>();
        email = SaveSharedPreference.getUserName(PoiskMagazin.this);
        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
        final Query query1 = reference1.child("user").orderByChild("email").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {

                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        city = issue.child("city").getValue().toString();
                        final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                        final Query query2 = reference2.child("user").orderByChild("isprodavec").equalTo(1);
                        PD1.show();
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {

                                    String cityforcheck = issue.child("city").getValue().toString();
                                    String block = issue.child("blokirovka").getValue().toString();
                                    int blokirovka = Integer.parseInt(block);
                                    Log.d("prost",city);
                                    if (cityforcheck.equals(city) && blokirovka !=1) {
                                        String namemagaz = issue.child("namemagazin").getValue().toString();
                                        String adres = issue.child("adres").getValue().toString();
                                        myObjects.add(new MyObject(namemagaz, adres));
                                    }
                                    PD1.dismiss();
                                }
                                if (myObjects.isEmpty()){

                                    AlertDialog.Builder a_builder = new AlertDialog.Builder(PoiskMagazin.this);
                                    a_builder.setMessage("В вашем городе нет магазинов зарегистрированных в приложении!")
                                            .setCancelable(false)

                                            .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    PoiskMagazin.this.onBackPressed();
                                                    PoiskMagazin.this.finish();
                                                }
                                            });

                                    AlertDialog alert = a_builder.create();
                                    alert.setTitle("Внимание");
                                    alert.show();
                                    alert.setCanceledOnTouchOutside(false);
                                }
                                adapter = new AdapterForFirebase(myObjects);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(PoiskMagazin.this));
                                mRecyclerView.setAdapter(adapter);
                                adapter.updateData(myObjects);
                                reference1.removeEventListener(this);
                                reference2.removeEventListener(this);
                                query2.removeEventListener(this);
                                query1.removeEventListener(this);

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

        adapter.getFilter().filter(newText);
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
        fateam = this;
    }
}
