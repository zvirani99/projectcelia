package com.zeeshanvirani.projectcelia;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothSocketHandler {

    private final BrewingProcess bpInstance;

    private final BluetoothSocket btSocket;

    private OutputStream outputStream;
    private InputStream inputStream;

    public BluetoothSocketHandler( BrewingProcess instance, BluetoothSocket btSocket ) {
        System.out.println("BLUETOOTHSOCKETHANDLER CREATED");
        this.bpInstance = instance;
        this.btSocket = btSocket;
        try {
            outputStream = btSocket.getOutputStream();
            inputStream = btSocket.getInputStream();
            createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createConnection() {
        try {
            System.out.println("CONNECTING BT");
            btSocket.connect();
            new InputStreamThread().start();
            DataHandler.DEVICE_CONNECTED = true;
            sendMessage("START_BREW");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            closeSocket();
        }
    }

    public BluetoothSocket getSocket() {
        return this.btSocket;
    }

    public boolean closeSocket() {
        try {
            System.out.println("CLOSING SOCKET");
            sendMessage("endConnection");
            btSocket.close();
            DataHandler.DEVICE_CONNECTED = false;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendMessage(String msgToSend ) {
        try {
            outputStream.write( msgToSend.getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class InputStreamThread extends Thread {
        public void run() {
            byte[] inBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = inputStream.read(inBuffer);
                    String readMessage = new String(inBuffer, 0, numBytes);
                    System.out.println( readMessage );
                    bpInstance.runOnUiThread(() -> bpInstance.statusUpdate(readMessage));
                } catch (IOException e) {
                    System.out.println("Input stream was disconnected" + e);
                    break;
                }
            }
        }
    }

}
