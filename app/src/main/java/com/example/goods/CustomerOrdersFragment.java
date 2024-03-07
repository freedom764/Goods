package com.example.goods;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerOrdersFragment extends Fragment {
    AdapterForPoiskTovar adaptertovar;
    RecyclerView mRecyclerView;
    int count;
    ArrayList<MyObjectForPoiskTovar> myObjects;
    DatabaseReference reference;
    ProgressDialog PD1;
    int year,month,day;
    public String id,email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_customer,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PD1 = new ProgressDialog(GoodsCustomer.fa);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        mRecyclerView = view.findViewById(R.id.recyclerView1337);
        myObjects = new ArrayList<>();
        email = SaveSharedPreference.getUserName(GoodsCustomer.fa);
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

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {

                                    final String nametovar = issue.child("tovarname").getValue().toString();
                                    final String amounttovar = issue.child("amount").getValue().toString();
                                    String prodavec = issue.child("prodavec").getValue().toString();
                                    final String price = issue.child("price").getValue().toString();
                                    Toast.makeText(GoodsCustomer.fa,prodavec,Toast.LENGTH_SHORT).show();

                                    final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                                    final Query query2 = reference1.child("user").orderByChild("id").equalTo(prodavec);
                                    query2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot1) {
                                            if (dataSnapshot1.exists()) {



                                                for (DataSnapshot issue : dataSnapshot1.getChildren()) {


                                                    String namemagazin =  issue.child("namemagazin").getValue().toString();
                                                    String adres =  issue.child("adres").getValue().toString();

                                                     myObjects.add(new MyObjectForPoiskTovar(nametovar, "Цена: " +  price+ " тенге", "Количество товаров: "+amounttovar,namemagazin,adres));





                                                }
                                            }
                                            adaptertovar = new AdapterForPoiskTovar(myObjects);
                                            mRecyclerView.setLayoutManager(new LinearLayoutManager(GoodsCustomer.fa));
                                            mRecyclerView.setAdapter(adaptertovar);
                                            adaptertovar.updateData(myObjects);
                                            reference.removeEventListener(this);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError1) {

                                        }
                                    });

                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                    PD1.dismiss();
                    PD1.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (myObjects.size() == 0) {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(GoodsCustomer.fa);
                                a_builder.setMessage("Ваш список заказов пуст!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                GoodsCustomer.fa.finish();
                                                Intent intent = new Intent(GoodsCustomer.fa, GoodsCustomer.class);
                                                startActivity(intent);

                                            }
                                        });

                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Список заказов");
                                alert.show();
                            }
                        }
                    }, 1000);
                    PD1.dismiss();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });







    }
}
