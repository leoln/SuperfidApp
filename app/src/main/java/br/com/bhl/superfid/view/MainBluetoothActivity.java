package br.com.bhl.superfid.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;
import br.com.bhl.superfid.R;
import br.com.bhl.superfid.service.BluetoothDataService;

public class MainBluetoothActivity extends Activity {

    public static final int ENABLE_BLUETOOTH = 1;
    private String qrResult;

    public TextView status;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bluetooth);

        status = findViewById(R.id.statusLabel);

        status.setText("Inicializando...");

        //Pega a string enviada da Activity Principal, faz o split e separa nas variaveis
        Intent intent = getIntent();

        qrResult = intent.getStringExtra("qrResult");

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            //statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
            status.setText("Bluetooth não está funcionando.");
            finish();
            onDestroy();
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
                startService(new Intent(this, BluetoothDataService.class).putExtra("qrResult", qrResult));
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
                startService(new Intent(this, BluetoothDataService.class).putExtra("qrResult", qrResult));

            } else {
                //statusMessage.setText("Bluetooth não ativado :(");
                status.setText("Erro ao conectar");
                Toast.makeText(getApplicationContext(), "Não foi possível ativar o BT", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, BluetoothDataService.class));
        super.onDestroy();
    }
}
