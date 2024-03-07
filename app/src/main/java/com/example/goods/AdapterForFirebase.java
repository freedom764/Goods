package com.example.goods;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class AdapterForFirebase extends RecyclerView.Adapter<AdapterForFirebase.MyViewHolder1>
        implements View.OnClickListener, Filterable {
    private List<MyObject> myObjects;
    private List<MyObject> reservedList;
    public String nametext,adrestext;
    String nameprodavec,city,email,telephone;
    ProgressDialog dialog;




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
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
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
        dialog = new ProgressDialog(v.getContext());
        dialog.setMessage("Загрузка...");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        TextView clickedName1=(TextView)v.findViewById(R.id.title);
        nametext = clickedName1.getText().toString();
        TextView clickedName2=(TextView)v.findViewById(R.id.sub_title);
        adrestext = clickedName2.getText().toString();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        final Query query1 = reference.child("user").orderByChild("namemagazin").equalTo(nametext);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {
                    final Query query1 = reference.child("user").orderByChild("adres").equalTo(adrestext);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            if (dataSnapshot1.exists()) {


                                for (DataSnapshot issue : dataSnapshot1.getChildren()) {
                                    // do something with the individual "issues"
                                    nameprodavec = issue.child("nameprodavec").getValue().toString();
                                    city = issue.child("city").getValue().toString();
                                    email = issue.child("email").getValue().toString();
                                    telephone = issue.child("telephone").getValue().toString();
                                    Intent myIntent = new Intent(v.getContext(), Magazin.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("namemagazin", nametext);
                                    extras.putString("adres", adrestext);
                                    extras.putString("email", email);
                                    extras.putString("nameprodavec", nameprodavec);
                                    extras.putString("city", city);
                                    extras.putString("telephone", telephone);
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

            @Override
            public void onCancelled(DatabaseError databaseError1) {

            }
        });





    }

    public AdapterForFirebase(List<MyObject> myObjects){
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
                        if (row.title.toLowerCase().contains(charString) || row.subTitle.toLowerCase().contains(charString)) {
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

    class MyViewHolder1 extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;

        MyViewHolder1(View itemView) {
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
