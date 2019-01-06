package com.dasinnovationapps.prat.servicecentral;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_profile);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();
        final TextView replaceContactName = findViewById(R.id.contactNameReplacement);
        final TextView replaceOrganizationName = findViewById(R.id.organizationNameReplacement);
        final TextView replaceContactEmail = findViewById(R.id.organizationEmailReplacement);
        final TextView replaceContactPhoneNumber= findViewById(R.id.contactPhoneNumberReplacement);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference contactNameReference = database.getReference("Users").child("Organizations").child(userID).child("Contact Name");
        contactNameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = String.valueOf(dataSnapshot.getValue(String.class));
                Log.d("TAG 3", "Value is: " + value);
                replaceContactName.setText(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG 2", "failed to read value", databaseError.toException());
            }
        });
        DatabaseReference organizationReference = database.getReference("Users").child("Organizations").child(userID).child("Organization Name");
        organizationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = String.valueOf(dataSnapshot.getValue(String.class));
                Log.d("TAG 1", "Value is: " + value);
                replaceOrganizationName.setText(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG 2", "failed to read value", databaseError.toException());
            }
        });
        DatabaseReference contactEmail = database.getReference("Users").child("Organizations").child(userID).child("Contact Email");
        contactEmail.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String value = String.valueOf(dataSnapshot.getValue(String.class));
            Log.d("TAG 1", "Value is: " + value);
            replaceContactEmail.setText(value);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("TAG 2", "failed to read value", databaseError.toException());
        }
    });
        DatabaseReference contactPhone = database.getReference("Users").child("Organizations").child(userID).child("Contact Phone");
        contactPhone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = String.valueOf(dataSnapshot.getValue(String.class));
                Log.d("TAG 1", "Value is: " + value);
                replaceContactPhoneNumber.setText(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG 2", "failed to read value", databaseError.toException());
            }
        });
}


    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.logOutButton):
                FirebaseAuth.getInstance().signOut();
                Intent toLogin = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(toLogin);
                finish();
                break;

        }
    }
}


