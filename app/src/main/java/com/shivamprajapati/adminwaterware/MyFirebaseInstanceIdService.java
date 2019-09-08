package com.shivamprajapati.adminwaterware;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AnoopKumarPrajapati").child("token").setValue(FirebaseInstanceId.getInstance().getToken());

    }
}
