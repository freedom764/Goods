package com.example.goods;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomerUserFragment extends Fragment {
    TextView txtforname,txtforcity,txtformagazin,txtforadres,txtforphone,txtforstatus,txtforemail;
    String name,city,magazin,adres,phone,blok,email;
    ProgressDialog PD1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_user_customer,container,false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PD1 = new ProgressDialog(getActivity());
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        txtforname = view.findViewById(R.id.txtforname1);
        txtforcity = view.findViewById(R.id.txtforcity1);
        txtforphone = view.findViewById(R.id.txtforphone1);
        txtforstatus = view.findViewById(R.id.txtforstatus1);
        txtforemail = view.findViewById(R.id.txtforemail1);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        PD1.show();
        email = SaveSharedPreference.getUserName(getActivity());
        final Query query1 = reference.child("user").orderByChild("email").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {


                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {

                        name = issue.child("nameprodavec").getValue().toString();
                        city = issue.child("city").getValue().toString();
                        phone = issue.child("telephone").getValue().toString();
                        blok = issue.child("blokirovka").getValue().toString();
                        txtforname.setText(name);
                        txtforcity.setText("Город: "+city);
                        txtforphone.setText("Телефон: "+phone);
                        txtforemail.setText("Email: "+email);
                        if (blok.equals("1")) {
                            txtforstatus.setText("Статус аккаунта: Не подтвержден");
                        }
                        else {
                            txtforstatus.setText("Статус аккаунта: Подтвержден");
                        }
                        PD1.dismiss();
                    }


                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.example_menu1, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item11:
                Intent intent = new Intent(getActivity(), ChangeCustomer.class);
                startActivity(intent);
                return true;
            case R.id.item22:
                new AlertDialog.Builder(getActivity())
                        .setMessage("Вы уверены что хотите выйти из аккаунта?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SaveSharedPreference.setUserName(getActivity(), null);

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                GoodsCustomer.fa.finish();
                            }
                        })
                        .setNegativeButton("Нет", null)
                        .show();

        }
        return super.onOptionsItemSelected(item);
    }

}
