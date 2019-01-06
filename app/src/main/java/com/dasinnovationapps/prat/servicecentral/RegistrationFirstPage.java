package com.dasinnovationapps.prat.servicecentral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegistrationFirstPage extends AppCompatActivity implements View.OnClickListener{
    Button toOrganizationRegistration, toVolunteerRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.organizationLink).setOnClickListener(this);
        findViewById(R.id.volunteerLink).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.organizationLink):
                Intent toOrganizationRegistrationPage = new Intent(this, OrganizationRegistration.class);
                startActivity(toOrganizationRegistrationPage);
                finish();
                break;
        }
    }
}
