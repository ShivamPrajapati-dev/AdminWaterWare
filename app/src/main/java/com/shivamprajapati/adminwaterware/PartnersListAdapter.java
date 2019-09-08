package com.shivamprajapati.adminwaterware;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class PartnersListAdapter extends RecyclerView.Adapter<PartnersListAdapter.ViewHolder> {
    private List<PartnersList> partnersLists;

    PartnersListAdapter(List<PartnersList> partnersLists) {
        this.partnersLists = partnersLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.partner_list_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        viewHolder.name.setText(partnersLists.get(i).getName());
        viewHolder.address.setText(partnersLists.get(i).getAddress());
        viewHolder.phone.setText(partnersLists.get(i).getPhoneNumber());
        viewHolder.function(partnersLists.get(i));

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partnersLists.get(i).setEnabled(!partnersLists.get(i).isEnabled());
                notifyItemChanged(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return partnersLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,address;
        RelativeLayout relativeLayout;
        CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.Name);
            phone=(TextView)itemView.findViewById(R.id.tphone);
            address=(TextView)itemView.findViewById(R.id.Add);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.shortDetailsRl);
            cardView=(CardView)itemView.findViewById(R.id.cardForLongDetailsRl);

        }

        void function(PartnersList partnersList){

            cardView.setVisibility(partnersList.isEnabled()?View.VISIBLE:View.GONE);

        }
    }
}
