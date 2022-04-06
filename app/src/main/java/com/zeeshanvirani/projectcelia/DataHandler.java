package com.zeeshanvirani.projectcelia;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

// Handles all modifications of stored local data
public class DataHandler {

    public static boolean DEVICE_CONNECTED = false;
    public static BluetoothSocketHandler btSocketHandler = null;
    // UUID
    //
    public static String DB_USER_ID = "user_id";
    public static String DB_DATE = "date";
    public static String DB_TIME = "time";
    public static String DB_ROAST_TYPE = "roast_type";
    public static String DB_CUP_SIZE = "cup_size";
    public static String DB_RATING = "rating";
    public static String DB_TEMPERATURE = "temperature";
    public static String DB_TARGET_SATURATION = "target_saturation";

    // Update all preferences
    public static void updateSharedPreferences( Context context ) {
        assert FirebaseAuth.getInstance().getCurrentUser() != null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        DocumentReference docRef;

        docRef = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            String name = (String) documentSnapshot.get("name");
            boolean notifyBrewingStatus = (boolean) documentSnapshot.get("notifyBrewingStatus");
            boolean notifyMaintenanceReminders = (boolean) documentSnapshot.get("notifyMaintenanceReminders");
            editor.putString("account_name", name);
            editor.putBoolean( "notifications_brewing_status", notifyBrewingStatus);
            editor.putBoolean( "notifications_maintenance_reminders", notifyMaintenanceReminders);
            editor.apply();
        });
        editor.putString("account_email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        editor.apply();
    }

}
