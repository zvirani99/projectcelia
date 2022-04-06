package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    // Sets up Activity
    // Checks if user is already logged in and redirects to MainActivity
    // Otherwise displays current page.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        System.out.println("LAUNCHACTIVITY : ON CREATE CALLED");

        if ( FirebaseAuth.getInstance().getCurrentUser() != null ) {
            DataHandler.updateSharedPreferences( getApplicationContext() );
            System.out.println("SWITCHING TO MAIN ACTIVITY");
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
            finish();
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

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ( networkInfo == null || !networkInfo.isConnectedOrConnecting() ) {
            // No Internet Connection Available
            // Display some error message here
//            Snackbar.make( findViewById(R.id.createaccount_button),
//                    "No Internet Connection Available",
//                    Snackbar.LENGTH_SHORT )
//                    .show();
            createaccount_btn.setEnabled( false );
            login_btn.setEnabled( false );
        } else {
            createaccount_btn.setEnabled( true );
            login_btn.setEnabled( true );
        }

    }
}