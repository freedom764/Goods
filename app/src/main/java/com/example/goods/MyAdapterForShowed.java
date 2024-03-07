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

import java.util.ArrayList;
import java.util.List;

public class MyAdapterForShowed extends RecyclerView.Adapter<MyAdapterForShowed.MyViewHolder>
        implements View.OnClickListener, Filterable {
    private List<MyObjectForShowed> myObjects;
    private List<MyObjectForShowed> reservedList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showed_item, parent, false);
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
    public void onClick(View v) {

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




                String email = SaveSharedPreference.getUserName(v.getContext());

                TextView clickedName=(TextView)v.findViewById(R.id.title1337);
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
                    stdayactivity = c.getInt(8);


                }


                Intent myIntent = new Intent(v.getContext(), GoodsSellerOpenTovar.class);
                Bundle extras = new Bundle();
                extras.putString("Name", nameactivity);
                extras.putInt("Year", yearactivity);
                extras.putInt("Month", monthactivity);
                extras.putInt("Day", dayactivity);
                extras.putInt("Price", priceactivity);
                extras.putInt("StYear", styearactivity);
                extras.putInt("StMonth", stmonthactivity);
                extras.putInt("StDay", stdayactivity);
                extras.putString("Email",email);
                myIntent.putExtras(extras);

                v.getContext().startActivity(myIntent);



            }

    public MyAdapterForShowed(List<MyObjectForShowed> myObjects){
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        TextView subTitle;

        MyViewHolder(View itemView) {
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