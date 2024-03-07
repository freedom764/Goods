package com.example.goods;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class MyAdapterForOrders extends RecyclerView.Adapter<MyAdapterForOrders.MyViewHolder>
        implements View.OnClickListener, Filterable {
    private List<MyObject> myObjects;
    private List<MyObject> reservedList;
    public String nametext;
    DatabaseHelper myDB;


    int idactivity;
    String nameactivity;
    int yearactivity;
    int monthactivity;
    int dayactivity;
    int priceactivity;
    int styearactivity;
    int stmonthactivity;
    int stdayactivity;






    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder vh=new MyViewHolder(view);
        view.setOnClickListener(this);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return myObjects.size();
    }

    public void updateData(ArrayList<MyObject> items) {
        myObjects = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    private MyObject getItem(int position) {
        if (position >= 0 && position < myObjects.size())
            return myObjects.get(position);

        return null;
    }

    @Override
    public void onClick(final View v) {

        idactivity = 0;
        nameactivity = null;
        yearactivity = 0;
        monthactivity = 0;
        dayactivity = 0;
        priceactivity = 0;
        styearactivity = 0;
        stmonthactivity = 0;
        stdayactivity = 0;


        myDB = new DatabaseHelper(v.getContext());



        TextView clickedName=(TextView)v.findViewById(R.id.title);
        nametext = clickedName.getText().toString();

        Cursor c = myDB.showDataonSelect(nametext);



        if(c.moveToFirst()) {

            idactivity = c.getInt(0) ;
            nameactivity = c.getString(1) ;
            yearactivity = c.getInt(2) ;
            monthactivity = c.getInt(3) ;
            dayactivity = c.getInt(4);
            priceactivity = c.getInt(5);
            styearactivity = c.getInt(6);
            stmonthactivity = c.getInt(7);
            stdayactivity = c.getInt(8);}

               final String email = SaveSharedPreference.getUserName(v.getContext());
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        final Query query1 = reference.child("user").orderByChild("email").equalTo(email);


        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {
                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                        final String id = issue.child("id").getValue().toString();
                        final Query query2 = reference.child("user/"+id+"/orders").orderByChild("tovarname").equalTo(nametext);
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                if (dataSnapshot1.exists()) {
                                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {

                                        // do something with the individual "issues"
                                        final String telephone = issue.child("telephone").getValue().toString();
                                       final  String amount = issue.child("amount").getValue().toString();
                                        final String price = issue.child("price").getValue().toString();
                                        final String pokupatel = issue.child("pokupatel").getValue().toString();
                                        final String email1 = issue.child("email").getValue().toString();

                                        final Query query2 = reference.child("user").orderByChild("id").equalTo(pokupatel);
                                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                                if (dataSnapshot1.exists()) {
                                                    for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                                        String nameprodavec = issue.child("nameprodavec").getValue().toString();
                                        int amount1 = Integer.parseInt(amount);
                                        int price1 = Integer.parseInt(price);

                                        Intent myIntent = new Intent(v.getContext(), Order.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("Id", id);
                                        extras.putString("Name", nametext);
                                        extras.putInt("Year", yearactivity);
                                        extras.putInt("Month", monthactivity);
                                        extras.putInt("Day", dayactivity);
                                        extras.putInt("Price", price1);
                                        extras.putInt("StYear", styearactivity);
                                        extras.putInt("StMonth", stmonthactivity);
                                        extras.putInt("StDay", stdayactivity);
                                        extras.putString("Email",email1);
                                        extras.putInt("Amount",amount1);
                                        extras.putString("Telephone",telephone);
                                        extras.putString("Pokupatel",pokupatel);
                                        extras.putString("Nameprodavec",nameprodavec);

                                        myIntent.putExtras(extras);

                                        v.getContext().startActivity(myIntent);



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
            }

            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });







            }

    public MyAdapterForOrders(List<MyObject> myObjects){
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
                    List<MyObject> filteredList = new ArrayList<>();
                    for (MyObject row : reservedList) {
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
                myObjects = (ArrayList<MyObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.sub_title);
        }

        void bindData(MyObject myObject) {
            if (myObject == null)
                return;

            title.setText(myObject.title);
            subTitle.setText(myObject.subTitle);
        }
    }

}