package com.zeeshanvirani.projectcelia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        EditTextPreference namePreference = findPreference("account_name");
        assert namePreference != null;
        namePreference.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>)
                EditTextPreference::getText
        );

        EditTextPreference emailPreference = findPreference("account_email");
        assert emailPreference != null;
        emailPreference.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>)
                EditTextPreference::getText
        );

        emailPreference.setOnBindEditTextListener(editText ->
                editText.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        );

        // Change password has a second textbox for confirmation and checks if both fields are same

        // Device information brings up alertdialog with device information

        Preference visitwebsite_preference = findPreference("visitwebsite");
        assert visitwebsite_preference != null;
        visitwebsite_preference.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.zeeshanvirani.com/")); // CREATE ONE PAGE SITE FOR FAQ AND CONTACT INFO
            startActivity(intent);
            return true;
        });

        Preference signout_preference = findPreference( "signout" );
        assert signout_preference != null;
        signout_preference.setOnPreferenceClickListener(preference -> {
            FirebaseAuth.getInstance().signOut();
            assert getActivity() != null;
            startActivity( new Intent( getActivity().getApplicationContext(), LaunchActivity.class ) );
            getActivity().finishAffinity();
            return true;
        });
    }

}