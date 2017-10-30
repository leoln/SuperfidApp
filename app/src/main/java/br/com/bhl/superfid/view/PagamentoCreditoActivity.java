package br.com.bhl.superfid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Compra;

/**
 * Created by hericlespontes on 30/10/2017.
 */

public class PagamentoCreditoActivity extends Activity {

    private Compra compra;

    //campos da activity
    private EditText numeroCartao;
    private EditText validade;
    private EditText codSeguranca;//CVV
    private EditText nomeTitular;
    private EditText CPF;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamentocredito);

        Intent intent = getIntent();

        compra = (Compra) intent.getSerializableExtra("compra");//todos os dados de compra

        numeroCartao = findViewById(R.id.edt_numero_cartao);
        validade = findViewById(R.id.edt_validade);
        codSeguranca = findViewById(R.id.edt_codigo_seguranca);
        nomeTitular = findViewById(R.id.edt_nome_titular);
        CPF = findViewById(R.id.edt_cpf_titular);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean checarCampos(){
        if(numeroCartao.getText().toString().isEmpty()
                || validade.getText().toString().isEmpty()
                || codSeguranca.getText().toString().isEmpty()
                || nomeTitular.getText().toString().isEmpty()
                || CPF.getText().toString().isEmpty()){

            return false;
        }else{
            return true;
        }
    }

    public void onClickPagarCredito(View view){

        if(checarCampos()){
            //se nao tem campos vazios

            //envia para webservice de pagamento para confirmar

            //se confirmou com sucesso abre a tela de confirmação

            Intent it = new Intent(this, PagamentoSucessoActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);
            finish();

        }else{
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
        }

    }
}
