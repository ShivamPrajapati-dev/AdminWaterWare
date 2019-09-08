package com.shivamprajapati.adminwaterware;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private List<NotificationList> notificationLists;

    NotificationListAdapter(List<NotificationList> notificationLists){
        this.notificationLists=notificationLists;
    }

    @NonNull
    @Override
    public NotificationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_notification_list_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.ViewHolder viewHolder,final int i) {

        viewHolder.title.setText(notificationLists.get(i).getTitle());
        viewHolder.body.setText(notificationLists.get(i).getBody());
        viewHolder.phone.setText(notificationLists.get(i).getPhone());
        viewHolder.address.setText(notificationLists.get(i).getAddress());
        viewHolder.date.setText(notificationLists.get(i).getDate());
        viewHolder.function(notificationLists.get(i));


        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationLists.get(i).isExpandable()) {
                    {
                        notificationLists.get(i).setExpanded(!notificationLists.get(i).isExpanded());
                        notifyItemChanged(i);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,body,phone,address,date;
        CardView cardView;
        RelativeLayout relativeLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.notificationHeading);
            body=(TextView)itemView.findViewById(R.id.notificationBody);
            phone=(TextView)itemView.findViewById(R.id.pn2);
            address=(TextView)itemView.findViewById(R.id.ad2);
            date=(TextView)itemView.findViewById(R.id.notiDate);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.shortRelativeLayout);
            cardView=(CardView)itemView.findViewById(R.id.longCard);

        }
        void function(NotificationList notificationList){

            cardView.setVisibility(notificationList.isExpanded()?View.VISIBLE:View.GONE);
        }
    }
}
