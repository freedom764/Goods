package com.example.goods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegCustomer extends AppCompatActivity {
    private ProgressDialog PD;
    private FirebaseAuth auth;
    private EditText fio,telephone,email,password,passwordcheck;
    private Button registration;
    private Spinner mySpinner;
    DatabaseReference databasePokupatel;
    String fio1,city1,email1,password1,telephone1,passwordcheck1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mySpinner = (Spinner) findViewById(R.id.spinner10);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(RegCustomer.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cities));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter1);
        databasePokupatel = FirebaseDatabase.getInstance().getReference("user");
        PD = new ProgressDialog(this);
        PD.setMessage("Загрузка...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();

        fio = findViewById(R.id.fio10);

        email = findViewById(R.id.email10);
        telephone = findViewById(R.id.telefon10);
        password = findViewById(R.id.password10);
        passwordcheck = findViewById(R.id.passwordcheck10);

        registration = findViewById(R.id.registration10);
        fio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    fio.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.edittextborder));
                }else{
                    fio.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.redborderedit));
                }

            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    email.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.edittextborder));
                }else{
                    email.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.redborderedit));
                }

            }
        });


        telephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    telephone.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.edittextborder));
                }else{
                    telephone.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.redborderedit));
                }

            }
        });
        telephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    telephone.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.edittextborder));
                }else{
                    telephone.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.redborderedit));
                }

            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    password.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.edittextborder));
                }else{
                    password.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.redborderedit));
                }

            }
        });
        passwordcheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    passwordcheck.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.edittextborder));
                }else{
                    passwordcheck.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.redborderedit));
                }

            }
        });


        fio.addTextChangedListener(logintextwatcher);
        telephone.addTextChangedListener(logintextwatcher);
        email.addTextChangedListener(logintextwatcher);
        password.addTextChangedListener(logintextwatcher);
        passwordcheck.addTextChangedListener(logintextwatcher);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email1 = email.getText().toString();
                password1 = password.getText().toString();
                city1 = mySpinner.getSelectedItem().toString();
                fio1 = fio.getText().toString();

                passwordcheck1 = passwordcheck.getText().toString();
                telephone1 = telephone.getText().toString();
                try {
                    if (password1.length() > 0 && email1.length() > 0 && city1.length() > 0 && fio1.length() > 0  && passwordcheck1.length() > 0 && telephone1.length()>0) {
                        if (isEmailValid(email1)) {
                            if(!city1.equals("Ваш город")){
                            if (passwordcheck1.equals(password1)) {
                                if (telephone1.length()==11) {
                                PD.show();
                                auth.createUserWithEmailAndPassword(email1, password1)
                                        .addOnCompleteListener(RegCustomer.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(
                                                            RegCustomer.this,
                                                            "Ошибка регистрации! Пароль должен иметь минимум 6 символов, a зарегистрированный email - не может зарегистрироваться снова",
                                                            Toast.LENGTH_LONG).show();

                                                } else {
                                                    Intent intent = new Intent(RegCustomer.this, MainActivity.class);
                                                    Toast.makeText(RegCustomer.this, "Регистрация успешно завершена. Мы с вами свяжемся для подтверждения вашего аккаунта!", Toast.LENGTH_LONG).show();

                                                    String id = databasePokupatel.push().getKey();
                                                    int isprodavec = 0;
                                                    int blokirovka = 1;

                                                    Pokupatel pokupatel = new Pokupatel(id, fio1, city1, telephone1, email1, password1,isprodavec,blokirovka);
                                                    databasePokupatel.child(id).setValue(pokupatel);
                                                    startActivity(intent);
                                                    finish();
                                                    MainActivity.fa2.finish();
                                                }
                                                PD.dismiss();
                                            }
                                        });
                                } else {
                                    Toast.makeText(RegCustomer.this, "Введите корректный телефон", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(RegCustomer.this, "Пароли должны совпадать", Toast.LENGTH_LONG).show();
                            }
                        } else {
                                Toast.makeText(RegCustomer.this, "Выберите город", Toast.LENGTH_LONG).show();
                            }
                        } else {

                            Toast.makeText(RegCustomer.this, "Введите правильный email", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(
                                RegCustomer.this,
                                "Заполните все поля",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
            String textemail = email.getText().toString().trim();
            String textpassword = password.getText().toString().trim();
            String textfio = fio.getText().toString().trim();

            String texttelephone = telephone.getText().toString().trim();
            String textpasswordcheck = passwordcheck.getText().toString().trim();

            boolean check = isEmailValid(textemail);
            if(textemail.length()>0 && check && textpassword.length()>0 && textfio.length()>0 &&
                      texttelephone.length()>0 && textpasswordcheck.length() > 0) {
                registration.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.custombutton));
                registration.setTextColor(getResources().getColor(R.color.colorWhite));
                registration.setEnabled(true);
            }
            else {
                registration.setBackground(ContextCompat.getDrawable(RegCustomer.this, R.drawable.loginbuttonoff));
                registration.setTextColor(getResources().getColor(R.color.colorGray));
                registration.setEnabled(false);
            }
        }
    };
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
