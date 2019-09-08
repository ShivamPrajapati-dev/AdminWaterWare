package com.shivamprajapati.adminwaterware;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class BillingDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BillingDetailsFragment() {
        // Required empty public constructor
    }


    public static BillingDetailsFragment newInstance(String param1, String param2) {
        BillingDetailsFragment fragment = new BillingDetailsFragment();
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
    EditText cleaningCharges,visitingCharges;
    int cc=0,vc=0;
    TextView total;
    Button postBill;
    Context context;
    ConnectivityManager connectivityManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getContext();
        View view= inflater.inflate(R.layout.fragment_billing_details, container, false);
        cleaningCharges=view.findViewById(R.id.Rs300);
        visitingCharges=view.findViewById(R.id.Rs50);
        total=view.findViewById(R.id.total);
        postBill=view.findViewById(R.id.post);
        cleaningCharges.setText(SharedPref.readSavedSettingsCleaningCharges(getContext(),"cleaning","300"));
        visitingCharges.setText(SharedPref.readSavedSettingsVisitingCharges(getContext(),"visiting","50"));
        if(!cleaningCharges.getText().toString().isEmpty())
            cc=Integer.valueOf(cleaningCharges.getText().toString());
        if(!visitingCharges.getText().toString().isEmpty())
            vc=Integer.valueOf(visitingCharges.getText().toString());
        int x=cc+vc;
        total.setText(""+x);

        cleaningCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty())
                {
                    cc=Integer.valueOf(s.toString());
                    int x=cc+vc;
                    total.setText(""+x);
                }
                else {
                    cc=0;
                    int x=cc+vc;
                    total.setText(""+x);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        visitingCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().isEmpty())
                {
                    vc=Integer.valueOf(s.toString());
                    int x=cc+vc;
                    total.setText(""+x);
                }
                else {
                    vc=0;
                    int x=cc+vc;
                    total.setText(""+x);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        postBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(cleaningCharges.getText().toString())||TextUtils.isEmpty(visitingCharges.getText().toString())){
                    Toast.makeText(context,"Fields cannot remain empty",Toast.LENGTH_LONG).show();
                }
                else {


                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure")
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Map map = new HashMap();
                                        map.put("cleaning", cleaningCharges.getText().toString().trim());
                                        map.put("visiting", visitingCharges.getText().toString().trim());
                                        map.put("total", total.getText().toString().trim());
                                        FirebaseDatabase.getInstance().getReference().child("AnoopKumarPrajapati").child("Bill").updateChildren(map);
                                        SharedPref.saveSharedSettingsCleaningCharges(context,"cleaning",cleaningCharges.getText().toString());
                                        SharedPref.saveSharedSettingsVisitingCharges(context,"visiting",visitingCharges.getText().toString());

                                    }
                                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }else{
                        Toast.makeText(context,"No internet connection",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
