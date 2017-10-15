package br.com.bhl.superfid.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import br.com.bhl.superfid.view.ComprasActivity;
import br.com.bhl.superfid.view.MainActivity;
import br.com.bhl.superfid.view.MainBluetoothActivity;

public class BluetoothDataService extends Service {

    private String codigoRecebido = "";
    final int handlerState = 0;                        //used to identify handler message
    Handler bluetoothIn;
    private BluetoothAdapter btAdapter = null;

    private ConnectingThread mConnectingThread;
    private ConnectedThread mConnectedThread;

    private boolean stopThread;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String for MAC address
    private static String MAC_ADDRESS = "";
    private static String PWD = "";

    private StringBuilder recDataString = new StringBuilder();

    public void startCompras() {
        Intent dialogIntent = new Intent(this, ComprasActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    public BluetoothDataService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BT SERVICE", "SERVICE CREATED");
        stopThread = false;

        //define os filtros para o broadcast do BT

        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        registerReceiver(mBroadcastReceiver1, filter2);

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mBroadcastReceiver1, filter3);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //pega dados de conexao
        String qrResult = intent.getStringExtra("qrResult");

        if(!qrResult.isEmpty()){
            String[] textoSeparado = qrResult.split(";");
            MAC_ADDRESS = textoSeparado[0];
            PWD = textoSeparado[2];
        }else{
            onDestroy();
        }


        Log.d("BT SERVICE", "SERVICE STARTED");

        bluetoothIn = new Handler() {

            public void handleMessage(android.os.Message msg) {
                Log.d("DEBUG", "handleMessage");
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);
                    Log.d("RECORDED", recDataString.toString());
                    // Do stuff here with your data, like adding it to the database

                    codigoRecebido = codigoRecebido + recDataString;
                    if (codigoRecebido.equals("@")) {
                        mConnectedThread.write("1");
                    } else if (codigoRecebido.contains("#")) {
                        codigoRecebido = codigoRecebido.replace("@", "");
                    }

                    //Se receber o terminador $, insere o produto e zera a variavel temporaria

                    if (codigoRecebido.contains("$")) {
                        codigoRecebido = codigoRecebido.replace("#", "");
                        codigoRecebido = codigoRecebido.replace("$", "");
                        codigoRecebido = codigoRecebido.replace(" ", "");
                        codigoRecebido = codigoRecebido.replace("\n", "");

                        //adicionar produto
                        ComprasActivity.addProduto(codigoRecebido);

                        codigoRecebido = "";

                    }
                }
                recDataString.delete(0, recDataString.length());                    //clear all string data
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bluetoothIn.removeCallbacksAndMessages(null);
        stopThread = true;
        unregisterReceiver(mBroadcastReceiver1);
        if (mConnectedThread != null) {
            mConnectedThread.closeStreams();
        }
        if (mConnectingThread != null) {
            mConnectingThread.closeSocket();
        }
        Log.d("SERVICE", "onDestroy");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (btAdapter == null) {
            Log.d("BT SERVICE", "BLUETOOTH NOT SUPPORTED BY DEVICE, STOPPING SERVICE");
            stopSelf();
        } else {
            if (btAdapter.isEnabled()) {
                Log.d("DEBUG BT", "BT ENABLED! BT ADDRESS : " + btAdapter.getAddress() + " , BT NAME : " + btAdapter.getName());
                try {
                    BluetoothDevice device = btAdapter.getRemoteDevice(MAC_ADDRESS);
                    Log.d("DEBUG BT", "ATTEMPTING TO CONNECT TO REMOTE DEVICE : " + MAC_ADDRESS);
                    mConnectingThread = new ConnectingThread(device);
                    mConnectingThread.start();
                } catch (IllegalArgumentException e) {
                    Log.d("DEBUG BT", "PROBLEM WITH MAC ADDRESS : " + e.toString());
                    Log.d("BT SEVICE", "ILLEGAL MAC ADDRESS, STOPPING SERVICE");
                    stopSelf();
                }
            } else {
                Log.d("BT SERVICE", "BLUETOOTH NOT ON, STOPPING SERVICE");
                stopSelf();
            }
        }
    }

