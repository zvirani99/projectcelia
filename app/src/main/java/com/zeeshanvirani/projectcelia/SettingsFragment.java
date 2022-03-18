package com.zeeshanvirani.projectcelia;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    View view;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getActivity() != null;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, new PreferenceFragment())
                .commit();

        // Set preference change listener
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        sp.registerOnSharedPreferenceChangeListener(this);

        return view;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        assert getActivity() != null;
        assert FirebaseAuth.getInstance().getCurrentUser() != null;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        Map<String, Object> data = new HashMap<>();
        switch (s) {
            case "notifications_brewing_status":
                if ( sp.getBoolean( "notifications_brewing_status", true) ) {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will be notified about the status of your brews.", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will no longer be notified about the status of your brews.", Snackbar.LENGTH_SHORT)
                            .show();
                }

                data.put("notifyBrewingStatus", sp.getBoolean( "notifications_brewing_status", true) );
                FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .set(data, SetOptions.merge());
                break;

            case "notifications_maintenance_reminders":
                if ( sp.getBoolean( "notifications_maintenance_reminders", true) ) {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will be notified about maintenance items.", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will no longer be notified about maintenance items.", Snackbar.LENGTH_SHORT)
                            .show();
                }

                data.put("notifyMaintenanceReminders", sp.getBoolean( "notifications_maintenance_reminders", true) );
                FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .set(data, SetOptions.merge());
                break;

            case "notifications_darklight_mode":
                if ( sp.getBoolean( "notifications_darklight_mode", true) ) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                break;

            case "account_name":
                data.put("name", sp.getString( "account_name", "" ));

                FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .set(data, SetOptions.merge());
                break;

            case "account_email":
                String newEmail = sp.getString( "account_email", "" );
                FirebaseAuth.getInstance().getCurrentUser().updateEmail( newEmail );
                break;

        }
    }
}