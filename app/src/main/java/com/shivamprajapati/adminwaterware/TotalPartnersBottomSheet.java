package com.shivamprajapati.adminwaterware;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TotalPartnersBottomSheet extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    List<PartnersList> partnersLists;
    Context context;
    public  static String id;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.total_partners_bottomsheet,container,false);

        recyclerView=view.findViewById(R.id.recyclerViewTotalPartners);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        View parentView=getActivity().findViewById(R.id.Rs300);


        Gson gson=new Gson();
        context=getContext();
        Type type=new TypeToken<ArrayList<PartnersList>>(){}.getType();
        if(SharedPref.readSavedSettingsPartnesList(context,"pl",null)!=null){
            partnersLists=gson.fromJson(SharedPref.readSavedSettingsPartnesList(context,"pl",null),type);
            TotalPartnersRecyclerViewAdapter adapter=new TotalPartnersRecyclerViewAdapter(partnersLists,parentView,id,getDialog());
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
