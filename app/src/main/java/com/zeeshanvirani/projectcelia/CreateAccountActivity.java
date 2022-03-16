package com.zeeshanvirani.projectcelia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private ImageButton back_btn;
    private Button createaccount_btn;

    private TextInputEditText name_textbox;
    private TextInputEditText email_textbox;
    private TextInputEditText password_textbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        name_textbox = (TextInputEditText) findViewById(R.id.name_textbox);
        email_textbox = (TextInputEditText) findViewById(R.id.email_textbox);
        password_textbox = (TextInputEditText) findViewById(R.id.password_textbox);

        back_btn = (ImageButton) findViewById(R.id.back_button);
        back_btn.setOnClickListener(view -> {
            // Return to launch activity
            startActivity( new Intent(this, LaunchActivity.class) );
        });

        createaccount_btn = (Button) findViewById(R.id.createaccount_button);
        createaccount_btn.setOnClickListener(view -> {

            // Check if passwords match and report if they do not

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_textbox.getText().toString(), password_textbox.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            String name = name_textbox.getText().toString();
                            Map<String, Object> data = new HashMap<>();
                            data.put("firstName", name.split(" ")[0]);
                            data.put("lastName", name.split(" ")[1]);

                            FirebaseFirestore.getInstance().collection("users")
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .set(data);

                            editor.putString("account_name", name );
                            editor.putString( "account_email", user.getEmail().toString() );
                            editor.apply();

                            startActivity( new Intent(getApplicationContext(), MainActivity.class) );
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            });
        });

    }
}