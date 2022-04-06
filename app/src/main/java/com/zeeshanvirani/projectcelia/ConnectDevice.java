package com.zeeshanvirani.projectcelia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ConnectDevice extends Fragment {

    private final String MY_MACADDR = "B8:27:EB:B6:98:20";
    private final UUID MY_UUID = UUID.fromString("b3f75a8f-fa4b-4dbc-8e79-51a486a30fa9");

    private BluetoothAdapter btAdapter;
    private BluetoothSocket btSocket;
    private BluetoothDevice btDevice;

    public ConnectDevice() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect_device, container, false);

        TextView connection_status_text = view.findViewById(R.id.deviceconnecting_status);

//        int CONSTANT = 0;
//        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CONSTANT);
//
//        if ( !DataHandler.DEVICE_CONNECTED ) startPairing();
//        else sendDataToDevice();

        connection_status_text.setText( DataHandler.btSocketHandler != null ?
                "Socket Connected" : "Not Connected" );

        Button sendmsg = view.findViewById(R.id.deviceconnecting_sendmsg);
        sendmsg.setOnClickListener( view1 -> {
            sendDataToDevice();
        });

        return view;
    }

    @Override
    public void onDestroy() {
        //requireActivity().unregisterReceiver(receiver);
        //DataHandler.btSocketHandler.closeSocket();
        super.onDestroy();
    }

    private void sendDataToDevice() {
        DataHandler.btSocketHandler.sendMessage("on");
    }

}