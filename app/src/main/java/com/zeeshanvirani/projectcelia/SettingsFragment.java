package com.zeeshanvirani.projectcelia;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    View view;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, new PreferenceFragment())
                .commit();

        // Set preference change listener
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        sp.registerOnSharedPreferenceChangeListener(this);

        return view;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        switch (s) {
            case "notifications_brewing_status":
                if ( sp.getBoolean( "notifications_brewing_status", true) ) {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will be notified about the status of your brews.", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will no longer be notified about the status of your brews.", Snackbar.LENGTH_SHORT)
                            .show();
                }
                break;

            case "notifications_maintenance_reminders":
                if ( sp.getBoolean( "notifications_maintenance_reminders", true) ) {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will be notified about maintenance items.", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar.make(view.findViewById(R.id.settings_heading), "You will no longer be notified about maintenance items.", Snackbar.LENGTH_SHORT)
                            .show();
                }
                break;

        }
    }
}