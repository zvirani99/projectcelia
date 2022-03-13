package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateAccountActivity extends AppCompatActivity {

    private ImageButton back_btn;
    private Button createaccount_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        back_btn = (ImageButton) findViewById(R.id.back_button);
        back_btn.setOnClickListener(view -> {
            // Return to launch activity
            startActivity( new Intent(this, LaunchActivity.class) );
        });

        createaccount_btn = (Button) findViewById(R.id.createaccount_button);
        createaccount_btn.setOnClickListener(view -> {
            // Check is password fields match
            // Check if email exists in database
            // Add new account info to database
            // Login user and switch to mainactivity
        });

    }
}