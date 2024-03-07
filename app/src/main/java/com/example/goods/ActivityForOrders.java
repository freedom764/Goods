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
import java.util.Collections;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ActivityForOrders extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    DatabaseHelper myDB;
    MyAdapterForOrders mAdapter;
    RecyclerView mRecyclerView;
    int year,month,day;
    int count;
    ArrayList<MyObject> myObjects;
    ProgressDialog PD1;
    String id;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_orders);
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
        mRecyclerView = findViewById(R.id.recyclerView1337);
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
                        reference = FirebaseDatabase.getInstance().getReference().child("user/" + id + "/orders");

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                   final  String tovarname = issue.child("tovarname").getValue().toString();
                                   final  String amount = issue.child("amount").getValue().toString();
                                    final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                                    final Query query1 = reference1.child("user/" + id + "/tovar").orderByChild("name").equalTo(tovarname);
                                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                    myObjects.add(new MyObject(tovarname, "Количество товаров: "+amount));
                                                }
                                                PD1.dismiss();
                                            }

                                            Collections.sort(myObjects,new Comparator<MyObject>(){
                                                @Override
                                                public int compare(MyObject obj1, MyObject obj2){
                                                    return obj1.title.compareTo(obj2.title);
                                                }
                                            });

                                            mAdapter = new MyAdapterForOrders(myObjects);
                                            mRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityForOrders.this));
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
            Intent intent = new Intent(ActivityForOrders.this, GoodsSeller.class);
            intent.putExtra("From","FromShowed");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public AlertDialog alertWhenDelete(int count){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(ActivityForOrders.this);
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
}
