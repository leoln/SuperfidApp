package br.com.bhl.superfid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import br.com.bhl.superfid.R;

/**
 * Created by hericlespontes on 30/10/2017.
 */

public class PagamentoSucessoActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamentosucesso);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // não chame o super desse método
        // isso bloqueia o botao voltar
    }

    public void onClickFecharPagamentoSucesso(View view){
        Intent it = new Intent(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(it);
        finish();
    }
}
