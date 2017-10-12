package br.com.bhl.superfid.view;

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
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.bhl.superfid.controller.ComprasAdapter;
import br.com.bhl.superfid.controller.DividerItemDecoration;
import br.com.bhl.superfid.R;
import br.com.bhl.superfid.controller.ConnectionThread;
import br.com.bhl.superfid.model.Produto;

/**
 * Created by hericlespontes on 16/07/17.
 */

public class MainBluetoothActivity extends AppCompatActivity {

    public static final int ENABLE_BLUETOOTH = 1;
    public static RecyclerView recyclerView;
    public static List<Produto> produtos = new ArrayList<>();
    public static String codigoRecebido = "";

    private static ConnectionThread connect;
    private String macAddress, ssId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //seta activity de loading
        setContentView(R.layout.activity_main_bluetooth);
        //define os filtros para o broadcast do BT
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver1, filter1);

        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        registerReceiver(mBroadcastReceiver1, filter2);

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mBroadcastReceiver1, filter3);

        IntentFilter filter4 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBroadcastReceiver1, filter4);


        //Pega a string enviada da Activity Principal, faz o split e separa nas variaveis
        Intent intent = getIntent();

        String qrResult = intent.getStringExtra("qrResult");

        String[] textoSeparado = qrResult.split(";");

        macAddress = textoSeparado[0];
        ssId = textoSeparado[1];
        password = textoSeparado[2];

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            //statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
            finish();
            onDestroy();
        } else {
            //statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");

            if (!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);

                //statusMessage.setText("Solicitando ativação do Bluetooth...");
            } else {
                //statusMessage.setText("Bluetooth já ativado :)");

                //conecta no carrinho se ja esta ativado o BT
                connect = new ConnectionThread(macAddress, password);
                connect.start();

            }
        }

    }

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
                        //Toast.makeText(getApplicationContext(), "BT on", Toast.LENGTH_SHORT).show();

                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:

                        break;

                }

            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                //Do something if connected
                //Toast.makeText(getApplicationContext(), "BT Connected", Toast.LENGTH_SHORT).show();

                //Ativa activity compras

                setContentView(R.layout.activity_compras);

                recyclerView = (RecyclerView) findViewById(R.id.recycler);

                recyclerView.addItemDecoration(new DividerItemDecoration(MainBluetoothActivity.this));

                recyclerView.setAdapter(new ComprasAdapter(MainBluetoothActivity.produtos, MainBluetoothActivity.this));

                RecyclerView.LayoutManager layout = new LinearLayoutManager(MainBluetoothActivity.this, LinearLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(layout);

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                Toast.makeText(getApplicationContext(), "Carrinho Desconectado", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(MainBluetoothActivity.this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(it);

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

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N")) {
                //implementar erro
            } else if (dataString.equals("---S")) {

                byte[] init = "1".getBytes();
                connect.write(init);

            } else {
                //concatena dados recebidos pelo buffer na variavel temporaria
                if(dataString.equals("@")){
                    byte[] init = "1".getBytes();
                    connect.write(init);
                }

                codigoRecebido = codigoRecebido + dataString;

                if(codigoRecebido.contains("#")){
                    codigoRecebido = codigoRecebido.replace("@", "");
                }

                //Se receber o terminador $, insere o produto e zera a variavel temporaria

                if(codigoRecebido.contains("$")){
                    codigoRecebido = codigoRecebido.replace("#", "");
                    codigoRecebido = codigoRecebido.replace("$", "");
                    codigoRecebido = codigoRecebido.replace("\n","");

                    //produtos.add(new Produto(codigoRecebido, 4.70, "C", "23/10/2018", "L4052", 1.0));

                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

                    codigoRecebido = "";

                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                //statusMessage.setText("Bluetooth ativado :D");

                //conecta no carrinho apos ativar com sucesso o BT
                connect = new ConnectionThread(macAddress, password);
                connect.start();

            } else {
                //statusMessage.setText("Bluetooth não ativado :(");
                Toast.makeText(getApplicationContext(), "Não foi possível ativar o BT", Toast.LENGTH_SHORT).show();
                cancel();
            }
        }
    }

    public void cancel(){
        unregisterReceiver(mBroadcastReceiver1);
        connect.cancel();
        finish();

    }
    @Override
    protected void onDestroy() {
        this.cancel();
        super.onDestroy();
    }
}
