package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Check if user is already logged in
        if ( FirebaseAuth.getInstance().getCurrentUser() != null ) {
            DataHandler.updateSharedPreferences( getApplicationContext() );
            startActivity(new Intent(this, MainActivity.class));
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
        // Check for connected network
        if ( networkInfo == null || !networkInfo.isConnectedOrConnecting() ) {
            Toast.makeText(getApplicationContext(), "No Internet Connection available. Connect to the internet and restart the application.",
                    Toast.LENGTH_SHORT).show();
            createaccount_btn.setEnabled( false );
            login_btn.setEnabled( false );
        } else {
            createaccount_btn.setEnabled( true );
            login_btn.setEnabled( true );
        }

    }
}