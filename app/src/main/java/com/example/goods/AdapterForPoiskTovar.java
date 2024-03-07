package com.example.goods;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterForPoiskTovar extends RecyclerView.Adapter<AdapterForPoiskTovar.MyViewHolder1>
        implements View.OnClickListener, Filterable{
    private List<MyObjectForPoiskTovar> myObjects;
    private List<MyObjectForPoiskTovar> reservedList;
    public String nametext,namemagazin,email,adresmagazin;
    String styear1str,stmonth1str,stday1str,year1str,month1str,day1str,price1str, amount1str,pricebegin1str,telephone;
   int styear1,stmonth1,stday1,year1,month1,day1,price1, amount1,pricebegin1;
    ProgressDialog PD1;
    DatabaseHelperForID dbid;

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tovar_item, parent, false);
        MyViewHolder1 vh=new MyViewHolder1(view);
        view.setOnClickListener(this);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return myObjects.size();
    }

    public void updateData(ArrayList<MyObjectForPoiskTovar> items) {
        myObjects = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    private MyObjectForPoiskTovar getItem(int position) {
        if (position >= 0 && position < myObjects.size())
            return myObjects.get(position);

        return null;
    }

    @Override
    public void onClick(final View v) {
        PD1 = new ProgressDialog(v.getContext());
        PD1.setMessage("Загрузка...");
        PD1.setCancelable(true);
        PD1.setCanceledOnTouchOutside(false);
        TextView clickedName1=(TextView)v.findViewById(R.id.title1337228);
        TextView clickedName2=(TextView)v.findViewById(R.id.price1337228);
        TextView clickedName3=(TextView)v.findViewById(R.id.amount1337228);

        TextView clickedName4=(TextView)v.findViewById(R.id.namemagazin1337228);

        TextView clickedName5=(TextView)v.findViewById(R.id.adresmagazin1337228);

        nametext = clickedName1.getText().toString();
        namemagazin = clickedName4.getText().toString();
        adresmagazin = clickedName5.getText().toString();
        PD1.show();

final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        final Query query1 = reference.child("user").orderByChild("namemagazin").equalTo(namemagazin);


                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {
                            final Query query2 = reference.child("user").orderByChild("adres").equalTo(adresmagazin);
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {
                                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {

                                            // do something with the individual "issues"
                                            final String dbid = issue.child("id").getValue().toString();
                                            email = issue.child("email").getValue().toString();
                                            telephone = issue.child("telephone").getValue().toString();
                                            String emailnew = email.replace(".","");
                                            final String nameprodavec = issue.child("nameprodavec").getValue().toString();

                                            final Query query3 = reference.child("tovar").orderByChild("id").equalTo(nametext+"_"+emailnew);
                                            query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                                    if (dataSnapshot1.exists()) {
                                                        for (DataSnapshot issue : dataSnapshot1.getChildren()) {

                                                            // do something with the individual "issues"
                                                            styear1str = issue.child("styear").getValue().toString();
                                                            stmonth1str = issue.child("stmonth").getValue().toString();
                                                            stday1str = issue.child("stday").getValue().toString();
                                                            year1str = issue.child("year").getValue().toString();
                                                            month1str = issue.child("month").getValue().toString();
                                                            day1str = issue.child("day").getValue().toString();
                                                            price1str = issue.child("price").getValue().toString();
                                                            amount1str = issue.child("amount").getValue().toString();
                                                            pricebegin1str = issue.child("pricebegin").getValue().toString();


                                                            styear1 = Integer.parseInt(styear1str);
                                                            stmonth1 = Integer.parseInt(stmonth1str);
                                                            stday1 = Integer.parseInt(stday1str);
                                                            year1 = Integer.parseInt(year1str);
                                                            month1 = Integer.parseInt(month1str);
                                                            day1 = Integer.parseInt(day1str);
                                                            price1 = Integer.parseInt(price1str);
                                                            amount1 = Integer.parseInt(amount1str);
                                                            pricebegin1 = Integer.parseInt(pricebegin1str);
                                                            Intent myIntent = new Intent(v.getContext(), ActivityForTovarOrder.class);
                                                            Bundle extras = new Bundle();
                                                            extras.putString("id", dbid);
                                                            extras.putString("name", nametext);
                                                            extras.putInt("styear", styear1);
                                                            extras.putInt("stmonth", stmonth1);
                                                            extras.putInt("stday", stday1);
                                                            extras.putInt("year", year1);
                                                            extras.putInt("month", month1);
                                                            extras.putInt("day", day1);
                                                            extras.putInt("price", price1);
                                                            extras.putInt("amount", amount1);
                                                            extras.putInt("pricebegin", pricebegin1);
                                                            extras.putString("telephone", telephone);
                                                            extras.putString("nameprodavec", nameprodavec);
                                                            extras.putString("namemagazin", namemagazin);
                                                            extras.putString("adresmagazin", adresmagazin);
                                                            extras.putString("email", email);




                                                            myIntent.putExtras(extras);

                                                            v.getContext().startActivity(myIntent);

                                                            PD1.dismiss();
                                                        }


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError1) {

                                                }
                                            });


                                        }


                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError1) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError1) {

                    }
                });





    }

    public AdapterForPoiskTovar(List<MyObjectForPoiskTovar> myObjects){
        this.myObjects = myObjects;
        this.reservedList =  myObjects;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (!charString.isEmpty()) {
                    List<MyObjectForPoiskTovar> filteredList = new ArrayList<>();
                    for (MyObjectForPoiskTovar row : reservedList) {
                        if (row.title.toLowerCase().contains(charString) ) {
                            filteredList.add(row);
                        }
                    }
                    myObjects = filteredList;
                } else {
                    myObjects = reservedList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = myObjects;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                myObjects = (ArrayList<MyObjectForPoiskTovar>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    class MyViewHolder1 extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        TextView amount;
        TextView namemagazin;
        TextView adresmagazin;

        MyViewHolder1(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1337228);
            price = itemView.findViewById(R.id.price1337228);
            amount = itemView.findViewById(R.id.amount1337228);
            namemagazin = itemView.findViewById(R.id.namemagazin1337228);
            adresmagazin = itemView.findViewById(R.id.adresmagazin1337228);
        }

        void bindData(MyObjectForPoiskTovar myObject) {
            if (myObject == null)
                return;

            title.setText(myObject.title);
            price.setText(myObject.price);
            amount.setText(myObject.amount);
            namemagazin.setText(myObject.namemagazin);
            adresmagazin.setText(myObject.adresmagazin);
        }
    }
}
