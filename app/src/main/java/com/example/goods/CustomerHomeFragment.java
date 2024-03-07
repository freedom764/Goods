package com.example.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CustomerHomeFragment extends Fragment {
    Button poisk_magazin,poisk_tovar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_customer,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        poisk_magazin = view.findViewById(R.id.poisk_magazin);
        poisk_tovar = view.findViewById(R.id.poisk_tovar);

        poisk_magazin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsCustomer.fa, PoiskMagazin.class);
                startActivity(intent);
            }
        });
        poisk_tovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GoodsCustomer.fa, PoiskTovar.class);
                startActivity(intent1);

            }
        });

    }
}
