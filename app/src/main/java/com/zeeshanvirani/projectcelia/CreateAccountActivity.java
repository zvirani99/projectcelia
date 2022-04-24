package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "ProjectCelia:CreateAccountActivity";

    private TextInputEditText name_textbox;
    private TextInputEditText email_textbox;
    private TextInputEditText password_textbox;
    private TextInputEditText confirmpassword_textbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize views
        name_textbox = findViewById(R.id.name_textbox);
        email_textbox = findViewById(R.id.email_textbox);
        password_textbox = findViewById(R.id.password_textbox);
        confirmpassword_textbox = findViewById(R.id.confirmpassword_textbox);

        ImageButton back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(view -> {
            // Return to launch activity
            onBackPressed();
        });

        Button createaccount_btn = findViewById(R.id.createaccount_button);
        createaccount_btn.setOnClickListener(view -> {

            // Verify if fields are properly filled in
            if ( name_textbox.getText() == null
                    || email_textbox.getText() == null
                    || password_textbox.getText() == null
                    || confirmpassword_textbox.getText() == null ) { // Text boxes are null

                // Display error message and have user retry
                Toast.makeText(getApplicationContext(), "Fields cannot be null.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if ( name_textbox.getText().toString().isEmpty()
                    || email_textbox.getText().toString().isEmpty()
                    || password_textbox.getText().toString().isEmpty()
                    || confirmpassword_textbox.getText().toString().isEmpty() ) { // Text boxes are empty

                // Display error message and have user retry
                Toast.makeText(getApplicationContext(), "Fields cannot be empty.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if ( !isValidEmail( email_textbox.getText().toString() ) ) { // Invalid email format
                Toast.makeText(getApplicationContext(), "Please enter a valid email address.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Check if passwords match and report if they do not
            if ( !password_textbox.getText().toString().equals( confirmpassword_textbox.getText().toString() ) ) {
                Toast.makeText(getApplicationContext(), "Passwords do not match.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Check if email exists
            FirebaseAuth.getInstance().fetchSignInMethodsForEmail( email_textbox.getText().toString() ).addOnCompleteListener( task -> {
                Log.d(TAG, "Gathered " + task.getResult().getSignInMethods().size() + " existing logins for " + email_textbox.getText().toString() );

                if ( task.getResult().getSignInMethods().size() == 0 ) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_textbox.getText().toString(), password_textbox.getText().toString())
                            .addOnCompleteListener(this, task2 -> {
                                assert FirebaseAuth.getInstance().getCurrentUser() != null;
                                if (task2.isSuccessful()) { // Account creation success

                                    // Data to store in database
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("name", name_textbox.getText().toString());
                                    data.put("notifyBrewingStatus", true );
                                    data.put("notifyMaintenanceReminders", true );
                                    data.put("next_target_temperature", "200");
                                    data.put("next_target_saturation", "50");

                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .set(data, SetOptions.merge());

                                    DataHandler.updateSharedPreferences( getApplicationContext() );

                                    startActivity( new Intent(getApplicationContext(), MainActivity.class) );
                                    finishAffinity();
                                } else { // Account creation failed
                                    Toast.makeText(getApplicationContext(), "Account creation failed. Try again later.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Email already exists. Go back to log in or use another email.",
                            Toast.LENGTH_SHORT).show();
                }
            });

        });

    }

    // Determines if a provided string (email) is a valid email
    public static boolean isValidEmail( String email ) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}