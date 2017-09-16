package br.com.bhl.superfid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.bhl.superfid.bluetooth.ConnectionThread;
import br.com.bhl.superfid.bluetooth.DiscoveredDevices;
import br.com.bhl.superfid.bluetooth.PairedDevices;
import br.com.bhl.superfid.model.Produto;

/**
 * Created by hericlespontes on 16/07/17.
 */

public class MainBluetoothActivity extends AppCompatActivity {
    public static final int ENABLE_BLUETOOTH = 1;
    public static final int SELECT_PAIRED_DEVICE = 2;
    public static final int SELECT_DISCOVERED_DEVICE = 3;
    public static TextView statusMessage;
    public static TextView textSpace;
    public static RecyclerView recyclerView;
    public static List<Produto> produtos = new ArrayList<>();
    public static String codigoRecebido = "";

    private ConnectionThread connect;

    private String macAddress, ssId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bluetooth);

        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver1, filter1);

        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        registerReceiver(mBroadcastReceiver1, filter2);

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mBroadcastReceiver1, filter3);

        statusMessage = (TextView) findViewById(R.id.statusMessage);
        textSpace = (TextView) findViewById(R.id.textSpace);

        //Pega a string enviada da Activity Principal, faz o split e separa nas variaveis
        Intent intent = getIntent();

        String qrResult = intent.getStringExtra("qrResult");

        String[] textoSeparado = qrResult.split(";");

        macAddress = textoSeparado[0];
        ssId = textoSeparado[1];
        password = textoSeparado[2];

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
        } else {
            statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
            statusMessage.setText("Solicitando ativação do Bluetooth...");
        } else {
            statusMessage.setText("Bluetooth já ativado :)");
        }


        //conecta no carrinho
        connect = new ConnectionThread(macAddress, password);
        connect.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                statusMessage.setText("Bluetooth ativado :D");
            } else {
                statusMessage.setText("Bluetooth não ativado :(");
            }
        } else if (requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {
            if (resultCode == RESULT_OK) {
                statusMessage.setText("Você selecionou " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress"));

                //connect = new ConnectionThread(data.getStringExtra("btDevAddress"));
                //connect.start();
            } else {
                statusMessage.setText("Nenhum dispositivo selecionado :(");
            }
        }
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N")) {
                statusMessage.setText("Ocorreu um erro durante a conexão D:");
            } else if (dataString.equals("---S")) {
                statusMessage.setText("Conectado :D");

            } else {
                //concatena dados recebidos pelo buffer na variavel temporaria
                codigoRecebido = codigoRecebido + new String(data);
                //Se receber o terminador $, insere o produto e zera a variavel temporaria

                //if(dataString.contains("$")){
                codigoRecebido = codigoRecebido.replace("$", "");
                produtos.add(new Produto(codigoRecebido, 4.70, "C", "23/10/2018", "L4052", 1.0));
                recyclerView.getAdapter().notifyDataSetChanged();
                codigoRecebido = "";
                //}


                //textSpace.setText(textSpace.getText() + new String(data));


            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:

                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:

                        break;
                    case BluetoothAdapter.STATE_ON:

                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:

                        break;
                }

            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                //Do something if connected
                //Toast.makeText(getApplicationContext(), "BT Connected", Toast.LENGTH_SHORT).show();

                setContentView(R.layout.activity_compras);
                recyclerView = (RecyclerView) findViewById(R.id.recycler);

                recyclerView.addItemDecoration(new DividerItemDecoration(MainBluetoothActivity.this));

                recyclerView.setAdapter(new ComprasAdapter(MainBluetoothActivity.produtos, MainBluetoothActivity.this));

                RecyclerView.LayoutManager layout = new LinearLayoutManager(MainBluetoothActivity.this, LinearLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(layout);

            } else if (intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                try {
                    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice mBluetoothDevice = btAdapter.getRemoteDevice(macAddress);
                    byte[] pin = (byte[]) BluetoothDevice.class.getMethod("convertPinToBytes", String.class).invoke(BluetoothDevice.class, password);
                    Method m = mBluetoothDevice.getClass().getMethod("setPin", byte[].class);
                    m.invoke(mBluetoothDevice, pin);
                    mBluetoothDevice.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(mBluetoothDevice, true);
                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }
    };

    public void searchPairedDevices(View view) {

        Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
    }

    public void discoverDevices(View view) {

        Intent searchPairedDevicesIntent = new Intent(this, DiscoveredDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_DISCOVERED_DEVICE);
    }

    public void enableVisibility(View view) {

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        startActivity(discoverableIntent);
    }

    public void waitConnection(View view) {

        connect = new ConnectionThread();
        connect.start();
    }

    public void sendMessage(View view) {

        EditText messageBox = (EditText) findViewById(R.id.editText_MessageBox);
        String messageBoxString = messageBox.getText().toString();
        byte[] data = messageBoxString.getBytes();
        connect.write(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mBroadcastReceiver1);
    }
}
