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

import java.time.chrono.MinguoDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UpdateTovar extends AppCompatActivity implements  View.OnClickListener{
    DatabaseHelper myDB;
    EditText name3, year3, price3,styear3;
    Button btn_update;
    String email228;
    String namefrom,yearfromAsString,monthfromAsString,dayfromAsString,pricefromAsString,styearfromAsString,stmonthfromAsString,stdayfromAsString;
    int yearfrom,monthfrom,dayfrom,pricefrom,styearfrom,stmonthfrom,stdayfrom;

    int year123,month123,day123;
    private int stYear12, stMonth12, stDay12, endYear12, endMonth12,endDay12, endYear1,endMonth1,endDay1,stYear1,stMonth1,stDay1;
    DatabaseReference databaseTovary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tovar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseTovary = FirebaseDatabase.getInstance().getReference("tovar");
        Calendar calcu = Calendar.getInstance();
        year123 = calcu.get(Calendar.YEAR);
        month123 = calcu.get(Calendar.MONTH);
        day123 = calcu.get(Calendar.DAY_OF_MONTH);
        name3 = findViewById(R.id.name1);
        year3 = findViewById(R.id.enddata1);
        price3 = findViewById(R.id.price1);
        styear3 = findViewById(R.id.startdata1);
        btn_update = findViewById(R.id.addtovar1);


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
                    name3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.edittextborder));
                }else{
                    name3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.redborderedit));
                }

            }
        });
        styear3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    styear3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.edittextborder));
                }else{
                    styear3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.redborderedit));
                }

            }
        });
        year3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    year3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.edittextborder));
                }else{
                    year3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.redborderedit));
                }

            }
        });

        price3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    price3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.edittextborder));
                }else{
                    price3.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.redborderedit));
                }

            }
        });
        name3.addTextChangedListener(logintextwatcher);
        styear3.addTextChangedListener(logintextwatcher);
        year3.addTextChangedListener(logintextwatcher);
        price3.addTextChangedListener(logintextwatcher);
        styear3.setOnClickListener(this);
        year3.setOnClickListener(this);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name3.getText().toString();
                String year2 = year3.getText().toString();
                String price2 = price3.getText().toString();
                String styear2 = styear3.getText().toString();

                if (name1.length() == 0 || year2.length() == 0  || price2.length() == 0
                        || styear2.length() == 0  ) {
                    Toast.makeText(UpdateTovar.this, "Вам нужно заполнить все поля", Toast.LENGTH_LONG).show();


                } else {

                            String email = SaveSharedPreference.getUserName(UpdateTovar.this);
                            long price4 = Long.parseLong(price2);
                            if (price4 > 0 && price4 < 2000000000) {
                                int price5 = Integer.parseInt(price2);
                                Cursor data = myDB.searchForData(name1,email);
                                if (data.getCount() == 0 || name1.equals(namefrom)) {

                                    if ((endYear1 < year123) || (endYear1 == year123 && (endMonth1) < (month123 + 1)) || (endYear1 == year123 && (endMonth1) == (month123 + 1) && endDay1 <= day123)) {
                                        AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateTovar.this);
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
                                            AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateTovar.this);
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
                                            int year1 = endYear1;
                                            int month1 = endMonth1;
                                            int day1 = endDay1;
                                            int styear1 = stYear1;
                                            int stmonth1 = stMonth1;
                                            int stday1 = stDay1;
                                            myDB.updateData(namefrom, name1, year1, month1, day1, price5, styear1, stmonth1, stday1,email228);



                                            name3.setText("");
                                            year3.setText("");
                                            styear3.setText("");
                                            price3.setText("");

                                            Intent intent = new Intent(UpdateTovar.this, SellerSpisokTovarov.class);
                                            startActivity(intent);
                                            SellerSpisokTovarov.fa.finish();
                                            finish();
                                            GoodsSellerOpenTovar.fa1.finish();
                                        }
                                    }




                                } else {
                                    AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateTovar.this);
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


                                Toast.makeText(UpdateTovar.this, "Цена должна быть больше 0 и меньше 2 миллиардов", Toast.LENGTH_LONG).show();

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
            stDay12= c.get(Calendar.DAY_OF_MONTH);


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
        if(id== android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    public Calendar getmycal (int iyear,int imonth){
        int iday = 1;
        Calendar mycal = new GregorianCalendar(iyear, imonth, iday);

        return mycal;

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



            if(textname.length()>0 && textstartdate.length()>0 && textenddate.length()>0 && textprice.length()>0 ) {
                btn_update.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.custombutton));
                btn_update.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_update.setEnabled(true);
            }
            else {
                btn_update.setBackground(ContextCompat.getDrawable(UpdateTovar.this, R.drawable.loginbuttonoff));
                btn_update.setTextColor(getResources().getColor(R.color.colorGray));
                btn_update.setEnabled(false);
            }
        }
    };
}
