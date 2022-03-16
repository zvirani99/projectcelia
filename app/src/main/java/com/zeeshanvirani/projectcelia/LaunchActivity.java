package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    // Sets up Activity
    // Checks if user is already logged in and redirects to MainActivity
    // Otherwise displays current page.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if ( FirebaseAuth.getInstance().getCurrentUser() != null ) {
            DataHandler.updateSharedPreferences( getApplicationContext() );
            startActivity( new Intent(this, MainActivity.class) );
        }

        Button createaccount_btn = findViewById(R.id.button_createaccount);
        createaccount_btn.setOnClickListener(view -> {
            // Switch to Create Account Activity so user can create their new account
            startActivity( new Intent(this, CreateAccountActivity.class) );
        });

        Button login_btn = findViewById(R.id.button_login);
        login_btn.setOnClickListener(view -> {
            // Switch to Login Activity so that user can login with their account info
            startActivity( new Intent(this, LoginActivity.class) );
        });

    }
}