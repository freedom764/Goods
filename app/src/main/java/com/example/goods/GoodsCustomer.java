package com.example.goods;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GoodsCustomer extends AppCompatActivity {
Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_customer);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom_customer);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                new CustomerHomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener1 =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.nav_home1:
                            selectedFragment = new CustomerHomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new CustomerOrdersFragment();
                            break;
                        case R.id.nav_user1:
                            selectedFragment = new CustomerUserFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                            selectedFragment).commit();
                    return true;
                }
            };
    public static Activity fa;
    {
        fa = this;
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Вы уверены что хотите выйти из приложения?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GoodsCustomer.super.onBackPressed();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}
