package br.com.bhl.superfid.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.controller.ComprasAdapter;
import br.com.bhl.superfid.controller.DividerItemDecoration;
import br.com.bhl.superfid.model.Carrinho;
import br.com.bhl.superfid.model.ItemCarrinho;
import br.com.bhl.superfid.model.Produto;
import br.com.bhl.superfid.service.BluetoothDataService;
import br.com.bhl.superfid.util.WebClient;

public class ComprasActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;

    private static Carrinho carrinho;

    private static TextView subTotal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        subTotal = (TextView) findViewById(R.id.subTotal);

        carrinho = new Carrinho();

        //inicializando a list view de produtos
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(new ComprasAdapter(this.carrinho.getListaCarrinho(), this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        //filtros bluetooth
        IntentFilter filter4 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mBroadcastReceiver2, filter4);
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver2, filter1);

    }

    public static void addProduto(String codigoRecebido) {
        GetJson getJson = new GetJson();
        getJson.execute(codigoRecebido);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(ComprasActivity.this, BluetoothDataService.class));
        unregisterReceiver(mBroadcastReceiver2);
        finish();
    }

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

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

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                Toast.makeText(getApplicationContext(), "Carrinho Desconectado", Toast.LENGTH_SHORT).show();

                onDestroy();
                Intent dialogIntent = new Intent(ComprasActivity.this, MainActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(dialogIntent);
            }
        }
    };

    private static class GetJson extends AsyncTask<String, Void, Produto> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Produto doInBackground(String... strings) {
            Produto produto = null;
            Gson gson = new Gson();
            String json = "";
            WebClient webClient = new WebClient();

            try {
                json = webClient.get("/produto/parseJson?rfid=", strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            produto = gson.fromJson(json, Produto.class);

            return produto;
        }

        @Override
        protected void onPostExecute(Produto produto) {
            super.onPostExecute(produto);

            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setCarrinho(carrinho);
            itemCarrinho.setProduto(produto);

            carrinho.setListaCarrinho( itemCarrinho );

            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

            //String subTotalFormated = String.format("%.2f", carrinho.getSubtotal());

            NumberFormat formatarSubtotal = NumberFormat.getCurrencyInstance(new Locale("pt" ,"BR"));
            subTotal.setText("R$" + formatarSubtotal.format(carrinho.getSubtotal()));
        }
    }
}
