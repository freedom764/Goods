package com.example.goods;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
   String id;
    ProgressDialog PD1;
    DatabaseReference reference;
    int year11,month11,day11;
    Button ordersbtn,showbtn;
    int sum,sum1;
    TextView nesheorders,nesheshow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    sum = 0;
    sum1 = 0;
    nesheorders = view.findViewById(R.id.nesheorder);
    nesheshow = view.findViewById(R.id.nesheshow);
    ordersbtn = view.findViewById(R.id.ordersbtn);
    showbtn = view.findViewById(R.id.showbtn);
    nesheorders.setVisibility(View.INVISIBLE);
        nesheshow.setVisibility(View.INVISIBLE);
        ordersbtn.setVisibility(View.INVISIBLE);
        showbtn.setVisibility(View.INVISIBLE);



        PD1 = new ProgressDialog(getActivity());
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        Calendar cal = Calendar.getInstance();
        year11 = cal.get(Calendar.YEAR);
        month11 = cal.get(Calendar.MONTH);
        day11 = cal.get(Calendar.DAY_OF_MONTH);

        String email = SaveSharedPreference.getUserName(getActivity());
        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
        final Query query1 = reference1.child("user").orderByChild("email").equalTo(email);
        PD1.show();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        id = issue.child("id").getValue().toString();
                        DatabaseReference reference1;

                        reference1 = FirebaseDatabase.getInstance().getReference().child("user/" + id + "/orders");

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {

                                    String tovarname = issue.child("tovarname").getValue().toString();
                                    final String pokupatel     = issue.child("pokupatel").getValue().toString();
                                    final String idaga     = issue.child("id").getValue().toString();
                                    final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                                    final Query query2 = reference1.child("user/" + id + "/tovar").orderByChild("name").equalTo(tovarname);

                                    query2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                                Log.d("prost", id);
                                                if (issue.child("year").getValue() != null && issue.child("month").getValue() != null && issue.child("day").getValue() != null) {
                                                    String endyearstr = issue.child("year").getValue().toString();
                                                    String endmonthstr = issue.child("month").getValue().toString();
                                                    String enddaystr = issue.child("day").getValue().toString();
                                                    int endyear = Integer.parseInt(endyearstr);
                                                    int endmonth = Integer.parseInt(endmonthstr);
                                                    int endday = Integer.parseInt(enddaystr);
                                                    if ((endyear < year11) || (endyear == year11 && (endmonth) < (month11 + 1)) || (endyear == year11 && (endmonth) == (month11 + 1) && endday <= day11)) {
                                                        final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                                                        reference2.child("user/" + id + "/orders/"+idaga).removeValue();
                                                        reference2.child("user/" + pokupatel + "/orders/"+idaga).removeValue();
                                                    } else {
                                                        sum1 = sum1 + 1;
                                                        reference1.removeEventListener(this);
                                                    }

                                                }
                                                else {
                                                    query1.removeEventListener(this);
                                                }
                                                if (sum1 == 0) {
                                                    nesheorders.setText("У вас нет заказов!");
                                                } else {
                                                    nesheorders.setText("Количество заказов: " + sum1);
                                                }


                                            }

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
                        query1.removeEventListener(this);
                    }


                    PD1.dismiss();


                }}
            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });

        PD1.show();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        reference = FirebaseDatabase.getInstance().getReference().child("user/" + id + "/tovar");

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot   issue : dataSnapshot.getChildren()) {
                                    Log.d("prost", id);
                                    if (issue.child("year").getValue() != null && issue.child("month").getValue() != null && issue.child("day").getValue() != null) {
                                        String endyearstr = issue.child("year").getValue().toString();
                                        String endmonthstr = issue.child("month").getValue().toString();
                                        String enddaystr = issue.child("day").getValue().toString();
                                        int endyear = Integer.parseInt(endyearstr);
                                        int endmonth = Integer.parseInt(endmonthstr);
                                        int endday = Integer.parseInt(enddaystr);
                                        if ((endyear < year11) || (endyear == year11 && (endmonth) < (month11 + 1)) || (endyear == year11 && (endmonth) == (month11 + 1) && endday <= day11)) {
                                        } else {
                                            sum = sum + 1;
                                        }

                                    }
                                    else {
                                        query1.removeEventListener(this);
                                    }
                                    if (sum == 0) {
                                        nesheshow.setText("Вы еще не опубликовали товары!");
                                    } else {
                                        nesheshow.setText("Количество опубликованных товаров: " + sum);
                                    }


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

                            String vnutri = nesheshow.getText().toString();
                            String vnutri1 = nesheorders.getText().toString();
                            if (vnutri.equals("Вы еще не опубликовали товары!")) {
                                showbtn.setBackground(ContextCompat.getDrawable(GoodsSeller.fa, R.drawable.loginbuttonoff));
                                showbtn.setTextColor(GoodsSeller.fa.getColor(R.color.colorGray));
                                showbtn.setEnabled(false);


                            }
                            else {
                                showbtn.setBackground(ContextCompat.getDrawable(GoodsSeller.fa, R.drawable.custombutton));
                                showbtn.setTextColor(GoodsSeller.fa.getColor(R.color.colorWhite));
                                showbtn.setEnabled(true);
                            }

                            if (vnutri1.equals("У вас сейчас нет заказов!")) {
                                ordersbtn.setBackground(ContextCompat.getDrawable(GoodsSeller.fa, R.drawable.loginbuttonoff));
                                ordersbtn.setTextColor(GoodsSeller.fa.getColor(R.color.colorGray));
                                ordersbtn.setEnabled(false);


                            }
                            else {
                                ordersbtn.setBackground(ContextCompat.getDrawable(GoodsSeller.fa, R.drawable.custombutton));
                                ordersbtn.setTextColor(GoodsSeller.fa.getColor(R.color.colorWhite));
                                ordersbtn.setEnabled(true);
                            }
                            nesheorders.setVisibility(View.VISIBLE);
                            nesheshow.setVisibility(View.VISIBLE);
                            ordersbtn.setVisibility(View.VISIBLE);
                            showbtn.setVisibility(View.VISIBLE);
                            PD1.dismiss();

                        }

                    }, 500);


}}
@Override
public void onCancelled(DatabaseError databaseError1) {

        }
        });







        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpisokShowed.class);
                startActivity(intent);

            }
        });
        ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityForOrders.class);
                startActivity(intent);

            }
        });

    }
}

