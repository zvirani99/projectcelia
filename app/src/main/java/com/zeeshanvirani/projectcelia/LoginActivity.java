package com.zeeshanvirani.projectcelia;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private ImageButton back_btn;
    private Button login_btn;

    private TextInputEditText email_textbox;
    private TextInputEditText password_textbox;

    private TextView invalid_text;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();

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
            // VERIFY IF FIELDS ARE FILLED WITH PROPER INFORMATION

            mAuth.signInWithEmailAndPassword(email_textbox.getText().toString(), password_textbox.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Setup shared preferences
                            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users")
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String name = (String) documentSnapshot.get("firstName") + " " + documentSnapshot.get("lastName");
                                    editor.putString("account_name", name);
                                    editor.apply();
                                }
                            });
                            editor.putString("account_email", user.getEmail().toString());
                            editor.apply();

                            startActivity( new Intent(getApplicationContext(), MainActivity.class) );
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        });

    }

}