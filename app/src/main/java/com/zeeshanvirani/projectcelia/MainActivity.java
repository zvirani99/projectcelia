package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HistoryFragment historyFragment = new HistoryFragment();
        BrewFragment brewFragment = new BrewFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        if ( PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean( "notifications_darklight_mode", true) ) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch ( item.getItemId() ) {
                case R.id.history:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                            .replace( R.id.fragment_container, historyFragment ).commit();
                    break;
                case R.id.brew:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                            .replace( R.id.fragment_container, brewFragment ).commit();
                    break;
                case R.id.settings:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                            .replace( R.id.fragment_container, settingsFragment ).commit();
                    break;
            }

            return true;
        });
        bottomNavigationView.setSelectedItemId( R.id.brew );

    }

}
