package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.UUID;

public class BrewingProcess extends AppCompatActivity {

    public static final String TAG = "ProjectCelia:BrewingProcess";

    private BluetoothAdapter btAdapter;
    private BluetoothSocket btSocket;

    public MyBroadcastReceiver myReceiver;

    TextView brewing_text;
    MaterialButton returnhome_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewing_process);

        brewing_text = findViewById(R.id.brewing_text);
        returnhome_btn = findViewById(R.id.brewing_gohome);

        returnhome_btn.setOnClickListener(view -> finish());

        myReceiver = new MyBroadcastReceiver(this);
        // Setup Bluetooth Connection
        int CONSTANT = 0;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CONSTANT);

        // Get master database and gather information
        // Connect to Coffee Maker using Bluetooth
        if (!DataHandler.DEVICE_CONNECTED) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            myReceiver.register(this, filter); // register
            startPairing();
        }

        // Waits until device is connected, then beginBrewing is called from establishHandler()

        // Demo
//        new Thread(() -> {
//            System.out.println("Thread Running");
//            try {
//                ChangeText( "Heating Water." );
//                Thread.sleep(500);
//                ChangeText( "Heating Water.." );
//                Thread.sleep(500);
//                ChangeText( "Heating Water..." );
//                Thread.sleep(500);
//                ChangeText( "Heating Water." );
//                Thread.sleep(500);
//                ChangeText( "Heating Water.." );
//                Thread.sleep(500);
//                ChangeText( "Pouring Water" );
//                Thread.sleep(500);
//                ChangeText( "Pouring Water." );
//                Thread.sleep(500);
//                ChangeText( "Pouring Water.." );
//                Thread.sleep(500);
//                ChangeText( "Pouring Water..." );
//                Thread.sleep(500);
//                ChangeText( "Pouring Water." );
//                Thread.sleep(500);
//                ChangeText( "Your Coffee is Ready!" );
//                runOnUiThread(() -> returnhome_btn.setVisibility( View.VISIBLE ));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

    }

    public void statusUpdate( String newStatus ) {
        brewing_text.setText( newStatus );
        // Wait for confirmation
        // Read in updates
        // End brewing when complete
        if ( newStatus.equals("OUT_OF_WATER") ) {
            return;
        } else if ( newStatus.equals("UNKNOWN_ERROR") ) {
            return;
        } else if ( newStatus.equals("STARTED_HEATING") ) {
            return;
        } else if ( newStatus.equals("STARTED_POURING") ) {
            return;
        } else if ( newStatus.equals("BREWING_COMPLETE") ) {
            // DISCONNECT BLUETOOTH DEVICE
            myReceiver.unregister(this); // register
            DataHandler.btSocketHandler.closeSocket();
            DataHandler.btSocketHandler = null;
            returnhome_btn.setVisibility( View.VISIBLE );
        }

    }

    private void startPairing() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) return;

        try {
            if (!btAdapter.isEnabled()) {
                Log.d(TAG, "Enabled Bluetooth Adapter");
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(turnOn);
            }
            if ( btAdapter.isDiscovering() ) btAdapter.cancelDiscovery();
            System.out.println(btAdapter.startDiscovery());
        } catch (SecurityException e ) {
            e.printStackTrace();
        }
    }

    public void establishHandler( BluetoothDevice device ) {
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            btAdapter.cancelDiscovery();
            Log.d(TAG, "Found a device. Attempting to connect.");
            btSocket = device.createInsecureRfcommSocketToServiceRecord( DataHandler.MY_UUID );
            DataHandler.btSocketHandler = new BluetoothSocketHandler( this, btSocket );
        } catch (SecurityException | IOException e) {
            Log.d(TAG, "Error connecting to the device.");
        }
    }
}

class MyBroadcastReceiver extends BroadcastReceiver {

    public boolean isRegistered;
    private BrewingProcess activity;

    private boolean deviceFound = false;

    public MyBroadcastReceiver(BrewingProcess activity) {
        this.activity = activity;
    }

    public Intent register(Context context, IntentFilter filter) {
        try {
            Log.d(BrewingProcess.TAG, "Registered Receiver Adapter");
            return !isRegistered
                    ? context.registerReceiver(this, filter)
                    : null;
        } finally {
            isRegistered = true;
        }
    }
    public boolean unregister(Context context) {
        Log.d(BrewingProcess.TAG, "Unregistered Receiver Adapter");
        return isRegistered
                && unregisterInternal(context);
    }

    private boolean unregisterInternal(Context context) {
        context.unregisterReceiver(this);
        isRegistered = false;
        return true;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            //discovery starts, we can show progress dialog or perform other tasks
            Log.d(BrewingProcess.TAG, "Started Bluetooth Discovery");
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            //discovery finishes, dismiss progress dialog
            if ( !deviceFound ) {
                Log.d(BrewingProcess.TAG, "Device not found.");
                activity.finish();
            }
            Log.d(BrewingProcess.TAG, "Discovery Completed");
        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            //bluetooth device found
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if ( device.getAddress().equals( DataHandler.DEVICE_MACADDR ) ) {
                deviceFound = true;
                activity.establishHandler(device);
            }

        }
    }
}