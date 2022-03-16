package com.zeeshanvirani.projectcelia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.google.firebase.auth.FirebaseAuth;

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
        emailPreference.setOnBindEditTextListener( new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS );
            }
        });

        // Change password has a second textbox for confirmation and checks if both fields are same

        // Device information brings up alertdialog with device information

        Preference visitwebsite_preference = findPreference("visitwebsite");
        visitwebsite_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.zeeshanvirani.com/")); // CREATE ONE PAGE SITE FOR FAQ AND CONTACT INFO
                startActivity(intent);
                return true;
            }
        });

        Preference signout_preference = findPreference( "signout" );
        signout_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent( getActivity().getApplicationContext(), LaunchActivity.class ) );
                return true;
            }
        });
    }

}