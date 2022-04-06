package com.zeeshanvirani.projectcelia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

        Preference changepassword_preference = findPreference("account_password");
        assert changepassword_preference != null;
        changepassword_preference.setOnPreferenceClickListener(preference -> {
            // Open dialog fragment
            DialogFragment dialog = new ChangePasswordDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(),"changepw");
            return true;
        });


        // Device information brings up alertdialog with device information
        Preference deviceinformation_preference = findPreference("device_information");
        assert deviceinformation_preference != null;
        deviceinformation_preference.setVisible( DataHandler.DEVICE_CONNECTED );
        deviceinformation_preference.setOnPreferenceClickListener(preference -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.Theme_ProjectCelia_MaterialAlertDialog);
            builder.setMessage("Device Information");
            builder.setView( R.layout.dialog_device_information );
            builder.setPositiveButton("Close", null);
            final AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        });


        Preference deviceconnect_preference = findPreference("device_connect");
        assert deviceconnect_preference != null;
        deviceconnect_preference.setTitle( DataHandler.DEVICE_CONNECTED ? "Disconnect Device" : "Connect Device" );
        deviceconnect_preference.setOnPreferenceClickListener(preference -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace( R.id.fragment_container, new ConnectDevice() ).commit();
//            DataHandler.DEVICE_CONNECTED = !DataHandler.DEVICE_CONNECTED;
//            deviceconnect_preference.setTitle( DataHandler.DEVICE_CONNECTED ? "Disconnect Device" : "Connect Device" );
//            deviceinformation_preference.setVisible( DataHandler.DEVICE_CONNECTED );
            return true;
        });


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