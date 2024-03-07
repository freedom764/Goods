package com.example.goods;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class GoodsSellerOpenTovar extends AppCompatActivity implements Dialog.DialogListener{
    TextView txt1,txt2,txt6;
    ProgressBar progress;
    Button btnUpdate,btnDelete,btnShow,btnDeletePub, btnUpdateFirebase;
    String name;
    int year,month,day,price,styear,stmonth,stday;
    String yearAsString, monthAsString, dayAsString, priceAsString, styearAsString, stmonthAsString, stdayAsString,percentAsString,email;
    DatabaseHelper myDB;
    int yearwunder,monthwunder,daywunder;
    ObjectAnimator progressAnimator;
    int skidka;
    double minuspercent;
    String id;
    int price3,amount3;
    ProgressDialog PD3,PD4;
    String blokirovkastr;
    int blokirovka;
    TextView first,second,third,fourth;
    String pricefornariman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_seller_open_tovar);
        Calendar c = Calendar.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        yearwunder = c.get(Calendar.YEAR);
        monthwunder = c.get(Calendar.MONTH);
        daywunder = c.get(Calendar.DAY_OF_MONTH);
        txt1 = findViewById(R.id.nametovar1);
        txt2 = findViewById(R.id.sroktovar1);
        first = findViewById(R.id.firstpricetovar1);
        second = findViewById(R.id.recommendedpricetovar1);
        third = findViewById(R.id.publishedpricetovar1);
        fourth = findViewById(R.id.amounttovar1);
        txt6 = findViewById(R.id.textView13);
        PD3 = new ProgressDialog(this);
        PD3.setMessage("Загрузка...");
        PD3.setCancelable(true);
        PD3.setCanceledOnTouchOutside(false);
        PD4 = new ProgressDialog(this);
        PD4.setMessage("Загрузка...");
        PD4.setCancelable(true);
        PD4.setCanceledOnTouchOutside(false);
        progress = findViewById(R.id.progressbar);
        btnUpdateFirebase = findViewById(R.id.btnUpdateFirebase);
        btnUpdate = findViewById(R.id.btnUpdate2);
        btnDelete = findViewById(R.id.btnDelete);
        btnShow = findViewById(R.id.btnShow);
        btnDeletePub = findViewById(R.id.btnUnshow);
        btnShow.setVisibility(View.GONE);
        btnDeletePub.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
        btnUpdateFirebase.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
        third.setVisibility(View.GONE);
        fourth.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("Name");
        year = extras.getInt("Year");
        month = extras.getInt("Month");
        day = extras.getInt("Day");
        price = extras.getInt("Price");
        styear = extras.getInt("StYear");
        stmonth = extras.getInt("StMonth");
        stday = extras.getInt("StDay");
        email = extras.getString("Email");
        price3 = 0;
        amount3 = 0;

        yearAsString = Integer.toString(year);
        monthAsString = Integer.toString(month);
        dayAsString = Integer.toString(day);
        priceAsString = Integer.toString(price);
        styearAsString = Integer.toString(styear);
        stmonthAsString = Integer.toString(stmonth);
        stdayAsString = Integer.toString(stday);
        LayerDrawable progressBarDrawable = (LayerDrawable) progress.getProgressDrawable();
        Drawable progressDrawable = progressBarDrawable.getDrawable(1);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        PD3.show();
        final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        id = issue.child("id").getValue().toString();


                    }

                    final Query query2 = reference.child("user/" + id + "/tovar").orderByChild("name").equalTo(name);
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            if (dataSnapshot2.exists()) {
                                third.setVisibility(View.VISIBLE);
                                fourth.setVisibility(View.VISIBLE);
                                btnUpdateFirebase.setVisibility(View.VISIBLE);
                                btnDelete.setVisibility(View.VISIBLE);
                                btnDeletePub.setVisibility(View.VISIBLE);




                                for (DataSnapshot issue : dataSnapshot2.getChildren()) {
                                    // do something with the individual "issues"
                                    pricefornariman = issue.child("price").getValue().toString();
                                    String amountfornariman = issue.child("amount").getValue().toString();
                                    third.setText("Опубликованная цена: "+pricefornariman+" тенге");
                                    fourth.setText("Количество опубликованных товаров: "+amountfornariman);
                                }
                            }
                            else {
                                btnShow.setVisibility(View.VISIBLE);
                                btnUpdate.setVisibility(View.VISIBLE);
                                btnDelete.setVisibility(View.VISIBLE);
                            }
                            PD3.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });
        txt1.setText(name);
        if(stmonth<10){

            if (stday<10){
                txt2.setText("0"+stdayAsString+".0"+stmonthAsString+"."+styearAsString);            }
            else{
                txt2.setText(stdayAsString+".0"+stmonthAsString+"."+styearAsString);              }
        }
        else {
            if (stday<10){
                txt2.setText("0"+stdayAsString+"."+stmonthAsString+"."+styearAsString);             }
            else {
                txt2.setText(stdayAsString + "." + stmonthAsString + "." + styearAsString);
            }}


        if(month<10){

            if (day<10){
                txt2.append("—0"+dayAsString+".0"+monthAsString+"."+yearAsString);            }
            else{
                txt2.append("—"+dayAsString+".0"+monthAsString+"."+yearAsString);              }
        }
        else {
            if (day<10){
                txt2.append("—0"+dayAsString+"."+monthAsString+"."+yearAsString);             }
            else{
                txt2.append("—"+dayAsString+"."+monthAsString+"."+yearAsString);             }}

        first.setText("Изначальная цена: "+priceAsString+" тенге");
        Date datestart = getDate(styear,(stmonth-1),stday);
        Date dateend = getDate(year,(month-1),day);
        Date datetoday = getDate(yearwunder,monthwunder,daywunder);

        double millisstart = datestart.getTime();
        double millisend = dateend.getTime();
        double millistoday = datetoday.getTime();
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        double percent = (((millistoday - millisstart) / (millisend - millisstart)) * 100);
        double daysleft = (millisend/8.64e+7)-(millistoday/8.64e+7);

        String daysleftstring = df.format(daysleft);
        txt6.setText(daysleftstring);

        if(percent<100 && percent>=90){

            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorRed), PorterDuff.Mode.SRC_IN);
            txt6.setTextColor(getResources().getColor(R.color.colorRed));
        }
        if(percent<90 && percent>=75){
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange), PorterDuff.Mode.SRC_IN);
            txt6.setTextColor(getResources().getColor(R.color.colorOrange));
        }
        if(percent<75 && percent>=50){
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorYellow), PorterDuff.Mode.SRC_IN);
            txt6.setTextColor(getResources().getColor(R.color.colorYellow));
        }
        if(percent<50 && percent>=0){
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            txt6.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        minuspercent = 100-percent;
        if(minuspercent<=50) {
            if (minuspercent <= 40) {
                if (minuspercent <= 30) {
                    if (minuspercent <= 20) {
                        if (minuspercent <= 15) {
                            if (minuspercent <= 10) {
                                if (minuspercent <= 7.5) {
                                    if (minuspercent <= 5) {
                                        if (minuspercent <= 2.5) {
                                            if (minuspercent <= 1) {
                                                if (minuspercent <= 0.5) {
                                                    skidka = 75;
                                                } else {
                                                    skidka = 70;
                                                }

                                            } else {
                                                skidka = 65;
                                            }

                                        } else {
                                            skidka = 60;
                                        }

                                    } else {
                                        skidka = 55;
                                    }

                                } else {
                                    skidka = 50;
                                }

                            } else {
                                skidka = 40;
                            }

                        } else {
                            skidka = 30;
                        }


                    } else {
                        skidka = 20;
                    }
                } else {
                    skidka = 10;
                }
            }
            else {skidka = 5;}
        }
        else{skidka = 0;}

        String percentintstring = df.format(percent);
        int percentint = Integer.parseInt(percentintstring);


        progressAnimator.ofInt(progress, "progress", percentint)
                .setDuration(1000)
                .start();
        double doubleprice = price;
        double doubleskidka = skidka;
        double doublepricewithskidka = (price-((doubleprice*doubleskidka)/100));

        String doublepricewithskidkastring = df.format(doublepricewithskidka);

        second.setText("Рекомендуемая цена: "+doublepricewithskidkastring+" тенге");
        btnUpdateFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentwunder = new Intent(GoodsSellerOpenTovar.this, UpdateTovarFirebase.class);
                Bundle extras1 = new Bundle();
                extras1.putString("Name1", name);
                extras1.putInt("Year1", year);
                extras1.putInt("Month1", month);
                extras1.putInt("Day1", day);
                extras1.putInt("Price1", price);
                extras1.putInt("StYear1", styear);
                extras1.putInt("StMonth1", stmonth);
                extras1.putInt("StDay1", stday);
                extras1.putString("email",email);
                myIntentwunder.putExtras(extras1);
                startActivity(myIntentwunder);



            }
        });

        myDB = new DatabaseHelper(this);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntentwunder = new Intent(GoodsSellerOpenTovar.this, UpdateTovar.class);
                Bundle extras1 = new Bundle();
                extras1.putString("Name1", name);
                extras1.putInt("Year1", year);
                extras1.putInt("Month1", month);
                extras1.putInt("Day1", day);
                extras1.putInt("Price1", price);
                extras1.putInt("StYear1", styear);
                extras1.putInt("StMonth1", stmonth);
                extras1.putInt("StDay1", stday);
                extras1.putString("email",email);
                myIntentwunder.putExtras(extras1);
                startActivity(myIntentwunder);


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(GoodsSellerOpenTovar.this);
                a_builder.setMessage("Вы действительно хотите удалить этот товар?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){

                                myDB.deleteData(name,email);
                                Toast.makeText(GoodsSellerOpenTovar.this, "Товар успешно удален", Toast.LENGTH_LONG).show();
                                Intent intentwunder = new Intent(GoodsSellerOpenTovar.this, SellerSpisokTovarov.class);
                                startActivity(intentwunder);
                                finish();
                                SellerSpisokTovarov.fa.finish();

                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Удаление товара");
                alert.show();


            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                PD4.show();
                final Query query11 = reference1.child("user").orderByChild("email").equalTo(email);
                query11.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {


                            for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                // do something with the individual "issues"
                                blokirovkastr = issue.child("blokirovka").getValue().toString();
                                blokirovka = Integer.parseInt(blokirovkastr);

                                PD4.dismiss();

                                if (blokirovka==1){
                                    AlertDialog.Builder a_builder = new AlertDialog.Builder(GoodsSellerOpenTovar.this);
                                    a_builder.setMessage("Вы не можете публиковать товары!")
                                            .setCancelable(false)
                                            .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                }
                                            });

                                    AlertDialog alert = a_builder.create();
                                    alert.setTitle("Блокировка!");
                                    alert.show();
                                    alert.setCanceledOnTouchOutside(false);
                                }
                                else {


                                        if (price3 <= 0 || amount3 <= 0 ) {
                                            openDialog();
                                            Toast.makeText(GoodsSellerOpenTovar.this, "Цена на товар и количество товаров должны быть больше 0!", Toast.LENGTH_SHORT).show();
                                        }
                                        if (price3 > price) {
                                            openDialog();
                                            Toast.makeText(GoodsSellerOpenTovar.this, "Цена при публикации не должна быть больше изначальной цены на товар!", Toast.LENGTH_LONG).show();

                                        }
                                        if (price3 > 0 && amount3 > 0 && price3 <= price) {


                                                String email = SaveSharedPreference.getUserName(GoodsSellerOpenTovar.this);

                                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                                final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
                                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot1) {
                                                        if (dataSnapshot1.exists()) {


                                                            for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                                                // do something with the individual "issues"
                                                                String id = issue.child("id").getValue().toString();
                                                                String nameprodavec = issue.child("nameprodavec").getValue().toString();
                                                                String namemagazin = issue.child("namemagazin").getValue().toString();
                                                                String adres = issue.child("adres").getValue().toString();
                                                                String city = issue.child("city").getValue().toString();
                                                                String email = issue.child("email").getValue().toString();

                                                                String  telephone = issue.child("telephone").getValue().toString();


                                                                String idaga = reference.push().getKey();
                                                                reference.child("user/" + id + "/tovar/" + name + "/name").setValue(name);
                                                                reference.child("user/" + id + "/tovar/" + name + "/id").setValue(idaga);
                                                                reference.child("user/" + id + "/tovar/" + name + "/year").setValue(year);
                                                                reference.child("user/" + id + "/tovar/" + name + "/month").setValue(month);
                                                                reference.child("user/" + id + "/tovar/" + name + "/day").setValue(day);
                                                                reference.child("user/" + id + "/tovar/" + name + "/price").setValue(price3);
                                                                reference.child("user/" + id + "/tovar/" + name + "/styear").setValue(styear);
                                                                reference.child("user/" + id + "/tovar/" + name + "/stmonth").setValue(stmonth);
                                                                reference.child("user/" + id + "/tovar/" + name + "/stday").setValue(stday);
                                                                reference.child("user/" + id + "/tovar/" + name + "/amount").setValue(amount3);
                                                                reference.child("user/" + id + "/tovar/" + name + "/pricebegin").setValue(price);

                                                                String emailnew = email.replace(".","");

                                                                reference.child("tovar/" + name+"_"+emailnew + "/name").setValue(name);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/year").setValue(year);
                                                                reference.child("tovar/" + name+"_"+emailnew +"/month").setValue(month);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/day").setValue(day);
                                                                reference.child("tovar/" + name+"_"+emailnew +"/price").setValue(price3);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/styear").setValue(styear);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/stmonth").setValue(stmonth);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/stday").setValue(stday);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/amount").setValue(amount3);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/pricebegin").setValue(price);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/idprodavec").setValue(id);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/nameprodavec").setValue(nameprodavec);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/namemagazin").setValue(namemagazin);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/adres").setValue(adres);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/city").setValue(city);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/telephone").setValue(telephone);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/email").setValue(email);
                                                                reference.child("tovar/" + name+"_"+emailnew + "/id").setValue(name+"_"+emailnew);

                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError1) {

                                                    }
                                                });

                                                Toast.makeText(GoodsSellerOpenTovar.this, "Товар успешно опубликован!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(GoodsSellerOpenTovar.this, SellerSpisokTovarov.class);
                                                startActivity(intent);
                                                finish();
                                                SellerSpisokTovarov.fa.finish();

                                            }
                                        }



                                }
                            }
                        }




                    @Override
                    public void onCancelled(DatabaseError databaseError1) {

                    }
                });

            }


        });
        btnDeletePub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {


                            for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                // do something with the individual "issues"
                                String  id = issue.child("id").getValue().toString();
                                String emailnew = email.replace(".","");
                                reference.child("user/" + id + "/tovar/" + name).removeValue();
                                reference.child("tovar/"+name+"_"+emailnew).removeValue();

                                Toast.makeText(GoodsSellerOpenTovar.this, "Товар снят с публикации!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(GoodsSellerOpenTovar.this, SellerSpisokTovarov.class);
                                startActivity(intent);
                                finish();
                                SellerSpisokTovarov.fa.finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError1) {

                    }
                });

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    public Date getDate(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        return date;
    }
    public static Activity fa2;

    {
        fa2 = this;
    }
    public void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"dialog");

    }

    @Override
    public void applytext(int price1, int amount1) {
        if ((String.valueOf(price3)).length() == 0 || (String.valueOf(amount3)).length() == 0) {
            Toast.makeText(GoodsSellerOpenTovar.this, "Вам нужно ввести цену и количество товаров", Toast.LENGTH_SHORT).show();


        } else {
            price3 = price1;
            amount3 = amount1;

        }
    }

    public static Activity fa1;
    {
        fa1 = this;
    }
}
