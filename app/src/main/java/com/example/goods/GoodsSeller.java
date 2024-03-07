package com.example.goods;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GoodsSeller extends AppCompatActivity {
Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_seller);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        Intent intent = this.getIntent();
        if(intent !=null)
        {
            String strdata = intent.getExtras().getString("From");
            if(strdata.equals("FromSpisok"))
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListFragment()).commit();
bottomNavigationView.setSelectedItemId(R.id.nav_list);


            }
            if(strdata.equals("FromUser"))
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.nav_user);
                ChangeSeller.fa.finish();

            }


        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_list:
                            selectedFragment = new ListFragment();
                            break;
                        case R.id.nav_user:
                            selectedFragment = new UserFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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
                            GoodsSeller.super.onBackPressed();
                            GoodsSeller.fa.finish();
                        }
                    })
                    .setNegativeButton("Нет", null)
                    .show();
        }


}
