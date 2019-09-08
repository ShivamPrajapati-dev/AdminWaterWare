package com.shivamprajapati.adminwaterware;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements CurrentRequestFragment.OnFragmentInteractionListener ,PartnersFragment.OnFragmentInteractionListener,NotificationFragment.OnFragmentInteractionListener,HistoryFragment.OnFragmentInteractionListener,BillingDetailsFragment.OnFragmentInteractionListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {




        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pending:

                    Fragment fragment=new CurrentRequestFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container,fragment,"refresh").commit();


                    return true;
                case R.id.navigation_partners:

                    Fragment fragment1=new PartnersFragment();
                    FragmentManager fragmentManager1=getSupportFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.container,fragment1).commit();


                    return true;
                case R.id.navigation_history:


                    Fragment fragment2=new HistoryFragment();
                    FragmentManager fragmentManager2=getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.container,fragment2).commit();

                    return true;
                case R.id.navigation_notification:


                    Fragment fragment3=new NotificationFragment();
                    FragmentManager fragmentManager3=getSupportFragmentManager();
                    fragmentManager3.beginTransaction().replace(R.id.container,fragment3).commit();

                    return  true;
                case R.id.navigation_bill:
                    Fragment fragment4=new BillingDetailsFragment();
                    FragmentManager fragmentManager4=getSupportFragmentManager();
                    fragmentManager4.beginTransaction().replace(R.id.container,fragment4).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment=new CurrentRequestFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
       databaseReference.child("AnoopKumarPrajapati").child("token").setValue(FirebaseInstanceId.getInstance().getToken());




    }


        @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
