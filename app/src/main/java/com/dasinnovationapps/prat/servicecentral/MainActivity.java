package com.dasinnovationapps.prat.servicecentral;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    EditText editEmailAddress, entered_password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_circular);
        editEmailAddress = (findViewById(R.id.loginEmailInput));
        entered_password = findViewById(R.id.loginPasswordInput);
        findViewById(R.id.toRegistration).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);
    }

    private void userLogin() {
        String email = editEmailAddress.getText().toString().trim();
        String password = entered_password.getText().toString().trim();

        if(email.isEmpty()){
            editEmailAddress.setError("Email is required");
            editEmailAddress.requestFocus();
            return;
        }
        if (! Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmailAddress.setError("Please enter a valid email");
            editEmailAddress.requestFocus();
            return;
        }
        if(password.isEmpty()){
            entered_password.setError("Password is required");
            entered_password.requestFocus();
            return;
        }
        if(password.length() < 6) {
            entered_password.setError("Minimum Length of Password should be 6");
            entered_password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    Intent toProfile = new Intent(MainActivity.this, RegistrationFirstPage.class );
                    Log.d("stub1", "Line 68");
                    toProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toProfile);
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.toRegistration):
                Intent toSignUp = new Intent(this,RegistrationFirstPage.class);
                startActivity(toSignUp);
                finish();
                break;

            case (R.id.loginButton):
                userLogin();
                break;
        }
    }
}