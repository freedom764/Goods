package com.example.goods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {
Button prodavec,pokupatel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prodavec = findViewById(R.id.button1);
        pokupatel = findViewById(R.id.button2);
        prodavec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose.this, RegSeller.class);
                startActivity(intent);
                Choose.this.finish();
            }
        });
        LoadingDialog loadingDialog = new LoadingDialog(Choose.this);

        pokupatel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Choose.this, RegCustomer.class);
                startActivity(intent1);
                Choose.this.finish();
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
}
