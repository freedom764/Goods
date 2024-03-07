package com.example.goods;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

public class AdapterForTovar extends RecyclerView.Adapter<AdapterForTovar.MyViewHolder1>
        implements View.OnClickListener, Filterable {
    private List<MyObjectForShowed> myObjects;
    private List<MyObjectForShowed> reservedList;
    public String nametext;
    String styear1str,stmonth1str,stday1str,year1str,month1str,day1str,price1str, amount1str,pricebegin1str;
   int styear1,stmonth1,stday1,year1,month1,day1,price1, amount1,pricebegin1;
    ProgressDialog dialog;
    DatabaseHelperForID dbid;

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showed_item, parent, false);
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

    public void updateData(ArrayList<MyObjectForShowed> items) {
        myObjects = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    private MyObjectForShowed getItem(int position) {
        if (position >= 0 && position < myObjects.size())
            return myObjects.get(position);

        return null;
    }

    @Override
    public void onClick(final View v) {
        TextView clickedName1=(TextView)v.findViewById(R.id.title1337);
        nametext = clickedName1.getText().toString();
        dbid = new DatabaseHelperForID(v.getContext());
        Cursor data1 = dbid.getListContents2();
        if(data1.getCount() == 0){
            AlertDialog.Builder a_builder = new AlertDialog.Builder(v.getContext());
            a_builder.setMessage("Ошибочка")
                    .setCancelable(false)
                    .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

            AlertDialog alert = a_builder.create();
            alert.setTitle("Список товаров");
            alert.show();
        }else {

            while (data1.moveToNext()) {

                final String dbid = data1.getString(2);

                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                final Query query1 = reference.child("user/" + dbid + "/tovar").orderByChild("name").equalTo(nametext);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                Intent myIntent = new Intent(v.getContext(), ActivityForTovar.class);
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

    public AdapterForTovar(List<MyObjectForShowed> myObjects){
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
                    List<MyObjectForShowed> filteredList = new ArrayList<>();
                    for (MyObjectForShowed row : reservedList) {
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
                myObjects = (ArrayList<MyObjectForShowed>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    class MyViewHolder1 extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        TextView subTitle;

        MyViewHolder1(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1337);
            price = itemView.findViewById(R.id.price1337);
            subTitle = itemView.findViewById(R.id.sub_title1337);
        }

        void bindData(MyObjectForShowed myObject) {
            if (myObject == null)
                return;

            title.setText(myObject.title);
            price.setText(myObject.price);
            subTitle.setText(myObject.subTitle);
        }
    }
}
