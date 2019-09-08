package com.shivamprajapati.adminwaterware;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private List<HistoryList> historyLists;

    HistoryListAdapter(List<HistoryList> historyLists) {
        this.historyLists = historyLists;
    }

    @NonNull
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_history_list_layout,viewGroup,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.names.setText(historyLists.get(i).getName());
        viewHolder.phone.setText(historyLists.get(i).getPhone());
        viewHolder.add.setText(historyLists.get(i).getAddress());
        viewHolder.date.setText(historyLists.get(i).getDate());


    }

    @Override
    public int getItemCount() {
        return historyLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView names,phone,add,date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            names=(TextView)itemView.findViewById(R.id.historyName);
            phone=(TextView)itemView.findViewById(R.id.historyPhone);
            add=(TextView)itemView.findViewById(R.id.historyAddress);
            date=(TextView)itemView.findViewById(R.id.historyDate);

        }
    }
}
