package com.shivamprajapati.adminwaterware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentRequestFragment extends Fragment implements CurrentRequestAdapter.OnRecyclerViewAdapter {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CurrentRequestFragment() {
    }

    public static CurrentRequestFragment newInstance(String param1, String param2) {
        CurrentRequestFragment fragment = new CurrentRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    DatabaseReference databaseReference;
    CurrentRequestDetails currentRequestDetails;
    CurrentRequestAdapter currentRequestAdapter;
     List<CurrentRequestDetails> currentRequestDetailsList;
     Context context;
     TextView textView;
     ConnectivityManager connectivityManager;
     ShimmerFrameLayout frameLayout;
     Activity activity;

    @Override
    public void onResume() {
        super.onResume();
        frameLayout.startShimmerAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_current_request, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.currentRequestRecyclerView);
        recyclerView.setHasFixedSize(true);
        relativeLayout=view.findViewById(R.id.emptyState);
        frameLayout=view.findViewById(R.id.shimmer);
        textView=view.findViewById(R.id.workText);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);



        databaseReference=FirebaseDatabase.getInstance().getReference();


       context=getContext();

       activity=getActivity();
        connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED||connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED){

         databaseReference.child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        currentRequestDetailsList=new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if ((snapshot.child("status").getValue().toString().equals("Yes")||snapshot.child("status").getValue().toString().equals("Done"))&&snapshot.child("adminCheck").getValue().toString().equals("no")) {

                                currentRequestDetails = new CurrentRequestDetails();
                                String name = snapshot.child("name").getValue().toString();
                                String phoneNumber=snapshot.child("phone").getValue().toString();
                                String address=snapshot.child("address").getValue().toString();
                                String fromTime=snapshot.child("fromTime").getValue().toString();
                                String toTime=snapshot.child("toTime").getValue().toString();
                                String assigned=snapshot.child("shopId").getValue().toString();
                                String date=snapshot.child("date").getValue().toString();
                                String sun=snapshot.child("SUNDAY").getValue().toString();
                                String mon=snapshot.child("MONDAY").getValue().toString();
                                String tue=snapshot.child("TUESDAY").getValue().toString();
                                String wed=snapshot.child("WEDNESDAY").getValue().toString();
                                String thu=snapshot.child("THURSDAY").getValue().toString();
                                String fri=snapshot.child("FRIDAY").getValue().toString();
                                String sat=snapshot.child("SATURDAY").getValue().toString();
                                String[] days=new String[]{sun,mon,tue,wed,thu,fri,sat};

                                currentRequestDetails.setName(name);
                                currentRequestDetails.setAddress(address);
                                currentRequestDetails.setMobileNumber(phoneNumber);
                                currentRequestDetails.setFromTime(fromTime);
                                currentRequestDetails.setToTime(toTime);
                                currentRequestDetails.setAssigned(assigned);
                                currentRequestDetails.setDays(days);
                                currentRequestDetails.setDate(date);
                                currentRequestDetails.setId(snapshot.child("uid").getValue().toString());
                                currentRequestDetails.setStatus(snapshot.child("status").getValue().toString());


                                currentRequestDetailsList.add(currentRequestDetails);

                                frameLayout.stopShimmerAnimation();
                                frameLayout.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }else{
                                frameLayout.stopShimmerAnimation();
                                frameLayout.setVisibility(View.GONE);
                                textView.setText("No work for you");
                            }
                        }

                        currentRequestAdapter = new CurrentRequestAdapter(currentRequestDetailsList, CurrentRequestFragment.this);
                        recyclerView.setAdapter(currentRequestAdapter);

                    }else{

                            frameLayout.stopShimmerAnimation();
                            frameLayout.setVisibility(View.GONE);
                            textView.setText("No work for you");

                    }

                    if(frameLayout.isAnimationStarted()){
                        frameLayout.stopShimmerAnimation();
                        frameLayout.setVisibility(View.GONE);
                        textView.setText("No work for you");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(context,"No internet connection",Toast.LENGTH_LONG).show();
        }



        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==1){

            int i=data.getIntExtra("pos",0);
            int size=data.getIntExtra("mSize",0);

            currentRequestDetailsList.remove(i);
            recyclerView.removeViewAt(i);
            currentRequestAdapter.notifyItemRemoved(i);
            currentRequestAdapter.notifyItemRangeChanged(i,size);
            currentRequestAdapter.notifyDataSetChanged();
            recyclerView.invalidate();
            Toast.makeText(context,i+" "+size,Toast.LENGTH_LONG).show();



            databaseReference.child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        currentRequestDetailsList=new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if ((snapshot.child("status").getValue().toString().equals("Yes")||snapshot.child("status").getValue().toString().equals("Done"))&&snapshot.child("adminCheck").getValue().toString().equals("no")) {

                                currentRequestDetails = new CurrentRequestDetails();
                                String name = snapshot.child("name").getValue().toString();
                                String phoneNumber=snapshot.child("phone").getValue().toString();
                                String address=snapshot.child("address").getValue().toString();
                                String fromTime=snapshot.child("fromTime").getValue().toString();
                                String toTime=snapshot.child("toTime").getValue().toString();
                                String date=snapshot.child("date").getValue().toString();
                                String assigned=snapshot.child("shopId").getValue().toString();
                                String sun=snapshot.child("SUNDAY").getValue().toString();
                                String mon=snapshot.child("MONDAY").getValue().toString();
                                String tue=snapshot.child("TUESDAY").getValue().toString();
                                String wed=snapshot.child("WEDNESDAY").getValue().toString();
                                String thu=snapshot.child("THURSDAY").getValue().toString();
                                String fri=snapshot.child("FRIDAY").getValue().toString();
                                String sat=snapshot.child("SATURDAY").getValue().toString();
                                String[] days=new String[]{sun,mon,tue,wed,thu,fri,sat};

                                currentRequestDetails.setName(name);
                                currentRequestDetails.setAssigned(assigned);
                                currentRequestDetails.setAddress(address);
                                currentRequestDetails.setMobileNumber(phoneNumber);
                                currentRequestDetails.setFromTime(fromTime);
                                currentRequestDetails.setToTime(toTime);
                                currentRequestDetails.setDays(days);
                                currentRequestDetails.setDate(date);
                                currentRequestDetails.setId(snapshot.child("uid").getValue().toString());
                                currentRequestDetails.setStatus(snapshot.child("status").getValue().toString());


                                currentRequestDetailsList.add(currentRequestDetails);
                                frameLayout.stopShimmerAnimation();
                                frameLayout.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);


                            }else {
                                frameLayout.stopShimmerAnimation();
                                frameLayout.setVisibility(View.GONE);
                                textView.setText("No work for you");
                            }
                        }
                        currentRequestAdapter = new CurrentRequestAdapter(currentRequestDetailsList,CurrentRequestFragment.this);
                        recyclerView.setAdapter(currentRequestAdapter);
                    }else{

                        frameLayout.stopShimmerAnimation();
                        frameLayout.setVisibility(View.GONE);
                        textView.setText("No work for you");

                    }

                    if(frameLayout.isAnimationStarted()){
                        frameLayout.stopShimmerAnimation();
                        frameLayout.setVisibility(View.GONE);
                        textView.setText("No work for you");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }
    @Override
    public void onItemClicked(int i, int size, List<CurrentRequestDetails> currentRequestDetailsList,String d) {


        Intent intent=new Intent(context,PendingOrderDetails.class);
        intent.putExtra("name",currentRequestDetailsList.get(i).getName());
        intent.putExtra("address",currentRequestDetailsList.get(i).getAddress());
        intent.putExtra("date",currentRequestDetailsList.get(i).getDate());
        intent.putExtra("phone",currentRequestDetailsList.get(i).getMobileNumber().trim());
        intent.putExtra("days",d);
        intent.putExtra("fromTime",currentRequestDetailsList.get(i).getFromTime());
        intent.putExtra("toTime",currentRequestDetailsList.get(i).getToTime());
        intent.putExtra("id",currentRequestDetailsList.get(i).getId().trim());
        intent.putExtra("status",currentRequestDetailsList.get(i).getStatus());
        intent.putExtra("assigned",currentRequestDetailsList.get(i).getAssigned());

        intent.putExtra("position",i);
        intent.putExtra("size",currentRequestDetailsList.size());
        startActivityForResult(intent,1);


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
