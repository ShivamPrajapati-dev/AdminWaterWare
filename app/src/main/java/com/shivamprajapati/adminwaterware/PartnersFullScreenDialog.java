package com.shivamprajapati.adminwaterware;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PartnersFullScreenDialog extends DialogFragment {

    EditText shopName, address, phoneMain, phoneOne, phoneTwo, phoneThree;
    TextView send, cancel;
    List<PartnersList> partnersLists;
    PartnersList partnersInfo;
    Context context;
    Button button;
    TextInputLayout sn, sad, sph;

    public static PartnersFullScreenDialog newInstance() {
        return new PartnersFullScreenDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);

    }


    DatabaseReference databaseReference;
    String add;
    public static final int a = 1;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partners_list, container, false);


        context = getContext();



        shopName = (EditText) view.findViewById(R.id.editTextq);
        address = (EditText) view.findViewById(R.id.editTextw);
        phoneMain = (EditText) view.findViewById(R.id.editTextPhoneNMain);
        phoneOne = view.findViewById(R.id.editTextPhoneOne);
        phoneTwo = view.findViewById(R.id.editTextPhoneTwo);
        phoneThree = view.findViewById(R.id.editTextPhoneThree);
        send = (TextView) view.findViewById(R.id.fullscreen_dialog_action);
        cancel = (TextView) view.findViewById(R.id.fullscreen_dialog_cancel);
        button = (Button) view.findViewById(R.id.atca);
        sn = (TextInputLayout) view.findViewById(R.id.til1);
        sad = (TextInputLayout) view.findViewById(R.id.textInputLayout7);
        sph = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(TextUtils.isEmpty(shopName.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(phoneMain.getText().toString()))) {


                    String id=RandomString.getAlphaNumericString(30);
                    partnersInfo = new PartnersList();
                    partnersInfo.setShopId(id);
                    partnersInfo.setName(shopName.getText().toString());
                    partnersInfo.setPhoneNumber(phoneMain.getText().toString());
                    partnersInfo.setAddress(address.getText().toString());

                    Gson gson = new Gson();

                    if (SharedPref.readSavedSettingsPartnesList(context, "pl", null) != null) {
                        Type type = new TypeToken<ArrayList<PartnersList>>() {
                        }.getType();
                        partnersLists = gson.fromJson(SharedPref.readSavedSettingsPartnesList(context, "pl", null), type);

                    } else {
                        partnersLists = new ArrayList<>();

                    }

                    partnersLists.add(partnersInfo);

                    String json = gson.toJson(partnersLists);
                    SharedPref.saveSharedSettingsPartnersList(context, "pl", json);

                    assert getParentFragment() != null;
                    RecyclerView recyclerView = (RecyclerView) getParentFragment().getView().findViewById(R.id.partnersRecyclerView);
                    PartnersListAdapter adapter = new PartnersListAdapter(partnersLists);
                    recyclerView.setAdapter(adapter);

                    Map map = new HashMap();
                    if (!TextUtils.isEmpty(phoneMain.getText().toString().trim()))
                        map.put("phoneZero", phoneMain.getText().toString().trim());
                    if (!TextUtils.isEmpty(phoneOne.getText().toString().trim()))
                        map.put("phoneOne", phoneOne.getText().toString().trim());
                    if (!TextUtils.isEmpty(phoneTwo.getText().toString().trim()))
                        map.put("phoneTwo", phoneTwo.getText().toString().trim());
                    if (!TextUtils.isEmpty(phoneThree.getText().toString().trim()))
                        map.put("phoneThree", phoneThree.getText().toString().trim());


                    map.put("shopId",id);

                    databaseReference.child("admin").child("shops").child(shopName.getText().toString()).updateChildren(map);

                    dismiss();
                } else {
                    if (TextUtils.isEmpty(shopName.getText().toString())) {
                        shopName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                if (s.toString().length() == 0) {
                                    sn.setErrorEnabled(true);
                                    sn.setError("This field has to be filled");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    if (TextUtils.isEmpty(address.getText().toString())) {
                        address.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                if (s.toString().length() == 0) {
                                    sad.setErrorEnabled(true);
                                    sad.setError("This field has to be filled");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                    }
                    if (TextUtils.isEmpty(phoneMain.getText().toString())) {

                        phoneMain.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                if (s.toString().length() == 0) {
                                    sph.setErrorEnabled(true);
                                    sph.setError("This field has to be filled");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                if (!(TextUtils.isEmpty(shopName.getText().toString()) || TextUtils.isEmpty(phoneMain.getText().toString()))) {

                    ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                    shopName.getText().toString()).build());

                    ops.add(ContentProviderOperation.
                            newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneMain.getText().toString())
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());

                    try {
                        context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        Toast.makeText(context, "Contact saved", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Enter Phone number and Name", Toast.LENGTH_LONG).show();
                }


            }

        });


        return view;
    }


    public static class RandomString {

        // function to generate a random string of length n
        static String getAlphaNumericString(int n) {

            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int) (AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }

    }
}

