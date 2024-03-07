package com.example.goods;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Magazin extends AppCompatActivity {
TextView txtfornamemagazin,txtforadresmagazin,txtfornameprodavec,txtforemail,txtforphone,txtfortovars;
Button opentovars;
    String namemagazin,adresmagazin,nameprodavec,email,city,phone,id,tovars,namee;
    DatabaseReference reference1;
    ProgressDialog PD1;
    int sum;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sum = 0;
        PD1 = new ProgressDialog(Magazin.this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        Bundle extras = getIntent().getExtras();
        namemagazin = extras.getString("namemagazin");
        adresmagazin = extras.getString("adres");
        nameprodavec = extras.getString("nameprodavec");
        email = extras.getString("email");
        city = extras.getString("city");
        phone = extras.getString("telephone");
        txtfornamemagazin =  findViewById(R.id.txtfornamemagazin);
        txtforadresmagazin =  findViewById(R.id.txtforadresmagazin);
        txtfornameprodavec =  findViewById(R.id.txtfornameprodavec);
        txtforemail = findViewById(R.id.txtforemail);
        txtforphone =  findViewById(R.id.txtforphone);
        txtfortovars =  findViewById(R.id.txtfortovars);
        opentovars = findViewById(R.id.opentovars);
        txtfornamemagazin.setText(namemagazin);
        txtforadresmagazin.setText("Адрес: "+city+", "+adresmagazin);
        txtfornameprodavec.setText("Владелец магазина: "+nameprodavec);
        txtforemail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtforphone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtforemail.setText("Email: "+email);
        txtforphone.setText("Номер телефона: "+phone);
        txtfortovars.setText("Продавец еще не опубликовал товары!");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        // do something with the individual "issues"
                        id = issue.child("id").getValue().toString();

                        reference1 = FirebaseDatabase.getInstance().getReference().child("user/" + id + "/tovar");
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    namee = issue.child("name").getValue().toString();
                                      if (namee !=(null)) {
                                       sum = sum + 1;
                                         }

                                    if (sum == 0) {
                                        txtfortovars.setText("Продавец еще не опубликовал товары!");
                                    } else {
                                        txtfortovars.setText("Количество опубликованных товаров: " + sum);
                                    }





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

        PD1.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String vnutri = txtfortovars.getText().toString();
                if (vnutri.equals("Продавец еще не опубликовал товары!")) {
                    opentovars.setBackground(ContextCompat.getDrawable(Magazin.this, R.drawable.loginbuttonoff));
                    opentovars.setTextColor(getResources().getColor(R.color.colorGray));
                    opentovars.setEnabled(false);


                }
                else {
                    opentovars.setBackground(ContextCompat.getDrawable(Magazin.this, R.drawable.custombutton));
                    opentovars.setTextColor(getResources().getColor(R.color.colorWhite));
                    opentovars.setEnabled(true);
                }

                PD1.dismiss();
            }

        }, 1000);

        opentovars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentisha = new Intent(Magazin.this,MagazinSpisokTovarov.class);
                intentisha.putExtra("id",id);
                startActivity(intentisha);
            }
        });
        txtforemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Magazin.this);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher_foreground);
                builder.setTitle("Goods");
                builder.setMessage("Вы хотите отправить электронное сообщение продавцу?");

                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Email = new Intent(Intent.ACTION_SEND);
                        Email.setType("text/email");
                        Email.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
                        Email.putExtra(Intent.EXTRA_SUBJECT, "От покупателя");
                        Email.putExtra(Intent.EXTRA_TEXT, "Уважаемый продавец, " + "");
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
        txtforphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Magazin.this);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher_foreground);
                builder.setTitle("Goods");
                builder.setMessage("Вы хотите позвонить продавцу?");

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




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    private void makePhoneCall() {
        String number = txtforphone.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(Magazin.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Magazin.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(Magazin.this, "Нет номера", Toast.LENGTH_SHORT).show();
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
