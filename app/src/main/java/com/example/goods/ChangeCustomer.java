package com.example.goods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChangeCustomer extends AppCompatActivity {
    EditText password101,passwordcheck101;
    Button registration101;
    ProgressDialog PD1;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PD1 = new ProgressDialog(this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);

        password101 = findViewById(R.id.password101101);
        passwordcheck101 = findViewById(R.id.passwordcheck101101);
        registration101 = findViewById(R.id.registration101101);

        password101.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    password101.setBackground(ContextCompat.getDrawable(ChangeCustomer.this, R.drawable.edittextborder));
                }else{
                    password101.setBackground(ContextCompat.getDrawable(ChangeCustomer.this, R.drawable.redborderedit));
                }

            }
        });
        passwordcheck101.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    passwordcheck101.setBackground(ContextCompat.getDrawable(ChangeCustomer.this, R.drawable.edittextborder));
                }else{
                    passwordcheck101.setBackground(ContextCompat.getDrawable(ChangeCustomer.this, R.drawable.redborderedit));
                }

            }
        });
        password101.addTextChangedListener(logintextwatcher);
        passwordcheck101.addTextChangedListener(logintextwatcher);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        email = SaveSharedPreference.getUserName(this);



        registration101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String frompassword = password101.getText().toString();
                String frompasswordcheck = passwordcheck101.getText().toString();
                if(frompassword.length()>0 && frompasswordcheck.length()>0){
                    if (frompasswordcheck.equals(frompassword)) {


                        user.updatePassword(frompassword);


                        final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                if (dataSnapshot1.exists()) {


                                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                        // do something with the individual "issues"
                                        final String id = issue.child("id").getValue().toString();
                                        reference.child("user/" + id + "/password").removeValue();
                                        reference.child("user/" + id + "/password").setValue(frompassword);



                                    }
                                }
                                PD1.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError1) {

                            }
                        });
                        Toast.makeText(ChangeCustomer.this, "Пароль обновлен!", Toast.LENGTH_LONG).show();
                        GoodsCustomer.fa.finish();
                        Intent intent = new Intent(ChangeCustomer.this, GoodsCustomer.class);
                        intent.putExtra("From","FromUser");
                        startActivity(intent);



                    }
                    else {
                        Toast.makeText(ChangeCustomer.this, "Пароли должны совпадать", Toast.LENGTH_LONG).show();
                    }

                }

                else {
                    Toast.makeText(
                            ChangeCustomer.this,
                            "Пароль должен иметь минимум 6 символов",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

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
            String textpassword = password101.getText().toString().trim();
            String textpasswordcheck = passwordcheck101.getText().toString().trim();


            if(textpassword.length()>0 && textpasswordcheck.length() > 0) {
                registration101.setBackground(ContextCompat.getDrawable(ChangeCustomer.this, R.drawable.custombutton));
                registration101.setTextColor(getResources().getColor(R.color.colorWhite));
                registration101.setEnabled(true);
            }
            else {
                registration101.setBackground(ContextCompat.getDrawable(ChangeCustomer.this, R.drawable.loginbuttonoff));
                registration101.setTextColor(getResources().getColor(R.color.colorGray));
                registration101.setEnabled(false);
            }
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    public static Activity fa;
    {
        fa = this;
    }

}
