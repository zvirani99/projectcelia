package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class BrewingProcess extends AppCompatActivity {

    TextView brewing_text;
    MaterialButton returnhome_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewing_process);

        brewing_text = findViewById(R.id.brewing_text);
        returnhome_btn = findViewById(R.id.brewing_gohome);

        returnhome_btn.setOnClickListener(view -> finish());

        // Demo
        new Thread(() -> {
            System.out.println("Thread Running");
            try {
                ChangeText( "Heating Water." );
                Thread.sleep(500);
                ChangeText( "Heating Water.." );
                Thread.sleep(500);
                ChangeText( "Heating Water..." );
                Thread.sleep(500);
                ChangeText( "Heating Water." );
                Thread.sleep(500);
                ChangeText( "Heating Water.." );
                Thread.sleep(500);
                ChangeText( "Pouring Water" );
                Thread.sleep(500);
                ChangeText( "Pouring Water." );
                Thread.sleep(500);
                ChangeText( "Pouring Water.." );
                Thread.sleep(500);
                ChangeText( "Pouring Water..." );
                Thread.sleep(500);
                ChangeText( "Pouring Water." );
                Thread.sleep(500);
                ChangeText( "Your Coffee is Ready!" );
                runOnUiThread(() -> returnhome_btn.setVisibility( View.VISIBLE ));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    void ChangeText( String newText ) {
        runOnUiThread(() -> brewing_text.setText( newText ));
    }
}