    // New Class for Connecting Thread
    private class ConnectingThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectingThread(BluetoothDevice device) {
            Log.d("DEBUG BT", "IN CONNECTING THREAD");
            mmDevice = device;
            BluetoothSocket temp = null;
            Log.d("DEBUG BT", "MAC ADDRESS : " + MAC_ADDRESS);
            Log.d("DEBUG BT", "BT UUID : " + BTMODULEUUID);
            try {
                temp = mmDevice.createRfcommSocketToServiceRecord(BTMODULEUUID);
                Log.d("DEBUG BT", "SOCKET CREATED : " + temp.toString());
            } catch (IOException e) {
                Log.d("DEBUG BT", "SOCKET CREATION FAILED :" + e.toString());
                Log.d("BT SERVICE", "SOCKET CREATION FAILED, STOPPING SERVICE");
                stopSelf();
            }
            mmSocket = temp;
        }

        @Override
        public void run() {
            super.run();
            Log.d("DEBUG BT", "IN CONNECTING THREAD RUN");
            // Establish the Bluetooth socket connection.
            // Cancelling discovery as it may slow down connection
            btAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
                Log.d("DEBUG BT", "BT SOCKET CONNECTED");
                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
                Log.d("DEBUG BT", "CONNECTED THREAD STARTED");
                //I send a character when resuming.beginning transmission to check device is connected
                //If it is not an exception will be thrown in the write method and finish() will be called
                mConnectedThread.write("1");
            } catch (IOException e) {
                try {
                    Log.d("DEBUG BT", "SOCKET CONNECTION FAILED : " + e.toString());
                    Log.d("BT SERVICE", "SOCKET CONNECTION FAILED, STOPPING SERVICE");
                    mmSocket.close();
                    stopSelf();
                } catch (IOException e2) {
                    Log.d("DEBUG BT", "SOCKET CLOSING FAILED :" + e2.toString());
                    Log.d("BT SERVICE", "SOCKET CLOSING FAILED, STOPPING SERVICE");
                    stopSelf();
                    //insert code to deal with this
                }
            } catch (IllegalStateException e) {
                Log.d("DEBUG BT", "CONNECTED THREAD START FAILED : " + e.toString());
                Log.d("BT SERVICE", "CONNECTED THREAD START FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }

        public void closeSocket() {
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                mmSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
                Log.d("DEBUG BT", e2.toString());
                Log.d("BT SERVICE", "SOCKET CLOSING FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }
    }

    // New Class for Connected Thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            Log.d("DEBUG BT", "IN CONNECTED THREAD");
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.d("DEBUG BT", e.toString());
                Log.d("BT SERVICE", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                stopSelf();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.d("DEBUG BT", "IN CONNECTED THREAD RUN");
            byte[] buffer = new byte[256];
            int bytes;

            //Envia caractere de confirmacao para o hardware
            write("1");

            // Keep looping to listen for received messages
            while (true && !stopThread) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    Log.d("DEBUG BT PART", "CONNECTED THREAD " + readMessage);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    Log.d("DEBUG BT", e.toString());
                    Log.d("BT SERVICE", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                    stopSelf();
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Log.d("DEBUG BT", "UNABLE TO READ/WRITE " + e.toString());
                Log.d("BT SERVICE", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                stopSelf();
            }
        }

        public void closeStreams() {
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                mmInStream.close();
                mmOutStream.close();
            } catch (IOException e2) {
                //insert code to deal with this
                Log.d("DEBUG BT", e2.toString());
                Log.d("BT SERVICE", "STREAM CLOSING FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                //Ativa activity compras

                //MainBluetoothActivity.status.setText("Pronto!");
                startCompras();

            } else if (intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                try {
                    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice mBluetoothDevice = btAdapter.getRemoteDevice(MAC_ADDRESS);
                    byte[] pin = (byte[]) BluetoothDevice.class.getMethod("convertPinToBytes", String.class).invoke(BluetoothDevice.class, PWD);
                    Method m = mBluetoothDevice.getClass().getMethod("setPin", byte[].class);
                    m.invoke(mBluetoothDevice, pin);
                    mBluetoothDevice.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(mBluetoothDevice, true);
                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }
    };
}
