package com.example.goods;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DobavlenyeTovara extends AppCompatActivity implements View.OnClickListener{
Button  add;
EditText name,startdate,enddate,price;
    DatabaseHelper myDB;
    int year11,month11,day11;
    private int stYear11, stMonth11, stDay11, endYear11, endMonth11,endDay11, endYear,endMonth,endDay,stYear,stMonth,stDay;
    DatabaseReference databaseTovary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobavlenye_tovara);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseTovary = FirebaseDatabase.getInstance().getReference("tovar");
        Calendar cal = Calendar.getInstance();
        year11 = cal.get(Calendar.YEAR);
        month11 = cal.get(Calendar.MONTH);
        day11 = cal.get(Calendar.DAY_OF_MONTH);
        name = findViewById(R.id.name);
        startdate = findViewById(R.id.startdata);
        enddate = findViewById(R.id.enddata);
        price = findViewById(R.id.price);
        add = findViewById(R.id.addtovar);
        myDB = new DatabaseHelper(this);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    name.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.edittextborder));
                }else{
                    name.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.redborderedit));
                }

            }
        });
        startdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    startdate.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.edittextborder));
                }else{
                    startdate.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.redborderedit));
                }

            }
        });
        enddate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    enddate.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.edittextborder));
                }else{
                    enddate.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.redborderedit));
                }

            }
        });

        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    price.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.edittextborder));
                }else{
                    price.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.redborderedit));
                }

            }
        });
        startdate.setOnClickListener(this);
        enddate.setOnClickListener(this);
        name.addTextChangedListener(logintextwatcher);
        startdate.addTextChangedListener(logintextwatcher);
        enddate.addTextChangedListener(logintextwatcher);
        price.addTextChangedListener(logintextwatcher);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String price2 = price.getText().toString();
                String st = startdate.getText().toString();
                String end = enddate.getText().toString();


                if (name1.length() == 0  || price2.length() == 0 || st.length() == 0 || end.length() == 0 ) {
                    Toast.makeText(DobavlenyeTovara.this, "Вам нужно заполнить все поля", Toast.LENGTH_LONG).show();


                } else {



                            String email = SaveSharedPreference.getUserName(DobavlenyeTovara.this);

                            long price3 = Long.parseLong(price2);
                            if ( price3 > 0 && price3 < 2000000000){
                                int price1 = Integer.parseInt(price2);
                                Cursor data = myDB.searchForData(name1,email);
                                if (data.getCount() == 0) {
                                    if ((endYear < year11) || (endYear == year11 && (endMonth) < (month11 + 1)) || (endYear == year11 && (endMonth) == (month11 + 1) && endDay <= day11)) {
                                        AlertDialog.Builder a_builder = new AlertDialog.Builder(DobavlenyeTovara.this);
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
                                        if ((stYear > year11) || (stYear == year11 && stMonth > (month11 + 1)) || (stYear == year11 && stMonth == (month11 + 1) && stDay > day11)) {
                                            AlertDialog.Builder a_builder = new AlertDialog.Builder(DobavlenyeTovara.this);
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
                                            int year1 = endYear;
                                            int month1 = endMonth;
                                            int day1 = endDay;
                                            int styear1 = stYear;
                                            int stmonth1 = stMonth;
                                            int stday1 = stDay;

                                            AddData(name1, year1, month1, day1, price1, styear1, stmonth1, stday1, email);




                                            enddate.setText("");
                                            startdate.setText("");
                                            price.setText("");
                                            name.setText("");



                                            Intent intent13 = new Intent(DobavlenyeTovara.this, SellerSpisokTovarov.class);
                                            startActivity(intent13);
                                            finish();
                                            SellerSpisokTovarov.fa.finish();
                                        }
                                    }




                                } else {
                                    AlertDialog.Builder a_builder = new AlertDialog.Builder(DobavlenyeTovara.this);
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
                            }
                            else{

                                Toast.makeText(DobavlenyeTovara.this, "Цена должна быть больше 0 и меньше 2 миллиардов", Toast.LENGTH_LONG).show();

                            }

                    }}
        });


    }
    @Override
    public void onClick(View view) {

        if (view == startdate) {
            final Calendar c = Calendar.getInstance();
            stYear11 = c.get(Calendar.YEAR);
            stMonth11 = c.get(Calendar.MONTH);
            stDay11= c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int stYear22,
                                              int stMonth22, int stDay22) {

                            startdate.setText(stDay22 + "." + (stMonth22 + 1) + "." + stYear22);
                            stYear = stYear22;
                            stMonth = stMonth22 + 1;
                            stDay = stDay22;
                        }
                    }, stYear11, stMonth11, stDay11);
            datePickerDialog.show();
        }
        if (view == enddate) {

            // Get Current Time
            final Calendar calendar = Calendar.getInstance();
            endYear11 = calendar.get(Calendar.YEAR);
            endMonth11 = calendar.get(Calendar.MONTH);
            endDay11 = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int endYear22,
                                              int endMonth22, int endDay22) {

                            enddate.setText(endDay22 + "." + (endMonth22 + 1) + "." + endYear22);
                            endYear = endYear22;
                            endMonth = endMonth22 + 1;
                            endDay = endDay22;
                        }
                    }, endYear11, endMonth11, endDay11);
            datePickerDialog1.show();
        }
    }
        public void AddData(String name1, int year1, int month1, int day1, int price1, int styear1, int stmonth1, int stday1, String email) {

            boolean insertData = myDB.addData(name1,year1,month1,day1,price1,styear1,stmonth1,stday1,email);

            if(insertData==true){
                Toast.makeText(this, "Товар успешно добавлен!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Что-то пошло не так :(", Toast.LENGTH_LONG).show();
            }
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {
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
            String textname = name.getText().toString().trim();
            String textstartdate = startdate.getText().toString().trim();
            String textenddate = enddate.getText().toString().trim();
            String textprice = price.getText().toString().trim();



            if(textname.length()>0 && textstartdate.length()>0 && textenddate.length()>0 && textprice.length()>0 ) {
                add.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.custombutton));
                add.setTextColor(getResources().getColor(R.color.colorWhite));
                add.setEnabled(true);
            }
            else {
                add.setBackground(ContextCompat.getDrawable(DobavlenyeTovara.this, R.drawable.loginbuttonoff));
                add.setTextColor(getResources().getColor(R.color.colorGray));
                add.setEnabled(false);
            }
        }
    };
}
