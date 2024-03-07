package com.example.goods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GoodsMain extends AppCompatActivity {
Button signout;
ProgressDialog PD1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_main);

        PD1 = new ProgressDialog(this);
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);


        if(SaveSharedPreference.getUserName(GoodsMain.this).length() == 0)
        {
            // call Login Activity
            Intent intent = new Intent(GoodsMain.this, MainActivity.class);
            startActivity(intent);
            GoodsMain.this.finish();
        }
        else
        {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            String user = SaveSharedPreference.getUserName(GoodsMain.this);

            final Query query1 = reference.child("user").orderByChild("email").equalTo(user);
            PD1.show();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot1) {
                    if (dataSnapshot1.exists()) {
                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {
String isprodavec =issue.child("isprodavec").getValue().toString();


if (isprodavec.equals("1")) {
    Intent intent1 = new Intent(GoodsMain.this, GoodsSeller.class);
    intent1.putExtra("From","FromGoodsMain");
    startActivity(intent1);
    GoodsMain.this.finish();
}
if(isprodavec.equals("0")) {
    Intent intent = new Intent(GoodsMain.this, GoodsCustomer.class);
    startActivity(intent);
    GoodsMain.this.finish();
}
                            PD1.dismiss();
                    }}}
                @Override
                public void onCancelled(DatabaseError databaseError1) {

                }
            });






        }
    }
}
