package com.example.goods;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class Order extends AppCompatActivity {
    TextView txt1,txt2,txt4,txt5,txt6,txtnew,txt7,txt8,txt9,txt10,txt11;
    String yearAsString, monthAsString, dayAsString, priceAsString, styearAsString, stmonthAsString, stdayAsString,percentAsString,email1,amountAsString,pricebeginAsString;
    int year,month,day,price,styear,stmonth,stday, amount,pricebegin,yearwunder,monthwunder,daywunder;
    String name,id,idprodavec,telephone,nameprodavec,namemagazin,adresmagazin,pokupatel;
    ProgressDialog PD3;
    ObjectAnimator progressAnimator;
    ProgressBar progress;
    Button btn1337,btn;
    String blokirovkastr;
    int blokirovka,amount3;
    DatabaseReference reference;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar c = Calendar.getInstance();
        Bundle extras = getIntent().getExtras();
        idprodavec = extras.getString("Id");
        name = extras.getString("Name");
        styear = extras.getInt("StYear");
        stmonth = extras.getInt("StMonth");
        stday = extras.getInt("StDay");
        year = extras.getInt("Year");
        month = extras.getInt("Month");
        day = extras.getInt("Day");
        price = extras.getInt("Price");
        amount = extras.getInt("Amount");
        pricebegin = extras.getInt("Pricebegin");
        email1 = extras.getString("Email");
        telephone = extras.getString("Telephone");
        nameprodavec = extras.getString("Nameprodavec");
        pokupatel = extras.getString("Pokupatel");


        yearwunder = c.get(Calendar.YEAR);
        monthwunder = c.get(Calendar.MONTH);
        daywunder = c.get(Calendar.DAY_OF_MONTH);
        PD3 = new ProgressDialog(this);
        PD3.setMessage("Загрузка...");
        PD3.setCancelable(true);
        PD3.setCanceledOnTouchOutside(false);

        btn = findViewById(R.id.btngood228);
        txt1 = findViewById(R.id.nametovargood);
        txt2 = findViewById(R.id.sroktovargood);

        txt4 = findViewById(R.id.publishedpricetovargood);
        txt5 = findViewById(R.id.textViewgood);
        txt6 = findViewById(R.id.amounttovargood);
        txt7 = findViewById(R.id.namepokupatelgood);
        txt10 = findViewById(R.id.emailgood);
        txt11 = findViewById(R.id.telephonegood);
        progress = findViewById(R.id.progressbargood);

        yearAsString = Integer.toString(year);
        monthAsString = Integer.toString(month);
        dayAsString = Integer.toString(day);
        priceAsString = Integer.toString(price);
        styearAsString = Integer.toString(styear);
        stmonthAsString = Integer.toString(stmonth);
        stdayAsString = Integer.toString(stday);
        amountAsString = Integer.toString(amount);
        pricebeginAsString = Integer.toString(pricebegin);

        LayerDrawable progressBarDrawable = (LayerDrawable) progress.getProgressDrawable();
        Drawable progressDrawable = progressBarDrawable.getDrawable(1);
        txt1.setText(name);
        txt6.setText("Количество товаров в заказе: "+amountAsString);
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


        txt4.setText("Опубликованная цена: "+priceAsString+" тенге");

        Date datestart = getDate(styear,(stmonth-1),stday);
        Date dateend = getDate(year,(month-1),day);
        Date datetoday = getDate(yearwunder,monthwunder,daywunder);

        double millisstart = datestart.getTime();
        double millisend = dateend.getTime();
        double millistoday = datetoday.getTime();
        double percent = (((millistoday - millisstart) / (millisend - millisstart)) * 100);
        double daysleft = (millisend/8.64e+7)-(millistoday/8.64e+7);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        String daysleftstring = df.format(daysleft);
        txt5.setText(daysleftstring);
        txt7.setText("Покупатель: "+nameprodavec);
        txt10.setText("Email: "+email1);
        txt11.setText("Номер телефона: "+telephone);
        txt10.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt11.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher_foreground);
                builder.setTitle("Goods");
                builder.setMessage("Вы хотите отправить электронное сообщение покупателю?");

                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Email = new Intent(Intent.ACTION_SEND);
                        Email.setType("text/email");
                        Email.putExtra(Intent.EXTRA_EMAIL, new String[] {email1});
                        Email.putExtra(Intent.EXTRA_SUBJECT, "От продавца");
                        Email.putExtra(Intent.EXTRA_TEXT, "Уважаемый покупатель, " + "");
                        startActivity(Intent.createChooser(Email, "Отправить письмо через:"));
                    }
                });

                builder.setNeutralButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        txt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher_foreground);
                builder.setTitle("Goods");
                builder.setMessage("Вы хотите позвонить покупателю?");

                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        makePhoneCall();
                    }
                });

                builder.setNeutralButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        if(percent<100 && percent>=90){

            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorRed), PorterDuff.Mode.SRC_IN);
            txt5.setTextColor(getResources().getColor(R.color.colorRed));
        }
        if(percent<90 && percent>=75){
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange), PorterDuff.Mode.SRC_IN);
            txt5.setTextColor(getResources().getColor(R.color.colorOrange));
        }
        if(percent<75 && percent>=50){
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            txt5.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        if(percent<50 && percent>=0){
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            txt5.setTextColor(getResources().getColor(R.color.colorGreen));
        }

        String percentintstring = df.format(percent);
        int percentint = Integer.parseInt(percentintstring);
        progressAnimator.ofInt(progress, "progress", percentint)
                .setDuration(1000)
                .start();
       final String  email = SaveSharedPreference.getUserName(Order.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                final Query query1 = reference1.child("user").orderByChild("email").equalTo(email);
                PD3.show();
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {


                            for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                // do something with the individual "issues"
                                final String id = issue.child("id").getValue().toString();
                                final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                                final Query query2 = reference2.child("user/" + id + "/orders").orderByChild("tovarname").equalTo(name);

                                query2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot issue : dataSnapshot.getChildren()) {

                                            String nameoftheorder = issue.child("tovarname").getValue().toString();
                                            String orderamount = issue.child("amount").getValue().toString();
                                            final String idprost = issue.child("id").getValue().toString();

                                            reference1.child("user/" + pokupatel + "/orders/"+idprost).removeValue();
                                            reference1.child("user/" + idprodavec + "/orders/"+idprost).removeValue();





                                        }
                                        reference1.removeEventListener(this);
                                        PD3.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }


                            query1.removeEventListener(this);


                        }}
                    @Override
                    public void onCancelled(DatabaseError databaseError1) {

                    }
                });
                PD3.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        GoodsSeller.fa.finish();
                        Toast.makeText(Order.this, "Заказ успешно удален!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Order.this,GoodsSeller.class);
                        intent.putExtra("From","FromShowed");
                        startActivity(intent);
                        finish();


                    }

                }, 500);
                PD3.dismiss();



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




    private void makePhoneCall() {
        String number = telephone;
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(Order.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Order.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(Order.this, "Нет номера", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Нет разрешения", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Activity fa1;
    {
        fa1 = this;
    }
}
