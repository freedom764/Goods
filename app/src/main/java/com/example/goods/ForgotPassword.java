package com.example.goods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    EditText emailforget;
    Button recover;
    private ProgressDialog PD;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emailforget = findViewById(R.id.email112);
        recover = findViewById(R.id.recover);
        emailforget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    emailforget.setBackground(ContextCompat.getDrawable(ForgotPassword.this, R.drawable.edittextborder));
                }else{
                    emailforget.setBackground(ContextCompat.getDrawable(ForgotPassword.this, R.drawable.redborderedit));
                }

            }
        });
        emailforget.addTextChangedListener(logintextwatcher);
        PD = new ProgressDialog(this);
        PD.setMessage("Загрузка...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();

        recover.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {

                final String email1 = emailforget.getText().toString();

                try {

                    if ( emailforget.length() > 0) {
                        if (isEmailValid(email1)){

                            FirebaseAuth.getInstance().sendPasswordResetEmail(email1)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ForgotPassword.this, "Вам отправлено письмо для восстановления пароля!", Toast.LENGTH_LONG).show();
                                            }
                                            else {

                                                Toast.makeText(ForgotPassword.this, "Ошибка при восстановлении пароля! Возможно данный email не зарегистрирован или уже получил много таких сообщений", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "Введите правильный адрес электронной почты", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(
                                ForgotPassword.this,
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
            String textemail = emailforget.getText().toString().trim();


            boolean check = isEmailValid(textemail);
            if(textemail.length()>0 && check) {
                recover.setBackground(ContextCompat.getDrawable(ForgotPassword.this, R.drawable.custombutton));
                recover.setTextColor(getResources().getColor(R.color.colorWhite));
                recover.setEnabled(true);
            }
            else {
                recover.setBackground(ContextCompat.getDrawable(ForgotPassword.this, R.drawable.loginbuttonoff));
                recover.setTextColor(getResources().getColor(R.color.colorGray));
                recover.setEnabled(false);
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
