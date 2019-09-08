package com.shivamprajapati.adminwaterware;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PendingOrderDetails extends AppCompatActivity {

    String name,phone,address,status,days,date,fromTime,toTime,id,assigned;
    TextView nm,ph,d,a,st,jobAssigned,totalDays,from,to;
    Button markAsDone,assignCleaner;
    List<HistoryList> historyLists;
    List<PartnersList> partnersLists;
    HistoryList list;
    Context context;
    public int position,size;
    public static final int RESULT_OK=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order_details);

        context=this;

        nm=findViewById(R.id.nameOfUser);
        ph=findViewById(R.id.phoneNumberUser);
        d=findViewById(R.id.orderDate);
        a=findViewById(R.id.id2);
        st=findViewById(R.id.Rs50);
        jobAssigned=findViewById(R.id.Rs300);
        totalDays=findViewById(R.id.days);
        from=findViewById(R.id.timeFrom);
        to=findViewById(R.id.timeTo);
        markAsDone=findViewById(R.id.button);
        assignCleaner=findViewById(R.id.qqq);


        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        address=intent.getStringExtra("address");
        phone=intent.getStringExtra("phone");
        status=intent.getStringExtra("status");
        days=intent.getStringExtra("days");
        date=intent.getStringExtra("date");
        fromTime=intent.getStringExtra("fromTime");
        toTime=intent.getStringExtra("toTime");
        id=intent.getStringExtra("id");

        assigned=intent.getStringExtra("assigned");

        position=intent.getIntExtra("position",0);
        size=intent.getIntExtra("size",0);


        nm.setText(name);;
        ph.setText(phone.trim());
        d.setText(date);
        a.setText(address);
        st.setText(status);
        totalDays.setText(days);
        from.setText(fromTime);
        to.setText(toTime);


        FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if (snapshot.child("uid").getValue().toString().equals(id)) {
                            fun();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(status.equals("Done")){
            markAsDone.setVisibility(View.VISIBLE);
        }

        markAsDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(status.equals("Done")){


                    Gson gson=new Gson();
                    if(SharedPref.readSavedSettingsHistoryList(context,"hl",null)!=null){
                        Type type=new TypeToken<ArrayList<HistoryList>>(){}.getType();
                        historyLists=gson.fromJson(SharedPref.readSavedSettingsHistoryList(context,"hl",null),type);
                    }
                    else{
                        historyLists=new ArrayList<>();
                    }



                    Calendar calendar=Calendar.getInstance();
                    Date date=calendar.getTime();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, MMM d");
                    String d=simpleDateFormat.format(date);
                    list=new HistoryList();
                    list.setDate(d);

                    list.setName(name);
                    list.setPhone(phone);
                    list.setAddress(address);

                    historyLists.add(list);
                    String json=gson.toJson(historyLists);
                    SharedPref.saveSharedSettingsHistoryList(context,"hl",json);

                }

                FirebaseDatabase.getInstance().getReference().child("admin").child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if (snapshot.child("uid").getValue().toString().equals(id)) {
                                    snapshot.child("adminCheck").getRef().setValue("yes");

                                     finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });


      final TotalPartnersBottomSheet bottomSheet=new TotalPartnersBottomSheet();
        assignCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TotalPartnersBottomSheet.id =id;
                bottomSheet.show(getSupportFragmentManager(),"tag");


            }
        });





        if(SharedPref.readSavedSettingsPartnesList(context,"pl",null)!=null){
            Gson gson=new Gson();
            Type type=new TypeToken<ArrayList<PartnersList>>(){}.getType();
            partnersLists=gson.fromJson(SharedPref.readSavedSettingsPartnesList(context,"pl",null),type);
        }






    }

    private void fun() {
        FirebaseDatabase.getInstance().getReference().child("admin").child("shops").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        if (snapshot1.child("shopId").getValue().toString().trim().equals(assigned.trim())) {
                            jobAssigned.setText(snapshot1.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void finish() {

        Intent intent=new Intent();
        intent.putExtra("pos",position);
        intent.putExtra("mSize",size);
        setResult(RESULT_OK,intent);
        super.finish();
    }
}
