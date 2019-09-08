package com.shivamprajapati.adminwaterware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TotalPartnersRecyclerViewAdapter extends RecyclerView.Adapter<TotalPartnersRecyclerViewAdapter.ViewHolder> {

    private List<PartnersList> partnersLists;
    Context context;
    private View parentView;
    String id;
    private Dialog dialogX;
    private int count=0;

    TotalPartnersRecyclerViewAdapter(List<PartnersList> partnersLists, View parentView, String id, Dialog dialog) {
        this.partnersLists = partnersLists;
        this.parentView=parentView;
        this.id=id;
        this.dialogX=dialog;
    }

    @NonNull
    @Override
    public TotalPartnersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottomsheet_layout,viewGroup,false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TotalPartnersRecyclerViewAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.name.setText(partnersLists.get(i).getName().trim());
        viewHolder.phone.setText(partnersLists.get(i).getPhoneNumber().trim());
        viewHolder.address.setText(partnersLists.get(i).getAddress().trim());

        FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    count=0;
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(snapshot.child("shopId").getValue().toString().equals(partnersLists.get(i).getShopId())&&snapshot.child("status").getValue().toString().equals("Yes")){
                            count++;
                        }
                    }
                    viewHolder.jobs.setText(String.valueOf(count));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Are you sure")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which) {


                                ((TextView)parentView).setText(partnersLists.get(i).getName());

                                FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                if (snapshot.child("uid").getValue().toString().equals(id.trim())) {
                                                    snapshot.child("shopId").getRef().setValue(partnersLists.get(i).getShopId());

                                                   // snapshot.child("assigned").getRef().setValue(partnersLists.get(i).getName().trim());

                                                    dialogX.dismiss();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }).setNegativeButton("No",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();




            }
        });
    }

    @Override
    public int getItemCount() {
        return partnersLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView name,phone,address,jobs;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout=itemView.findViewById(R.id.cl);
            name=itemView.findViewById(R.id.nameOfUser);
            phone=itemView.findViewById(R.id.phoneNumberUser);
            address=itemView.findViewById(R.id.id2);
            jobs=itemView.findViewById(R.id.jobs);


        }
    }
}
