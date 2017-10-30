package br.com.bhl.superfid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Compra;

public class FinalizarCompraActivity extends Activity {

    private String array_spinner[];
    private String subtotal;
    private TextView total;
    private Compra compra;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizarcompra);

        total = (TextView) findViewById(R.id.totalPagar);

        Intent intent = getIntent();

        subtotal = intent.getStringExtra("subtotal");//formato definido em texto com moeda
        compra = (Compra) intent.getSerializableExtra("compra");//todos os dados de compra

        total.setText(subtotal);

        array_spinner =new String[1];
        array_spinner[0]="Cartão de Crédito";
        //array_spinner[1]="Cartão de Débito";

        Spinner s = (Spinner) findViewById(R.id.spinnerFormaPagamento);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onCLickPagar(View view){

        Intent it = new Intent(this, PagamentoCreditoActivity.class);
        it.putExtra("compra", compra);
        startActivity(it);
        finish();

    }
}
