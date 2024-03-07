package com.example.goods;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UpdateTovarFirebase extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper myDB;
    EditText name3, year3, price3, styear3, priceaga, amountaga;
    Button btn_update;
    String email228;
    String namefrom, yearfromAsString, monthfromAsString, dayfromAsString, pricefromAsString, styearfromAsString, stmonthfromAsString, stdayfromAsString;
    int yearfrom, monthfrom, dayfrom, pricefrom, styearfrom, stmonthfrom, stdayfrom, amount, pricefirebase;

    int year123, month123, day123;
    private int stYear12, stMonth12, stDay12, endYear12, endMonth12, endDay12, endYear1, endMonth1, endDay1, stYear1, stMonth1, stDay1;
    DatabaseReference databaseTovary;
    ProgressDialog PD3, PD4;
    String id;
    String pricefornariman, amountfornariman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tovar_firebase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PD3 = new ProgressDialog(this);
        PD3.setMessage("Загрузка...");
        PD3.setCancelable(true);
        PD3.setCanceledOnTouchOutside(false);
        databaseTovary = FirebaseDatabase.getInstance().getReference("tovar");
        Calendar calcu = Calendar.getInstance();
        year123 = calcu.get(Calendar.YEAR);
        month123 = calcu.get(Calendar.MONTH);
        day123 = calcu.get(Calendar.DAY_OF_MONTH);
        name3 = findViewById(R.id.name2);
        year3 = findViewById(R.id.enddata2);
        price3 = findViewById(R.id.price2);
        styear3 = findViewById(R.id.startdata2);
        priceaga = findViewById(R.id.price3);
        amountaga = findViewById(R.id.amount2);
        btn_update = findViewById(R.id.addtovar2);
        myDB = new DatabaseHelper(this);
        Bundle extras1 = getIntent().getExtras();
        namefrom = extras1.getString("Name1");
        yearfrom = extras1.getInt("Year1");
        monthfrom = extras1.getInt("Month1");
        dayfrom = extras1.getInt("Day1");
        pricefrom = extras1.getInt("Price1");
        styearfrom = extras1.getInt("StYear1");
        stmonthfrom = extras1.getInt("StMonth1");
        stdayfrom = extras1.getInt("StDay1");
        email228 = extras1.getString("email");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        PD3.show();
        final Query query1 = reference.child("user").orderByChild("email").equalTo(email228);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        id = issue.child("id").getValue().toString();


                    }

                    final Query query2 = reference.child("user/" + id + "/tovar").orderByChild("name").equalTo(namefrom);
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            if (dataSnapshot2.exists()) {


                                for (DataSnapshot issue : dataSnapshot2.getChildren()) {
                                    // do something with the individual "issues"
                                    pricefornariman = issue.child("price").getValue().toString();
                                    amountfornariman = issue.child("amount").getValue().toString();
                                    priceaga.setText(pricefornariman);
                                    amountaga.setText(amountfornariman);
                                    PD3.dismiss();
                                }
                            }

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


        yearfromAsString = Integer.toString(yearfrom);
        monthfromAsString = Integer.toString(monthfrom);
        dayfromAsString = Integer.toString(dayfrom);
        pricefromAsString = Integer.toString(pricefrom);
        styearfromAsString = Integer.toString(styearfrom);
        stmonthfromAsString = Integer.toString(stmonthfrom);
        stdayfromAsString = Integer.toString(stdayfrom);

        name3.setText(namefrom);
        styear3.setText(stdayfrom + "-" + (stmonthfrom) + "-" + styearfrom);
        price3.setText(pricefromAsString);
        year3.setText(dayfrom + "-" + (monthfrom) + "-" + yearfrom);
        endYear1 = yearfrom;
        endMonth1 = monthfrom;
        endDay1 = dayfrom;
        stYear1 = styearfrom;
        stMonth1 = stmonthfrom;
        stDay1 = stdayfrom;
        name3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    name3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.edittextborder));
                }else{
                    name3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.redborderedit));
                }

            }
        });
        styear3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    styear3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.edittextborder));
                }else{
                    styear3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.redborderedit));
                }

            }
        });
        year3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    year3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.edittextborder));
                }else{
                    year3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.redborderedit));
                }

            }
        });

        price3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    price3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.edittextborder));
                }else{
                    price3.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.redborderedit));
                }

            }
        });
        priceaga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    priceaga.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.edittextborder));
                }else{
                    priceaga.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.redborderedit));
                }

            }
        });
        amountaga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    amountaga.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.edittextborder));
                }else{
                    amountaga.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.redborderedit));
                }

            }
        });
        name3.addTextChangedListener(logintextwatcher);
        styear3.addTextChangedListener(logintextwatcher);
        year3.addTextChangedListener(logintextwatcher);
        price3.addTextChangedListener(logintextwatcher);
        priceaga.addTextChangedListener(logintextwatcher);
        amountaga.addTextChangedListener(logintextwatcher);
        styear3.setOnClickListener(this);
        year3.setOnClickListener(this);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1 = name3.getText().toString();
                String year2 = year3.getText().toString();
                 String price2 = price3.getText().toString();
                String styear2 = styear3.getText().toString();
                 String pricenariman = priceaga.getText().toString();
                 String amountnariman = amountaga.getText().toString();

                if (name1.length() == 0 || year2.length() == 0 || price2.length() == 0
                        || styear2.length() == 0 || pricenariman.length() == 0 | amountnariman.length() == 0) {
                    Toast.makeText(UpdateTovarFirebase.this, "Вам нужно заполнить все поля", Toast.LENGTH_LONG).show();


                } else {

                            String email = SaveSharedPreference.getUserName(UpdateTovarFirebase.this);
                            long price4 = Long.parseLong(price2);
                            long pricefornarik = Long.parseLong(pricenariman);
                            long amountfornarik = Long.parseLong(amountnariman);
                            if (price4 > 0 && price4 < 2000000000 || pricefornarik > 0 && pricefornarik < 2000000000) {

                                if (amountfornarik > 0 && amountfornarik < 2000000000) {
                                    final int price5 = Integer.parseInt(price2);
                                    final int pricenarik = Integer.parseInt(pricenariman);
                                    final int amountnarik = Integer.parseInt(amountnariman);
                                    Cursor data = myDB.searchForData(name1, email);
                                    if (data.getCount() == 0 || name1.equals(namefrom)) {

                                        if ((endYear1 < year123) || (endYear1 == year123 && (endMonth1) < (month123 + 1)) || (endYear1 == year123 && (endMonth1) == (month123 + 1) && endDay1 <= day123)) {
                                            AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateTovarFirebase.this);
                                            a_builder.setMessage("Вы ввели истекший срок годности!")
                                                    .setCancelable(true)
                                                    .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {


                                                        }
                                                    });


                                            AlertDialog alert = a_builder.create();
                                            alert.setTitle("Внимание!");
                                            alert.show();
                                        } else {
                                            if ((stYear1 > year123) || (stYear1 == year123 && stMonth1 > (month123 + 1)) || (stYear1 == year123 && stMonth1 == (month123 + 1) && stDay1 > day123)) {
                                                AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateTovarFirebase.this);
                                                a_builder.setMessage("Данный товар еще не был изготовлен!")
                                                        .setCancelable(true)
                                                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {


                                                            }
                                                        });

                                                AlertDialog alert = a_builder.create();
                                                alert.setTitle("Внимание!");
                                                alert.show();
                                            } else {
                                                if (pricenarik<=price5) {
                                                    final int year1 = endYear1;
                                                    final int month1 = endMonth1;
                                                    final int day1 = endDay1;
                                                    final int styear1 = stYear1;
                                                    final int stmonth1 = stMonth1;
                                                    final int stday1 = stDay1;
                                                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                                    PD3.show();
                                                    final Query query1 = reference.child("user").orderByChild("email").equalTo(email228);
                                                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot1) {
                                                            if (dataSnapshot1.exists()) {


                                                                for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                                                    // do something with the individual "issues"
                                                                    String telephone = issue.child("telephone").getValue().toString();
                                                                    String city = issue.child("city").getValue().toString();
                                                                    String namemagazin = issue.child("namemagazin").getValue().toString();
                                                                    String adres = issue.child("adres").getValue().toString();
                                                                    String id = issue.child("id").getValue().toString();
                                                                    String nameprodavec = issue.child("nameprodavec").getValue().toString();
                                                                    myDB.updateData21(namefrom, name1, year1, month1, day1, pricenarik, styear1, stmonth1, stday1, email228, amountnarik, price5,telephone,city,nameprodavec,namemagazin,adres);
                                                                    myDB.updateData(namefrom, name1, year1, month1, day1, price5, styear1, stmonth1, stday1, email228);
                                                                    PD3.dismiss();
                                                                }



                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError1) {

                                                        }
                                                    });



                                                    name3.setText("");
                                                    year3.setText("");
                                                    styear3.setText("");
                                                    price3.setText("");
                                                    priceaga.setText("");
                                                    amountaga.setText("");

                                                    PD3.show();
                                                    final Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            GoodsSeller.fa.finish();
                                                            if (SellerSpisokTovarov.fa != null){

                                                                Intent intent = new Intent(UpdateTovarFirebase.this, GoodsSeller.class);

                                                                intent.putExtra("From","FromSpisok");
                                                                startActivity(intent);

                                                            }
                                                            if (SpisokShowed.fa != null){

                                                                Intent intent = new Intent(UpdateTovarFirebase.this, GoodsSeller.class);

                                                                intent.putExtra("From","FromShowed");
                                                                startActivity(intent);
                                                                SpisokShowed.fa.finish();
                                                            }
                                                            PD3.dismiss();
                                                            UpdateTovarFirebase.this.finish();
                                                            GoodsSellerOpenTovar.fa2.finish();

                                                        }
                                                    }, 1000);


                                                }
                                                else {
                                                    Toast.makeText(UpdateTovarFirebase.this, "Цена при публикации не должна быть больше изначальной цены на товар!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }


                                    } else {
                                        AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateTovarFirebase.this);
                                        a_builder.setMessage("У вас уже есть товар с таким названием, переименуйте его! " +
                                                "(Вы можете дополнительно указать ему уникальный код)")
                                                .setCancelable(true)
                                                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {


                                                    }
                                                });

                                        AlertDialog alert = a_builder.create();
                                        alert.setTitle("Внимание!");
                                        alert.show();
                                    }
                                } else {
                                    Toast.makeText(UpdateTovarFirebase.this, "Количество товаров должно быть больше 0 и меньше 2 миллиардов", Toast.LENGTH_LONG).show();
                                }
                            } else {


                                Toast.makeText(UpdateTovarFirebase.this, "Все цены должны быть больше 0 и меньше 2 миллиардов", Toast.LENGTH_LONG).show();

                            }
                        }


            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == styear3) {
            final Calendar c = Calendar.getInstance();
            stYear12 = c.get(Calendar.YEAR);
            stMonth12 = c.get(Calendar.MONTH);
            stDay12 = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int stYear221,
                                              int stMonth221, int stDay221) {

                            styear3.setText(stDay221 + "-" + (stMonth221 + 1) + "-" + stYear221);
                            stYear1 = stYear221;
                            stMonth1 = stMonth221 + 1;
                            stDay1 = stDay221;
                        }
                    }, stYear12, stMonth12, stDay12);
            datePickerDialog.show();
        }
        if (view == year3) {

            // Get Current Time
            final Calendar calendar = Calendar.getInstance();
            endYear12 = calendar.get(Calendar.YEAR);
            endMonth12 = calendar.get(Calendar.MONTH);
            endDay12 = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int endYear221,
                                              int endMonth221, int endDay221) {

                            year3.setText(endDay221 + "-" + (endMonth221 + 1) + "-" + endYear221);
                            endYear1 = endYear221;
                            endMonth1 = endMonth221 + 1;
                            endDay1 = endDay221;
                        }
                    }, endYear12, endMonth12, endDay12);
            datePickerDialog1.show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    private TextWatcher logintextwatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            String textname = name3.getText().toString().trim();
            String textstartdate = styear3.getText().toString().trim();
            String textenddate = year3.getText().toString().trim();
            String textprice = price3.getText().toString().trim();
            String textpriceaga = priceaga.getText().toString().trim();
            String textamountaga = amountaga.getText().toString().trim();



            if(textname.length()>0 && textstartdate.length()>0 && textenddate.length()>0 && textprice.length()>0 && textpriceaga.length()>0 && textamountaga.length()>0 ) {
                btn_update.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.custombutton));
                btn_update.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_update.setEnabled(true);
            }
            else {
                btn_update.setBackground(ContextCompat.getDrawable(UpdateTovarFirebase.this, R.drawable.loginbuttonoff));
                btn_update.setTextColor(getResources().getColor(R.color.colorGray));
                btn_update.setEnabled(false);
            }
        }
    };

}
