package com.example.goods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListFragment extends Fragment {

    NavigationView navigationView;
    DatabaseHelper myDB;
    MyAdapter mAdapter;
    RecyclerView mRecyclerView;
    int year,month,day;
    int count;
    ArrayList<MyObject> myObjects;
   Button spisok,port;
   int sum;
   TextView nesheport;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    sum = 0;
nesheport = view.findViewById(R.id.nesheport);

        spisok = view.findViewById(R.id.spisok);
        spisok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SellerSpisokTovarov.class);
                startActivity(intent);

            }
        });
        port = view.findViewById(R.id.portyashiesya);
        String email = SaveSharedPreference.getUserName(getActivity());
        myDB = new DatabaseHelper(getActivity());
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);



        Cursor data = myDB.getListContents(email);



            while(data.moveToNext()){
                int year228 = data.getInt(2);
                int month228 = data.getInt(3);
                int day228 = data.getInt(4);

                Date dateendtofind = getDate(year228,(month228-1),day228);
                Date datetoday = getDate(year,month,day);
                double millisend = dateendtofind.getTime();
                double millistoday = datetoday.getTime();
                double daysleft = (millisend/8.64e+7)-(millistoday/8.64e+7);
                String str = Double.toString(daysleft);
                Log.d("prost",str);
                DecimalFormat df = new DecimalFormat("#");
                df.setRoundingMode(RoundingMode.CEILING);



                if (daysleft<=10) {
                    if((year228 < year) || (year228 == year && (month228) < (month + 1)) || (year228 == year && (month228) == (month + 1) && day228 <= day))   {

                    }
                    else {
                        sum = sum+1;
                    }

                }
                if (sum==0) {
                    nesheport.setText("У вас нет портящихся товаров");
                }
                if (sum!=0) {
                    nesheport.setText("Количество портящихся товаров: "+sum);


                }
                String vnutri = nesheport.getText().toString();
                if (vnutri.equals("У вас нет портящихся товаров")) {
                    port.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.loginbuttonoff));
                    port.setTextColor(getResources().getColor(R.color.colorGray));
                    port.setEnabled(false);

                }
                else{
                    port.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custombutton));
                    port.setTextColor(getResources().getColor(R.color.colorWhite));
                    port.setEnabled(true);
                }
            }
            port.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PortyashyesyaSpisok.class);
                    startActivity(intent);
                }
            });
        }
    public Date getDate(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        return date;
    }
    public static Activity fa;
    {
        fa = getActivity();
    }
    }


