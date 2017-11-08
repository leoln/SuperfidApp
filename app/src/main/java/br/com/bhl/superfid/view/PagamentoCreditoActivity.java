package br.com.bhl.superfid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.CartaoCredito;
import br.com.bhl.superfid.model.Compra;
import br.com.bhl.superfid.model.Pagamento;
import br.com.bhl.superfid.util.WebClient;

public class PagamentoCreditoActivity extends Activity {

    private Compra compra;

    //campos da activity
    private EditText numeroCartao;
    private EditText validade;
    private EditText codSeguranca;//CVV
    private EditText nomeTitular;
    private EditText CPF;

    private static String isPagamentoOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamentocredito);

        Intent intent = getIntent();
        isPagamentoOk = "";

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

        Gson gson = new Gson();

        if(checarCampos()){
            //se nao tem campos vazios
            Pagamento pagamento = new Pagamento();
            pagamento.setCompra(compra);

            CartaoCredito cartao = new CartaoCredito();
            cartao.setCPF(CPF.getText().toString());
            cartao.setCVV(Integer.parseInt( codSeguranca.getText().toString() ));
            cartao.setNomeTitular(nomeTitular.getText().toString());
            cartao.setValidade(validade.getText().toString());
            cartao.setNumeroCartao(numeroCartao.getText().toString());

            pagamento.setCartao(cartao);

            String json = gson.toJson(pagamento);
            Log.v("JSONPAG", URLEncoder.encode(json));

            //envia para webservice de pagamento para confirmar
            PagamentoWebService pagamentoWebService = new PagamentoWebService();
            pagamentoWebService.execute(URLEncoder.encode(json));

        }else{
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
        }

    }

    private class PagamentoWebService extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {
                isPagamentoOk = WebClient.get("/pagamento/isPagamentoSuccess?json=", strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.v("STATUSPAG", isPagamentoOk);
            return isPagamentoOk;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(isPagamentoOk.equals("sucesso")) {
                compra.setIndicadorPagamento("1");

                CompraWebService compraWebService = new CompraWebService();
                compraWebService.execute(compra);

                Intent it = new Intent(PagamentoCreditoActivity.this, PagamentoSucessoActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(it);
                finish();
            } else {
                Toast.makeText(PagamentoCreditoActivity.this, "Pagamento não realizado por falta de crédito.", Toast.LENGTH_LONG).show();
                Intent it = new Intent(PagamentoCreditoActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        }
    }

    private static class CompraWebService extends AsyncTask<Compra, Void, String> {

        @Override
        protected String doInBackground(Compra... compras) {

            Gson gson = new Gson();

            try {
                Log.v("COMPRA", gson.toJson(compras[0]));
                WebClient.post("/compra/cadastrar", gson.toJson(compras[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}