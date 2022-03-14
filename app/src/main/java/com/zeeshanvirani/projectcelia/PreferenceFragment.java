package com.zeeshanvirani.projectcelia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        EditTextPreference namePreference = findPreference("account_name");
        assert namePreference != null;
        namePreference.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>() {
            @Override
            public CharSequence provideSummary(@NonNull EditTextPreference preference) {
                return preference.getText();
            }
        });

        EditTextPreference emailPreference = findPreference("account_email");
        assert emailPreference != null;
        emailPreference.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>() {
            @Override
            public CharSequence provideSummary(@NonNull EditTextPreference preference) {
                return preference.getText();
            }
        });

        // Change password has a second textbox for confirmation and checks if both fields are same

        // Add on notifications switch being updated to display a snackbar popup

        // Device information brings up alertdialog with device information

        // Frequently Asked Questions takes you to a website or something

        // Contact us provides pop up with contact information
    }
}