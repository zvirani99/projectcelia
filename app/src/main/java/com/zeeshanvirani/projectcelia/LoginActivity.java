package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private ImageButton back_btn;
    private Button login_btn;

    private TextInputEditText email_textbox;
    private TextInputEditText password_textbox;

    private TextView invalid_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        email_textbox = (TextInputEditText) findViewById(R.id.email_textbox);
        password_textbox = (TextInputEditText) findViewById(R.id.password_textbox);

        invalid_text = (TextView) findViewById(R.id.invalid_login_text);

        back_btn = (ImageButton) findViewById(R.id.back_button);
        back_btn.setOnClickListener(view -> {
            // Return to launch activity
            startActivity( new Intent(this, LaunchActivity.class) );
        });

        login_btn = (Button) findViewById(R.id.login_button);
        login_btn.setOnClickListener( view -> {
            invalid_text.setVisibility( View.GONE );
            // Check if email and password are valid
            if ( Arrays.asList( TempDatabaseClass.email ).contains(email_textbox.getText().toString())
                && Arrays.asList( TempDatabaseClass.passwords )
                    .get( Arrays.asList( TempDatabaseClass.email ).indexOf(email_textbox.getText().toString()) )
                    .equals( password_textbox.getText().toString()) ) {
                // Set isloggedin to true
                editor.putBoolean("isLoggedIn", true);
                editor.apply();
                // Transfer user to mainactivity
                startActivity( new Intent(this, MainActivity.class) );
            } else {
                // Display that email or password is invalid
                invalid_text.setVisibility( View.VISIBLE );
            }

        });


    }
}