package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    private Button createaccount_btn;
    private Button login_btn;
    private Button demomode_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Check if user is already logged in and switch to MainActivity
        // Otherwise, display launch page
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if ( sharedPreferences.getBoolean("isLoggedIn", false) ) {
            Intent newActivity = new Intent(this, MainActivity.class);
            startActivity(newActivity);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        createaccount_btn = findViewById(R.id.button_createaccount);
        login_btn = findViewById(R.id.button_login);
        login_btn.setOnClickListener(view -> {
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
        });

        demomode_btn = findViewById(R.id.button_demomode);
        demomode_btn.setOnClickListener(view -> {
            Intent newActivity = new Intent(this, MainActivity.class);
            startActivity(newActivity);
        });

    }
}