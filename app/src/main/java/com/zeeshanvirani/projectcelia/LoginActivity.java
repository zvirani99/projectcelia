package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    private ImageButton back_btn;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        back_btn = (ImageButton) findViewById(R.id.back_button);
        back_btn.setOnClickListener(view -> {
            // Return to launch activity
            startActivity( new Intent(this, LaunchActivity.class) );
        });

        login_btn = (Button) findViewById(R.id.login_button);
        login_btn.setOnClickListener( view -> {
            // Check if email and password are valid
            // Set isloggedin to true
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
            // Transfer user to mainactivity
            startActivity( new Intent(this, MainActivity.class) );
        });


    }
}