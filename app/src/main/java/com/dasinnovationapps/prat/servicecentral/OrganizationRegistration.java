package com.dasinnovationapps.prat.servicecentral;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class OrganizationRegistration extends AppCompatActivity implements View.OnClickListener {
    EditText organizationNameInput, organizationAddressInput, contactNameInput,
            contactEmailInput, accountUsernameInput, accountPasswordInput, contactPhoneInput;
    TextView backToRegistration;
    Button registrationForOrganization;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_registration);
        organizationAddressInput = findViewById(R.id.organizationAddress);
        organizationNameInput = findViewById(R.id.organizationName);
        contactNameInput = findViewById(R.id.contactName);
        contactEmailInput = findViewById(R.id.contactEmail);
        contactPhoneInput = findViewById(R.id.contactPhoneNumber);
        accountUsernameInput = findViewById(R.id.organizationAccountUsername);
        accountPasswordInput = findViewById(R.id.organizationAccountPassword);

        findViewById(R.id.organizationRegistration).setOnClickListener(this);
        findViewById(R.id.goBackToFirstPage).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
    private  void registerOrganizationAuthInfo(){
        String organizationName= organizationNameInput.getText().toString();
        String organizationAddress = organizationAddressInput.getText().toString();
        String contactName = contactNameInput.getText().toString();
        String contactEmail = contactEmailInput.getText().toString();
        String contactPhone = contactPhoneInput.getText().toString();
        String accountUsername = accountUsernameInput.getText().toString();
        String accountPassword = accountPasswordInput.getText().toString();

        if(organizationName.isEmpty()){
            organizationNameInput.setError("Organization name is required");
            organizationNameInput.requestFocus();
            return;
        }
        if(organizationAddress.isEmpty()){
            organizationAddressInput.setError("Organization address is required");
            organizationAddressInput.requestFocus();
            return;
        }
        if(contactName.isEmpty()){
            contactNameInput.setError("Organization contact Name is required");
            contactNameInput.requestFocus();
            return;
        }
        if(contactEmail.isEmpty()){
            contactEmailInput.setError("Organization contact email is required");
            contactEmailInput.requestFocus();
            return;
        }
        if (! Patterns.EMAIL_ADDRESS.matcher(contactEmail).matches()) {
            contactEmailInput.setError("Please enter a valid email");
            contactEmailInput.requestFocus();
            return;
        }
        if(contactPhone.isEmpty()){
            contactPhoneInput.setError("Organization contact phone is required");
            contactPhoneInput.requestFocus();
            return;
        }
        if(! Patterns.PHONE.matcher(contactPhone).matches()){
            contactPhoneInput.setError("Please enter a valid phone number");
            contactPhoneInput.requestFocus();
            return;
        }
        if(accountUsername.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(accountUsername).matches())){
            accountUsernameInput.setError("Valid account emails are required");
            accountUsernameInput.requestFocus();
            return;
        }
        if(accountPassword.isEmpty()){
            accountPasswordInput.setError("Account password is required");
            accountPasswordInput.requestFocus();
            return;
        }
        if(accountPassword.length()<6){
            accountPasswordInput.setError("Minimum password length is 6 characters");
            accountPasswordInput.requestFocus();
            return;
        }
        Log.d("stub1", "Error Line 73");
                Log.d("stub1", "Error Line 75");
        mAuth.createUserWithEmailAndPassword(accountUsername,accountPassword).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("stub3", "Error Line 80");
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Organization Registered Successful", Toast.LENGTH_SHORT).show();
                    storeNameInDatabase();
                    Intent toProfile = new Intent(OrganizationRegistration.this, ProfileActivity.class );
                    startActivity(toProfile);
                    finish();
                }else{
                    if(task.getException() instanceof FirebaseAuthException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "Some error has occurred", Toast.LENGTH_SHORT).show();
                    }}
            }
        });
    }
    public void storeNameInDatabase() {
        String organizationName= organizationNameInput.getText().toString().trim();
        String organizationAddress = organizationAddressInput.getText().toString().trim();
        String contactName = contactNameInput.getText().toString().trim();
        String contactEmail = contactEmailInput.getText().toString().trim();
        String contactPhone = contactPhoneInput.getText().toString().trim();
        String accountUsername = accountUsernameInput.getText().toString().trim();
        String accountPassword = accountPasswordInput.getText().toString().trim();
        if (organizationName.isEmpty()) {
            organizationNameInput.setError("Organization Name is required");
            organizationNameInput.requestFocus();

        } else {
            String userID = mAuth.getCurrentUser().getUid();
            DatabaseReference name_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Organizations").child(userID);
            Map<String, String> newPost = new HashMap<String, String>();
            newPost.put("Organization Name",organizationName );
            newPost.put("Organization Address", organizationAddress);
            newPost.put("Contact Name", contactName);
            newPost.put("Contact Email", contactEmail);
            newPost.put("Contact Phone", contactPhone);
            newPost.put("Account User Name", accountUsername);
            newPost.put("Account Password", accountPassword);
            Log.d("name ref", "Error Line 155");
            name_ref.setValue(newPost);
            Log.d("after_name ref", "Error Line 158");
        }

        }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.organizationRegistration):
                registerOrganizationAuthInfo();
                break;
            case R.id.goBackToFirstPage:
                Intent toLogin = new Intent(this,MainActivity.class);
                startActivity(toLogin);
                finish();
                break;
        }
    }
    }
