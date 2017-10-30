package br.com.bhl.superfid.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Dispositivo;
import br.com.bhl.superfid.model.Usuario;
import br.com.bhl.superfid.service.BluetoothDataService;

public class MainBluetoothActivity extends Activity {

    public static final int ENABLE_BLUETOOTH = 1;
    private String qrResult;

    private Usuario usuario;
    private Dispositivo dispositivo;

    public TextView status;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bluetooth);

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mBroadcastReceiver1, filter3);

        status = findViewById(R.id.statusLabel);

        status.setText("Inicializando...");

        //Pega a string enviada da Activity Principal, faz o split e separa nas variaveis
        Intent intent = getIntent();

        usuario = (Usuario) intent.getSerializableExtra("usuario");

        qrResult = intent.getStringExtra("qrResult");
        String[] textoSeparado = qrResult.split(";");
        dispositivo = new Dispositivo();
        dispositivo.setMacAddress(textoSeparado[0]);
        dispositivo.setSsId(textoSeparado[1]);
        dispositivo.setPassword(textoSeparado[2]);

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            //statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
            status.setText("Bluetooth não está funcionando.");
            finish();
        } else {
            //statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");

            if (!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);

                status.setText("Ativando Bluetooth...");
                //statusMessage.setText("Solicitando ativação do Bluetooth...");
            } else {
                //statusMessage.setText("Bluetooth já ativado :)");

                status.setText("Conectando...");
                //conecta no carrinho se ja esta ativado o BT
                startService(new Intent(this, BluetoothDataService.class).putExtra("dispositivo", dispositivo));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                //statusMessage.setText("Bluetooth ativado :D");

                status.setText("Conectando...");
                //conecta no carrinho apos ativar com sucesso o BT
                startService(new Intent(this, BluetoothDataService.class).putExtra("dispositivo", dispositivo));

            } else {
                //statusMessage.setText("Bluetooth não ativado :(");
                status.setText("Erro ao conectar");
                Toast.makeText(getApplicationContext(), "Não foi possível ativar o BT", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void startCompras() {

        Intent dialogIntent = new Intent(this, ComprasActivity.class);
        dialogIntent.putExtra("usuario",usuario);
        startActivity(dialogIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                //Ativa activity compras
                startCompras();
            }
        }
    };
}
