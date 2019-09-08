package com.shivamprajapati.adminwaterware;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentRequestAdapter extends RecyclerView.Adapter<CurrentRequestAdapter.ViewHolder> {

    public List<CurrentRequestDetails> currentRequestDetailsList;
    DatabaseReference databaseReference;

    HistoryList list;

    String d="";

    Context context;



    public CurrentRequestAdapter(List<CurrentRequestDetails> currentRequestDetailsList, OnRecyclerViewAdapter onRecyclerViewAdapter) {
        this.currentRequestDetailsList = currentRequestDetailsList;
        this.onRecyclerViewAdapter=onRecyclerViewAdapter;
    }

    public interface OnRecyclerViewAdapter{
        void onItemClicked(int position,int size,List<CurrentRequestDetails> currentRequestDetailsList,String d);
    }

     OnRecyclerViewAdapter onRecyclerViewAdapter;
    @NonNull
    @Override
    public CurrentRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_request_layout,viewGroup,false);


        context=viewGroup.getContext();


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CurrentRequestAdapter.ViewHolder viewHolder, final int i) {

        databaseReference=FirebaseDatabase.getInstance().getReference();

        databaseReference.child("admin").child(currentRequestDetailsList.get(i).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&&dataSnapshot.child("waiting").getValue().toString().equals("yes")){

                    viewHolder.icon.setVisibility(View.GONE);
                }else{
                    viewHolder.icon.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        viewHolder.name.setText(currentRequestDetailsList.get(i).getName());

        if(currentRequestDetailsList.get(i).getStatus().equals("Yes")){

            viewHolder.icon.setBackgroundResource(R.drawable.ic_remove_red_eye_black_24dp);
        }



        if(currentRequestDetailsList.get(i).getStatus().equals("Done")){

            viewHolder.icon.setBackgroundResource(R.drawable.ic_check_black_24dp);

        }





       final Map map=new HashMap();
        map.put("waiting","yes");



        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("admin").child(currentRequestDetailsList.get(i).getId()).updateChildren(map);
                viewHolder.icon.setVisibility(View.GONE);
                notifyItemChanged(i);
                String[] days;
                days= Arrays.copyOf(currentRequestDetailsList.get(i).days,currentRequestDetailsList.get(i).getDays().length);


                d="";
                for(int x=0;x<days.length;x++){
                    if(!days[x].equals("false")){
                        d=d+days[x]+"\n";

                    }
                }

                if(onRecyclerViewAdapter!=null)
                onRecyclerViewAdapter.onItemClicked(viewHolder.getAdapterPosition(),currentRequestDetailsList.size(),currentRequestDetailsList,d);






            }
        });



    }

    @Override
    public int getItemCount() {
        return currentRequestDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView name;
         RelativeLayout relativeLayout;

         ImageView icon;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.tellName);
            icon=(ImageView)itemView.findViewById(R.id.notiIcon);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.shortDetails);


        }


    }
}
